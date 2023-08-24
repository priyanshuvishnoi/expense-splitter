package com.example.backend.user.dtos;

import jakarta.validation.constraints.NotNull;

public record GetUserDetailsReqDto(
        @NotNull
        String username
) {
}
