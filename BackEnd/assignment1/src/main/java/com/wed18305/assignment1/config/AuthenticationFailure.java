package com.wed18305.assignment1.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wed18305.assignment1.Responses.Response;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class AuthenticationFailure implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) 
                                        throws IOException, ServletException {
        Response newresponse = new Response(false, "ERROR!", exception.getMessage(), null);
        response.setContentType("application/json");
        // response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getWriter().print(newresponse.toString());
        response.getWriter().flush();

    }
    
}