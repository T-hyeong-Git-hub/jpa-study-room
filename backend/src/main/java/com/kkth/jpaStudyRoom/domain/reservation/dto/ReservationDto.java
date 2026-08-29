package com.kkth.jpaStudyRoom.domain.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReservationDto {
    private Long reservationId;
    private String memberName;
    private String roomName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
