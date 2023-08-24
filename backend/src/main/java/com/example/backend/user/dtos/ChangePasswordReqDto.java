package com.example.backend.user.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ChangePasswordReqDto {
    @NotNull
    private String userName;
    @NotNull
    private String oldPassword;
    @NotNull
    @Length(min = 8)
    private String newPassword;
}
