package com.kkth.jpaStudyRoom.domain.room.controller;

import com.kkth.jpaStudyRoom.domain.room.dto.RoomResponse;
import com.kkth.jpaStudyRoom.domain.room.service.RoomService;
import com.kkth.jpaStudyRoom.global.response.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ApiResponse<List<RoomResponse>> getRooms() {
        return ApiResponse.success(roomService.getRooms());
    }
}
