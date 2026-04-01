package com.kkth.jpaStudyRoom.domain.member.repository;

import com.kkth.jpaStudyRoom.domain.member.dto.MemberDto;
import com.kkth.jpaStudyRoom.domain.member.dto.MemberSearchCondition;
import com.kkth.jpaStudyRoom.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }
    @Test
    public void memberSave() throws Exception{
        Member member = new Member("kim");

        memberRepository.save(member);
        Member findMember = memberRepository.findAll().get(0);

        System.out.println("createdAt = " + findMember.getCreatedAt());
        System.out.println("updatedAt = " + findMember.getUpdatedAt());

    }
    
    @Test
    public void querydslTest() throws Exception{
        //given
        Member member1 = new Member("kim");
        Member member2 = new Member("lee");

        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<Member> searchMember = memberRepository.searchByName("kim");

        //then
        assertThat(searchMember.size()).isEqualTo(1);
    }

    @Test
    public void dynamicQueryTest() throws Exception{
        //given
        Member member1 = new Member("kim");
        Member member2 = new Member("lee");

        memberRepository.save(member1);
        memberRepository.save(member2);

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setName("kim");
        //when
        List<Member> result = memberRepository.search(condition);

        //then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void pagingTest() throws Exception{

        for (int i = 0; i < 20; i++) {
            memberRepository.save(new Member("member" + i));
        }

        MemberSearchCondition condition = new MemberSearchCondition();

        Pageable pageable = PageRequest.of(0,5);

        Page<Member> result = memberRepository.searchPage(condition, pageable);

        assertThat(result.getContent().size()).isEqualTo(5);
        assertThat(result.getTotalElements()).isEqualTo(20);

    }

    @Test
    public void dtoQueryTest() throws Exception{
        memberRepository.save(new Member("kim"));
        memberRepository.save(new Member("lee"));

        List<MemberDto> result = memberRepository.findMemberDtoList();

        assertThat(result.size()).isEqualTo(2);

    }
}