package com.wed18305.assignment1;

import com.wed18305.assignment1.model.User;
import com.wed18305.assignment1.repositories.User_Repository;

import java.util.ArrayList;
import java.util.Arrays;

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

	// //Comment out CommandLineRunner when not using it for testing
	// @Bean 
  	// public CommandLineRunner demo(User_Repository repository) {
    // 	return (args) -> {
	// 		// save a few customers
	// 		repository.save(new User("Jack", "Jacky", "1234", "0000000000", (long) 1));
	// 		repository.save(new User("Chloe", "O'Brian", "1234", "0000000000", (long) 1));
	// 		repository.save(new User("Kim", "Bauer", "1234", "0000000000", (long) 1));
	// 		repository.save(new User("David", "Palmer", "1234", "0000000000", (long) 1));
	// 		repository.save(new User("Michelle", "Dessler", "1234", "0000000000", (long) 1));

	// 		// fetch all customers
	// 		log.info("Users found with findAll():");
	// 		log.info("-------------------------------");
	// 		for (User user : repository.findAll()) {
	// 			log.info(user.toString());
	// 		}
	// 		log.info("");

	// 		// fetch an individual customer by ID
	// 		ArrayList<Long> ids = new ArrayList<Long>(Arrays.asList((long)1,(long)2));
	// 		Iterable<User> user = repository.findAllById(ids);
	// 		log.info("Customer found with findById('1','2'):");
	// 		log.info("--------------------------------");
	// 		log.info(user.toString());
	// 		log.info("");

	// 		// fetch customers by last name
	// 		log.info("Customer found with findByNameAndPassword('David','1234'):");
	// 		log.info("--------------------------------------------");
	// 		User user1 = repository.findByNameAndPassword("David","1234");
	// 		log.info(user1.toString());
	// 		log.info("");
    //   	};
	// }
}
