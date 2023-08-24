package com.example.backend.user.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
public class LoginReqDto {
    @NotNull
    private String userName;

    @NotNull
    private String password;
}
