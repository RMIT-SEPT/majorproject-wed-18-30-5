package com.wed18305.assignment1.userTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class User_ControllerTests {

	@Autowired
	private MockMvc mvc;

	/*
	 * Make sure you uncomment the public CommandLineRunner demo() That will create
	 * the UserTypes and default data you can test with.
	 */

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

	@Test
	public void postCustomer() throws Exception {
		// Creating a JSONObject object
		JSONObject customer = new JSONObject();
		customer.put("name", "neil kennedy");
		customer.put("username", "s3561388@student.rmit.edu.au");
		customer.put("password", "1234");
		customer.put("contactNumber", "0425000000");

		String contentSTRING = customer.toString();
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createCustomer")
				.contentType(MediaType.APPLICATION_JSON).content(contentSTRING);
		mvc.perform(builder).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.body.username").value("s3561388@student.rmit.edu.au"));
	}

	@Test
	public void postCustomer_NoName() throws Exception {
		// Creating a JSONObject object
		JSONObject customer = new JSONObject();
		customer.put("name", "");
		customer.put("username", "s3561388@student.rmit.edu.au");
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

	@Test
	public void postCustomer_NoPassword() throws Exception {
		// Creating a JSONObject object
		JSONObject customer = new JSONObject();
		customer.put("name", "neil kennedy");
		customer.put("username", "s3561388@student.rmit.edu.au");
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

	@Test
	public void postCustomer_NoContactNumber() throws Exception {
		// Creating a JSONObject object
		JSONObject customer = new JSONObject();
		customer.put("name", "neil kennedy");
		customer.put("username", "s3561388@student.rmit.edu.au");
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

	@Test
	public void Login_Success() throws Exception {
		MockHttpServletRequestBuilder builderLogin = MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(buildUrlEncodedFormEntity("username", "Palmer", "password", "1234"));
		mvc.perform(builderLogin).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
	}

	@Test
	public void Login_Failure() throws Exception {
		MockHttpServletRequestBuilder builderLogin = MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(buildUrlEncodedFormEntity("username", "Palmer", "password", ""));
		mvc.perform(builderLogin).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.errors").value("Bad credentials"));
	}
 
}