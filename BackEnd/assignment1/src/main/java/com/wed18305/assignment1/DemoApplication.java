package com.wed18305.assignment1;

import com.wed18305.assignment1.config.DateTimeStatic;
import com.wed18305.assignment1.model.*;
import com.wed18305.assignment1.repositories.Booking_Repository;
import com.wed18305.assignment1.repositories.Schedule_Repository;
import com.wed18305.assignment1.repositories.Service_Repository;
import com.wed18305.assignment1.repositories.UserType_Repository;
import com.wed18305.assignment1.repositories.User_Repository;

import java.time.OffsetDateTime;

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
			
			// Create admn/employee/customer(s)
			Entity_User jack = new Entity_User("Jack", "Jacky", "1234", "0000000000", customer);
			Entity_User chloe = new Entity_User("Chloe", "O'Brian", "1234", "0000000000", customer);
			Entity_User kim = new Entity_User("Kim", "Bauer", "1234", "0000000000", customer);
			Entity_User david = new Entity_User("David", "Palmer", "1234", "0000000000", admin);
			Entity_User michelle = new Entity_User("Michelle", "Dessler", "1234", "0000000000", employee);
			Entity_User leslie = new Entity_User("Leslie", "Messler", "1234", "0000000000", employee);		
			//Delete users - JUST used for JUnit test so that I can delete them
			Entity_User delete1 = new Entity_User("Joe","delete1","1234","0000000000", customer);	
			Entity_User delete2 = new Entity_User("Joe","delete2","1234","0000000000", employee);
			Entity_User delete3 = new Entity_User("Joe","delete3","1234","0000000000", admin);		

			//Save the users
			UserRepository.save(jack);
			UserRepository.save(chloe);
			UserRepository.save(kim);
			UserRepository.save(david);
			UserRepository.save(michelle);
			UserRepository.save(leslie);
			UserRepository.save(delete1);
			UserRepository.save(delete2);
			UserRepository.save(delete3);

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

			//Add services to employees, david, michelle, leslie
			david.getServices().add(falafel);
			david.getServices().add(hotDogs);
			michelle.getServices().add(falafel);
			michelle.getServices().add(hotDogs);
			michelle.getServices().add(service5);
			leslie.getServices().add(falafel);
			leslie.getServices().add(hotDogs);
			leslie.getServices().add(service3);
			leslie.getServices().add(service4);
			leslie.getServices().add(service5);
			delete1.getServices().add(service5);
			delete2.getServices().add(service5);
			delete3.getServices().add(service5);

			//Save Schedules 
			//Date 2020-09-07
			//UTC melbourne +10
			//david 0900-1700, michelle 1700-2400, leslie 0500-1200
			OffsetDateTime d_ts = OffsetDateTime.parse("2020-09-07T09:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime d_te = OffsetDateTime.parse("2020-09-07T17:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime m_ts = OffsetDateTime.parse("2020-09-07T17:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime m_te = OffsetDateTime.parse("2020-09-07T24:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime l_ts = OffsetDateTime.parse("2020-09-07T05:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime l_te = OffsetDateTime.parse("2020-09-07T12:00+10:00", DateTimeStatic.getFormatter());
			Entity_Schedule scheduleDavid = new Entity_Schedule(d_ts, d_te);
			Entity_Schedule scheduleMichelle = new Entity_Schedule(m_ts, m_te);
			Entity_Schedule scheduleLeslie = new Entity_Schedule(l_ts, l_te);
			ScheduleRepository.save(scheduleDavid);
			ScheduleRepository.save(scheduleMichelle);
			ScheduleRepository.save(scheduleLeslie);

			//Add Schedules to employees
			david.getSchedules().add(scheduleDavid);
			michelle.getSchedules().add(scheduleMichelle);
			leslie.getSchedules().add(scheduleLeslie);
			delete1.getSchedules().add(scheduleLeslie);
			delete2.getSchedules().add(scheduleLeslie);
			delete3.getSchedules().add(scheduleLeslie);
			
			// Save Bookings
			Entity_Booking Booking = new Entity_Booking(OffsetDateTime.parse("2020-09-07T17:00+10:00", DateTimeStatic.getFormatter()), 
														OffsetDateTime.parse("2020-09-07T19:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking completedBooking = new Entity_Booking(OffsetDateTime.parse("3019-08-03T16:20+05:30", DateTimeStatic.getFormatter()),
																 OffsetDateTime.parse("3019-08-03T16:20+05:30", DateTimeStatic.getFormatter()));
			BookingRepository.save(Booking);
			BookingRepository.save(completedBooking);

			//Add bookings to users
			jack.getBookings().add(Booking);
			chloe.getBookings().add(Booking);
			michelle.getBookings().add(Booking);
			jack.getBookings().add(completedBooking);
			michelle.getBookings().add(completedBooking);
			delete1.getBookings().add(completedBooking);
			delete2.getBookings().add(completedBooking);
			delete3.getBookings().add(completedBooking);

			//Update the users
			UserRepository.save(jack);
			UserRepository.save(chloe);
			UserRepository.save(kim);
			UserRepository.save(david);
			UserRepository.save(michelle);
			UserRepository.save(leslie);
			UserRepository.save(delete1);
			UserRepository.save(delete2);
			UserRepository.save(delete3);
      	};
	}
}
