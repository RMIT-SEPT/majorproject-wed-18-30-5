package com.wed18305.assignment1;

import com.wed18305.assignment1.model.Entity_Booking;
import com.wed18305.assignment1.model.Entity_Service;
import com.wed18305.assignment1.model.Entity_User;
import com.wed18305.assignment1.model.Entity_UserType;
import com.wed18305.assignment1.repositories.Booking_Repository;
import com.wed18305.assignment1.repositories.Schedule_Repository;
import com.wed18305.assignment1.repositories.Service_Repository;
import com.wed18305.assignment1.repositories.UserType_Repository;
import com.wed18305.assignment1.repositories.User_Repository;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.wed18305.assignment1.services.User_Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DemoApplication {

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class);
	}

//	@Configuration
//	public class CorsConfiguration
//	{
//		@Bean
//		public WebMvcConfigurer corsConfigurer()
//		{
//			return new WebMvcConfigurer() {
//				@Override
//				public void addCorsMappings(CorsRegistry registry) {
//					registry.addMapping("/**").allowedOrigins("http://localhost:3000");
//				}
//			};
//		}
//	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000");
			}
		};
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
								  Schedule_Repository ScheduleRepository,
								  User_Service UsrService) {

		return (args) -> {
			//save the three types
			Entity_UserType admin = new Entity_UserType("admin");
			Entity_UserType employee = new Entity_UserType("employee");
			Entity_UserType customer = new Entity_UserType("customer");
			TypeRepository.save(admin);//1
			TypeRepository.save(employee);//2
			TypeRepository.save(customer);//3
 
			Entity_User jack = new Entity_User("Jack", "Jacky", "1234", "0000000000", customer);
			Entity_User chloe = new Entity_User("Chloe", "O'Brian", "1234", "0000000000", customer);
			Entity_User kim = new Entity_User("Kim", "Bauer", "1234", "0000000000", customer);
			Entity_User david = new Entity_User("David", "Palmer", "1234", "0000000000", admin);
			Entity_User michelle = new Entity_User("Michelle", "Dessler", "1234", "0000000000", employee);
			Entity_User leslie = new Entity_User("Leslie", "Messler", "1234", "0000000000", employee);			

			// save a few customers
			UserRepository.save(jack);
			UserRepository.save(chloe);
			UserRepository.save(kim);
			UserRepository.save(david);
			UserRepository.save(michelle);

			// //Create schedules for employees
			// ArrayList<User_model> employees = new ArrayList<User_model>();
			// employees.add(chloe);
			// employees.add(jack);
			// ArrayList<Schedule> schedules = new ArrayList<Schedule>();
			// Schedule s1 = new Schedule(LocalDateTime.parse("1999-01-01 12:30", DateTimeStatic.getFormatter()),
			// 							LocalDateTime.parse("1999-01-01 13:30", DateTimeStatic.getFormatter()));
			// ScheduleRepository.save(s1);
			// Schedule s2 = new Schedule(LocalDateTime.parse("1999-01-01 13:30", DateTimeStatic.getFormatter()),
			// 							LocalDateTime.parse("1999-01-01 14:30", DateTimeStatic.getFormatter()));
			// ScheduleRepository.save(s2);
			// schedules.add(s1);
			// schedules.add(s2);
			// UsrService.addSchedulesToEmployees(employees, schedules);

			// Save And Print Booking Data
			// BookingRepository.save(new Booking(LocalDateTime.parse("1999-01-01 12:30", formatter), LocalDateTime.parse("1999-01-01 12:30", formatter), customers, employees));

			// Save Services
			Entity_Service falafel = new Entity_Service("Freddie's Falafels");
			Entity_Service hotDogs = new Entity_Service("Joe's HotDogs");
			Entity_Service service3 = new Entity_Service("Service3");
			Entity_Service service4 = new Entity_Service("Service4");
			Entity_Service service5 = new Entity_Service("Service5");
			ServiceRepository.save(falafel);
			ServiceRepository.save(hotDogs);
			ServiceRepository.save(service3);
			ServiceRepository.save(service4);
			ServiceRepository.save(service5);

			// Save Bookings
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm:ss z"); // "Day, Month, Year, Hour, Minute."
			ArrayList<Entity_User> customers = new ArrayList<>();
			customers.add(jack);
			customers.add(chloe);
			ArrayList<Entity_User> employees = new ArrayList<>();
			employees.add(michelle);

			Entity_Booking booking = new Entity_Booking(LocalDateTime.parse("03/08/2019T16:20:00 UTC+05:30", formatter), 
														LocalDateTime.parse("03/08/2019T16:20:00 UTC+05:30", formatter), 
														customers,
														employees,
														falafel);
			BookingRepository.save(booking);
      	};
	}
}
