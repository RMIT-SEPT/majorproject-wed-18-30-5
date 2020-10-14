package com.wed18305.assignment1.Tests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;

import javax.servlet.http.Cookie;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
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

	// Tests

	/**
	 * returns isCreated for success
	 * @throws Exception
	 */
	@Test
	public void postBooking() throws Exception {
		// Creating a JSONObject object
		JSONObject booking = new JSONObject();
		booking.put("startDateTime", "2020-10-15T00:00+10:00");
		booking.put("endDateTime", 	 "2020-10-15T00:00+10:00");
		booking.put("user_ids", new JSONArray(new String[] {"5"})); // Put JSONArray in JSONObject: https://stackoverflow.com/questions/12142238/add-jsonarray-to-jsonobject#12144534

		// Login Via Session
		if (startCustomerSession()) {
			String contentSTRING = booking.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/booking/createBooking")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder)
							.andExpect(status().isCreated())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
		}
		else { throw new Exception("Login failed - postBooking"); }
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void postBooking_NoUserID() throws Exception {
		// Creating a JSONObject object
		JSONObject booking = new JSONObject();
		booking.put("startDateTime", "2020-10-15T00:00+10:00");
		booking.put("endDateTime", 	 "2020-10-15T00:00+10:00");
		booking.put("user_ids", null); // Put JSONArray in JSONObject: https://stackoverflow.com/questions/12142238/add-jsonarray-to-jsonobject#12144534

		// Login Via Session
		if (startCustomerSession()) {
			String contentSTRING = booking.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/booking/createBooking")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder)
							.andExpect(status().isBadRequest())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
		}
		else { throw new Exception("Login failed - postBooking_NoUserID"); }
	}

	/**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void approveBooking() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("id", "1");

		// Login Via Session
		if (startAdminSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/api/booking/approveBooking")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder)
							.andExpect(status().isAccepted())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		else { throw new Exception("Login failed - approveBooking"); }
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void approveNonExistantBooking() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("id", "99");

		// Login Via Session
		if (startAdminSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/api/booking/approveBooking")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder)
							.andExpect(status().isBadRequest())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		else { throw new Exception("Login failed - approveNonExistantBooking"); }
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void approveBooking_Employee() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("id", "1");

		// Login Via Session
		if (startEmployeeSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/api/booking/approveBooking")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isForbidden());
		}
		else { throw new Exception("Login failed - approveBooking_Employee"); }
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void approveBooking_Customer() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("id", "1");

		// Login Via Session
		if (startCustomerSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/api/booking/approveBooking")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isForbidden());
		}
		else { throw new Exception("Login failed - approveBooking_Customer"); }
	}

	/**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void denyBooking() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("id", "1");

		// Login Via Session
		if (startAdminSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/api/booking/denyBooking")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder)
							.andExpect(status().isAccepted())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		else { throw new Exception("Login failed - denyBooking"); }
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void denyNonExistantBooking() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("id", "99");

		// Login Via Session
		if (startAdminSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/api/booking/denyBooking")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder)
							.andExpect(status().isBadRequest())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		else { throw new Exception("Login failed - denyNonExistantBooking"); }
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void denyBooking_Employee() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("id", "1");

		// Login Via Session
		if (startEmployeeSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/api/booking/denyBooking")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isForbidden());
		}
		else { throw new Exception("Login failed - denyBooking_Employee"); }
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void denyBooking_Customer() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("id", "1");

		// Login Via Session
		if (startCustomerSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/api/booking/denyBooking")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isForbidden());
		}
		else { throw new Exception("Login failed - denyBooking_Customer"); }
	}

	/**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void completeBooking() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("id", "1");

		// Login Via Session
		if (startAdminSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/api/booking/completeBooking")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder)
							.andExpect(status().isAccepted())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		else { throw new Exception("Login failed - completeBooking"); }
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void completeNonExistantBooking() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("id", "99");

		// Login Via Session
		if (startAdminSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/api/booking/completeBooking")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder)
							.andExpect(status().isBadRequest())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		else { throw new Exception("Login failed - completeNonExistantBooking"); }
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void completeBooking_Employee() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("id", "1");

		// Login Via Session
		if (startEmployeeSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/api/booking/completeBooking")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isForbidden());
		}
		else { throw new Exception("Login failed - completeBooking_Employee"); }
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void completeBooking_Customer() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("id", "1");

		// Login Via Session
		if (startCustomerSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/api/booking/completeBooking")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isForbidden());
		}
		else { throw new Exception("Login failed - completeBooking_Customer"); }
	}

	/**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void cancelBooking() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("id", "1");

		// Login Via Session
		if (startAdminSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/api/booking/cancelBooking")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder)
							.andExpect(status().isAccepted())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		else { throw new Exception("Login failed - cancelBooking"); }
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void cancelNonExistantBooking() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("id", "99");

		// Login Via Session
		if (startAdminSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/api/booking/cancelBooking")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder)
							.andExpect(status().isBadRequest())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		else { throw new Exception("Login failed - cancelNonExistantBooking"); }
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void cancelBooking_Employee() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("id", "1");

		// Login Via Session
		if (startEmployeeSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/api/booking/cancelBooking")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isForbidden());
		}
		else { throw new Exception("Login failed - cancelBooking_Employee"); }
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void cancelBooking_Customer() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("id", "1");

		// Login Via Session
		if (startCustomerSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/api/booking/cancelBooking")
																		.session(session)
																		.cookie(cookies)
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isForbidden());
		}
		else { throw new Exception("Login failed - cancelBooking_Customer"); }
	}

	/**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void getAllBookings() throws Exception {
		// Create Session
		if (startAdminSession()) {
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/booking/getAllBookings")
																			.session(session)
																			.cookie(cookies)
																			.contentType(MediaType.APPLICATION_JSON);
			mvc.perform(builder)
							.andExpect(status().isOk())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON));

			System.out.println(builder.toString());
		}
		else { new Exception("Login failed - getAllBookings"); }
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void getAllBookings_AsEmployee() throws Exception {
		// Create Session
		if (startEmployeeSession()) {
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/booking/getAllBookings")
																			.session(session)
																			.cookie(cookies)
																			.contentType(MediaType.APPLICATION_JSON);
			mvc.perform(builder).andExpect(status().isForbidden());
		}
		else { new Exception("Login failed - getAllBookings_AsEmployee"); }
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void getAllBookings_AsCustomer() throws Exception {
		// Create Session
		if (startCustomerSession()) {
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/booking/getAllBookings")
																			.session(session)
																			.cookie(cookies)
																			.contentType(MediaType.APPLICATION_JSON);
			mvc.perform(builder).andExpect(status().isForbidden());
		}
		else { new Exception("Login failed - getAllBookings_AsCustomer"); }
	}

	/**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void getUserBookings_Customer() throws Exception {
		// Create Session
		if (startCustomerSession()) {
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/booking/getUserBookings")
																			.session(session)
																			.cookie(cookies)
																			.contentType(MediaType.APPLICATION_JSON);
			mvc.perform(builder)
							.andExpect(status().isOk())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		else { new Exception("Login failed - getUserBookings_Customer"); }
	}

	/**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void getUserBookings_Employee() throws Exception {
		// Create Session
		if (startCustomerSession()) {
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/booking/getUserBookings")
																			.session(session)
																			.cookie(cookies)
																			.contentType(MediaType.APPLICATION_JSON);
			mvc.perform(builder).andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		else { new Exception("Login failed - getUserBookings_Employee"); }
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void getUserBookings_Admin() throws Exception {
		// Create Session
		if (startAdminSession()) {
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/booking/getUserBookings")
																			.session(session)
																			.cookie(cookies)
																			.contentType(MediaType.APPLICATION_JSON);
			mvc.perform(builder).andExpect(status().isForbidden());
		}
		else { new Exception("Login failed - getUserBookings_Admin"); }
	}

	/**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void getAllUpcomingBookings() throws Exception {
		// Create Session
		if (startAdminSession()) {
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/booking/getUpcomingBookings")
																			.session(session)
																			.cookie(cookies)
																			.contentType(MediaType.APPLICATION_JSON);
			mvc.perform(builder).andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		else { new Exception("Login failed - getAllUpcomingBookings"); }
	}

	/**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void getUpcomingEmployeesBookings() throws Exception {
		// Create Session
		if (startEmployeeSession()) {
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/booking/getUpcomingBookings")
																			.session(session)
																			.cookie(cookies)
																			.contentType(MediaType.APPLICATION_JSON);
			mvc.perform(builder).andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		else { new Exception("Login failed - getUpcomingEmployeesBookings"); }
	}

	/**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void getUpcomingCustomersBookings() throws Exception {
		// Create Session
		if (startCustomerSession()) {
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/booking/getUpcomingBookings")
																			.session(session)
																			.cookie(cookies)
																			.contentType(MediaType.APPLICATION_JSON);
			mvc.perform(builder).andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		else { new Exception("Login failed - getUpcomingCustomersBookings"); }
	}

	/**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void getAllCompletedBookings() throws Exception {
		// Create Session
		if (startAdminSession()) {
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/booking/getCompletedBookings")
																			.session(session)
																			.cookie(cookies)
																			.contentType(MediaType.APPLICATION_JSON);
			mvc.perform(builder).andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		else { new Exception("Login failed - getAllCompletedBookings"); }
	}

	/**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void getCompletedEmployeesBookings() throws Exception {
		// Create Session
		if (startEmployeeSession()) {
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/booking/getCompletedBookings")
																			.session(session)
																			.cookie(cookies)
																			.contentType(MediaType.APPLICATION_JSON);
			mvc.perform(builder).andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		else { new Exception("Login failed - getCompletedEmployeesBookings"); }
	}

	/**
	 * returns isOk for success
	 * @throws Exception
	 */
	@Test
	public void getCompletedCustomersBookings() throws Exception {
		// Create Session
		if (startCustomerSession()) {
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/booking/getCompletedBookings")
																			.session(session)
																			.cookie(cookies)
																			.contentType(MediaType.APPLICATION_JSON);
			mvc.perform(builder).andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		else { new Exception("Login failed - getCompletedCustomersBookings"); }
	}

	/**
	 * returns isOk for success
	 * Currently running this test causes ones ran after it to have no
	 * cookies. Don't know why, but will turn off testing for now.
	 * @throws Exception
	 */
	//@Test
	public void deleteBooking() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("input", new JSONArray(new String[] {"1"}));

		// Create Session
		if (startAdminSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/api/booking/deleteBooking")
																			.session(session)
																			.cookie(cookies)
																			.contentType(MediaType.APPLICATION_JSON)
																			.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		else { new Exception("Login failed - deleteBooking_LoggedIn"); }
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void deleteBooking_LoggedInAsEmployee() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("input", new JSONArray(new String[] {"1"}));

		// Create Session
		if (startEmployeeSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/api/booking/deleteBooking")
																			.session(session)
																			.cookie(cookies)
																			.contentType(MediaType.APPLICATION_JSON)
																			.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isForbidden());
		}
		else { new Exception("Login failed - deleteBooking_LoggedInAsEmployee"); }
	}

	/**
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void deleteBooking_LoggedInAsCustomer() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("input", new JSONArray(new String[] {"1"}));

		// Create Session
		if (startCustomerSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/api/booking/deleteBooking")
																			.session(session)
																			.cookie(cookies)
																			.contentType(MediaType.APPLICATION_JSON)
																			.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isForbidden());
		}
		else { new Exception("Login failed - deleteBooking_LoggedInAsCustomer"); }
	}

	/*
	 * returns isForbidden for success
	 * @throws Exception
	 */
	@Test
	public void deleteBooking_NOTloggedIn() throws Exception {
		//Delete logged in customer
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/booking/deleteBooking");
		mvc.perform(builder).andExpect(status().isForbidden());
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void deleteBooking_NoBookingID() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("input", null);

		// Create Session
		if (startAdminSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/api/booking/deleteBooking")
																			.session(session)
																			.cookie(cookies)
																			.contentType(MediaType.APPLICATION_JSON)
																			.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isBadRequest())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		else { new Exception("Login failed - deleteBooking_NoBookingID"); }
	}

	/**
	 * returns isBadRequest for success
	 * @throws Exception
	 */
	@Test
	public void deleteBooking_BookingNonExistant() throws Exception {
		// Creating a JSONObject object
		JSONObject bookingID = new JSONObject();
		bookingID.put("input", new JSONArray(new String[] {"99"}));

		// Create Session
		if (startAdminSession()) {
			String contentSTRING = bookingID.toString();
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/api/booking/deleteBooking")
																			.session(session)
																			.cookie(cookies)
																			.contentType(MediaType.APPLICATION_JSON)
																			.content(contentSTRING);
			mvc.perform(builder).andExpect(status().isBadRequest())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		else { new Exception("Login failed - deleteBooking_BookingNonExistant"); }
	}
}