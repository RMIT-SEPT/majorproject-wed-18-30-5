package com.wed18305.assignment1;

import com.wed18305.assignment1.model.User;
import com.wed18305.assignment1.model.UserType;
import com.wed18305.assignment1.repositories.UserType_Repository;
import com.wed18305.assignment1.repositories.User_Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class);
	}

	// // Comment out CommandLineRunner when not using it for testing
	// @Bean
	// public CommandLineRunner demo(User_Repository UserRepository, UserType_Repository TypeRepository) {
	// 	return (args) -> {
	// 		//save the three types
	// 		UserType admin = new UserType("admin");
	// 		UserType employee = new UserType("employee");
	// 		UserType customer = new UserType("customer");
	// 		TypeRepository.save(admin);//1
	// 		TypeRepository.save(employee);//2
	// 		TypeRepository.save(customer);//3
			
	// 		// save a few customers
	// 		UserRepository.save(new User("Jack", "Jacky", "1234", "0000000000", customer));
	// 		UserRepository.save(new User("Chloe", "O'Brian", "1234", "0000000000", customer));
	// 		UserRepository.save(new User("Kim", "Bauer", "1234", "0000000000", customer));
	// 		UserRepository.save(new User("David", "Palmer", "1234", "0000000000", admin));
	// 		UserRepository.save(new User("Michelle", "Dessler", "1234", "0000000000", employee));

	// 		// fetch all types
	// 		log.info("User types found with findAll():");
	// 		log.info("-------------------------------");
	// 		for (UserType userType : TypeRepository.findAll()) {
	// 			log.info(userType.toString());
	// 		}
	// 		log.info("");

	// 		// fetch all users
	// 		log.info("Users found with findAll():");
	// 		log.info("-------------------------------");
	// 		for (User user : UserRepository.findAll()) {
	// 			log.info(user.toString());
	// 		}
	// 		log.info("");

	// 		// fetch an individual customer by ID
	// 		ArrayList<Long> ids = new ArrayList<Long>(Arrays.asList((long) 1, (long) 2));
	// 		Iterable<User> user = UserRepository.findAllById(ids);
	// 		log.info("Customer found with findById('1','2'):");
	// 		log.info("--------------------------------");
	// 		log.info(user.toString());
	// 		log.info("");

	// 		// fetch customers by last name
	// 		log.info("Customer found with findByNameAndPassword('David','1234'):");
	// 		log.info("--------------------------------------------");
	// 		Optional<User> user1 = UserRepository.findByUsernameAndPassword("David", "1234");
	// 		if(user1.isPresent()){
	// 			log.info(user1.get().toString());
	// 		}
	// 		log.info("");
    //   	};
	// }
}
