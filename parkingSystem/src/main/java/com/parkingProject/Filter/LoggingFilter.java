package com.parkingProject.Filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        long startTime = System.currentTimeMillis();

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // Wrap the request and response
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        // Extract data before chain
        String clientIp = StringUtils.defaultIfBlank(
            wrappedRequest.getHeader("X-FORWARDED-FOR"), 
            wrappedRequest.getRemoteAddr()
        );
        String requestMethod = wrappedRequest.getMethod();
        String requestUrl = wrappedRequest.getRequestURI();

        // Proceed with filter chain
        filterChain.doFilter(wrappedRequest, wrappedResponse);

        // Read bodies after chain
        String requestBody = getRequestBody(wrappedRequest);
        int responseStatus = wrappedResponse.getStatus();
        String responseBody = getResponseBody(wrappedResponse);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Log request and response
        logger.info("Request - Method: {}, URL: {}, Client IP: {}, Body: {}", requestMethod, requestUrl, clientIp, requestBody);
        logger.info("Response - URL: {}, Status: {}, Duration: {} ms, Body: {}", requestUrl, responseStatus, duration, responseBody);

        // Write response back
        wrappedResponse.copyBodyToResponse();
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        byte[] buf = request.getContentAsByteArray();
        return (buf.length > 0) ? new String(buf, StandardCharsets.UTF_8) : "EMPTY_BODY";
    }

    private String getResponseBody(ContentCachingResponseWrapper response) throws IOException {
        byte[] buf = response.getContentAsByteArray();
        return (buf.length > 0) ? new String(buf, StandardCharsets.UTF_8) : "EMPTY_BODY";
    }
}