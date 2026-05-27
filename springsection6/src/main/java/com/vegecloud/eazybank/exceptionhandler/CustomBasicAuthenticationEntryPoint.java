package com.vegecloud.eazybank.exceptionhandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     *  We can customise the logic for when an Authentication Exception is thrown
     *  by implementing the AuthenticationEntryPoint interface and overriding the commence() method.
     *  These customisations should be specified in .httpBasic() method of the SecurityFilterChain.
     */

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("eazybank-error-reason", "Authentication failed");
        //response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");

        // Customising json response in the response body when authentication has failed (must comment out .sendError).
        LocalDateTime currentTimeStamp = LocalDateTime.now();
        String message = (authException != null && authException.getMessage() != null) ? authException.getMessage() : "Unauthorised";
        String path = request.getRequestURI();

        String jsonResponse = String.format(
            "{\"timestamp\": \"%s\", \"status\" :%d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
            currentTimeStamp,
            HttpStatus.UNAUTHORIZED.value(),
            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
            message,
            path
        );
        response.getWriter().write(jsonResponse);
    }
}
