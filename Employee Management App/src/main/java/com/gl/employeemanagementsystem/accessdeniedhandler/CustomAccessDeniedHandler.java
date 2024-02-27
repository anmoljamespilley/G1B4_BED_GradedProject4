package com.gl.employeemanagementsystem.accessdeniedhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // Set the response status to 403 Forbidden
        response.setStatus(HttpStatus.FORBIDDEN.value());

        // Create a JSON response body with an error message
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("error", "Access denied");

        // Write the JSON response to the response body
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(response.getOutputStream(), responseBody);
        } catch (IOException e) {
            // Handle the IOException appropriately
            throw new ServletException("Failed to write response body", e);
        }
    }
}
