package com.resolute.zero;

import com.resolute.zero.domains.User;
import com.resolute.zero.repositories.UserRepository;
import com.resolute.zero.utilities.ApplicationUtility;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ZeroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZeroApplication.class, args);
	}


	@Bean
	public CommandLineRunner run(UserRepository userRepository){
		return args -> {


			var users = userRepository.findAll();
			if(users.isEmpty()){

				User user = new User();
				user.setUserName("userAdmin");
				user.setPassword(ApplicationUtility.encryptPassword("root1234"));
				user.setRole("admin");
				userRepository.save(user);

			}


			System.out.println("hello");
		};
	}

}
