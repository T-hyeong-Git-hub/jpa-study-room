package com.kkth.jpaStudyRoom.domain.member.service;

import com.kkth.jpaStudyRoom.global.exception.CustomException;
import com.kkth.jpaStudyRoom.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("이미 존재하는 이메일로 회원가입하면 실패한다")
    void signupFailWhenEmailDuplicated() {
        // given
        memberService.signup(
                "태형",
                "test@test.com",
                "1234"
        );

        // when
        Throwable thrown = catchThrowable(() ->
                memberService.signup(
                        "철수",
                        "test@test.com",
                        "5678"
                )
        );

        // then
        assertThat(thrown)
                .isInstanceOf(CustomException.class);

        CustomException exception = (CustomException) thrown;

        assertThat(exception.getErrorCode())
                .isEqualTo(ErrorCode.DUPLICATED_EMAIL);
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 로그인하면 실패한다")
    void loginFailWhenEmailNotFound() {
        // given
        String email = "notfound@test.com";
        String password = "1234";

        // when & then
        assertThatThrownBy(() ->
                memberService.login(email, password)
        ).isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 로그인에 실패한다")
    void loginFailWhenPasswordDoesNotMatch() {
        // given
        memberService.signup(
                "태형",
                "test@test.com",
                "1234"
        );

        // when & then
        assertThatThrownBy(() ->
                memberService.login(
                        "test@test.com",
                        "wrong-password"
                )
        ).isInstanceOf(CustomException.class);
    }
}