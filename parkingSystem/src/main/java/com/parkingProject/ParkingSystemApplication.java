package com.parkingProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParkingSystemApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(ParkingSystemApplication.class, args);
		
		System.out.println("Welcome to Vehicle Parking System");
	}

}
