package com.kkth.jpaStudyRoom.global.config;

import com.kkth.jpaStudyRoom.domain.member.entity.Member;
import com.kkth.jpaStudyRoom.domain.member.repository.MemberRepository;
import com.kkth.jpaStudyRoom.domain.reservation.entity.Reservation;
import com.kkth.jpaStudyRoom.domain.reservation.repository.ReservationRepository;
import com.kkth.jpaStudyRoom.domain.room.entity.Room;
import com.kkth.jpaStudyRoom.domain.room.repository.RoomRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@RequiredArgsConstructor
public class InitDataConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initData(
            MemberRepository memberRepository,
            RoomRepository roomRepository,
            ReservationRepository reservationRepository
    ) {

        return args -> {

            if (memberRepository.count() > 0) {
                return;
            }

            // 회원 생성
            Member member = memberRepository.save(
                    new Member(
                            "태형",
                            "test@test.com",
                            passwordEncoder.encode("1234")
                    )
            );

            // 방 생성
            Room room = roomRepository.save(
                    new Room("스터디룸A", 4)
            );

            // 예약 1
            reservationRepository.save(
                    new Reservation(
                            member,
                            room,
                            LocalDateTime.of(2025, 1, 1, 9, 0),
                            LocalDateTime.of(2025, 1, 1, 10, 0)
                    )
            );

            // 예약 2
            reservationRepository.save(
                    new Reservation(
                            member,
                            room,
                            LocalDateTime.of(2025, 1, 1, 10, 0),
                            LocalDateTime.of(2025, 1, 1, 12, 0)
                    )
            );

            // 예약 3
            reservationRepository.save(
                    new Reservation(
                            member,
                            room,
                            LocalDateTime.of(2025, 1, 1, 13, 0),
                            LocalDateTime.of(2025, 1, 1, 15, 0)
                    )
            );

            System.out.println("초기 데이터 생성 완료");
        };
    }
}
