package com.wed18305.assignment1;

import com.wed18305.assignment1.config.DateTimeStatic;
import com.wed18305.assignment1.model.*;
import com.wed18305.assignment1.repositories.Booking_Repository;
import com.wed18305.assignment1.repositories.Schedule_Repository;
import com.wed18305.assignment1.repositories.Service_Repository;
import com.wed18305.assignment1.repositories.UserType_Repository;
import com.wed18305.assignment1.repositories.User_Repository;

import java.time.OffsetDateTime;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import com.wed18305.assignment1.services.User_Service;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@SpringBootApplication
public class DemoApplication {

	//private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class);
	}

	@PostConstruct
    public void init(){
      // Setting Spring Boot SetTimeZone
      TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	
	@Bean
	public CookieSerializer cookieSerializer() {
		DefaultCookieSerializer serializer = new DefaultCookieSerializer();
		serializer.setCookieName("JSESSIONID"); 
		serializer.setCookiePath("/"); 
		serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
		serializer.setSameSite("None");
		serializer.setUseHttpOnlyCookie(true);
		serializer.setUseSecureCookie(false);
		return serializer;
	}

	//Comment out CommandLineRunner when not using it for testing OR when using mySQL
	@Bean
	public CommandLineRunner demo(User_Repository UserRepository, 
								  UserType_Repository TypeRepository, 
								  Service_Repository ServiceRepository, 
								  Booking_Repository BookingRepository,
								  Schedule_Repository ScheduleRepository,
								  User_Service UsrService,
								  PasswordEncoder passwordEncoder) {

		return (args) -> {
			// Save the three user types
			Entity_UserType admin = new Entity_UserType("admin");
			Entity_UserType employee = new Entity_UserType("employee");
			Entity_UserType customer = new Entity_UserType("customer");
			TypeRepository.save(admin);//1
			TypeRepository.save(employee);//2
			TypeRepository.save(customer);//3
			
			// Create admn/employee/customer(s)
			Entity_User jackCustomer = new Entity_User("Jack", "Jacky", passwordEncoder.encode("1234"), "0000000000", "42 somewhere street", customer);
			Entity_User chloeCustomer = new Entity_User("Chloe", "O'Brian", passwordEncoder.encode("1234"), "0000000000", "42 somewhere street", customer);
			Entity_User kimCustomer = new Entity_User("Kim", "Bauer", passwordEncoder.encode("1234"), "0000000000", "42 somewhere street", customer);
			Entity_User davidAdmin = new Entity_User("David", "Palmer", passwordEncoder.encode("1234"), "0000000000", "42 somewhere street", admin);
			Entity_User michelleEmployee = new Entity_User("Michelle", "Dessler", passwordEncoder.encode("1234"), "0000000000", "42 somewhere street", employee);
			Entity_User leslieEmployee = new Entity_User("Leslie", "Messler", passwordEncoder.encode("1234"), "0000000000", "42 somewhere street", employee);
			Entity_User karinEmployee = new Entity_User("Karin", "Kalamari", passwordEncoder.encode("1234"), "0000000000", "42 somewhere street", employee);
			Entity_User lammyEmployee = new Entity_User("Lammy", "Lam", passwordEncoder.encode("1234"), "0000000000", "42 somewhere street", employee);
			Entity_User rammyEmployee = new Entity_User("Rammy", "Ram", passwordEncoder.encode("1234"), "0000000000", "42 somewhere street", employee);	
			Entity_User jamesEmployee = new Entity_User("James", "Jones", passwordEncoder.encode("1234"), "0000000000", "42 somewhere street", employee);
			Entity_User fredEmployee = new Entity_User("Fred", "Flinstone", passwordEncoder.encode("1234"), "0000000000", "42 somewhere street", employee);
			Entity_User JoelEmployee = new Entity_User("Joel", "Stevens", passwordEncoder.encode("1234"), "0000000000", "42 somewhere street", employee);		
			// Delete users - JUST used for JUnit test so that I can delete them
			Entity_User delete1 = new Entity_User("Joe","delete1",passwordEncoder.encode("1234"),"0000000000","42 somewhere street", customer);	
			Entity_User delete2 = new Entity_User("Joe","delete2",passwordEncoder.encode("1234"),"0000000000","42 somewhere street", employee);
			Entity_User delete3 = new Entity_User("Joe","delete3",passwordEncoder.encode("1234"),"0000000000","42 somewhere street", admin);		

			// Save the users
			UserRepository.save(jackCustomer);
			UserRepository.save(chloeCustomer);
			UserRepository.save(kimCustomer);
			UserRepository.save(davidAdmin);
			UserRepository.save(michelleEmployee);
			UserRepository.save(leslieEmployee);
			UserRepository.save(karinEmployee);
			UserRepository.save(lammyEmployee);
			UserRepository.save(rammyEmployee);
			UserRepository.save(jamesEmployee);
			UserRepository.save(fredEmployee);
			UserRepository.save(JoelEmployee);
			UserRepository.save(delete1);
			UserRepository.save(delete2);
			UserRepository.save(delete3);

			// Save Services
			Entity_Service standardConsult = new Entity_Service("Standard Consultation(15 min)",15);
			Entity_Service longConsult = new Entity_Service("Long Consultation(30 min)",30);
			Entity_Service skinCheck = new Entity_Service("Skin check(1 hour)",60);
			Entity_Service papSmear = new Entity_Service("Pap smear(30 min)",30);
			Entity_Service travelVac = new Entity_Service("Travel vaccination(10 min)",10);
			ServiceRepository.save(standardConsult);
			ServiceRepository.save(longConsult);
			ServiceRepository.save(skinCheck);
			ServiceRepository.save(papSmear);
			ServiceRepository.save(travelVac);

			// Add services to employees, david, michelle, leslie
			michelleEmployee.getServices().add(standardConsult);
			leslieEmployee.getServices().add(skinCheck);
			karinEmployee.getServices().add(longConsult);
			lammyEmployee.getServices().add(papSmear);
			rammyEmployee.getServices().add(travelVac);
			jamesEmployee.getServices().add(standardConsult);
			fredEmployee.getServices().add(longConsult);
			JoelEmployee.getServices().add(skinCheck);
			delete1.getServices().add(travelVac);
			delete2.getServices().add(travelVac);
			delete3.getServices().add(travelVac);

			// Save Schedules -UTC melbourne +10
			// Michelle Schedule(standard consult) -Mon(19th)->Thurs(22nd) 9am to 6pm
			OffsetDateTime m_timeStart1 = OffsetDateTime.parse("2020-10-19T09:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime m_timeEnd1 = OffsetDateTime.parse("2020-10-19T18:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime m_timeStart2 = OffsetDateTime.parse("2020-10-20T09:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime m_timeEnd2 = OffsetDateTime.parse("2020-10-20T18:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime m_timeStart3 = OffsetDateTime.parse("2020-10-21T09:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime m_timeEnd3 = OffsetDateTime.parse("2020-10-21T18:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime m_timeStart4 = OffsetDateTime.parse("2020-10-22T09:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime m_timeEnd4 = OffsetDateTime.parse("2020-10-22T18:00+10:00", DateTimeStatic.getFormatter());
			Entity_Schedule m_schedule1 = new Entity_Schedule(m_timeStart1, m_timeEnd1);
			Entity_Schedule m_schedule2 = new Entity_Schedule(m_timeStart2, m_timeEnd2);
			Entity_Schedule m_schedule3 = new Entity_Schedule(m_timeStart3, m_timeEnd3);
			Entity_Schedule m_schedule4 = new Entity_Schedule(m_timeStart4, m_timeEnd4);
			// Leslie Schedule(Skin check) -Mon(19th)->Wed(21st)->Fri(23rd) 10am to 5pm
			OffsetDateTime l_timeStart1 = OffsetDateTime.parse("2020-10-19T10:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime l_timeEnd1 = OffsetDateTime.parse("2020-10-19T17:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime l_timeStart2 = OffsetDateTime.parse("2020-10-21T10:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime l_timeEnd2 = OffsetDateTime.parse("2020-10-21T17:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime l_timeStart3 = OffsetDateTime.parse("2020-10-23T10:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime l_timeEnd3 = OffsetDateTime.parse("2020-10-23T17:00+10:00", DateTimeStatic.getFormatter());
			Entity_Schedule l_schedule1 = new Entity_Schedule(l_timeStart1, l_timeEnd1);
			Entity_Schedule l_schedule2 = new Entity_Schedule(l_timeStart2, l_timeEnd2);
			Entity_Schedule l_schedule3 = new Entity_Schedule(l_timeStart3, l_timeEnd3);
			// Karin Schedule(Long consult) -Tue(20th)->Fri(23rd) 8am to 4pm
			OffsetDateTime k_timeStart1 = OffsetDateTime.parse("2020-10-20T08:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime k_timeEnd1 = OffsetDateTime.parse("2020-10-20T16:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime k_timeStart2 = OffsetDateTime.parse("2020-10-21T08:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime k_timeEnd2 = OffsetDateTime.parse("2020-10-21T16:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime k_timeStart3 = OffsetDateTime.parse("2020-10-22T08:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime k_timeEnd3 = OffsetDateTime.parse("2020-10-22T16:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime k_timeStart4 = OffsetDateTime.parse("2020-10-23T08:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime k_timeEnd4 = OffsetDateTime.parse("2020-10-23T16:00+10:00", DateTimeStatic.getFormatter());
			Entity_Schedule k_schedule1 = new Entity_Schedule(k_timeStart1, k_timeEnd1);
			Entity_Schedule k_schedule2 = new Entity_Schedule(k_timeStart2, k_timeEnd2);
			Entity_Schedule k_schedule3 = new Entity_Schedule(k_timeStart3, k_timeEnd3);
			Entity_Schedule k_schedule4 = new Entity_Schedule(k_timeStart4, k_timeEnd4);
			// Lammy Schedule(Pap smear) -Mon(19th) and Thurs(22th) 10am to 2pm
			OffsetDateTime lam_timeStart1 = OffsetDateTime.parse("2020-10-19T10:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime lam_timeEnd1 = OffsetDateTime.parse("2020-10-19T14:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime lam_timeStart2 = OffsetDateTime.parse("2020-10-22T10:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime lam_timeEnd2 = OffsetDateTime.parse("2020-10-22T14:00+10:00", DateTimeStatic.getFormatter());
			Entity_Schedule lam_schedule1 = new Entity_Schedule(lam_timeStart1, lam_timeEnd1);
			Entity_Schedule lam_schedule2 = new Entity_Schedule(lam_timeStart2, lam_timeEnd2);
			// Rammy Schedule(Travel vac) -Mon(19th)->Wed(21th) 10am to 12am
			OffsetDateTime Ram_timeStart1 = OffsetDateTime.parse("2020-10-19T10:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime Ram_timeEnd1 = OffsetDateTime.parse("2020-10-19T12:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime Ram_timeStart2 = OffsetDateTime.parse("2020-10-20T10:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime Ram_timeEnd2 = OffsetDateTime.parse("2020-10-20T12:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime Ram_timeStart3 = OffsetDateTime.parse("2020-10-21T10:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime Ram_timeEnd3 = OffsetDateTime.parse("2020-10-21T12:00+10:00", DateTimeStatic.getFormatter());
			Entity_Schedule Ram_schedule1 = new Entity_Schedule(Ram_timeStart1, Ram_timeEnd1);
			Entity_Schedule Ram_schedule2 = new Entity_Schedule(Ram_timeStart2, Ram_timeEnd2);
			Entity_Schedule Ram_schedule3 = new Entity_Schedule(Ram_timeStart3, Ram_timeEnd3);
			// James Schedule(Standard consult) -Mon(19th)->Fri(23th) 10am to 7pm
			OffsetDateTime j_timeStart1 = OffsetDateTime.parse("2020-10-19T10:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime j_timeEnd1 = OffsetDateTime.parse("2020-10-19T19:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime j_timeStart2 = OffsetDateTime.parse("2020-10-20T10:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime j_timeEnd2 = OffsetDateTime.parse("2020-10-20T19:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime j_timeStart3 = OffsetDateTime.parse("2020-10-21T10:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime j_timeEnd3 = OffsetDateTime.parse("2020-10-21T19:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime j_timeStart4 = OffsetDateTime.parse("2020-10-22T10:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime j_timeEnd4 = OffsetDateTime.parse("2020-10-22T19:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime j_timeStart5 = OffsetDateTime.parse("2020-10-23T10:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime j_timeEnd5 = OffsetDateTime.parse("2020-10-23T19:00+10:00", DateTimeStatic.getFormatter());
			Entity_Schedule j_schedule1 = new Entity_Schedule(j_timeStart1, j_timeEnd1);
			Entity_Schedule j_schedule2 = new Entity_Schedule(j_timeStart2, j_timeEnd2);
			Entity_Schedule j_schedule3 = new Entity_Schedule(j_timeStart3, j_timeEnd3);
			Entity_Schedule j_schedule4 = new Entity_Schedule(j_timeStart4, j_timeEnd4);
			Entity_Schedule j_schedule5 = new Entity_Schedule(j_timeStart5, j_timeEnd5);
			// Fred Schedule(Long consult) -Mon(19th)->Thurs(22rd) 9am to 5pm
			OffsetDateTime f_timeStart1 = OffsetDateTime.parse("2020-10-19T09:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime f_timeEnd1 = OffsetDateTime.parse("2020-10-19T17:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime f_timeStart2 = OffsetDateTime.parse("2020-10-20T09:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime f_timeEnd2 = OffsetDateTime.parse("2020-10-20T17:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime f_timeStart3 = OffsetDateTime.parse("2020-10-21T09:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime f_timeEnd3 = OffsetDateTime.parse("2020-10-21T17:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime f_timeStart4 = OffsetDateTime.parse("2020-10-22T09:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime f_timeEnd4 = OffsetDateTime.parse("2020-10-22T17:00+10:00", DateTimeStatic.getFormatter());
			Entity_Schedule f_schedule1 = new Entity_Schedule(f_timeStart1, f_timeEnd1);
			Entity_Schedule f_schedule2 = new Entity_Schedule(f_timeStart2, f_timeEnd2);
			Entity_Schedule f_schedule3 = new Entity_Schedule(f_timeStart3, f_timeEnd3);
			Entity_Schedule f_schedule4 = new Entity_Schedule(f_timeStart4, f_timeEnd4);
			// Joel Schedule(Skin check) -Tue(20th)->Sat(24th) 10am to 5pm
			OffsetDateTime joel_timeStart1 = OffsetDateTime.parse("2020-10-20T10:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime joel_timeEnd1 = OffsetDateTime.parse("2020-10-20T17:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime joel_timeStart2 = OffsetDateTime.parse("2020-10-21T10:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime joel_timeEnd2 = OffsetDateTime.parse("2020-10-21T17:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime joel_timeStart3 = OffsetDateTime.parse("2020-10-22T10:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime joel_timeEnd3 = OffsetDateTime.parse("2020-10-22T17:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime joel_timeStart4 = OffsetDateTime.parse("2020-10-23T10:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime joel_timeEnd4 = OffsetDateTime.parse("2020-10-23T17:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime joel_timeStart5 = OffsetDateTime.parse("2020-10-24T10:00+10:00", DateTimeStatic.getFormatter());
			OffsetDateTime joel_timeEnd5 = OffsetDateTime.parse("2020-10-24T17:00+10:00", DateTimeStatic.getFormatter());
			Entity_Schedule joel_schedule1 = new Entity_Schedule(joel_timeStart1, joel_timeEnd1);
			Entity_Schedule joel_schedule2 = new Entity_Schedule(joel_timeStart2, joel_timeEnd2);
			Entity_Schedule joel_schedule3 = new Entity_Schedule(joel_timeStart3, joel_timeEnd3);
			Entity_Schedule joel_schedule4 = new Entity_Schedule(joel_timeStart4, joel_timeEnd4);
			Entity_Schedule joel_schedule5 = new Entity_Schedule(joel_timeStart5, joel_timeEnd5);

			// Save the schedules
			ScheduleRepository.save(m_schedule1);
			ScheduleRepository.save(m_schedule2);
			ScheduleRepository.save(m_schedule3);
			ScheduleRepository.save(m_schedule4);
			ScheduleRepository.save(l_schedule1);
			ScheduleRepository.save(l_schedule2);
			ScheduleRepository.save(l_schedule3);
			ScheduleRepository.save(k_schedule1);
			ScheduleRepository.save(k_schedule2);
			ScheduleRepository.save(k_schedule3);
			ScheduleRepository.save(k_schedule4);
			ScheduleRepository.save(lam_schedule1);
			ScheduleRepository.save(lam_schedule2);
			ScheduleRepository.save(Ram_schedule1);
			ScheduleRepository.save(Ram_schedule2);
			ScheduleRepository.save(Ram_schedule3);
			ScheduleRepository.save(j_schedule1);
			ScheduleRepository.save(j_schedule2);
			ScheduleRepository.save(j_schedule3);
			ScheduleRepository.save(j_schedule4);
			ScheduleRepository.save(j_schedule5);
			ScheduleRepository.save(f_schedule1);
			ScheduleRepository.save(f_schedule2);
			ScheduleRepository.save(f_schedule3);
			ScheduleRepository.save(f_schedule4);
			ScheduleRepository.save(joel_schedule1);
			ScheduleRepository.save(joel_schedule2);
			ScheduleRepository.save(joel_schedule3);
			ScheduleRepository.save(joel_schedule4);
			ScheduleRepository.save(joel_schedule5);


			// Add Schedules to employees
			michelleEmployee.getSchedules().add(m_schedule1);
			michelleEmployee.getSchedules().add(m_schedule2);
			michelleEmployee.getSchedules().add(m_schedule3);
			michelleEmployee.getSchedules().add(m_schedule4);
			leslieEmployee.getSchedules().add(l_schedule1);
			leslieEmployee.getSchedules().add(l_schedule2);
			leslieEmployee.getSchedules().add(l_schedule3);
			karinEmployee.getSchedules().add(m_schedule1);
			karinEmployee.getSchedules().add(m_schedule2);
			karinEmployee.getSchedules().add(m_schedule3);
			karinEmployee.getSchedules().add(m_schedule4);
			lammyEmployee.getSchedules().add(lam_schedule1);
			lammyEmployee.getSchedules().add(lam_schedule2);
			rammyEmployee.getSchedules().add(Ram_schedule1);
			rammyEmployee.getSchedules().add(Ram_schedule2);
			rammyEmployee.getSchedules().add(Ram_schedule3);
			jamesEmployee.getSchedules().add(j_schedule1);
			jamesEmployee.getSchedules().add(j_schedule2);
			jamesEmployee.getSchedules().add(j_schedule3);
			jamesEmployee.getSchedules().add(j_schedule4);
			jamesEmployee.getSchedules().add(j_schedule5);
			fredEmployee.getSchedules().add(f_schedule1);
			fredEmployee.getSchedules().add(f_schedule2);
			fredEmployee.getSchedules().add(f_schedule3);
			fredEmployee.getSchedules().add(f_schedule4);
			JoelEmployee.getSchedules().add(joel_schedule1);
			JoelEmployee.getSchedules().add(joel_schedule2);
			JoelEmployee.getSchedules().add(joel_schedule3);
			JoelEmployee.getSchedules().add(joel_schedule4);
			JoelEmployee.getSchedules().add(joel_schedule5);
			// These are used for testing
			delete1.getSchedules().add(f_schedule1);
			delete2.getSchedules().add(f_schedule1);
			delete3.getSchedules().add(f_schedule1);
			
			// Save Bookings
			// Michelle bookings(standard consult 15min) -Mon(19th)->Thurs(22nd) 9am to 6pm
			// Monday bookings
			Entity_Booking m_bookng1 = new Entity_Booking(OffsetDateTime.parse("2020-10-19T17:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-19T17:15+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking m_bookng2 = new Entity_Booking(OffsetDateTime.parse("2020-10-19T17:45+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-19T18:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking m_bookng3 = new Entity_Booking(OffsetDateTime.parse("2020-10-19T13:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-19T13:45+10:00", DateTimeStatic.getFormatter()));
			// Tuesday bookings
			Entity_Booking m_bookng4 = new Entity_Booking(OffsetDateTime.parse("2020-10-20T10:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-20T10:15+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking m_bookng5 = new Entity_Booking(OffsetDateTime.parse("2020-10-20T12:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-20T12:45+10:00", DateTimeStatic.getFormatter()));
			// Wednesday bookings
			Entity_Booking m_bookng6 = new Entity_Booking(OffsetDateTime.parse("2020-10-21T09:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-21T09:15+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking m_bookng7 = new Entity_Booking(OffsetDateTime.parse("2020-10-21T14:45+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-21T15:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking m_bookng8 = new Entity_Booking(OffsetDateTime.parse("2020-10-21T11:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-21T11:45+10:00", DateTimeStatic.getFormatter()));
			// Thursday bookings
			Entity_Booking m_bookng9 = new Entity_Booking(OffsetDateTime.parse("2020-10-22T09:45+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-22T10:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking m_bookng10 = new Entity_Booking(OffsetDateTime.parse("2020-10-22T12:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-22T12:45+10:00", DateTimeStatic.getFormatter()));
			// Leslie bookings(Skin check 60min) -Mon(19th)->Wed(21st)->Fri(23rd) 10am to 5pm
			// Monday bookings
			Entity_Booking l_bookng1 = new Entity_Booking(OffsetDateTime.parse("2020-10-19T10:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-19T11:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking l_bookng2 = new Entity_Booking(OffsetDateTime.parse("2020-10-19T14:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-19T15:00+10:00", DateTimeStatic.getFormatter()));
			// Wednesday bookings
			Entity_Booking l_bookng3 = new Entity_Booking(OffsetDateTime.parse("2020-10-21T12:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-21T13:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking l_bookng4 = new Entity_Booking(OffsetDateTime.parse("2020-10-21T16:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-21T17:00+10:00", DateTimeStatic.getFormatter()));
			// Friday bookings
			Entity_Booking l_bookng5 = new Entity_Booking(OffsetDateTime.parse("2020-10-23T09:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-23T09:15+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking l_bookng6 = new Entity_Booking(OffsetDateTime.parse("2020-10-23T14:45+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-23T15:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking l_bookng7 = new Entity_Booking(OffsetDateTime.parse("2020-10-23T11:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-23T11:45+10:00", DateTimeStatic.getFormatter()));
			// Karin bookings(Long consult 30min) -Tue(20th)->Fri(23rd) 8am to 4pm
			// Tuesday bookings
			Entity_Booking k_bookng1 = new Entity_Booking(OffsetDateTime.parse("2020-10-20T08:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-20T08:30+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking k_bookng2 = new Entity_Booking(OffsetDateTime.parse("2020-10-20T10:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-20T11:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking k_bookng3 = new Entity_Booking(OffsetDateTime.parse("2020-10-20T15:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-20T16:00+10:00", DateTimeStatic.getFormatter()));
			// Wednesday bookings
			Entity_Booking k_bookng4 = new Entity_Booking(OffsetDateTime.parse("2020-10-21T09:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-21T09:30+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking k_bookng5 = new Entity_Booking(OffsetDateTime.parse("2020-10-21T13:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-21T14:00+10:00", DateTimeStatic.getFormatter()));
			// Thursday bookings
			Entity_Booking k_bookng6 = new Entity_Booking(OffsetDateTime.parse("2020-10-22T09:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-22T09:30+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking k_bookng7 = new Entity_Booking(OffsetDateTime.parse("2020-10-22T15:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-22T16:00+10:00", DateTimeStatic.getFormatter()));
			// Friday bookings
			Entity_Booking k_bookng8 = new Entity_Booking(OffsetDateTime.parse("2020-10-23T10:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-23T11:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking k_bookng9 = new Entity_Booking(OffsetDateTime.parse("2020-10-23T13:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-23T14:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking k_bookng10 = new Entity_Booking(OffsetDateTime.parse("2020-10-23T14:30+10:00", DateTimeStatic.getFormatter()), 
														   OffsetDateTime.parse("2020-10-23T15:00+10:00", DateTimeStatic.getFormatter()));
			// Lammy bookings(Pap smear 30min) -Mon(19th) and Thurs(22th) 10am to 2pm
			// Monday bookings
			Entity_Booking lam_bookng1 = new Entity_Booking(OffsetDateTime.parse("2020-10-19T10:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-19T11:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking lam_bookng2 = new Entity_Booking(OffsetDateTime.parse("2020-10-19T13:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-19T14:00+10:00", DateTimeStatic.getFormatter()));
			// Thursday bookings
			Entity_Booking lam_bookng3 = new Entity_Booking(OffsetDateTime.parse("2020-10-22T10:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-22T10:30+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking lam_bookng4 = new Entity_Booking(OffsetDateTime.parse("2020-10-22T11:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-22T12:00+10:00", DateTimeStatic.getFormatter()));
			// Rammy bookings(Travel vac 10min) -Mon(19th)->Wed(21th) 10am to 12am
			// Monday bookings
			Entity_Booking Ram_bookng1 = new Entity_Booking(OffsetDateTime.parse("2020-10-19T10:30+10:00", DateTimeStatic.getFormatter()), 
															OffsetDateTime.parse("2020-10-19T10:40+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking Ram_bookng2 = new Entity_Booking(OffsetDateTime.parse("2020-10-19T11:20+10:00", DateTimeStatic.getFormatter()), 
															OffsetDateTime.parse("2020-10-19T11:30+10:00", DateTimeStatic.getFormatter()));
			// Tuesday bookings
			Entity_Booking Ram_bookng3 = new Entity_Booking(OffsetDateTime.parse("2020-10-20T10:50+10:00", DateTimeStatic.getFormatter()), 
															OffsetDateTime.parse("2020-10-20T11:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking Ram_bookng4 = new Entity_Booking(OffsetDateTime.parse("2020-10-20T11:40+10:00", DateTimeStatic.getFormatter()), 
															OffsetDateTime.parse("2020-10-20T11:50+10:00", DateTimeStatic.getFormatter()));
			// Wednesday bookings
			Entity_Booking Ram_bookng5 = new Entity_Booking(OffsetDateTime.parse("2020-10-21T11:00+10:00", DateTimeStatic.getFormatter()), 
															OffsetDateTime.parse("2020-10-21T11:10+10:00", DateTimeStatic.getFormatter()));
			// James bookings(Standard consult 15min) -Mon(19th)->Fri(23th) 10am to 7pm
			// Monday bookings
			Entity_Booking j_bookng1 = new Entity_Booking(OffsetDateTime.parse("2020-10-19T10:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-19T10:15+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking j_bookng2 = new Entity_Booking(OffsetDateTime.parse("2020-10-19T10:45+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-19T11:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking j_bookng3 = new Entity_Booking(OffsetDateTime.parse("2020-10-19T14:45+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-19T15:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking j_bookng4 = new Entity_Booking(OffsetDateTime.parse("2020-10-19T18:45+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-19T19:00+10:00", DateTimeStatic.getFormatter()));
			// Tuesday bookings
			Entity_Booking j_bookng5 = new Entity_Booking(OffsetDateTime.parse("2020-10-20T11:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-20T11:15+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking j_bookng6 = new Entity_Booking(OffsetDateTime.parse("2020-10-20T12:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-20T12:45+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking j_bookng7 = new Entity_Booking(OffsetDateTime.parse("2020-10-20T13:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-20T13:15+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking j_bookng8 = new Entity_Booking(OffsetDateTime.parse("2020-10-20T15:45+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-20T16:00+10:00", DateTimeStatic.getFormatter()));
			// Wednesday bookings
			Entity_Booking j_bookng9 = new Entity_Booking(OffsetDateTime.parse("2020-10-21T12:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-21T12:45+10:00", DateTimeStatic.getFormatter()));
			// Thursday bookings
			Entity_Booking j_bookng10 = new Entity_Booking(OffsetDateTime.parse("2020-10-22T10:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-22T10:15+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking j_bookng11 = new Entity_Booking(OffsetDateTime.parse("2020-10-22T12:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-22T12:45+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking j_bookng12 = new Entity_Booking(OffsetDateTime.parse("2020-10-22T15:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-22T15:15+10:00", DateTimeStatic.getFormatter()));
			// Friday bookings
			Entity_Booking j_bookng13 = new Entity_Booking(OffsetDateTime.parse("2020-10-23T13:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-23T13:15+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking j_bookng14 = new Entity_Booking(OffsetDateTime.parse("2020-10-23T18:45+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-23T19:00+10:00", DateTimeStatic.getFormatter()));
			// Fred bookings(Long consult 30min) -Mon(19th)->Thurs(22rd) 9am to 5pm
			// Monday bookings
			Entity_Booking f_bookng1 = new Entity_Booking(OffsetDateTime.parse("2020-10-19T09:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-19T09:30+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking f_bookng2 = new Entity_Booking(OffsetDateTime.parse("2020-10-19T10:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-19T11:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking f_bookng3 = new Entity_Booking(OffsetDateTime.parse("2020-10-19T14:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-19T14:30+10:00", DateTimeStatic.getFormatter()));
			// Tuesday bookings
			Entity_Booking f_bookng4 = new Entity_Booking(OffsetDateTime.parse("2020-10-20T12:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-20T12:30+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking f_bookng5 = new Entity_Booking(OffsetDateTime.parse("2020-10-20T13:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-20T14:00+10:00", DateTimeStatic.getFormatter()));
			// Wednesday bookings
			Entity_Booking f_bookng6 = new Entity_Booking(OffsetDateTime.parse("2020-10-21T11:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-21T11:30+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking f_bookng7 = new Entity_Booking(OffsetDateTime.parse("2020-10-21T16:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-21T17:00+10:00", DateTimeStatic.getFormatter()));
			// Thursday bookings
			Entity_Booking f_bookng8 = new Entity_Booking(OffsetDateTime.parse("2020-10-22T10:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-22T10:30+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking f_bookng9 = new Entity_Booking(OffsetDateTime.parse("2020-10-22T13:30+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-22T14:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking f_bookng10 = new Entity_Booking(OffsetDateTime.parse("2020-10-22T15:00+10:00", DateTimeStatic.getFormatter()), 
														   OffsetDateTime.parse("2020-10-22T15:30+10:00", DateTimeStatic.getFormatter()));
			// Joel bookings(Skin check 60min) -Tue(20th)->Sat(24th) 10am to 5pm
			// Tuesday bookings
			Entity_Booking joel_bookng1 = new Entity_Booking(OffsetDateTime.parse("2020-10-20T10:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-20T11:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking joel_bookng2 = new Entity_Booking(OffsetDateTime.parse("2020-10-20T11:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-20T12:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking joel_bookng3 = new Entity_Booking(OffsetDateTime.parse("2020-10-20T16:00+10:00", DateTimeStatic.getFormatter()), 
														  OffsetDateTime.parse("2020-10-20T17:00+10:00", DateTimeStatic.getFormatter()));
			// Wednesday bookings
			Entity_Booking joel_bookng4 = new Entity_Booking(OffsetDateTime.parse("2020-10-21T11:00+10:00", DateTimeStatic.getFormatter()), 
															 OffsetDateTime.parse("2020-10-21T12:00+10:00", DateTimeStatic.getFormatter()));
			// Thursday bookings
			Entity_Booking joel_bookng5 = new Entity_Booking(OffsetDateTime.parse("2020-10-22T13:00+10:00", DateTimeStatic.getFormatter()), 
															 OffsetDateTime.parse("2020-10-22T14:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking joel_bookng6 = new Entity_Booking(OffsetDateTime.parse("2020-10-22T16:00+10:00", DateTimeStatic.getFormatter()), 
															 OffsetDateTime.parse("2020-10-22T17:00+10:00", DateTimeStatic.getFormatter()));
			// Friday bookings
			Entity_Booking joel_bookng7 = new Entity_Booking(OffsetDateTime.parse("2020-10-23T14:00+10:00", DateTimeStatic.getFormatter()), 
															 OffsetDateTime.parse("2020-10-23T15:00+10:00", DateTimeStatic.getFormatter()));
			// Saturday bookings
			Entity_Booking joel_bookng8 = new Entity_Booking(OffsetDateTime.parse("2020-10-24T14:00+10:00", DateTimeStatic.getFormatter()), 
															 OffsetDateTime.parse("2020-10-24T15:00+10:00", DateTimeStatic.getFormatter()));
			Entity_Booking joel_bookng9 = new Entity_Booking(OffsetDateTime.parse("2020-10-24T15:00+10:00", DateTimeStatic.getFormatter()), 
															 OffsetDateTime.parse("2020-10-24T16:00+10:00", DateTimeStatic.getFormatter()));
			// Save the bookings
			BookingRepository.save(m_bookng1);
			BookingRepository.save(m_bookng2);
			BookingRepository.save(m_bookng3);
			BookingRepository.save(m_bookng4);
			BookingRepository.save(m_bookng5);
			BookingRepository.save(m_bookng6);
			BookingRepository.save(m_bookng7);
			BookingRepository.save(m_bookng8);
			BookingRepository.save(m_bookng9);
			BookingRepository.save(m_bookng10);
			BookingRepository.save(l_bookng1);
			BookingRepository.save(l_bookng2);
			BookingRepository.save(l_bookng3);
			BookingRepository.save(l_bookng4);
			BookingRepository.save(l_bookng5);
			BookingRepository.save(l_bookng6);
			BookingRepository.save(l_bookng7);
			BookingRepository.save(k_bookng1);
			BookingRepository.save(k_bookng2);
			BookingRepository.save(k_bookng3);
			BookingRepository.save(k_bookng4);
			BookingRepository.save(k_bookng5);
			BookingRepository.save(k_bookng6);
			BookingRepository.save(k_bookng7);
			BookingRepository.save(k_bookng8);
			BookingRepository.save(k_bookng9);
			BookingRepository.save(k_bookng10);
			BookingRepository.save(lam_bookng1);
			BookingRepository.save(lam_bookng2);
			BookingRepository.save(lam_bookng3);
			BookingRepository.save(lam_bookng4);
			BookingRepository.save(Ram_bookng1);
			BookingRepository.save(Ram_bookng2);
			BookingRepository.save(Ram_bookng3);
			BookingRepository.save(Ram_bookng4);
			BookingRepository.save(Ram_bookng5);
			BookingRepository.save(j_bookng1);
			BookingRepository.save(j_bookng2);
			BookingRepository.save(j_bookng3);
			BookingRepository.save(j_bookng4);
			BookingRepository.save(j_bookng5);
			BookingRepository.save(j_bookng6);
			BookingRepository.save(j_bookng7);
			BookingRepository.save(j_bookng8);
			BookingRepository.save(j_bookng9);
			BookingRepository.save(j_bookng10);
			BookingRepository.save(j_bookng11);
			BookingRepository.save(j_bookng12);
			BookingRepository.save(j_bookng13);
			BookingRepository.save(j_bookng14);
			BookingRepository.save(f_bookng1);
			BookingRepository.save(f_bookng2);
			BookingRepository.save(f_bookng3);
			BookingRepository.save(f_bookng4);
			BookingRepository.save(f_bookng5);
			BookingRepository.save(f_bookng6);
			BookingRepository.save(f_bookng7);
			BookingRepository.save(f_bookng8);
			BookingRepository.save(f_bookng9);
			BookingRepository.save(f_bookng10);
			BookingRepository.save(joel_bookng1);
			BookingRepository.save(joel_bookng2);
			BookingRepository.save(joel_bookng3);
			BookingRepository.save(joel_bookng4);
			BookingRepository.save(joel_bookng5);
			BookingRepository.save(joel_bookng6);
			BookingRepository.save(joel_bookng7);
			BookingRepository.save(joel_bookng8);
			BookingRepository.save(joel_bookng9);

			//Add bookings to Employees
			michelleEmployee.getBookings().add(m_bookng1);
			michelleEmployee.getBookings().add(m_bookng2);
			michelleEmployee.getBookings().add(m_bookng3);
			michelleEmployee.getBookings().add(m_bookng4);
			michelleEmployee.getBookings().add(m_bookng5);
			michelleEmployee.getBookings().add(m_bookng6);
			michelleEmployee.getBookings().add(m_bookng7);
			michelleEmployee.getBookings().add(m_bookng8);
			michelleEmployee.getBookings().add(m_bookng9);
			michelleEmployee.getBookings().add(m_bookng10);
			leslieEmployee.getBookings().add(l_bookng1);
			leslieEmployee.getBookings().add(l_bookng2);
			leslieEmployee.getBookings().add(l_bookng3);
			leslieEmployee.getBookings().add(l_bookng4);
			leslieEmployee.getBookings().add(l_bookng5);
			leslieEmployee.getBookings().add(l_bookng6);
			leslieEmployee.getBookings().add(l_bookng7);
			karinEmployee.getBookings().add(k_bookng1);
			karinEmployee.getBookings().add(k_bookng2);
			karinEmployee.getBookings().add(k_bookng3);
			karinEmployee.getBookings().add(k_bookng4);
			karinEmployee.getBookings().add(k_bookng5);
			karinEmployee.getBookings().add(k_bookng6);
			karinEmployee.getBookings().add(k_bookng7);
			karinEmployee.getBookings().add(k_bookng8);
			karinEmployee.getBookings().add(k_bookng9);
			karinEmployee.getBookings().add(k_bookng10);
			lammyEmployee.getBookings().add(lam_bookng1);
			lammyEmployee.getBookings().add(lam_bookng2);
			lammyEmployee.getBookings().add(lam_bookng3);
			lammyEmployee.getBookings().add(lam_bookng4);
			rammyEmployee.getBookings().add(Ram_bookng1);
			rammyEmployee.getBookings().add(Ram_bookng2);
			rammyEmployee.getBookings().add(Ram_bookng3);
			rammyEmployee.getBookings().add(Ram_bookng4);
			rammyEmployee.getBookings().add(Ram_bookng5);
			jamesEmployee.getBookings().add(j_bookng1);
			jamesEmployee.getBookings().add(j_bookng2);
			jamesEmployee.getBookings().add(j_bookng3);
			jamesEmployee.getBookings().add(j_bookng4);
			jamesEmployee.getBookings().add(j_bookng5);
			jamesEmployee.getBookings().add(j_bookng6);
			jamesEmployee.getBookings().add(j_bookng7);
			jamesEmployee.getBookings().add(j_bookng8);
			jamesEmployee.getBookings().add(j_bookng9);
			jamesEmployee.getBookings().add(j_bookng10);
			jamesEmployee.getBookings().add(j_bookng11);
			jamesEmployee.getBookings().add(j_bookng12);
			jamesEmployee.getBookings().add(j_bookng13);
			jamesEmployee.getBookings().add(j_bookng14);
			fredEmployee.getBookings().add(f_bookng1);
			fredEmployee.getBookings().add(f_bookng2);
			fredEmployee.getBookings().add(f_bookng3);
			fredEmployee.getBookings().add(f_bookng4);
			fredEmployee.getBookings().add(f_bookng5);
			fredEmployee.getBookings().add(f_bookng6);
			fredEmployee.getBookings().add(f_bookng7);
			fredEmployee.getBookings().add(f_bookng8);
			fredEmployee.getBookings().add(f_bookng9);
			fredEmployee.getBookings().add(f_bookng10);
			JoelEmployee.getBookings().add(joel_bookng1);
			JoelEmployee.getBookings().add(joel_bookng2);
			JoelEmployee.getBookings().add(joel_bookng3);
			JoelEmployee.getBookings().add(joel_bookng4);
			JoelEmployee.getBookings().add(joel_bookng5);
			JoelEmployee.getBookings().add(joel_bookng6);
			JoelEmployee.getBookings().add(joel_bookng7);
			JoelEmployee.getBookings().add(joel_bookng8);
			JoelEmployee.getBookings().add(joel_bookng9);
			//Add bookings to customers
			jackCustomer.getBookings().add(m_bookng1);
			jackCustomer.getBookings().add(m_bookng4);
			jackCustomer.getBookings().add(m_bookng7);
			jackCustomer.getBookings().add(m_bookng10);
			jackCustomer.getBookings().add(l_bookng3);
			jackCustomer.getBookings().add(l_bookng6);
			jackCustomer.getBookings().add(k_bookng2);
			jackCustomer.getBookings().add(k_bookng5);
			jackCustomer.getBookings().add(k_bookng8);
			jackCustomer.getBookings().add(lam_bookng1);
			jackCustomer.getBookings().add(lam_bookng4);
			jackCustomer.getBookings().add(Ram_bookng3);
			jackCustomer.getBookings().add(j_bookng1);
			jackCustomer.getBookings().add(j_bookng4);
			jackCustomer.getBookings().add(j_bookng7);
			jackCustomer.getBookings().add(j_bookng10);
			jackCustomer.getBookings().add(j_bookng13);
			jackCustomer.getBookings().add(f_bookng2);
			jackCustomer.getBookings().add(f_bookng5);
			jackCustomer.getBookings().add(f_bookng8);
			jackCustomer.getBookings().add(joel_bookng1);
			jackCustomer.getBookings().add(joel_bookng4);
			jackCustomer.getBookings().add(joel_bookng7);
			chloeCustomer.getBookings().add(m_bookng2);
			chloeCustomer.getBookings().add(m_bookng5);
			chloeCustomer.getBookings().add(m_bookng8);
			chloeCustomer.getBookings().add(l_bookng1);
			chloeCustomer.getBookings().add(l_bookng4);
			chloeCustomer.getBookings().add(l_bookng7);
			chloeCustomer.getBookings().add(k_bookng3);
			chloeCustomer.getBookings().add(k_bookng6);
			chloeCustomer.getBookings().add(k_bookng9);
			chloeCustomer.getBookings().add(lam_bookng2);
			chloeCustomer.getBookings().add(Ram_bookng1);
			chloeCustomer.getBookings().add(Ram_bookng4);
			chloeCustomer.getBookings().add(j_bookng2);
			chloeCustomer.getBookings().add(j_bookng5);
			chloeCustomer.getBookings().add(j_bookng8);
			chloeCustomer.getBookings().add(j_bookng11);
			chloeCustomer.getBookings().add(j_bookng14);
			chloeCustomer.getBookings().add(f_bookng3);
			chloeCustomer.getBookings().add(f_bookng6);
			chloeCustomer.getBookings().add(f_bookng9);
			chloeCustomer.getBookings().add(joel_bookng2);
			chloeCustomer.getBookings().add(joel_bookng5);
			chloeCustomer.getBookings().add(joel_bookng8);
			kimCustomer.getBookings().add(m_bookng3);
			kimCustomer.getBookings().add(m_bookng6);
			kimCustomer.getBookings().add(m_bookng9);
			kimCustomer.getBookings().add(l_bookng2);
			kimCustomer.getBookings().add(l_bookng5);
			kimCustomer.getBookings().add(k_bookng1);
			kimCustomer.getBookings().add(k_bookng4);
			kimCustomer.getBookings().add(k_bookng7);
			kimCustomer.getBookings().add(k_bookng10);
			kimCustomer.getBookings().add(lam_bookng3);
			kimCustomer.getBookings().add(Ram_bookng2);
			kimCustomer.getBookings().add(Ram_bookng5);
			kimCustomer.getBookings().add(j_bookng3);
			kimCustomer.getBookings().add(j_bookng6);
			kimCustomer.getBookings().add(j_bookng9);
			kimCustomer.getBookings().add(j_bookng12);
			kimCustomer.getBookings().add(f_bookng1);
			kimCustomer.getBookings().add(f_bookng4);
			kimCustomer.getBookings().add(f_bookng7);
			kimCustomer.getBookings().add(f_bookng10);
			kimCustomer.getBookings().add(joel_bookng3);
			kimCustomer.getBookings().add(joel_bookng6);
			kimCustomer.getBookings().add(joel_bookng9);

			//Update the users
			UserRepository.save(jackCustomer);
			UserRepository.save(chloeCustomer);
			UserRepository.save(kimCustomer);
			UserRepository.save(davidAdmin);
			UserRepository.save(michelleEmployee);
			UserRepository.save(leslieEmployee);
			UserRepository.save(karinEmployee);
			UserRepository.save(lammyEmployee);
			UserRepository.save(rammyEmployee);
			UserRepository.save(jamesEmployee);
			UserRepository.save(fredEmployee);
			UserRepository.save(JoelEmployee);
			UserRepository.save(delete1);
			UserRepository.save(delete2);
			UserRepository.save(delete3);
      	};
	}
}
