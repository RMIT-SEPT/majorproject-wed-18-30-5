package com.wed18305.assignment1.config;

import org.springframework.security.crypto.password.PasswordEncoder;

public class DefaultEncoder implements PasswordEncoder{

    @Override
    public String encode(CharSequence rawPassword) {
        //No encoding for now
        String pass = (String) rawPassword;
        return pass;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String pass = (String) rawPassword;
        return pass.equals(encodedPassword);
    }
    
}