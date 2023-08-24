package com.example.backend.group.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateGroupReqDto {
    @NotNull
    private String groupName;

    @NotNull
    private Integer userId;
}
