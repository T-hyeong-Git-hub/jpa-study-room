package com.kkth.jpaStudyRoom.domain.member.repository;

import com.kkth.jpaStudyRoom.domain.member.dto.MemberDto;
import com.kkth.jpaStudyRoom.domain.member.dto.MemberSearchCondition;
import com.kkth.jpaStudyRoom.domain.member.entity.Member;
import com.kkth.jpaStudyRoom.support.TestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

//    @BeforeEach
//    void setUp() {
//        memberRepository.deleteAll();
//    }
    @Test
    @DisplayName("회원 저장 시 생성일과 수정일이 자동으로 저장된다.")
    void saveMemberWithAuditingFields() {
        //given
        Member member = TestFixture.createMember();

        //when
        Member savedMember = memberRepository.save(member);

        //then
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getCreatedAt()).isNotNull();
        assertThat(savedMember.getUpdatedAt()).isNotNull();

    }
    
    @Test
    @DisplayName("QueryDSL로 이름이 일치하는 회원을 조회한다.")
    void searchByName() {
        //given
        memberRepository.save(new Member("kim"));
        memberRepository.save(new Member("lee"));

        //when
        List<Member> searchMember = memberRepository.searchByName("kim");

        //then
        assertThat(searchMember).hasSize(1);
        assertThat(searchMember.get(0).getName()).isEqualTo("kim");
    }

    @Test
    @DisplayName("검색 조건에 이름이 있으면 해당 이름의 회원만 조회한다")
    void searchWithNameCondition() {
        //given
        memberRepository.save(new Member("kim"));
        memberRepository.save(new Member("lee"));

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setName("kim");

        //when
        List<Member> result = memberRepository.search(condition);

        //then
        assertThat(result).hasSize(1);
        assertThat(result)
                .extracting(Member::getName)
                .containsExactly("kim");
    }

    @Test
    @DisplayName("검색 조건이 없으면 전체 회원을 조회한다")
    void searchWithoutCondition() {
        //given
        memberRepository.save(new Member("kim"));
        memberRepository.save(new Member("lee"));

        MemberSearchCondition condition = new MemberSearchCondition();

        //when
        List<Member> result = memberRepository.search(condition);

        //then
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("회원 목록을 페이징하여 조회한다")
    void searchPage() {
        //given
        saveMembers(20);

        MemberSearchCondition condition = new MemberSearchCondition();
        PageRequest pageRequest = PageRequest.of(0,5);

        //when
        Page<Member> result = memberRepository.searchPage(condition, pageRequest);

        //then
        assertThat(result.getContent()).hasSize(5);
        assertThat(result.getTotalElements()).isEqualTo(20);
        assertThat(result.getTotalPages()).isEqualTo(4);
        assertThat(result.getNumber()).isEqualTo(0);

    }

    @Test
    @DisplayName("회원 정보를 DTO로 조회한다")
    void findMemberDtoList() {
        //given
        memberRepository.save(new Member("kim"));
        memberRepository.save(new Member("lee"));

        //when
        List<MemberDto> result = memberRepository.findMemberDtoList();

        //then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(MemberDto::getName)
                .containsExactlyInAnyOrder("kim", "lee");

    }

    private void saveMembers(int count) {
        for (int i = 0; i < count; i++) {
            memberRepository.save(new Member("member" + i));
        }
    }

}