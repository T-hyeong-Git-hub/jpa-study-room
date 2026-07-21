package com.kkth.jpaStudyRoom.domain.member.controller;

import com.kkth.jpaStudyRoom.domain.member.dto.*;
import com.kkth.jpaStudyRoom.domain.member.entity.Member;
import com.kkth.jpaStudyRoom.domain.member.service.MemberService;
import com.kkth.jpaStudyRoom.global.response.ApiResponse;
import com.kkth.jpaStudyRoom.global.security.JwtProvider;
import com.kkth.jpaStudyRoom.global.security.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    private final JwtProvider jwtProvider;

    private final RefreshTokenService refreshTokenService;

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

        String accessToken = jwtProvider.createAccessToken(member.getId());

        String refreshToken = jwtProvider.createRefreshToken(member.getId());

        refreshTokenService.saveRefreshToken(
                member.getId(),
                refreshToken
        );

        return ApiResponse.success(
                new LoginResponse(
                        member.getId(),
                        member.getEmail(),
                        member.getName(),
                        accessToken,
                        refreshToken
                )
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(Authentication authentication) {

        Long memberId = (Long) authentication.getPrincipal();

        refreshTokenService.deleteRefreshToken(memberId);

        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(
            @RequestBody RefreshRequest request) {

        // 1. JWT 자체가 정상인지
        if (!jwtProvider.validateToken(request.getRefreshToken())) {
            return ResponseEntity.badRequest().build();
        }

        // 2. Refresh Token인지 확인
        if (!jwtProvider.getTokenType(request.getRefreshToken()).equals("refresh")) {
            return ResponseEntity.badRequest().build();
        }

        // 3. JWT 안의 memberId 추출
        Long memberId = jwtProvider.getMemberId(request.getRefreshToken());

        // 4. 요청 memberId와 JWT memberId가 같은지
        if (!memberId.equals(request.getMemberId())) {
            return ResponseEntity.badRequest().build();
        }

        // 5. Redis 조회
        String savedToken =
                refreshTokenService.getRefreshToken(memberId);

        if (savedToken == null) {
            return ResponseEntity.badRequest().build();
        }

        // 6. Redis와 비교
        if (!savedToken.equals(request.getRefreshToken())) {
            return ResponseEntity.badRequest().build();
        }

        // 7. 새 Access Token 발급
        String newAccessToken =
                jwtProvider.createAccessToken(memberId);

        return ResponseEntity.ok(
                new RefreshResponse(newAccessToken)
        );
    }
}
