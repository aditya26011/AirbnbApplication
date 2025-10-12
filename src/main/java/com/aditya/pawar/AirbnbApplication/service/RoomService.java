package com.aditya.pawar.AirbnbApplication.service;


import com.aditya.pawar.AirbnbApplication.dto.RoomDto;

import java.util.List;

public interface RoomService {

    RoomDto createNewRoom(Long hotelId,RoomDto roomDto);

    List<RoomDto> getAllRoomsInHotel(Long hotelId);

    RoomDto getRoomById(Long id);

    void deleteRoomById(Long id);

}
