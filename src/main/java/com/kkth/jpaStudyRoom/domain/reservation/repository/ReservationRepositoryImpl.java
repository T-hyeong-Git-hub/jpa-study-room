package com.kkth.jpaStudyRoom.domain.reservation.repository;

import com.kkth.jpaStudyRoom.domain.member.entity.QMember;
import com.kkth.jpaStudyRoom.domain.reservation.dto.ReservationDto;
import com.kkth.jpaStudyRoom.domain.reservation.dto.ReservationSearchCondition;
import com.kkth.jpaStudyRoom.domain.reservation.entity.QReservation;
import com.kkth.jpaStudyRoom.domain.room.entity.QRoom;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.spel.ast.Projection;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public List<ReservationDto> searchReservations(ReservationSearchCondition condition) {
        QMember member = QMember.member;
        QReservation reservation = QReservation.reservation;
        QRoom room = QRoom.room;

        return queryFactory.select(
                Projections.constructor(
                        ReservationDto.class,
                        reservation.id,
                        member.name,
                        room.name,
                        reservation.startTime,
                        reservation.endTime
                ))
                .from(reservation)
                .join(reservation.member, member)
                .join(reservation.room, room)
                .where(
                        memberNameEq(condition.getMemberName()),
                        roomNameEq(condition.getRoomName()),
                        startAfter(condition.getStartAfter()),
                        endBefore(condition.getEndBefore())
                ).fetch();
    }

    private BooleanExpression memberNameEq(String memberName) {
        return memberName != null ? QMember.member.name.eq(memberName) : null;
    }

    private BooleanExpression roomNameEq(String roomName) {
        return roomName != null ? QRoom.room.name.eq(roomName) : null;
    }

    private BooleanExpression startAfter(LocalDateTime startAfter) {
        return startAfter != null ? QReservation.reservation.startTime.goe(startAfter) : null;
    }

    private BooleanExpression endBefore(LocalDateTime endBefore) {
        return endBefore != null ? QReservation.reservation.endTime.loe(endBefore) : null;
    }
}
