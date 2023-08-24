package com.example.backend.utils;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private final boolean status;
    private final T data;
    private final String message;
}
