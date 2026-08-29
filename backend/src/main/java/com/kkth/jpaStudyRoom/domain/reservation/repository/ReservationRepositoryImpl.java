package com.kkth.jpaStudyRoom.domain.reservation.repository;

import com.kkth.jpaStudyRoom.domain.member.entity.QMember;
import com.kkth.jpaStudyRoom.domain.reservation.dto.ReservationDto;
import com.kkth.jpaStudyRoom.domain.reservation.dto.ReservationSearchCondition;
import com.kkth.jpaStudyRoom.domain.reservation.entity.QReservation;
import com.kkth.jpaStudyRoom.domain.room.entity.QRoom;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.expression.spel.ast.Projection;

import java.time.LocalDateTime;
import java.util.List;

import static com.kkth.jpaStudyRoom.domain.reservation.entity.QReservation.reservation;
import static org.springframework.util.StringUtils.hasText;

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
    public Page<ReservationDto> searchReservations(ReservationSearchCondition condition, Pageable pageable) {
        QMember member = QMember.member;
        QReservation reservation = QReservation.reservation;
        QRoom room = QRoom.room;

        List<ReservationDto> content = queryFactory
                .select(Projections.constructor(
                        ReservationDto.class,
                        reservation.id,
                        member.name,
                        room.name,
                        reservation.startTime,
                        reservation.endTime
                ))
                .from(reservation)
                .join(reservation.member, member)
                .join(reservation.room,room)
                .where(
                        memberNameEq(condition.getMemberName()),
                        roomNameEq(condition.getRoomName()),
                        startAfter(condition.getStartAfter()),
                        endBefore(condition.getEndBefore())
                )
                .orderBy(reservation.startTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(reservation.count())
                .from(reservation)
                .join(reservation.member, member)
                .join(reservation.room,room)
                .where(
                        memberNameEq(condition.getMemberName()),
                        roomNameEq(condition.getRoomName()),
                        startAfter(condition.getStartAfter()),
                        endBefore(condition.getEndBefore())
                );

        return PageableExecutionUtils.getPage(
                content,
                pageable,
                countQuery::fetchOne
        );
    }

    private BooleanExpression memberNameEq(String memberName) {
        return hasText(memberName) ? QMember.member.name.eq(memberName) : null;
    }

    private BooleanExpression roomNameEq(String roomName) {
        return hasText(roomName) ? QRoom.room.name.eq(roomName) : null;
    }

    private BooleanExpression startAfter(LocalDateTime startAfter) {
        return startAfter != null ? QReservation.reservation.startTime.goe(startAfter) : null;
    }

    private BooleanExpression endBefore(LocalDateTime endBefore) {
        return endBefore != null ? QReservation.reservation.endTime.loe(endBefore) : null;
    }
}
