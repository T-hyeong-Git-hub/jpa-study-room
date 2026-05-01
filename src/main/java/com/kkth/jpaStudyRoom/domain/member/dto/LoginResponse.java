package com.kkth.jpaStudyRoom.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private Long memberId;
    private String email;
    private String name;
}
