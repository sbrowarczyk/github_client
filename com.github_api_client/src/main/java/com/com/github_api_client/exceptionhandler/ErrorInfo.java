package com.com.github_api_client.exceptionhandler;

import org.springframework.web.ErrorResponseException;
import org.springframework.web.server.ResponseStatusException;

/**
 * the model used to send information when an exception occurs
 */
public class ErrorInfo {


    private int status;
    private String message;

    public ErrorInfo() {
    }


    ErrorInfo(ResponseStatusException responseStatusException) {
        this.status = responseStatusException.getStatusCode().value();
        this.message = responseStatusException.getReason();
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
}
