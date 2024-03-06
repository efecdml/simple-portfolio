package com.gungorefe.simpleportfolio.config.security;

import com.gungorefe.simpleportfolio.dto.security.SecuredEndpoint;
import com.gungorefe.simpleportfolio.service.security.JwtService;
import com.gungorefe.simpleportfolio.util.WebUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final String cookieName;
    private final Set<String> endpointsWithoutCompetentKeyword;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService, JwtService jwtService, SecurityProperties properties) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        cookieName = properties.accessTokenCookieName();
        endpointsWithoutCompetentKeyword = getEndpointsWithoutCompetentKeyword();
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final Optional<String> accessToken = WebUtils.getCookieValue(
                request,
                cookieName
        );
        final String username;

        if (accessToken.isEmpty()) {
            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }

        username = jwtService.extractUsername(accessToken.get());

        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Collection<? extends GrantedAuthority> authorities;
            UsernamePasswordAuthenticationToken authToken;
            boolean tokenValid = jwtService.isTokenValid(
                    accessToken.get(),
                    userDetails
            );

            if (tokenValid) {
                authorities = userDetails.getAuthorities();
                authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getAuthorities(),
                        authorities
                );
                WebAuthenticationDetails webAuthenticationDetails = new WebAuthenticationDetailsSource()
                        .buildDetails(request);
                authToken.setDetails(webAuthenticationDetails);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(
                request,
                response
        );
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String requestUri = request.getRequestURI();

        return isUriNotContainsCompetentKeyword(requestUri) &&
                isUriNotInSecuredEndpoints(requestUri);
    }

    private boolean isUriNotContainsCompetentKeyword(String requestUri) {
        return !requestUri.contains("competent");
    }

    private boolean isUriNotInSecuredEndpoints(String requestUri) {
        return !endpointsWithoutCompetentKeyword.contains(requestUri);
    }

    private Set<String> getEndpointsWithoutCompetentKeyword() {
        return Stream.ofNullable(SecuredEndpoint.getValues())
                .flatMap(Arrays::stream)
                .filter(e->!e.contains("competent"))
                .collect(Collectors.toSet());
    }
}
