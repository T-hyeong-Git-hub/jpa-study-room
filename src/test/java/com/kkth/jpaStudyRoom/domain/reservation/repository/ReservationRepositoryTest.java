package com.kkth.jpaStudyRoom.domain.reservation.repository;

import com.kkth.jpaStudyRoom.domain.member.entity.Member;
import com.kkth.jpaStudyRoom.domain.member.repository.MemberRepository;
import com.kkth.jpaStudyRoom.domain.reservation.entity.Reservation;
import com.kkth.jpaStudyRoom.domain.room.entity.Room;
import com.kkth.jpaStudyRoom.domain.room.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReservationRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void saveReservationTest() {
        Member member = memberRepository.save(new Member("태형"));
        Room room = roomRepository.save(new Room("스터디룸 A", 4));

        Reservation reservation = new Reservation(
                member,
                room,
                LocalDateTime.of(2026, 4, 6, 10, 0),
                LocalDateTime.of(2026, 4, 6, 12, 0)
        );

        Reservation savedReservation = reservationRepository.save(reservation);

        assertThat(savedReservation.getId()).isNotNull();
        assertThat(savedReservation.getMember().getName()).isEqualTo("태형");
        assertThat(savedReservation.getRoom().getName()).isEqualTo("스터디룸 A");
    }
}