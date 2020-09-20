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
public class Booking_ControllerTests{
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
	public void postBooking() throws Exception {
		// Creating a JSONObject object
		JSONObject booking = new JSONObject();
		booking.put("startDateTime", "2020-09-07T17:00+10:00");
		booking.put("endDateTime", "2020-09-07T19:00+10:00");
		booking.put("user_ids", "[5]");

		String contentSTRING = booking.toString();
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/booking/createBooking")
				.contentType(MediaType.APPLICATION_JSON).content(contentSTRING);
		mvc.perform(builder).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void postCustomer_NoName() throws Exception {
		// Creating a JSONObject object
		JSONObject customer = new JSONObject();
		customer.put("name", "");
		customer.put("username", "s3561388@customer");
		customer.put("password", "1234");
		customer.put("contactNumber", "0425000000");

		String contentSTRING = customer.toString();
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createCustomer")
				.contentType(MediaType.APPLICATION_JSON).content(contentSTRING);
		mvc.perform(builder).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.errors[:2].defaultMessage").value("name is required"));
	}

	@Test
	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	public void postCustomer_NoUsername() throws Exception {
		// Creating a JSONObject object
		JSONObject customer = new JSONObject();
		customer.put("name", "neil kennedy");
		customer.put("username", "");
		customer.put("password", "1234");
		customer.put("contactNumber", "0425000000");
		
		String contentSTRING = customer.toString();
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createCustomer")
																	  .contentType(MediaType.APPLICATION_JSON)
																	  .content(contentSTRING);
		mvc.perform(builder).andExpect(status().isBadRequest())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
							.andExpect(MockMvcResultMatchers.jsonPath("$.errors[:2].defaultMessage").value("username is required"));
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void postCustomer_NoPassword() throws Exception {
		// Creating a JSONObject object
		JSONObject customer = new JSONObject();
		customer.put("name", "neil kennedy");
		customer.put("username", "s3561388@customer");
		customer.put("password", "");
		customer.put("contactNumber", "0425000000");
		
		String contentSTRING = customer.toString();
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createCustomer")
																	  .contentType(MediaType.APPLICATION_JSON)
																	  .content(contentSTRING);
		mvc.perform(builder).andExpect(status().isBadRequest())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
							.andExpect(MockMvcResultMatchers.jsonPath("$.errors[:2].defaultMessage").value("password is required"));
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void postCustomer_NoContactNumber() throws Exception {
		// Creating a JSONObject object
		JSONObject customer = new JSONObject();
		customer.put("name", "neil kennedy");
		customer.put("username", "s3561388@customer");
		customer.put("password", "1234");
		customer.put("contactNumber", "");

		String contentSTRING = customer.toString();
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createCustomer")
				.contentType(MediaType.APPLICATION_JSON).content(contentSTRING);
		mvc.perform(builder).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.errors[:2].defaultMessage").value("contact number is required"));
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void postEmployee_NOTloggedIn() throws Exception {
		// Creating a JSONObject object
		JSONObject employee = new JSONObject();
		employee.put("name", "neil kennedy");
		employee.put("username", "s3561388@employee");
		employee.put("password", "1234");
		employee.put("contactNumber", "0425000000");

		String contentSTRING = employee.toString();
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createEmployee")
																	.contentType(MediaType.APPLICATION_JSON)
																	.content(contentSTRING);
		mvc.perform(builder).andExpect(status().isForbidden());
	}

	/**
	 * returns isCreated for success
	 * @throws Exception
	 */
	@Test
	public void postEmployee_loggedIn_Admin() throws Exception {
		// Creating a JSONObject object
		JSONObject employee = new JSONObject();
		employee.put("name", "neil kennedy");
		employee.put("username", "s3561388@employee");
		employee.put("password", "1234");
		employee.put("contactNumber", "0425000000");

		//Create Session
		if(startAdminSession()){
			String contentSTRING = employee.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createEmployee")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isCreated())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
							.andExpect(MockMvcResultMatchers.jsonPath("$.body.username").value("s3561388@employee"));
		}else{
			throw new Exception("Login failed - postEmployee_loggedIn_Admin");
		}
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void postEmployee_loggedIn_Customer() throws Exception {
		// Creating a JSONObject object
		JSONObject employee = new JSONObject();
		employee.put("name", "neil kennedy");
		employee.put("username", "s3561388@employee");
		employee.put("password", "1234");
		employee.put("contactNumber", "0425000000");

		//Create Session
		if(startCustomerSession()){
			String contentSTRING = employee.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createEmployee")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isForbidden());
		}else{
			throw new Exception("Login failed - postEmployee_loggedIn_Customer");
		}
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void postEmployee_loggedIn_Employee() throws Exception {
		// Creating a JSONObject object
		JSONObject employee = new JSONObject();
		employee.put("name", "neil kennedy");
		employee.put("username", "s3561388@employee");
		employee.put("password", "1234");
		employee.put("contactNumber", "0425000000");

		//Create Session
		if(startEmployeeSession()){
			String contentSTRING = employee.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createEmployee")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isForbidden());
		}else{
			throw new Exception("Login failed - postEmployee_loggedIn_Employee");
		}
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void postEmployee_NoName() throws Exception {
		// Creating a JSONObject object
		JSONObject customer = new JSONObject();
		customer.put("name", "");
		customer.put("username", "s3561388@employee");
		customer.put("password", "1234");
		customer.put("contactNumber", "0425000000");

		//Create Session
		if(startAdminSession()){
			String contentSTRING = customer.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createEmployee")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isBadRequest())
								.andExpect(content().contentType(MediaType.APPLICATION_JSON))
								.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
								.andExpect(MockMvcResultMatchers.jsonPath("$.errors[:2].defaultMessage").value("name is required"));
		}else{
			throw new Exception("Login failed - postEmployee_NoName");
		}
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void postEmployee_NoUsername() throws Exception {
		// Creating a JSONObject object
		JSONObject customer = new JSONObject();
		customer.put("name", "neil kennedy");
		customer.put("username", "");
		customer.put("password", "1234");
		customer.put("contactNumber", "0425000000");
		
		//Create Session
		if(startAdminSession()){
			String contentSTRING = customer.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createEmployee")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isBadRequest())
								.andExpect(content().contentType(MediaType.APPLICATION_JSON))
								.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
								.andExpect(MockMvcResultMatchers.jsonPath("$.errors[:2].defaultMessage").value("username is required"));
		}else{
			throw new Exception("Login failed - postEmployee_NoName");
		}
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void postEmployee_NoPassword() throws Exception {
		// Creating a JSONObject object
		JSONObject customer = new JSONObject();
		customer.put("name", "neil kennedy");
		customer.put("username", "s3561388@employee");
		customer.put("password", "");
		customer.put("contactNumber", "0425000000");
		
		//Create Session
		if(startAdminSession()){
			String contentSTRING = customer.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createEmployee")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isBadRequest())
								.andExpect(content().contentType(MediaType.APPLICATION_JSON))
								.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
								.andExpect(MockMvcResultMatchers.jsonPath("$.errors[:2].defaultMessage").value("password is required"));
		}else{
			throw new Exception("Login failed - postEmployee_NoPassword");
		}
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void postEmployee_NoContactNumber() throws Exception {
		// Creating a JSONObject object
		JSONObject customer = new JSONObject();
		customer.put("name", "neil kennedy");
		customer.put("username", "s3561388@employee");
		customer.put("password", "1234");
		customer.put("contactNumber", "");

		//Create Session
		if(startAdminSession()){
			String contentSTRING = customer.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createEmployee")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isBadRequest())
								.andExpect(content().contentType(MediaType.APPLICATION_JSON))
								.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
								.andExpect(MockMvcResultMatchers.jsonPath("$.errors[:2].defaultMessage").value("contact number is required"));
		}else{
			throw new Exception("Login failed - postEmployee_NoContactNumber");
		}
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void postAdmin_NOTloggedIn() throws Exception {
		// Creating a JSONObject object
		JSONObject employee = new JSONObject();
		employee.put("name", "neil kennedy");
		employee.put("username", "s3561388@admin");
		employee.put("password", "1234");
		employee.put("contactNumber", "0425000000");

		String contentSTRING = employee.toString();
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createAdmin")
																	.contentType(MediaType.APPLICATION_JSON)
																	.content(contentSTRING);
		mvc.perform(builder).andExpect(status().isForbidden());
	}

	/**
	 * returns isCreated for success
	 * @throws Exception
	 */
	@Test
	public void postAdmin_loggedIn_Admin() throws Exception {
		// Creating a JSONObject object
		JSONObject employee = new JSONObject();
		employee.put("name", "neil kennedy");
		employee.put("username", "s3561388@admin");
		employee.put("password", "1234");
		employee.put("contactNumber", "0425000000");

		//Create Session
		if(startAdminSession()){
			String contentSTRING = employee.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createAdmin")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isCreated())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
							.andExpect(MockMvcResultMatchers.jsonPath("$.body.username").value("s3561388@admin"));
		}else{
			throw new Exception("Login failed - postAdmin_loggedIn_Admin");
		}
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void postAdmin_loggedIn_Customer() throws Exception {
		// Creating a JSONObject object
		JSONObject employee = new JSONObject();
		employee.put("name", "neil kennedy");
		employee.put("username", "s3561388@admin");
		employee.put("password", "1234");
		employee.put("contactNumber", "0425000000");

		//Create Session
		if(startCustomerSession()){
			String contentSTRING = employee.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createAdmin")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isForbidden());
		}else{
			throw new Exception("Login failed - postAdmin_loggedIn_Customer");
		}
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void postAdmin_loggedIn_Employee() throws Exception {
		// Creating a JSONObject object
		JSONObject employee = new JSONObject();
		employee.put("name", "neil kennedy");
		employee.put("username", "s3561388@admin");
		employee.put("password", "1234");
		employee.put("contactNumber", "0425000000");

		//Create Session
		if(startEmployeeSession()){
			String contentSTRING = employee.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createAdmin")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isForbidden());
		}else{
			throw new Exception("Login failed - postAdmin_loggedIn_Employee");
		}
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void postAdmin_NoName() throws Exception {
		// Creating a JSONObject object
		JSONObject customer = new JSONObject();
		customer.put("name", "");
		customer.put("username", "s3561388@admin");
		customer.put("password", "1234");
		customer.put("contactNumber", "0425000000");

		//Create Session
		if(startAdminSession()){
			String contentSTRING = customer.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createAdmin")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isBadRequest())
								.andExpect(content().contentType(MediaType.APPLICATION_JSON))
								.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
								.andExpect(MockMvcResultMatchers.jsonPath("$.errors[:2].defaultMessage").value("name is required"));
		}else{
			throw new Exception("Login failed - postAdmin_NoName");
		}
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void postAdmin_NoUsername() throws Exception {
		// Creating a JSONObject object
		JSONObject customer = new JSONObject();
		customer.put("name", "neil kennedy");
		customer.put("username", "");
		customer.put("password", "1234");
		customer.put("contactNumber", "0425000000");
		
		//Create Session
		if(startAdminSession()){
			String contentSTRING = customer.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createAdmin")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isBadRequest())
								.andExpect(content().contentType(MediaType.APPLICATION_JSON))
								.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
								.andExpect(MockMvcResultMatchers.jsonPath("$.errors[:2].defaultMessage").value("username is required"));
		}else{
			throw new Exception("Login failed - postAdmin_NoName");
		}
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void postAdmin_NoPassword() throws Exception {
		// Creating a JSONObject object
		JSONObject customer = new JSONObject();
		customer.put("name", "neil kennedy");
		customer.put("username", "s3561388@admin");
		customer.put("password", "");
		customer.put("contactNumber", "0425000000");
		
		//Create Session
		if(startAdminSession()){
			String contentSTRING = customer.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createAdmin")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isBadRequest())
								.andExpect(content().contentType(MediaType.APPLICATION_JSON))
								.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
								.andExpect(MockMvcResultMatchers.jsonPath("$.errors[:2].defaultMessage").value("password is required"));
		}else{
			throw new Exception("Login failed - postAdmin_NoPassword");
		}
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void postAdmin_NoContactNumber() throws Exception {
		// Creating a JSONObject object
		JSONObject customer = new JSONObject();
		customer.put("name", "neil kennedy");
		customer.put("username", "s3561388@admin");
		customer.put("password", "1234");
		customer.put("contactNumber", "");

		//Create Session
		if(startAdminSession()){
			String contentSTRING = customer.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createAdmin")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isBadRequest())
								.andExpect(content().contentType(MediaType.APPLICATION_JSON))
								.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
								.andExpect(MockMvcResultMatchers.jsonPath("$.errors[:2].defaultMessage").value("contact number is required"));
		}else{
			throw new Exception("Login failed - postAdmin_NoContactNumber");
		}
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void deleteCustomer_NOTloggedIn() throws Exception {
		//Delete logged in customer
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/deleteCustomer");
		mvc.perform(builder).andExpect(status().isForbidden());
	}

	/**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void deleteCustomer_loggedIn_Customer() throws Exception {
		//Create Session
		if(startCustomerDelete1Session()){
			//Delete logged in customer
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/deleteCustomer")
																			.session(session)
																			.cookie(cookies);
			mvc.perform(builder).andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
		}else{
			throw new Exception("Login failed - deleteCustomer_loggedIn_Customer");
		}
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void deleteCustomer_loggedIn_Admin() throws Exception {
		//Create Session
		 if(startAdminSession()){
			//Delete logged in customer
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/deleteCustomer")
																			.session(session)
																			.cookie(cookies);
			mvc.perform(builder).andExpect(status().isForbidden());
		}else{
			throw new Exception("Login failed - deleteCustomer_loggedIn_Admin");
		}
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void deleteCustomer_loggedIn_Employee() throws Exception {
		//Create Session
		 if(startEmployeeSession()){
			//Delete logged in customer
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/deleteCustomer")
																			.session(session)
																			.cookie(cookies);
			mvc.perform(builder).andExpect(status().isForbidden());
		}else{
			throw new Exception("Login failed - deleteCustomer_loggedIn_Employee");
		}
	}

	/**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void Login_Success() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(buildUrlEncodedFormEntity("username", "Palmer", "password", "1234"));
		mvc.perform(builder).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void Login_Failure() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(buildUrlEncodedFormEntity("username", "Palmer", "password", ""));
		mvc.perform(builder).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.errors").value("Bad credentials"));
	}
 
	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void getEmployees_NOTloggedIn() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/user/getEmployees");
		mvc.perform(builder).andExpect(status().isForbidden());
	}

	/**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void getEmployees_loggedIn_Admin() throws Exception {
		//Create Session
		if(startAdminSession()){
			//get employees
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/user/getEmployees")
																		.session(session)
																		.cookie(cookies);
			mvc.perform(builder).andExpect(status().isOk())
								.andExpect(content().contentType(MediaType.APPLICATION_JSON))
								.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
		}else{
			throw new Exception("Login failed - getEmployees_loggedIn_Admin");
		}
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void getEmployees_loggedIn_Employee() throws Exception {
		//Create Session
		if(startEmployeeSession()){
			//get employees
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/user/getEmployees")
																		.session(session)
																		.cookie(cookies);
			mvc.perform(builder).andExpect(status().isForbidden());
		}else{
			throw new Exception("Login failed - getEmployees_loggedIn_Admin");
		}
	}

	/**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void getEmployees_loggedIn_Customer() throws Exception {
		//Create Session
		if(startCustomerSession()){
			//get employees
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/user/getEmployees")
																		.session(session)
																		.cookie(cookies);
			mvc.perform(builder).andExpect(status().isOk())
								.andExpect(content().contentType(MediaType.APPLICATION_JSON))
								.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
		}else{
			throw new Exception("Login failed - getEmployees_loggedIn_Customer");
		}
	}

	/**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void deleteUser_loggedIn_Admin() throws Exception {
		// Creating a JSONObject object
		JSONObject input = new JSONObject();
		JSONArray inputIDS = new JSONArray();
		inputIDS.put(7);
		inputIDS.put(9);
		input.put("input", inputIDS);
		String contentSTRING = input.toString();

		//Create Session
		if(startAdminSession()){
			//delte employees
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/deleteUser")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																	  	.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isOk())
								.andExpect(content().contentType(MediaType.APPLICATION_JSON))
								.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
		}else{
			throw new Exception("Login failed - deleteUser_loggedIn_Admin");
		}
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void deleteUser_loggedIn_Customer() throws Exception {
		// Creating a JSONObject object
		JSONObject delete = new JSONObject();
		JSONArray inputIDS = new JSONArray();
		inputIDS.put(7);
		inputIDS.put(9);
		delete.put("input", inputIDS);
		String contentSTRING = delete.toString();

		//Create Session
		if(startCustomerSession()){
			//delte employees
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/user/deleteUser")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																	  	.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isForbidden());
		}else{
			throw new Exception("Login failed - deleteUser_loggedIn_Customer");
		}
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void deleteUser_loggedIn_Employee() throws Exception {
		// Creating a JSONObject object
		JSONObject delete = new JSONObject();
		JSONArray inputIDS = new JSONArray();
		inputIDS.put(7);
		inputIDS.put(9);
		delete.put("input", inputIDS);
		String contentSTRING = delete.toString();

		//Create Session
		if(startEmployeeSession()){
			//delte employees
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/user/deleteUser")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																	  	.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isForbidden());
		}else{
			throw new Exception("Login failed - deleteUser_loggedIn_Employee");
		}
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void deleteUser_NOTloggedIn() throws Exception {
		// Creating a JSONObject object
		JSONObject delete = new JSONObject();
		JSONArray inputIDS = new JSONArray();
		inputIDS.put(7);
		inputIDS.put(9);
		delete.put("input", inputIDS);
		String contentSTRING = delete.toString();

		//delete employees
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/user/deleteUser")
																	.contentType(MediaType.APPLICATION_JSON)
																	.content(contentSTRING);
		mvc.perform(builder).andExpect(status().isForbidden());
	}
}