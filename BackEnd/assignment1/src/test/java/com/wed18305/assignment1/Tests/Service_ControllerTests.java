package com.wed18305.assignment1.Tests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

import javax.servlet.http.Cookie;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebMvc
public class Service_ControllerTests{
	/*
	 * Make sure you uncomment the public CommandLineRunner demo() That will create
	 * the UserTypes and default data you can test with.
	 */

	@Autowired
	private MockMvc mvc;
	protected MockHttpSession session;
	protected Cookie[] cookies;
	protected Principal principal;

	/**
	 * If the login is unsuccessful the session and cookie variables will be null
	 * Always make sure you check if your login was success
	 */
	protected boolean startAdminSession() {//Palmer is admin
		try {
			ResultActions result = mvc.perform(MockMvcRequestBuilders
											.post("/login").contentType(MediaType.APPLICATION_FORM_URLENCODED)
											.content(buildUrlEncodedFormEntity("username", "Palmer", "password", "1234")));
			this.session = (MockHttpSession) result.andReturn().getRequest().getSession();
			this.cookies = result.andReturn().getResponse().getCookies();
			this.principal = result.andReturn().getRequest().getUserPrincipal();	
			return true;			
		} catch (Exception e) {
			this.session = null;
			this.cookies = null;
		}
		return false;
	}
	/**
	 * If the login is unsuccessful the session and cookie variables will be null
	 * Always make sure you check if your login was success
	 */
	protected boolean startEmployeeSession() {//Dessler is employee
		try {
			ResultActions result = mvc.perform(MockMvcRequestBuilders
											.post("/login").contentType(MediaType.APPLICATION_FORM_URLENCODED)
											.content(buildUrlEncodedFormEntity("username", "Dessler", "password", "1234")));
			this.session = (MockHttpSession) result.andReturn().getRequest().getSession();
			this.cookies = result.andReturn().getResponse().getCookies();
			this.principal = result.andReturn().getRequest().getUserPrincipal();
			return true;				
		} catch (Exception e) {
			this.session = null;
			this.cookies = null;
		}
		return false;
	}
	/**
	 * If the login is unsuccessful the session and cookie variables will be null
	 * Always make sure you check if your login was success
	 */
	protected boolean startCustomerSession() {//Jacky is customer
		try {
			ResultActions result = mvc.perform(MockMvcRequestBuilders
											.post("/login").contentType(MediaType.APPLICATION_FORM_URLENCODED)
											.content(buildUrlEncodedFormEntity("username", "Jacky", "password", "1234")));
			this.session = (MockHttpSession) result.andReturn().getRequest().getSession();
			this.cookies = result.andReturn().getResponse().getCookies();
			return true;				
		} catch (Exception e) {
			this.session = null;
			this.cookies = null;
		}
		return false;
	}
	/**
	 * If the login is unsuccessful the session and cookie variables will be null
	 * Always make sure you check if your login was success
	 */
	protected boolean startCustomerDelete1Session() {//Jacky is customer
		try {
			ResultActions result = mvc.perform(MockMvcRequestBuilders
											.post("/login").contentType(MediaType.APPLICATION_FORM_URLENCODED)
											.content(buildUrlEncodedFormEntity("username", "delete1", "password", "1234")));
			this.session = (MockHttpSession) result.andReturn().getRequest().getSession();
			this.cookies = result.andReturn().getResponse().getCookies();
			return true;				
		} catch (Exception e) {
			this.session = null;
			this.cookies = null;
		}
		return false;
    }
	/**
	 * If the login is unsuccessful the session and cookie variables will be null
	 * Always make sure you check if your login was success
	 */
	protected boolean startSession(String username, String password){//Custom user
		try {
			ResultActions result = mvc.perform(MockMvcRequestBuilders
											.post("/login").contentType(MediaType.APPLICATION_FORM_URLENCODED)
											.content(buildUrlEncodedFormEntity("username", username, "password", password)));
			this.session = (MockHttpSession) result.andReturn().getRequest().getSession();
			this.cookies = result.andReturn().getResponse().getCookies();
			return true;			
		} catch (Exception e) {
			this.session = null;
			this.cookies = null;
		}
		return false;
	}

	@BeforeEach
    protected void endSession() {
        // this.session.clearAttributes();
		this.session = null;
		this.cookies = null;
    }

	private String buildUrlEncodedFormEntity(String... params) {
		if ((params.length % 2) > 0) {
			throw new IllegalArgumentException("Need to give an even number of parameters");
		}
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < params.length; i += 2) {
			if (i > 0) {
				result.append('&');
			}
			try {
				result.append(URLEncoder.encode(params[i], StandardCharsets.UTF_8.name())).append('=')
						.append(URLEncoder.encode(params[i + 1], StandardCharsets.UTF_8.name()));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return result.toString();
	}


	//Tests

	/**
	 * returns isCreated for success
	 * @throws Exception
	 */
	@Test
	public void postService() throws Exception {
		// Creating a JSONObject object
		JSONObject service = new JSONObject();
		service.put("name", "Freddie's Falafels");

		//Create Session
		if(startAdminSession()){
			String contentSTRING = service.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/service/createService")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isCreated())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
		}else{
			throw new Exception("Login failed - postService_loggedIn_Admin");
		}
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void postService_NoName() throws Exception {
		// Creating a JSONObject object
		JSONObject service = new JSONObject();
		service.put("name", "");

		//Create Session
		if(startAdminSession()){
			String contentSTRING = service.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/service/createService")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isBadRequest())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
		}else{
			throw new Exception("Login failed - postService_loggedIn_Admin");
		}
	}

	/**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void getServices() throws Exception {
		//Create Session
		if(startAdminSession()){
			//get services
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/service/getServices")
																		.session(session)
																		.cookie(cookies);
			mvc.perform(builder).andExpect(status().isOk())
								.andExpect(content().contentType(MediaType.APPLICATION_JSON))
								.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
		}else{
			throw new Exception("Login failed - getServices_loggedIn_Admin");
		}
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void getServices_CustomerAttempt() throws Exception {
		//Create Session
		if(startCustomerSession()){
			//get services
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/service/getServices")
																		.session(session)
																		.cookie(cookies);
			mvc.perform(builder).andExpect(status().isForbidden());
		}else{
			throw new Exception("Login failed - getServices_CustomerAttempt");
		}
	}
}