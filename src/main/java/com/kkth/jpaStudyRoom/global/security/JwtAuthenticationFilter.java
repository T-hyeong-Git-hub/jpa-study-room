package com.kkth.jpaStudyRoom.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = resolveToken(request);

        if(token != null && jwtProvider.validateToken(token)
                && "access".equals(jwtProvider.getTokenType(token))) {

            Long memberId = jwtProvider.getMemberId(token);
            setAuthentication(memberId);
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(Long memberId) {
        SecurityContextHolder.getContext().setAuthentication(
                new JwtAuthenticationToken(memberId)
        );
    }

    private String resolveToken(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        return authHeader.substring(7);
    }

}
