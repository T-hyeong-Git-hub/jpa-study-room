package com.kkth.jpaStudyRoom.domain.member.service;

import com.kkth.jpaStudyRoom.domain.member.entity.Member;
import com.kkth.jpaStudyRoom.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Member signup(String name, String email, String password) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        Member member = new Member(name, email, password);

        return memberRepository.save(member);
    }

    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        if (!member.getPassword().equals(password)) {
            throw new IllegalStateException("비밀번호 불일치");
        }

        return member;
    }
}
