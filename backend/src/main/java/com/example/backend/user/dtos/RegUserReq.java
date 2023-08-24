package com.example.backend.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegUserReq {
    @NotNull @Email String email;
    @NotNull @Length(min = 8) String password;
    @NotNull String userName;
}
