package com.gungorefe.simpleportfolio.entity.security;

import com.gungorefe.simpleportfolio.dto.security.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Table(name = "users")
@Entity
public class User implements UserDetails {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;
    @Column(unique = true)
    private String email;
    private String password;
    @JoinColumn(name = "role_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;
    @OneToOne(mappedBy = "user")
    private PasswordResetToken passwordResetToken;

    public User(String email, Role role) {
        this.email = email;
        this.role = role;
    }

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(UUID id, String email, String password, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserDto toDto() {
        return new UserDto(
                email,
                role.getName()
        );
    }

    public static Set<UserDto> toDtoSet(Collection<User> users) {
        return Stream.ofNullable(users)
                .flatMap(Collection::stream)
                .map(User::toDto)
                .collect(Collectors.toSet());
    }
}
