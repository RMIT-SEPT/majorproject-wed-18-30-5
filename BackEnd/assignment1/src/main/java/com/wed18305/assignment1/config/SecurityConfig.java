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

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security configuration.
 *
 * @author Rob Winch
 * @author Vedran Pavic
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// @formatter:off
	@Override
	public void configure(WebSecurity web) {
		web
			.ignoring().requestMatchers(PathRequest.toH2Console());
	}
	// @formatter:on

	// @formatter:off
	// tag::config[]
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests((authorize) -> authorize
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.anyRequest().authenticated()
			)
			.formLogin((formLogin) -> formLogin
				.permitAll()
			);
	}
	// end::config[]
	// @formatter:on

}
