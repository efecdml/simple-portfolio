package com.gungorefe.simpleportfolio.entity.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Table(name = "user_password_reset_tokens")
@Entity
public class PasswordResetToken implements Serializable {
    @Serial
    private static final long serialVersionUID = -6523078901582810174L;
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;
    private LocalDateTime expirationDate;
    private boolean sent;
    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;


}
