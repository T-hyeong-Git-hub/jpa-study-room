package com.kkth.jpaStudyRoom.domain.reservation.repository;

import com.kkth.jpaStudyRoom.domain.member.entity.Member;
import com.kkth.jpaStudyRoom.domain.reservation.entity.Reservation;
import com.kkth.jpaStudyRoom.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByMemberOrderByStartTimeDesc(Member member);

    /**
     * 동일 룸에서 새 구간(새 시작~새 종료)과 시간이 겹치는 예약 존재 여부.
     * 기존 시작이 새 종료보다 이전이고, 기존 종료가 새 시작보다 이후이면 겹침.
     */
    boolean existsByRoomAndStartTimeBeforeAndEndTimeAfter(
            Room room, LocalDateTime endTime, LocalDateTime startTime);
}
