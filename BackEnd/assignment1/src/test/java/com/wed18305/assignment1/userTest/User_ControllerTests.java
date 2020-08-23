package com.wed18305.assignment1.userTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.wed18305.assignment1.model.User;
import com.wed18305.assignment1.model.UserType;
import com.wed18305.assignment1.repositories.UserType_Repository;
import com.wed18305.assignment1.services.UserType_Service;
import com.wed18305.assignment1.services.User_Service;

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

	/* Make sure you uncomment the public CommandLineRunner demo()
	 * That will create the UserTypes and default data you can test with.
	*/

	@Test
	public void postCustomer() throws Exception {
		//Creating a JSONObject object
		JSONObject customer = new JSONObject();
		customer.put("name", "neil kennedy");
		customer.put("username", "s3561388@student.rmit.edu.au");
		customer.put("password", "1234");
		customer.put("contactNumber", "0425000000");

		String contentSTRING = customer.toString();
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createCustomer")
																	  .contentType(MediaType.APPLICATION_JSON)
																	  .content(contentSTRING);
		mvc.perform(builder).andExpect(status().isCreated())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
							.andExpect(MockMvcResultMatchers.jsonPath("$.body.username").value("s3561388@student.rmit.edu.au"));
	}

	@Test
	public void postCustomer_NoName() throws Exception {
		//Creating a JSONObject object
		JSONObject customer = new JSONObject();
		customer.put("name", "");
		customer.put("username", "s3561388@student.rmit.edu.au");
		customer.put("password", "1234");
		customer.put("contactNumber", "0425000000");

		String contentSTRING = customer.toString();
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createCustomer")
																	  .contentType(MediaType.APPLICATION_JSON)
																	  .content(contentSTRING);
		mvc.perform(builder).andExpect(status().isBadRequest())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
							.andExpect(MockMvcResultMatchers.jsonPath("$.errors[:2].defaultMessage").value("name is required"));
	}

	@Test
	public void postCustomer_NoUsername() throws Exception {
		//Creating a JSONObject object
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
		//Creating a JSONObject object
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
		//Creating a JSONObject object
		JSONObject customer = new JSONObject();
		customer.put("name", "neil kennedy");
		customer.put("username", "s3561388@student.rmit.edu.au");
		customer.put("password", "1234");
		customer.put("contactNumber", "");
		
		String contentSTRING = customer.toString();
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/user/createCustomer")
																	  .contentType(MediaType.APPLICATION_JSON)
																	  .content(contentSTRING);
		mvc.perform(builder).andExpect(status().isBadRequest())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false")) 
							.andExpect(MockMvcResultMatchers.jsonPath("$.errors[:2].defaultMessage").value("contact number is required"));
	}

	@Test
	public void Login() throws Exception {
		//Login with user details
		JSONObject login = new JSONObject();
		login.put("username", "Jacky");
		login.put("password","1234");
		String loginSTRING = login.toString();
		MockHttpServletRequestBuilder builderLogin = MockMvcRequestBuilders.post("/api/user/Login")
																	  	   .contentType(MediaType.APPLICATION_JSON)
																	  	   .content(loginSTRING);
		mvc.perform(builderLogin).andExpect(status().isOk())
								 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
								 .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
								 .andExpect(MockMvcResultMatchers.jsonPath("$.body.username").value("Jacky"));
	}

	@Test
	public void Login_incorrect() throws Exception {
		//Login with user details
		JSONObject login = new JSONObject();
		login.put("username", "Jacky");
		login.put("password","123");
		String loginSTRING = login.toString();
		MockHttpServletRequestBuilder builderLogin = MockMvcRequestBuilders.post("/api/user/Login")
																	  	   .contentType(MediaType.APPLICATION_JSON)
																	  	   .content(loginSTRING);
		mvc.perform(builderLogin).andExpect(status().isBadRequest())
								 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
								 .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
								 .andExpect(MockMvcResultMatchers.jsonPath("$.errors").value("user not found!"));
	}

	@Test
	public void Login_NoUsername() throws Exception {
		//Login with user details
		JSONObject login = new JSONObject();
		login.put("username", "");
		login.put("password","1234");
		String loginSTRING = login.toString();
		MockHttpServletRequestBuilder builderLogin = MockMvcRequestBuilders.post("/api/user/Login")
																	  	   .contentType(MediaType.APPLICATION_JSON)
																	  	   .content(loginSTRING);
		mvc.perform(builderLogin).andExpect(status().isBadRequest())
								 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
								 .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
								 .andExpect(MockMvcResultMatchers.jsonPath("$.errors[:2].defaultMessage").value("username is required"));
	}

	@Test
	public void Login_NoPassword() throws Exception {
		//Login with user details
		JSONObject login = new JSONObject();
		login.put("username", "Jacky");
		login.put("password","");
		String loginSTRING = login.toString();
		MockHttpServletRequestBuilder builderLogin = MockMvcRequestBuilders.post("/api/user/Login")
																	  	   .contentType(MediaType.APPLICATION_JSON)
																	  	   .content(loginSTRING);
		mvc.perform(builderLogin).andExpect(status().isBadRequest())
								 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
								 .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
								 .andExpect(MockMvcResultMatchers.jsonPath("$.errors[:2].defaultMessage").value("password is required"));
	}


 
}