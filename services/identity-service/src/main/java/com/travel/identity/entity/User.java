package com.travel.identity.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length=100)
    private String username;

    @Column(nullable = false, unique = true, length=255)
    private String email;

    @Column(name="password_hash",nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private boolean enabled=true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name="user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles=new HashSet<>();

    @PrePersist
    public void prePersist(){
        createdAt=LocalDateTime.now();
        updatedAt=LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate(){
        updatedAt=LocalDateTime.now();
    }
}
