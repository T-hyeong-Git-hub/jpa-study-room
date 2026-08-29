package com.kkth.jpaStudyRoom.domain.member.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
public class MemberSearchCondition {
    private String name;
    private LocalDateTime createdAt;
}
