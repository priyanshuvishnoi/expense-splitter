package com.example.backend.user.dtos;

import jakarta.validation.constraints.NotEmpty;

public record UpdateUserDetailsReqDto(
        @NotEmpty int userId,
        String email,
        String username
) {
}
