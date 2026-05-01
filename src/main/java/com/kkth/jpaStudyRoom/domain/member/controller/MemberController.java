package com.kkth.jpaStudyRoom.domain.member.controller;

import com.kkth.jpaStudyRoom.domain.member.dto.LoginRequest;
import com.kkth.jpaStudyRoom.domain.member.dto.LoginResponse;
import com.kkth.jpaStudyRoom.domain.member.dto.MemberSignupRequest;
import com.kkth.jpaStudyRoom.domain.member.dto.MemberSignupResponse;
import com.kkth.jpaStudyRoom.domain.member.entity.Member;
import com.kkth.jpaStudyRoom.domain.member.service.MemberService;
import com.kkth.jpaStudyRoom.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ApiResponse<MemberSignupResponse> signup(@RequestBody @Valid MemberSignupRequest request) {
        Member member = memberService.signup(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        return ApiResponse.success(
                new MemberSignupResponse(
                        member.getId(),
                        member.getEmail(),
                        member.getName()
                )
        );
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        Member member = memberService.login(
                request.getEmail(),
                request.getPassword()
        );

        return ApiResponse.success(
                new LoginResponse(
                        member.getId(),
                        member.getEmail(),
                        member.getName()
                )
        );
    }
}
