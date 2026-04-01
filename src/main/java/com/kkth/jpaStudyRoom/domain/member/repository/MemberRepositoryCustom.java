package com.kkth.jpaStudyRoom.domain.member.repository;

import com.kkth.jpaStudyRoom.domain.member.dto.MemberDto;
import com.kkth.jpaStudyRoom.domain.member.dto.MemberSearchCondition;
import com.kkth.jpaStudyRoom.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> searchByName(String name);
    List<Member> search(MemberSearchCondition condition);

    Page<Member> searchPage(MemberSearchCondition condition, Pageable pageable);

    List<MemberDto> findMemberDtoList();

}
