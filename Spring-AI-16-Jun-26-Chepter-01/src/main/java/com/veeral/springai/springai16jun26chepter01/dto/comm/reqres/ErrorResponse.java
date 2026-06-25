package com.veeral.springai.springai16jun26chepter01.dto.comm.reqres;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class ErrorResponse {

    private int status;
    private String message;
    private String error;
    private LocalDateTime timestamp;
    private List<String> details;

    public ErrorResponse() {
    }

    public ErrorResponse(int status, String message, String error, List<String> details) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.timestamp = LocalDateTime.now(ZoneId.systemDefault());
        this.details = details;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}
