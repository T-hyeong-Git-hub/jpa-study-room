package com.kkth.jpaStudyRoom.support;


import com.kkth.jpaStudyRoom.domain.member.entity.Member;
import com.kkth.jpaStudyRoom.domain.room.entity.Room;

import java.time.LocalDateTime;

public class TestFixture {

    public static Member createMember() {
        return new Member(
                "태형",
                "test@test.com",
                "1234"
        );
    }

    public static Room createRoom() {
        return new Room(
                "스터디룸A",
                4
        );
    }

    public static LocalDateTime startTime(int hour) {
        return LocalDateTime.of(
                2026,
                5,
                1,
                hour,
                0
        );
    }

    public static LocalDateTime endTime(int hour) {
        return LocalDateTime.of(
                2026,
                5,
                1,
                hour,
                0
        );
    }
}