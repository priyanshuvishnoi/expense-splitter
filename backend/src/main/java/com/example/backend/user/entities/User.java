package com.example.backend.user.entities;

import com.example.backend.group.entities.Group;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "username", unique = true)
    private String userName;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private List<Group> groups = new ArrayList<>();

    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY)
    private List<Group> adminGroups = new ArrayList<>();
}
