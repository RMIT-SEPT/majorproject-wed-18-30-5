/*
 * Copyright 2014-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wed18305.assignment1.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Spring Security configuration.
 *
 * @author Rob Winch
 * @author Vedran Pavic
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("SELECT username, password, 'true' AS enabled FROM USER_MODEL WHERE username=?")
				.authoritiesByUsernameQuery("SELECT username, type_id AS authority FROM USER_MODEL WHERE username=?");
	}

	@Bean
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new DefaultEncoder();
		// return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationSuccessHandler authSuccessHandler() {
   		return new AuthenticationSuccess();
	}

	@Bean
	public AuthenticationFailureHandler authFailureHandler() {
   		return new AuthenticationFailure();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().requestMatchers(PathRequest.toH2Console());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/api/user/createCustomer").permitAll()
			.antMatchers("/api/user/createEmployee").hasAuthority("1")
			.antMatchers("/api/user/createAdmin").hasAuthority("1")
			.anyRequest().authenticated()
			.and()
			.formLogin().successHandler(authSuccessHandler())
						.failureHandler(authFailureHandler())
						.permitAll()
            .and()
			.logout().permitAll()
			.deleteCookies("JSESSIONID")
			.and()
			.csrf().disable();
	}
}
