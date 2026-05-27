package com.kkth.jpaStudyRoom.domain.room.dto;

import com.kkth.jpaStudyRoom.domain.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class RoomResponse implements Serializable {

    private Long roomId;
    private String name;
    private int capacity;

    public static RoomResponse from(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getName(),
                room.getCapacity()
        );
    }
}
