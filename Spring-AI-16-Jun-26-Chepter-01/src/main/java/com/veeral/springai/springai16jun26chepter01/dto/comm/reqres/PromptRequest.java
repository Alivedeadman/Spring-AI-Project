package com.veeral.springai.springai16jun26chepter01.dto.comm.reqres;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PromptRequest {

    @NotBlank(message = "Prompt cannot be blank")
    @Size(min = 1, max = 5000, message = "Prompt must be between 1 and 5000 characters")
    private String prompt;

    public PromptRequest() {
    }

    public PromptRequest(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
