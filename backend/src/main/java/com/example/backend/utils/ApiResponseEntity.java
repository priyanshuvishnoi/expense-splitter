package com.example.backend.utils;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponseEntity<T> extends ResponseEntity<ApiResponse<T>> {

    public ApiResponseEntity(boolean status, T body, String message, HttpStatus statusCode) {
        super(new ApiResponse<T>(status, body, message), statusCode);
    }

    public static <T> ApiResponseEntity<T> Ok(T body) {
        return new ApiResponseEntity<T>(true, body, null, HttpStatus.OK);
    }

    public static  <T> ApiResponseEntity<T> Ok(T body, String message) {
        return new ApiResponseEntity<T>(true, body, message, HttpStatus.OK);
    }

    public static  ApiResponseEntity<Null> Ok(String message) {
        return new ApiResponseEntity<Null>(true, null, message, HttpStatus.OK);
    }

    public static  ApiResponseEntity<Null> Exception(String message) {
        return new ApiResponseEntity<Null>(false, null, message, HttpStatus.BAD_REQUEST);
    }

    public static  ApiResponseEntity<Null> Exception(String message, HttpStatus code) {
        return new ApiResponseEntity<Null>(false, null, message, code);
    }

    public static  ApiResponseEntity<Null> Exception(ApiException e) {
        return new ApiResponseEntity<Null>(false, null, e.getMessage(), e.getCode());
    }
}
