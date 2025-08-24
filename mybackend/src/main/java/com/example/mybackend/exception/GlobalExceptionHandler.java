package com.example.mybackend.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;

import com.example.mybackend.exception.ApiErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // handle not found user
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ApiErrorResponse handleUserNotFound(UserNotFoundException ex) {
        Map<String, String> errorDetails = Map.of("message", ex.getMessage());

        return new ApiErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "Not Found",
            errorDetails
        );
    }

    //handle invalid json
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiErrorResponse handleInvalidJson(HttpMessageNotReadableException ex) {
        return new ApiErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Malformed JSON request",
            Map.of("message", ex.getMostSpecificCause().getMessage())
        );
    }

}
