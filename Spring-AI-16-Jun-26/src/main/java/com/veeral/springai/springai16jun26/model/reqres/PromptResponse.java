package com.veeral.springai.springai16jun26.model.reqres;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class PromptResponse {

    private String response;
    private String provider;
    private LocalDateTime timestamp;
    private int httpStatus;

    public PromptResponse() {
    }

    public PromptResponse(String response, String provider, int httpStatus) {
        this.response = response;
        this.provider = provider;
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now(ZoneId.systemDefault());
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }
}
