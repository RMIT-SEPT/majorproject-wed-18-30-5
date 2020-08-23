package com.wed18305.assignment1;


import com.wed18305.assignment1.model.Booking;
import com.wed18305.assignment1.model.Service;
import com.wed18305.assignment1.model.User_model;
import com.wed18305.assignment1.model.UserType;
import com.wed18305.assignment1.repositories.Booking_Repository;
import com.wed18305.assignment1.repositories.Service_Repository;
import com.wed18305.assignment1.repositories.UserType_Repository;
import com.wed18305.assignment1.repositories.User_Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@SpringBootApplication
public class DemoApplication {

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class);
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
    	return new HttpSessionEventPublisher();
	}

	// Comment out CommandLineRunner when not using it for testing
	@Bean

	public CommandLineRunner demo(User_Repository UserRepository, UserType_Repository TypeRepository, Service_Repository ServiceRepository) {

		return (args) -> {
			//save the three types
			UserType admin = new UserType("admin");
			UserType employee = new UserType("employee");
			UserType customer = new UserType("customer");
			TypeRepository.save(admin);//1
			TypeRepository.save(employee);//2
			TypeRepository.save(customer);//3
 
			User_model jack = new User_model("Jack", "Jacky", "1234", "0000000000", customer);
			User_model chloe = new User_model("Chloe", "O'Brian", "1234", "0000000000", customer);
			User_model kim = new User_model("Kim", "Bauer", "1234", "0000000000", customer);
			User_model david = new User_model("David", "Palmer", "1234", "0000000000", admin);
			User_model michelle = new User_model("Michelle", "Dessler", "1234", "0000000000", employee);
			User_model leslie = new User_model("Leslie", "Messler", "1234", "0000000000", employee);
			// User_model[] customers = new User_model[]{ jack, chloe, kim};
			// User_model[] employees = new User_model[]{ michelle, leslie};			

			// save a few customers
			UserRepository.save(jack);
			UserRepository.save(chloe);
			UserRepository.save(kim);
			UserRepository.save(david);
			UserRepository.save(michelle);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // "Day, Month, Year, Hour, Minute."

			// Save And Print Booking Data
			// bookingRepository.save(new Booking(LocalDateTime.parse("1999-01-01 12:30", formatter), LocalDateTime.parse("1999-01-01 12:30", formatter), customers, employees));

			// Save Services
			Service falafel = new Service("Freddie's Falafels");
			
			ServiceRepository.save(falafel);

			// fetch all types
			log.info("User types found with findAll():");
			log.info("-------------------------------");
			for (UserType userType : TypeRepository.findAll()) {
				log.info(userType.toString());
			}
			log.info("");

			// fetch all users
			log.info("Users found with findAll():");
			log.info("-------------------------------");
			for (User_model user : UserRepository.findAll()) {
				log.info(user.toString());
			}
			log.info("");

			// // fetch all users
			// log.info("Bookings found with findAll():");
			// log.info("-------------------------------");
			// for (Booking booking : bookingRepository.findAll()) {
			// 	log.info(booking.toString());
			// }
			// log.info("");

			// Fetch All Services
			log.info("Services found with findAll():");
			log.info("-------------------------------");
			for (Service service: ServiceRepository.findAll()) {
				log.info(service.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			ArrayList<Long> ids = new ArrayList<Long>(Arrays.asList((long) 1, (long) 2));
			Iterable<User_model> user = UserRepository.findAllById(ids);

			log.info("Customer found with findById('1','2'):");
			log.info("--------------------------------");
			log.info(user.toString());
			log.info("");

			// fetch customers by last name
			log.info("Customer found with findByNameAndPassword('David','1234'):");
			log.info("--------------------------------------------");
			Optional<User_model> user1 = UserRepository.findByUsernameAndPassword("David", "1234");

			if(user1.isPresent()){
				log.info(user1.get().toString());
			}
			log.info("");
      	};
	}
}
