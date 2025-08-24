package com.example.mybackend.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ApiErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private Map<String, String> details;

    // Constructors
    public ApiErrorResponse(LocalDateTime timestamp, int status, String error, Map<String, String> details) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.details = details;
    }

    // Getters and Setters (needed for JSON serialization)
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }
}
