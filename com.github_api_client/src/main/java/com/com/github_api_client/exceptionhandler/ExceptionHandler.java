package com.com.github_api_client.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionHandler {

    /**
     * @param responseStatusException exception to be handled
     * @return Response with ExceptionInfo as a body, contains status and exception message in json format
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<ExceptionInfo> responseStatusExceptionHandler(ResponseStatusException responseStatusException) {
        return ResponseEntity.status(responseStatusException.getStatusCode()).body(new ExceptionInfo(responseStatusException));
    }

    /**
     * Handle exception, set content-type to application/JSON to send respond in JSON format even when request is send with header Accept: application/XML
     *
     * @param httpMediaTypeNotAcceptableException exception to be handled
     * @return Response with ExceptionInfo as a body, contains status and exception message in json format
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public ResponseEntity<ExceptionInfo> httpMediaTypeNotAcceptableExceptionHandler(HttpMediaTypeNotAcceptableException httpMediaTypeNotAcceptableException) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ExceptionInfo exceptionInfo = new ExceptionInfo();
        exceptionInfo.setStatus(httpMediaTypeNotAcceptableException.getStatusCode().value());
        exceptionInfo.setMessage("The server cannot produce a response matching the request's header");
        return ResponseEntity.status(httpMediaTypeNotAcceptableException.getStatusCode()).headers(responseHeaders).body(exceptionInfo);
    }


}
