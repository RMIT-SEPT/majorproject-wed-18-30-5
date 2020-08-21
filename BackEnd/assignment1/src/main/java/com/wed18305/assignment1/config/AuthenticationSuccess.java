package com.wed18305.assignment1.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wed18305.assignment1.Responses.Response;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AuthenticationSuccess implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                        HttpServletResponse response,
                                        Authentication authentication) 
                                        throws IOException, ServletException {
        User p = (User) authentication.getPrincipal();
        Response newresponse = new Response(true, p.getUsername()+" has logged in!", null, null);
        response.setContentType("application/json");
        // response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().print(newresponse.toString());
        response.getWriter().flush();
    }
    
}