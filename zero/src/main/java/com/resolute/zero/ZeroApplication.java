package com.resolute.zero;

import com.resolute.zero.domains.User;
import com.resolute.zero.repositories.UserRepository;
import com.resolute.zero.services.StartupAppService;
import com.resolute.zero.utilities.ApplicationUtility;
import org.springframework.beans.factory.annotation.Autowired;
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
	public CommandLineRunner run(StartupAppService startupAppService){
		return args -> {
			startupAppService.loadDefaultUsers();
			System.out.println("welcome to backend");
		};
	}



}
