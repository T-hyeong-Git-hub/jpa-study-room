package com.kkth.jpaStudyRoom.domain.reservation.repository;

import com.kkth.jpaStudyRoom.domain.member.entity.Member;
import com.kkth.jpaStudyRoom.domain.member.repository.MemberRepository;
import com.kkth.jpaStudyRoom.domain.reservation.dto.ReservationDto;
import com.kkth.jpaStudyRoom.domain.reservation.dto.ReservationSearchCondition;
import com.kkth.jpaStudyRoom.domain.reservation.entity.Reservation;
import com.kkth.jpaStudyRoom.domain.room.entity.Room;
import com.kkth.jpaStudyRoom.domain.room.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

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

    @Test
    void overlappingReservationTest() {

        Member member = memberRepository.save(new Member("태형"));
        Room room = roomRepository.save(new Room("스터디룸 A", 4));

        // 기존 예약 (10:00 ~ 12:00)
        reservationRepository.save(new Reservation(
                member,
                room,
                LocalDateTime.of(2026, 4, 6, 10, 0),
                LocalDateTime.of(2026, 4, 6, 12, 0)
        ));

        // 겹치는 예약 (11:00 ~ 13:00)
        boolean exists = reservationRepository.existsOverlappingReservation(
                room.getId(),
                LocalDateTime.of(2026, 4, 6, 11, 0),
                LocalDateTime.of(2026, 4, 6, 13, 0)
        );

        assertThat(exists).isTrue();
    }

    @Test
    void nonOverlappingReservationTest() {

        Member member = memberRepository.save(new Member("태형"));
        Room room = roomRepository.save(new Room("스터디룸 A", 4));

        // 기존 예약 (10:00 ~ 12:00)
        reservationRepository.save(new Reservation(
                member,
                room,
                LocalDateTime.of(2026, 4, 6, 10, 0),
                LocalDateTime.of(2026, 4, 6, 12, 0)
        ));

        // 안 겹침 (12:00 ~ 14:00)
        boolean exists = reservationRepository.existsOverlappingReservation(
                room.getId(),
                LocalDateTime.of(2026, 4, 6, 12, 0),
                LocalDateTime.of(2026, 4, 6, 14, 0)
        );

        assertThat(exists).isFalse();
    }

    @Test
    void 예약_조회_성공() {
        Member member1 = memberRepository.save(new Member("태형"));
        Member member2 = memberRepository.save(new Member("영희"));

        Room room1 = roomRepository.save(new Room("스터디룸A", 4));
        Room room2 = roomRepository.save(new Room("스터디룸B", 6));

        reservationRepository.save(new Reservation(
                member1,
                room1,
                LocalDateTime.of(2025, 1, 1, 10, 0),
                LocalDateTime.of(2025, 1, 1, 12, 0)
        ));

        reservationRepository.save(new Reservation(
                member2,
                room2,
                LocalDateTime.of(2025, 1, 2, 14, 0),
                LocalDateTime.of(2025, 1, 2, 16, 0)
        ));

        ReservationSearchCondition condition = new ReservationSearchCondition();
        condition.setMemberName("태형");

        List<ReservationDto> result = reservationRepository.searchReservations(condition);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMemberName()).isEqualTo("태형");
        assertThat(result.get(0).getRoomName()).isEqualTo("스터디룸A");
    }
}