package com.wed18305.assignment1.config;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wed18305.assignment1.Responses.Response;
import com.wed18305.assignment1.model.Entity_UserType;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AuthenticationSuccess implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        User p = (User) authentication.getPrincipal();
        Response newresponse = new Response(true, p.getUsername()+" has logged in!",null, null);
        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers",
                "Content-Type, Authorization, Content-Length, X-Requested-With");

        // determine redirect from the type of user logged in,
        // DONT add multiple auth or we have a problem here
        String authorityID = "";
        Collection<GrantedAuthority> auth = p.getAuthorities();
        for (GrantedAuthority grantedAuthority : auth) {
            authorityID = grantedAuthority.getAuthority();
        }
        if(authorityID.compareTo(String.valueOf(Entity_UserType.UserTypeID.ADMIN.id)) == 0){
            response.setHeader("Location", "http://localhost:3000/dashboardA");
        }else if(authorityID.compareTo(String.valueOf(Entity_UserType.UserTypeID.CUSTOMER.id)) == 0){
            response.setHeader("Location", "http://localhost:3000/dashboardU");
        }else if(authorityID.compareTo(String.valueOf(Entity_UserType.UserTypeID.EMPLOYEE.id)) == 0){
            response.setHeader("Location", "http://localhost:3000/dashboardE");
        }
        response.getWriter().print(newresponse.toString());
        response.getWriter().flush();
    }
    
}