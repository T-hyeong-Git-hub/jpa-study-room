package com.kkth.jpaStudyRoom.domain.reservation.controller;

import com.kkth.jpaStudyRoom.domain.reservation.dto.ReservationCreateRequest;
import com.kkth.jpaStudyRoom.domain.reservation.dto.ReservationResponse;
import com.kkth.jpaStudyRoom.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ReservationResponse createReservation(@RequestBody ReservationCreateRequest request) {
        Long reservationId = reservationService.createReservation(
                request.getMemberId(),
                request.getRoomId(),
                request.getStartTime(),
                request.getEndTime()
        );

        return new ReservationResponse(reservationId);
    }
}
