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
public class Schedule_ControllerTests {
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
    

    //TESTS

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void createSchedule_NOTloggedIn() throws Exception {
		// Creating a JSONObject object
        JSONObject schedule = new JSONObject();
        JSONArray inputIDS = new JSONArray();
        inputIDS.put(1);
        schedule.put("startDateTime", "2020-05-05T08:45+10:00");
        schedule.put("endDateTime", "2020-05-05T08:50+10:00");
        schedule.put("user_ids", inputIDS);

        String contentSTRING = schedule.toString();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/schedule/createSchedule")
                                                                    .contentType(MediaType.APPLICATION_JSON)
                                                                    .content(contentSTRING);
        mvc.perform(builder).andExpect(status().isForbidden());
    }
    
	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void createScheduleCustomer_loggedIn_Admin() throws Exception {
		// Creating a JSONObject object
        JSONObject schedule = new JSONObject();
        JSONArray inputIDS = new JSONArray();
        inputIDS.put(1);
        schedule.put("startDateTime", "2020-05-05T08:45+10:00");
        schedule.put("endDateTime", "2020-05-05T08:50+10:00");
        schedule.put("user_ids", inputIDS);

		//Create Session
		if(startAdminSession()){
			String contentSTRING = schedule.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/schedule/createSchedule")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isBadRequest());
		}else{
			throw new Exception("Login failed - createScheduleCustomer_loggedIn_Admin");
		}
    }
    
    /**
	 * returns isCreated for success
	 * @throws Exception
	 */
	@Test
	public void createScheduleEmployee_loggedIn_Admin() throws Exception {
		// Creating a JSONObject object
        JSONObject schedule = new JSONObject();
        JSONArray userIDS = new JSONArray();
        userIDS.put(5);
        schedule.put("startDateTime", "2020-05-05T08:45+10:00");
        schedule.put("endDateTime", "2020-05-05T08:50+10:00");
        schedule.put("user_ids", userIDS);

		//Create Session
		if(startAdminSession()){
			String contentSTRING = schedule.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/schedule/createSchedule")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isCreated())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
		}else{
			throw new Exception("Login failed - createScheduleEmployee_loggedIn_Admin");
		}
    }
    
    /**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void createSchedule_NoStartDate() throws Exception {
		// Creating a JSONObject object
        JSONObject schedule = new JSONObject();
        JSONArray userIDS = new JSONArray();
        userIDS.put(5);
        schedule.put("startDateTime", "");
        schedule.put("endDateTime", "2020-05-05T08:50+10:00");
        schedule.put("user_ids", userIDS);

		//Create Session
		if(startAdminSession()){
			String contentSTRING = schedule.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/schedule/createSchedule")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isBadRequest());
		}else{
			throw new Exception("Login failed - createSchedule_NoStartDate");
		}
    }
    
    /**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void createSchedule_NoEndDate() throws Exception {
		// Creating a JSONObject object
        JSONObject schedule = new JSONObject();
        JSONArray userIDS = new JSONArray();
        userIDS.put(5);
        schedule.put("startDateTime", "2020-05-05T08:45+10:00");
        schedule.put("endDateTime", "");
        schedule.put("user_ids", userIDS);

		//Create Session
		if(startAdminSession()){
			String contentSTRING = schedule.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/schedule/createSchedule")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isBadRequest());
		}else{
			throw new Exception("Login failed - createSchedule_NoEndDate");
		}
    }
    
        /**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void createSchedule_NoUserIds() throws Exception {
		// Creating a JSONObject object
        JSONObject schedule = new JSONObject();
        JSONArray userIDS = new JSONArray();
        userIDS.put(5);
        schedule.put("startDateTime", "2020-05-05T08:45+10:00");
        schedule.put("endDateTime", "2020-05-05T08:50+10:00");
        schedule.put("user_ids", null);

		//Create Session
		if(startAdminSession()){
			String contentSTRING = schedule.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/schedule/createSchedule")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isBadRequest());
		}else{
			throw new Exception("Login failed - createSchedule_NoEndDate");
		}
	}

    /**
	 * returns isCreated for success
	 * @throws Exception
	 */
	@Test
	public void createScheduleAdmin_loggedIn_Admin() throws Exception {
		// Creating a JSONObject object
        JSONObject schedule = new JSONObject();
        JSONArray inputIDS = new JSONArray();
        inputIDS.put(4);
        schedule.put("startDateTime", "2020-05-05T08:45+10:00");
        schedule.put("endDateTime", "2020-05-05T08:50+10:00");
        schedule.put("user_ids", inputIDS);

		//Create Session
		if(startAdminSession()){
			String contentSTRING = schedule.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/schedule/createSchedule")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
            mvc.perform(builder).andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
		}else{
			throw new Exception("Login failed - createScheduleAdmin_loggedIn_Admin");
		}
    }
    
