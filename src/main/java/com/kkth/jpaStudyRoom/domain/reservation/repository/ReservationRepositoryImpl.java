package com.kkth.jpaStudyRoom.domain.reservation.repository;

import com.kkth.jpaStudyRoom.domain.reservation.entity.QReservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static com.kkth.jpaStudyRoom.domain.reservation.entity.QReservation.reservation;

@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public boolean existsOverlappingReservation(Long roomId, LocalDateTime startTime, LocalDateTime endTime) {

        QReservation qReservation = reservation;

        Integer result = queryFactory
                .selectOne()
                .from(reservation)
                .where(
                        reservation.room.id.eq(roomId),
                        reservation.startTime.lt(endTime),
                        reservation.endTime.gt(startTime)
                        )
                .fetchFirst();

        return result != null;
    }
}
