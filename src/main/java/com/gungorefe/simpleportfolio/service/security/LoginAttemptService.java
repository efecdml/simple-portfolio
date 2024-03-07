package com.gungorefe.simpleportfolio.service.security;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.gungorefe.simpleportfolio.config.security.SecurityProperties;
import com.gungorefe.simpleportfolio.util.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class LoginAttemptService {
    private final HttpServletRequest request;
    private final int maxAttempt;
    private final LoadingCache<String, Integer> cache;

    public LoginAttemptService(SecurityProperties securityProperties, HttpServletRequest request) {
        this.request = request;
        maxAttempt = securityProperties.maxLoginAttempt();
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(securityProperties.forbiddenLoginDuration())
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    @NonNull
                    public Integer load(@NonNull String key) throws Exception {
                        return 0;
                    }
                });
    }

    public boolean isBlocked() {
        int attempts = getAttempts(WebUtils.getIp(request));

        return attempts > maxAttempt;
    }

    public void failedLogin() {
        String ip = WebUtils.getIp(request);
        int currentAttempts = getAttempts(ip);
        int newAttempts = currentAttempts + 1;

        cache.put(ip, newAttempts);
    }

    private int getAttempts(String ip) {
        try {
            return cache.get(ip);
        } catch (ExecutionException e) {
            return 0;
        }
    }
}
