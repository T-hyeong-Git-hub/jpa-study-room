package com.kkth.jpaStudyRoom.domain.member.repository;

import com.kkth.jpaStudyRoom.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom{
}