    /**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void createSchedule_loggedIn_Employee() throws Exception {
		// Creating a JSONObject object
        JSONObject schedule = new JSONObject();
        JSONArray inputIDS = new JSONArray();
        inputIDS.put(5);
        schedule.put("startDateTime", "2020-05-05T08:45+10:00");
        schedule.put("endDateTime", "2020-05-05T08:50+10:00");
        schedule.put("user_ids", inputIDS);

		//Create Session
		if(startEmployeeSession()){
			String contentSTRING = schedule.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/schedule/createSchedule")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isForbidden());
		}else{
			throw new Exception("Login failed - createSchedule_loggedIn_Employee");
		}
    }

    /**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void createSchedule_loggedIn_Customer() throws Exception {
		// Creating a JSONObject object
        JSONObject schedule = new JSONObject();
        JSONArray inputIDS = new JSONArray();
        inputIDS.put(1);
        schedule.put("startDateTime", "2020-05-05T08:45+10:00");
        schedule.put("endDateTime", "2020-05-05T08:50+10:00");
        schedule.put("user_ids", inputIDS);

		//Create Session
		if(startCustomerSession()){
			String contentSTRING = schedule.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/schedule/createSchedule")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isForbidden());
		}else{
			throw new Exception("Login failed - createSchedule_loggedIn_Customer");
		}
    }

    /**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void deleteSchedule_NOTloggedIn() throws Exception {
		// Creating a JSONObject object
        JSONObject schedule = new JSONObject();
        JSONArray inputIDS = new JSONArray();
        inputIDS.put(1);
        schedule.put("input", inputIDS);

        String contentSTRING = schedule.toString();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/schedule/deleteSchedule")
                                                                    .contentType(MediaType.APPLICATION_JSON)
                                                                    .content(contentSTRING);
        mvc.perform(builder).andExpect(status().isForbidden());
    }

    /**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void deleteSchedule_loggedIn_Admin() throws Exception {
        JSONObject schedule = new JSONObject();
        JSONArray inputIDS = new JSONArray();
        inputIDS.put(1);
        schedule.put("input", inputIDS);

		//Create Session
		if(startAdminSession()){
			String contentSTRING = schedule.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/schedule/deleteSchedule")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
            mvc.perform(builder).andExpect(status().isOk())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));;
		}else{
			throw new Exception("Login failed - deleteSchedule_loggedIn_Admin");
		}
    }

    /**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void deleteSchedule_loggedIn_Employee() throws Exception {
        JSONObject schedule = new JSONObject();
        JSONArray inputIDS = new JSONArray();
        inputIDS.put(1);
        schedule.put("input", inputIDS);

		//Create Session
		if(startEmployeeSession()){
			String contentSTRING = schedule.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/schedule/deleteSchedule")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
            mvc.perform(builder).andExpect(status().isForbidden());
		}else{
			throw new Exception("Login failed - deleteSchedule_loggedIn_Employee");
		}
    }

    /**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void deleteSchedule_loggedIn_Customer() throws Exception {
        JSONObject schedule = new JSONObject();
        JSONArray inputIDS = new JSONArray();
        inputIDS.put(1);
        schedule.put("input", inputIDS);

		//Create Session
		if(startEmployeeSession()){
			String contentSTRING = schedule.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/schedule/deleteSchedule")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
            mvc.perform(builder).andExpect(status().isForbidden());
		}else{
			throw new Exception("Login failed - deleteSchedule_loggedIn_Customer");
		}
    }
    
}