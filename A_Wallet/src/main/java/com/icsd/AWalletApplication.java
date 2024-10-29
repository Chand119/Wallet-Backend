package com.icsd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AWalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(AWalletApplication.class, args);
		
	}

}
