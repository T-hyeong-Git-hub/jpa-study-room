package com.kkth.jpaStudyRoom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
@EnableCaching
@SpringBootApplication
public class JpaStudyRoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaStudyRoomApplication.class, args);
	}


}

