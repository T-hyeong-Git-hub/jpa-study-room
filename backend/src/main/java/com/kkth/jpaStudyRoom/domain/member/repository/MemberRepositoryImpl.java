package com.kkth.jpaStudyRoom.domain.member.repository;

import com.kkth.jpaStudyRoom.domain.member.dto.MemberDto;
import com.kkth.jpaStudyRoom.domain.member.dto.MemberSearchCondition;
import com.kkth.jpaStudyRoom.domain.member.entity.Member;
import com.kkth.jpaStudyRoom.domain.member.entity.QMember;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static com.kkth.jpaStudyRoom.domain.member.entity.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{
    private final JPAQueryFactory queryFactory;

//    public MemberRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
//        this.jpaQueryFactory = jpaQueryFactory;
//    }

    @Override
    public List<Member> searchByName(String name) {
        QMember qMember = member;

        return queryFactory
                .selectFrom(member)
                .where(member.name.eq(name))
                .fetch();
    }

    @Override
    public List<Member> search(MemberSearchCondition condition) {
        QMember qMember = member;
        return queryFactory
                .selectFrom(member)
                .where(
                        nameEq(condition.getName()),
                        createdAfter(condition.getCreatedAt())
                )
                .fetch();
    }

    @Override
    public Page<Member> searchPage(MemberSearchCondition condition, Pageable pageable) {
        QMember qMember = member;

        List<Member> content = queryFactory
                .selectFrom(member)
                .where(
                        nameEq(condition.getName()),
                        createdAfter(condition.getCreatedAt())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(member.count())
                .from(member)
                .where(
                        nameEq(condition.getName()),
                        createdAfter(condition.getCreatedAt())
                ).fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<MemberDto> findMemberDtoList() {
        QMember member = QMember.member;

        return queryFactory
                .select(Projections.constructor(
                        MemberDto.class,
                        member.id,
                        member.name
                ))
                .from(member)
                .fetch();
    }

    private BooleanExpression nameEq(String name) {
        return name != null ? QMember.member.name.eq(name) : null;
    }

    private BooleanExpression createdAfter(LocalDateTime createdAt) {
        return createdAt != null ? QMember.member.createdAt.goe(createdAt) : null;
    }
}
