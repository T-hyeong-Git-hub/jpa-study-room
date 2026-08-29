package com.kkth.jpaStudyRoom.domain.room.service;

import com.kkth.jpaStudyRoom.domain.room.dto.RoomResponse;
import com.kkth.jpaStudyRoom.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {
    private final RoomRepository roomRepository;
    @Cacheable(value = "rooms")
    public List<RoomResponse> getRooms() {
        return roomRepository.findAll()
                .stream()
                .map(RoomResponse::from)
                .toList();
    }
}
