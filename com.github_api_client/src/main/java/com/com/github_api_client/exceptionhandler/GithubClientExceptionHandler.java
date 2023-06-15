package com.com.github_api_client.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GithubClientExceptionHandler {

    /**
     * @param responseStatusException exception to be handled
     * @return Response with ExceptionError as a body, contains status and exception message in JSON format
     */
    @ExceptionHandler({ResponseStatusException.class})

    public ResponseEntity<ErrorInfo> responseStatusExceptionHandler(ResponseStatusException responseStatusException) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.status(responseStatusException.getStatusCode()).headers(responseHeaders).body(new ErrorInfo(responseStatusException));
    }

}
