package com.kkth.jpaStudyRoom.domain.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreateRequest {

    private Long memberId;
    private Long roomId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
