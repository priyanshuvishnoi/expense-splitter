package com.example.backend.group.dtos;

import jakarta.validation.constraints.NotEmpty;

/**
 * AddUserToGroupReq
 */

public record AddUserToGroupReq(
        @NotEmpty int groupId,
        @NotEmpty int userId) {
}