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
import com.wed18305.assignment1.model.Entity_UserType.UserTypeID;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Security configuration. Good info for spring security
 * https://www.marcobehler.com/guides/spring-security#_web_application_security_101
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
				.usersByUsernameQuery("SELECT username, password, 'true' AS enabled FROM ENTITY_USER WHERE username=?")
				.authoritiesByUsernameQuery("SELECT username, type_id AS authority FROM ENTITY_USER WHERE username=?");
	}

	@Bean
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
    public UsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
        UsernamePasswordAuthenticationFilter authenticationFilter
			= new UsernamePasswordAuthenticationFilter();
		authenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
		authenticationFilter.setAuthenticationSuccessHandler(new AuthenticationSuccess());
		authenticationFilter.setAuthenticationFailureHandler(new AuthenticationFailure());
		authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationFilter;
    }

	/**
	 * When using mySQL comment this out
	 */
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().requestMatchers(PathRequest.toH2Console());
	}

	/*
	 * Tip: When testing, login through Postman, so it holds all session data.
	 */

	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/api/user/createCustomer").permitAll()
			.antMatchers("/api/user/createEmployee").hasAuthority(UserTypeID.getAdmin())
			.antMatchers("/api/user/createAdmin").hasAuthority(UserTypeID.getAdmin())
			.antMatchers("/api/user/deleteUser").hasAuthority(UserTypeID.getAdmin())
			.antMatchers("/api/user/deleteCustomer").hasAuthority(UserTypeID.getCustomer())
			.antMatchers("/api/user/getEmployees").hasAnyAuthority(UserTypeID.getAdmin(),UserTypeID.getCustomer())
			.antMatchers("/api/user/getEmployeesByService").hasAuthority(UserTypeID.getCustomer())
			.antMatchers("/api/booking/createBooking").hasAnyAuthority(UserTypeID.getAdmin(), UserTypeID.getCustomer())
			.antMatchers("/api/booking/approveBooking").hasAuthority(UserTypeID.getAdmin())
			.antMatchers("/api/booking/getAdminBookings").hasAuthority(UserTypeID.getAdmin())
			.antMatchers("/api/booking/getUpcomingAdminBookings").hasAuthority(UserTypeID.getAdmin())
			.antMatchers("/api/booking/getCompletedAdminBookings").hasAuthority(UserTypeID.getAdmin())
			.antMatchers("/api/booking/getEmployeeBookings").hasAuthority(UserTypeID.getEmployee())
			.antMatchers("/api/booking/getUpcomingEmployeeBookings").hasAuthority(UserTypeID.getEmployee())
			.antMatchers("/api/booking/getCompletedEmployeeBookings").hasAuthority(UserTypeID.getEmployee())
			.antMatchers("/api/booking/getCustomerBookings").hasAuthority(UserTypeID.getCustomer())
			.antMatchers("/api/booking/getUpcomingCustomerBookings").hasAuthority(UserTypeID.getCustomer())
			.antMatchers("/api/booking/getCompletedCustomerBookings").hasAuthority(UserTypeID.getCustomer())
			.antMatchers("/api/booking/deleteBooking").hasAuthority(UserTypeID.getAdmin())
			.antMatchers("/api/service/createService").hasAuthority(UserTypeID.getAdmin())
			.antMatchers("/api/service/getServices").hasAuthority(UserTypeID.getAdmin())
			.antMatchers("/api/schedule/createSchedule").hasAuthority(UserTypeID.getAdmin())
			.antMatchers("/api/schedule/deleteSchedule").hasAuthority(UserTypeID.getAdmin())
			.anyRequest().authenticated()
            .and()
			.logout().permitAll()
			.logoutSuccessHandler(new LogoutSuccess())
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.and()
			.csrf().disable();
	}
}
