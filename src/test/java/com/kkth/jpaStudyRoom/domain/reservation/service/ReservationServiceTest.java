package com.kkth.jpaStudyRoom.domain.reservation.service;

import com.kkth.jpaStudyRoom.domain.member.entity.Member;
import com.kkth.jpaStudyRoom.domain.member.repository.MemberRepository;
import com.kkth.jpaStudyRoom.domain.reservation.entity.Reservation;
import com.kkth.jpaStudyRoom.domain.reservation.repository.ReservationRepository;
import com.kkth.jpaStudyRoom.domain.room.entity.Room;
import com.kkth.jpaStudyRoom.domain.room.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Test
    void 예약_성공() {
        Member member = memberRepository.save(new Member("태형"));
        Room room = roomRepository.save(new Room("스터디룸B",4));

        reservationService.createReservation(
                member.getId(),
                room.getId(),
                LocalDateTime.of(2025, 1, 1, 10, 0),
                LocalDateTime.of(2025, 1, 1, 12, 0)
        );

        Long reservationId = reservationService.createReservation(
                member.getId(),
                room.getId(),
                LocalDateTime.of(2025, 1, 1, 8, 0),
                LocalDateTime.of(2025, 1, 1, 9, 0)
        );

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();

        assertThat(reservation.getMember().getId()).isEqualTo(member.getId());
        assertThat(reservation.getRoom().getId()).isEqualTo(room.getId());
        assertThat(reservation.getStartTime()).isEqualTo(LocalDateTime.of(2025, 1, 1, 8, 0));
        assertThat(reservation.getEndTime()).isEqualTo(LocalDateTime.of(2025, 1, 1, 9, 0));
    }

    @Test
    void 예약_실패_겹침() {
        Member member = memberRepository.save(new Member("태형"));
        Room room = roomRepository.save(new Room("스터디룸B",4));

        reservationService.createReservation(
                member.getId(),
                room.getId(),
                LocalDateTime.of(2025, 1, 1, 10, 0),
                LocalDateTime.of(2025, 1, 1, 12, 0)
        );

        assertThatThrownBy(() ->
                reservationService.createReservation(
                        member.getId(),
                        room.getId(),
                        LocalDateTime.of(2025, 1, 1, 11, 0),
                        LocalDateTime.of(2025, 1, 1, 13, 0)
                )
        ).isInstanceOf(IllegalStateException.class);
    }
}