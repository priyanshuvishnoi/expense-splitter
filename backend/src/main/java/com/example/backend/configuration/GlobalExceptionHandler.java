package com.example.backend.configuration;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.backend.utils.ApiException;
import com.example.backend.utils.ApiResponseEntity;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ApiResponseEntity<Null> handleException(Exception e, WebRequest request) {
        return ApiResponseEntity.Exception("Some error occurred!");
    }

    @ExceptionHandler(ApiException.class)
    public ApiResponseEntity<Null> handleApiException(ApiException e, WebRequest request) {
        return ApiResponseEntity.Exception(e);
    }
}
