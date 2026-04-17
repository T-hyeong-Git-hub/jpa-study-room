package com.kkth.jpaStudyRoom.domain.reservation.repository;

import com.kkth.jpaStudyRoom.domain.reservation.dto.ReservationDto;
import com.kkth.jpaStudyRoom.domain.reservation.dto.ReservationSearchCondition;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepositoryCustom {

    boolean existsOverlappingReservation(
            Long roomId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    List<ReservationDto> searchReservations(ReservationSearchCondition condition);

}
