package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import static java.lang.System.out;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		out.println ("Starting application...");
		SpringApplication.run(DemoApplication.class, args);
		
		
	}
	

}
