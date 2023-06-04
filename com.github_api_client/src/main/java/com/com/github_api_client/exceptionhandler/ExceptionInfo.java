package com.com.github_api_client.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.server.ResponseStatusException;

/**
 * the model used to send information when an exception occurs
 */
public class ExceptionInfo {

    private int status;
    @JsonProperty("Message")
    private String message;

    public ExceptionInfo() {
    }

    ExceptionInfo(ResponseStatusException responseStatusException) {
        this.status = responseStatusException.getStatusCode().value();
        this.message = responseStatusException.getReason();
    }

    int getStatus() {
        return status;
    }

    void setStatus(int status) {
        this.status = status;
    }

    String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }
}
