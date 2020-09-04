package com.wed18305.assignment1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
public class httpPublish extends HttpSessionEventPublisher {
    @Bean
	public HttpSessionEventPublisher HttpSessionEventPublisher() {
    	return new HttpSessionEventPublisher();
	}
}