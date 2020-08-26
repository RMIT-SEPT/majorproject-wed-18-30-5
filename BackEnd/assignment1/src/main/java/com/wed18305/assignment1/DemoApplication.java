package com.wed18305.assignment1;

import com.wed18305.assignment1.config.DateTimeStatic;
import com.wed18305.assignment1.model.Booking;
import com.wed18305.assignment1.model.Schedule;
import com.wed18305.assignment1.model.Service;
import com.wed18305.assignment1.model.User_model;
import com.wed18305.assignment1.model.UserType;
import com.wed18305.assignment1.repositories.Booking_Repository;
import com.wed18305.assignment1.repositories.Schedule_Repository;
import com.wed18305.assignment1.repositories.Service_Repository;
import com.wed18305.assignment1.repositories.UserType_Repository;
import com.wed18305.assignment1.repositories.User_Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

	public CommandLineRunner demo(User_Repository UserRepository, 
								  UserType_Repository TypeRepository, 
								  Service_Repository ServiceRepository, 
								  Booking_Repository BookingRepository,
								  Schedule_Repository ScheduleRepository) {

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

			// save a few customers
			UserRepository.save(jack);
			UserRepository.save(chloe);
			UserRepository.save(kim);
			UserRepository.save(david);
			UserRepository.save(michelle);

			// Save And Print Booking Data
			// BookingRepository.save(new Booking(LocalDateTime.parse("1999-01-01 12:30", formatter), LocalDateTime.parse("1999-01-01 12:30", formatter), customers, employees));

			// Save Services
			Service falafel = new Service("Freddie's Falafels");
			Service hotDogs = new Service("Joe's HotDogs");
			ServiceRepository.save(falafel);
			ServiceRepository.save(hotDogs);

			//Create employees list
			List<User_model> employees = new ArrayList<User_model>();
			employees.add(chloe);
			employees.add(jack);

			//Give the employees services
			chloe.getServices().add(falafel);
			chloe.getServices().add(hotDogs);
			jack.getServices().add(hotDogs);
			david.getServices().add(hotDogs);
			//update the users now that there attributes have been changed
			UserRepository.save(chloe);
			UserRepository.save(jack);
			UserRepository.save(david);

			//Create schedules for employees
			Schedule s1 = new Schedule(LocalDateTime.parse("1999-01-01 12:30", DateTimeStatic.getFormatter()),
										LocalDateTime.parse("1999-01-01 13:30", DateTimeStatic.getFormatter()),
										employees);
			ScheduleRepository.save(s1);
			Schedule s2 = new Schedule(LocalDateTime.parse("1999-01-01 13:30", DateTimeStatic.getFormatter()),
										LocalDateTime.parse("1999-01-01 14:30", DateTimeStatic.getFormatter()),
										employees);
			ScheduleRepository.save(s2);	
      	};
	}
}
