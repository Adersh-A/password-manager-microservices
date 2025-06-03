package com.macro.passwordservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Passwords")
@Data
public class PasswordEntity {

    @Id
    private UUID id;
    @Column(nullable = false)
    private String userEmail;
    @Column(nullable = false)
    private String serviceName;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
