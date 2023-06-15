package com.com.github_api_client.interceptor;

import ch.qos.logback.core.util.ContentTypeUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class Interceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accept = request.getHeader("Accept");
        if (MediaType.APPLICATION_XML_VALUE.equals(accept)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Wrong headers");
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
