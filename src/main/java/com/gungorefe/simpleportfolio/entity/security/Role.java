package com.gungorefe.simpleportfolio.entity.security;

import com.gungorefe.simpleportfolio.dto.security.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Table(name = "user_roles")
@Entity
public class Role implements GrantedAuthority {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private int id;
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private RoleName name;
    @OneToMany(mappedBy = "role")
    Collection<User> users;

    public Role(RoleName name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name.name();
    }
}
