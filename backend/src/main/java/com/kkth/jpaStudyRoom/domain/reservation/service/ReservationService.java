package com.kkth.jpaStudyRoom.domain.reservation.service;

import com.kkth.jpaStudyRoom.domain.member.entity.Member;
import com.kkth.jpaStudyRoom.domain.member.repository.MemberRepository;
import com.kkth.jpaStudyRoom.domain.reservation.dto.ReservationDto;
import com.kkth.jpaStudyRoom.domain.reservation.dto.ReservationSearchCondition;
import com.kkth.jpaStudyRoom.domain.reservation.entity.Reservation;
import com.kkth.jpaStudyRoom.domain.reservation.repository.ReservationRepository;
import com.kkth.jpaStudyRoom.domain.room.entity.Room;
import com.kkth.jpaStudyRoom.domain.room.repository.RoomRepository;
import com.kkth.jpaStudyRoom.global.exception.CustomException;
import com.kkth.jpaStudyRoom.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    public Long createReservation(Long memberId, Long roomId, LocalDateTime startTime, LocalDateTime endTime) {

        // 1. 회원 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        // 2. 방 조회
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("방 없음"));

        // 3. 예약 겹침 체크
        boolean exists = reservationRepository.existsOverlappingReservation(roomId, startTime, endTime);

        if (exists) {
            throw new CustomException(ErrorCode.RESERVATION_CONFLICT);
        }

        // 4. 예약 생성
        Reservation reservation = new Reservation(member, room, startTime, endTime);

        reservationRepository.save(reservation);

        return reservation.getId();
    }
    @Transactional(readOnly = true)
    public Page<ReservationDto> searchReservations(ReservationSearchCondition condition, Pageable pageable) {
        return reservationRepository.searchReservations(condition, pageable);
    }
}

