package com.kkth.jpaStudyRoom.domain.reservation.controller;

import com.kkth.jpaStudyRoom.domain.reservation.dto.ReservationCreateRequest;
import com.kkth.jpaStudyRoom.domain.reservation.dto.ReservationDto;
import com.kkth.jpaStudyRoom.domain.reservation.dto.ReservationResponse;
import com.kkth.jpaStudyRoom.domain.reservation.dto.ReservationSearchCondition;
import com.kkth.jpaStudyRoom.domain.reservation.service.ReservationService;
import com.kkth.jpaStudyRoom.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ApiResponse<ReservationResponse> createReservation(@RequestBody @Valid ReservationCreateRequest request) {

        Long memberId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Long reservationId = reservationService.createReservation(
                memberId,
                request.getRoomId(),
                request.getStartTime(),
                request.getEndTime()
        );

        return ApiResponse.success(new ReservationResponse(reservationId));
    }

    @GetMapping
    public ApiResponse<Page<ReservationDto>> searchReservations(ReservationSearchCondition condition, Pageable pageable) {
        Page<ReservationDto> result = reservationService.searchReservations(condition, pageable);

        return ApiResponse.success(result);
    }
}
