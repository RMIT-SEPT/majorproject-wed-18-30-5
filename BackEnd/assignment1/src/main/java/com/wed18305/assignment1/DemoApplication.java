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
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoApplication {

	//private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class);
	}

	// Comment out CommandLineRunner when not using it for testing OR when using mySQL
	@Bean
	public CommandLineRunner demo(User_Repository UserRepository, 
								  UserType_Repository TypeRepository, 
								  Service_Repository ServiceRepository, 
								  Booking_Repository BookingRepository,
								  Schedule_Repository ScheduleRepository,
								  User_Service UsrService,
								  PasswordEncoder passwordEncoder) {

		return (args) -> {
			//save the three types
			Entity_UserType admin = new Entity_UserType("admin");
			Entity_UserType employee = new Entity_UserType("employee");
			Entity_UserType customer = new Entity_UserType("customer");
			TypeRepository.save(admin);//1
			TypeRepository.save(employee);//2
			TypeRepository.save(customer);//3
			
			// Create admn/employee/customer(s)
			Entity_User jack = new Entity_User("Jack", "Jacky", passwordEncoder.encode("1234"), "0000000000", customer);
			Entity_User chloe = new Entity_User("Chloe", "O'Brian", passwordEncoder.encode("1234"), "0000000000", customer);
			Entity_User kim = new Entity_User("Kim", "Bauer", passwordEncoder.encode("1234"), "0000000000", customer);
			Entity_User david = new Entity_User("David", "Palmer", passwordEncoder.encode("1234"), "0000000000", admin);
			Entity_User michelle = new Entity_User("Michelle", "Dessler", passwordEncoder.encode("1234"), "0000000000", employee);
			Entity_User leslie = new Entity_User("Leslie", "Messler", passwordEncoder.encode("1234"), "0000000000", employee);		
			//Delete users - JUST used for JUnit test so that I can delete them
			Entity_User delete1 = new Entity_User("Joe","delete1",passwordEncoder.encode("1234"),"0000000000", customer);	
			Entity_User delete2 = new Entity_User("Joe","delete2",passwordEncoder.encode("1234"),"0000000000", employee);
			Entity_User delete3 = new Entity_User("Joe","delete3",passwordEncoder.encode("1234"),"0000000000", admin);		

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
			david.getServices().add(hotDogs);
			michelle.getServices().add(falafel);
			leslie.getServices().add(service3);
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
			OffsetDateTime dlong_ts = OffsetDateTime.parse("2020-09-22T00:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime dlong_te = OffsetDateTime.parse("2025-09-22T00:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime mlong_ts = OffsetDateTime.parse("2020-09-22T00:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime mlong_te = OffsetDateTime.parse("2025-09-22T00:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime llong_ts = OffsetDateTime.parse("2020-09-22T00:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime llong_te = OffsetDateTime.parse("2025-09-22T00:00+10:00", DateTimeStatic.getFormatter());
			Entity_Schedule scheduleDavid = new Entity_Schedule(d_ts, d_te);
			Entity_Schedule scheduleDavidLong = new Entity_Schedule(dlong_ts, dlong_te);
			Entity_Schedule scheduleMichelle = new Entity_Schedule(m_ts, m_te);
			Entity_Schedule scheduleMichelleLong = new Entity_Schedule(mlong_ts, mlong_te);
			Entity_Schedule scheduleLeslie = new Entity_Schedule(l_ts, l_te);
			Entity_Schedule scheduleLeslieLong = new Entity_Schedule(llong_ts, llong_te);
			ScheduleRepository.save(scheduleDavid);
			ScheduleRepository.save(scheduleDavidLong);
			ScheduleRepository.save(scheduleMichelle);
			ScheduleRepository.save(scheduleMichelleLong);
			ScheduleRepository.save(scheduleLeslie);
			ScheduleRepository.save(scheduleLeslieLong);

			//Add Schedules to employees
			david.getSchedules().add(scheduleDavid);
			michelle.getSchedules().add(scheduleMichelleLong);
			leslie.getSchedules().add(scheduleLeslie);
			delete1.getSchedules().add(scheduleLeslie);
			delete2.getSchedules().add(scheduleLeslie);
			delete3.getSchedules().add(scheduleLeslie);
			
			// Save Bookings
			Entity_Booking Booking1 = new Entity_Booking(OffsetDateTime.parse("2020-09-07T17:00+10:00", DateTimeStatic.getFormatter()), 
														OffsetDateTime.parse("2020-09-07T19:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking Booking2 = new Entity_Booking(OffsetDateTime.parse("3019-08-03T16:20+05:30", DateTimeStatic.getFormatter()),
																 OffsetDateTime.parse("3019-08-03T16:20+05:30", DateTimeStatic.getFormatter()));
			BookingRepository.save(Booking1);
			BookingRepository.save(Booking2);

			//Add bookings to users
			jack.getBookings().add(Booking1);
			michelle.getBookings().add(Booking1);
			jack.getBookings().add(Booking2);
			michelle.getBookings().add(Booking2);

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
