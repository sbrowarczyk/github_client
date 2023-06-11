package com.com.github_api_client.exceptionhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GithubClientExceptionHandler {

    /**
     * @param responseStatusException exception to be handled
     * @return Response with ExceptionInfo as a body, contains status and exception message in JSON format
     */
    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<?> responseStatusExceptionHandler(ResponseStatusException responseStatusException) {
        return ResponseEntity.status(responseStatusException.getStatusCode()).body(new ExceptionInfo(responseStatusException));
    }
}
