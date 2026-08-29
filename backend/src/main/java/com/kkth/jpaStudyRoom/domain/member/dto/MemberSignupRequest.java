package com.kkth.jpaStudyRoom.domain.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignupRequest {

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;
}
