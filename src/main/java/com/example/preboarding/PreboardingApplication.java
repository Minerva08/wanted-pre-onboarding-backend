package com.example.preboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class PreboardingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PreboardingApplication.class, args);
	}

}
