package com.wed18305.assignment1.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wed18305.assignment1.Responses.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public class LogoutSuccess implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                                throws IOException, ServletException{

        Response newresponse = new Response(true, "login",null, null);
        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers",
                "Content-Type, Authorization, Content-Length, X-Requested-With");
        // response.setHeader("Location", "http://localhost:3000/login");
        response.getWriter().print(newresponse.toString());
        response.getWriter().flush();
    }

    
    
}