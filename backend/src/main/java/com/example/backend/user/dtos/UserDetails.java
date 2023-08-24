package com.example.backend.user.dtos;

import java.util.ArrayList;
import java.util.List;

import com.example.backend.group.dtos.GroupDetails;
import com.example.backend.group.entities.Group;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
    private Integer userId;
    private String email;
    private String username;

    @JsonIgnore
    private String passwordHash;
    private List<GroupDetails> adminGroups = new ArrayList<>();
    private List<GroupDetails> groups = new ArrayList<>();

    public UserDetails(Integer userId, String email, String username, String passwordHash) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
    }
}
