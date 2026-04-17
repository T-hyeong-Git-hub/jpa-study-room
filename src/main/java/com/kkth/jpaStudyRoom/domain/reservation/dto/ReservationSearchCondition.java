package com.kkth.jpaStudyRoom.domain.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationSearchCondition {
    private String memberName;
    private String roomName;
    private LocalDateTime startAfter;
    private LocalDateTime endBefore;
}
