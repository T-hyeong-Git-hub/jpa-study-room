package com.kkth.jpaStudyRoom.domain.reservation.service;

import com.kkth.jpaStudyRoom.domain.member.entity.Member;
import com.kkth.jpaStudyRoom.domain.member.repository.MemberRepository;
import com.kkth.jpaStudyRoom.domain.reservation.entity.Reservation;
import com.kkth.jpaStudyRoom.domain.reservation.repository.ReservationRepository;
import com.kkth.jpaStudyRoom.domain.room.entity.Room;
import com.kkth.jpaStudyRoom.domain.room.repository.RoomRepository;
import com.kkth.jpaStudyRoom.global.exception.CustomException;
import com.kkth.jpaStudyRoom.support.TestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@Transactional
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
    @DisplayName("기존 예약과 시간이 겹치지 않으면 예약에 성공한다")
    void createReservationSuccessWhenTimeDoesNotOverlap() {
        //given
        Member member = saveMember();
        Room room = saveRoom();

        createReservation(member, room, 10, 12);

        //when
        Long reservationId = createReservation(member, room, 8, 9);

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();

        //then
        assertThat(reservation.getMember().getId()).isEqualTo(member.getId());
        assertThat(reservation.getRoom().getId()).isEqualTo(room.getId());
        assertThat(reservation.getStartTime()).isEqualTo(TestFixture.startTime(8));
        assertThat(reservation.getEndTime()).isEqualTo(TestFixture.endTime(9));
    }

    @Test
    @DisplayName("기존 예약과 시간이 겹치면 예약에 실패한다")
    void createReservationFailWhenTimeOverlaps() {
        //given
        Member member = saveMember();
        Room room = saveRoom();

        createReservation(member, room, 10, 12);

        //when & then
        assertThatThrownBy(() ->
                createReservation(member, room, 11, 13)
        ).isInstanceOf(CustomException.class);
    }

    private Member saveMember() {
        return memberRepository.save(TestFixture.createMember());
    }

    private Room saveRoom() {
        return roomRepository.save(TestFixture.createRoom());
    }

    private Long createReservation(Member member, Room room, int startHour, int endHour) {
        return reservationService.createReservation(
                member.getId(),
                room.getId(),
                TestFixture.startTime(startHour),
                TestFixture.endTime(endHour)
        );

    }

}