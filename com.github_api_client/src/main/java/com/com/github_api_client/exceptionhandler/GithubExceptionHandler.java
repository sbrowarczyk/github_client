package com.com.github_api_client.exceptionhandler;

import org.springframework.http.*;
import org.springframework.web.ErrorResponse;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GithubExceptionHandler {

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<ExceptionInfo> responseStatusExceptionHandler(ResponseStatusException responseStatusException) {
        return ResponseEntity.status(responseStatusException.getStatusCode()).body(new ExceptionInfo(responseStatusException));
    }

    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public ResponseEntity<ExceptionInfo> HttpMediaTypeNotAcceptableExceptionHandler(HttpMediaTypeNotAcceptableException httpMediaTypeNotAcceptableException) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ExceptionInfo exceptionInfo = new ExceptionInfo();
        exceptionInfo.setStatus(httpMediaTypeNotAcceptableException.getStatusCode().value());
        exceptionInfo.setMessage("Can not produce a response with given Accept headers");
        return ResponseEntity.status(httpMediaTypeNotAcceptableException.getStatusCode()).headers(responseHeaders).body(exceptionInfo);
    }


}
