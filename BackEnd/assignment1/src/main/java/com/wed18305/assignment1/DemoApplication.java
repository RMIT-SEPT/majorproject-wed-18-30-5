package com.wed18305.assignment1;

import com.wed18305.assignment1.config.DateTimeStatic;
import com.wed18305.assignment1.model.Entity_Booking;
import com.wed18305.assignment1.model.Entity_Service;
import com.wed18305.assignment1.model.Entity_User;
import com.wed18305.assignment1.model.Entity_UserType;
import com.wed18305.assignment1.repositories.Booking_Repository;
import com.wed18305.assignment1.repositories.Schedule_Repository;
import com.wed18305.assignment1.repositories.Service_Repository;
import com.wed18305.assignment1.repositories.UserType_Repository;
import com.wed18305.assignment1.repositories.User_Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import com.wed18305.assignment1.services.User_Service;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	//private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class);
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
			UserRepository.save(leslie);

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
			ArrayList<Entity_User> customers = new ArrayList<>();
			customers.add(jack);
			customers.add(chloe);
			ArrayList<Entity_User> employees = new ArrayList<>();
			employees.add(michelle);
			Entity_Booking upcomingBooking = new Entity_Booking(OffsetDateTime.parse("2019-08-03T16:20+05:30", DateTimeStatic.getFormatter()), 
																OffsetDateTime.parse("2019-08-03T16:20+05:30", DateTimeStatic.getFormatter()), 
														customers,
														employees);
			Entity_Booking completedBooking = new Entity_Booking(OffsetDateTime.parse("3019-08-03T16:20+05:30", DateTimeStatic.getFormatter()), 
																 OffsetDateTime.parse("3019-08-03T16:20+05:30", DateTimeStatic.getFormatter()), 
														customers,
														employees);
			BookingRepository.save(upcomingBooking);
			BookingRepository.save(completedBooking);
      	};
	}
}
