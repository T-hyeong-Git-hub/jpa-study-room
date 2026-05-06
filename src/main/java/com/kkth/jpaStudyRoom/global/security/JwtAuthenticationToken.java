package com.kkth.jpaStudyRoom.global.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final Long memberId;

    public JwtAuthenticationToken(Long memberId) {
        super(Collections.emptyList());
        this.memberId = memberId;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return memberId;
    }
}
