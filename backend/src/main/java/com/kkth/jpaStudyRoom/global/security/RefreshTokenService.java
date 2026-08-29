package com.kkth.jpaStudyRoom.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final StringRedisTemplate redisTemplate;

    //Refresh token 저장
    public void saveRefreshToken(Long memberId, String refreshToken) {
        redisTemplate.opsForValue().set(
                getKey(memberId),
                refreshToken,
                Duration.ofDays(14)
        );
    }

    // Refresh Token 조회
    public String getRefreshToken(Long memberId) {

        return redisTemplate.opsForValue().get(getKey(memberId));
    }

    // Refresh Token 삭제
    public void deleteRefreshToken(Long memberId) {

        redisTemplate.delete(getKey(memberId));
    }

    private String getKey(Long memberId) {
        return "refresh:" + memberId;
    }
}
