package com.kkth.jpaStudyRoom.domain.reservation.repository;

import java.time.LocalDateTime;

public interface ReservationRepositoryCustom {

    boolean existsOverlappingReservation(
            Long roomId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );
}
