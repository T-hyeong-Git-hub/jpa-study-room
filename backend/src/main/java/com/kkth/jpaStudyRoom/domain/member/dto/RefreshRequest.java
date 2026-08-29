package com.kkth.jpaStudyRoom.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshRequest {

    private Long memberId;

    private String refreshToken;

}
