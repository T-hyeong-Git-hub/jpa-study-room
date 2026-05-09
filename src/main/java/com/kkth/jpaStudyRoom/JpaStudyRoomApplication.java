package com.kkth.jpaStudyRoom;

import com.kkth.jpaStudyRoom.domain.member.entity.Member;
import com.kkth.jpaStudyRoom.domain.member.repository.MemberRepository;
import com.kkth.jpaStudyRoom.domain.room.entity.Room;
import com.kkth.jpaStudyRoom.domain.room.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpaStudyRoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaStudyRoomApplication.class, args);
	}


}

