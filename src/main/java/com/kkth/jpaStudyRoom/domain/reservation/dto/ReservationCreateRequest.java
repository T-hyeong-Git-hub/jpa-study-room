package com.kkth.jpaStudyRoom.domain.reservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreateRequest {
    @NotNull(message = "회원 ID는 필수입니다.")
    private Long memberId;
    @NotNull(message = "방 ID는 필수입니다.")
    private Long roomId;

    @NotNull(message = "예약 시작 시간은 필수입니다.")
    private LocalDateTime startTime;

    @NotNull(message = "예약 종료 시간은 필수입니다.")
    private LocalDateTime endTime;
}
