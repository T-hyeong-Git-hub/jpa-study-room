package com.kkth.jpaStudyRoom.domain.reservation.repository;

import com.kkth.jpaStudyRoom.domain.member.entity.Member;
import com.kkth.jpaStudyRoom.domain.member.repository.MemberRepository;
import com.kkth.jpaStudyRoom.domain.reservation.dto.ReservationDto;
import com.kkth.jpaStudyRoom.domain.reservation.dto.ReservationSearchCondition;
import com.kkth.jpaStudyRoom.domain.reservation.entity.Reservation;
import com.kkth.jpaStudyRoom.domain.room.entity.Room;
import com.kkth.jpaStudyRoom.domain.room.repository.RoomRepository;
import com.kkth.jpaStudyRoom.support.TestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ReservationRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("예약 저장 시 예약자와 이용하는 룸의 이름을 같이 저장한다.")
    void saveReservation() {
        //given
        Member member = saveMember();
        Room room = saveRoom();

        saveReservation(member, room,10, 12);

        //when
        Reservation savedReservation = saveReservation(member, room,10, 12);;

        //then
        assertThat(savedReservation.getId()).isNotNull();
        assertThat(savedReservation.getMember().getName()).isEqualTo("태형");
        assertThat(savedReservation.getRoom().getName()).isEqualTo("스터디룸A");
    }

    @Test
    @DisplayName("겹치는 예약이 존재하면 true를 반환한다.")
    void overlappingReservation() {
        //given
        Member member = saveMember();
        Room room = saveRoom();

        // 기존 예약 (10:00 ~ 12:00)
        saveReservation(member, room,10, 12);

        //when
        // 겹치는 예약 (11:00 ~ 13:00)
        boolean exists = reservationRepository.existsOverlappingReservation(
                room.getId(),
                TestFixture.startTime(11),
                TestFixture.endTime(13)
        );

        //then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("겹치지 않는 예약이면 false를 반환한다.")
    void nonOverlappingReservation() {
        //given
        Member member = saveMember();
        Room room = saveRoom();

        // 기존 예약 (10:00 ~ 12:00)
        saveReservation(member, room,10, 12);

        //when
        // 안 겹침 (12:00 ~ 14:00)
        boolean exists = reservationRepository.existsOverlappingReservation(
                room.getId(),
                TestFixture.startTime(12),
                TestFixture.endTime(14)
        );

        //then
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("예약 조회 결과를 페이징할 수 있다")
    void searchReservationWithPaging() {

        //given
        Member member = saveMember();
        Room room = saveRoom();

        saveReservation(member, room,10, 12);
        saveReservation(member, room,13, 15);

        ReservationSearchCondition condition = new ReservationSearchCondition();
        condition.setMemberName("태형");

        Pageable pageable = PageRequest.of(0, 1);

        //when
        Page<ReservationDto> result = reservationRepository.searchReservations(condition, pageable);

        //then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getTotalPages()).isEqualTo(2);
    }

    @Test
    @DisplayName("예약 조회 결과를 시작 시간 내림차순으로 정렬한다")
    void searchReservationOrderByStartTimeDesc() {
        // given
        Member member = saveMember();
        Room room = saveRoom();

        saveReservation(member, room,10, 12);
        saveReservation(member, room,13, 15);
        saveReservation(member, room,9, 10);

        ReservationSearchCondition condition = new ReservationSearchCondition();
        condition.setMemberName("태형");

        PageRequest pageRequest = PageRequest.of(0, 2);

        // when
        Page<ReservationDto> result = reservationRepository.searchReservations(
                condition,
                pageRequest
        );

        // then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getTotalPages()).isEqualTo(2);

        assertThat(result.getContent().get(0).getStartTime())
                .isEqualTo(TestFixture.startTime(13));

        assertThat(result.getContent().get(1).getStartTime())
                .isEqualTo(TestFixture.startTime(10));
    }

    private Member saveMember() {
        return memberRepository.save(TestFixture.createMember());
    }

    private Room saveRoom() {
        return roomRepository.save(TestFixture.createRoom());
    }
    private Reservation saveReservation(Member member, Room room, int startHour, int endHour) {
        Reservation reservation = new Reservation(
                member,
                room,
                TestFixture.startTime(startHour),
                TestFixture.endTime(endHour)
        );
        return reservationRepository.save(reservation);

    }
}