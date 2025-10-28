package com.aditya.pawar.AirbnbApplication.service;

import com.aditya.pawar.AirbnbApplication.dto.RoomDto;
import com.aditya.pawar.AirbnbApplication.entity.Hotel;
import com.aditya.pawar.AirbnbApplication.entity.Room;
import com.aditya.pawar.AirbnbApplication.exception.ResourceNotFoundException;
import com.aditya.pawar.AirbnbApplication.repository.HotelRepository;
import com.aditya.pawar.AirbnbApplication.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{


    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;

    @Override
    public RoomDto createNewRoom(Long hotelId,RoomDto roomDto) {
        log.info("Creating a new Room in hotel with Id:{}",hotelId);
        Hotel hotel=hotelRepository.findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel with Id not found:"+hotelId));
        Room room=modelMapper.map(roomDto,Room.class);
        room.setHotel(hotel);
       Room createdRoom= roomRepository.save(room);

       //TODO: create Inventory as soon as room is created and hotel is active
        if(hotel.getActive()){
            inventoryService.initalizeInventoryForAYear(room);
        }
       log.info("Created a new Room in hotel with Id:{}",hotelId);
        return modelMapper.map(createdRoom,RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        log.info("Getting all Room in hotel with hotel Id:{}",hotelId);
        Hotel hotel=hotelRepository.findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel with id not found:"+hotelId));

        return hotel.getRooms().
                stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public RoomDto getRoomById(Long id) {
       log.info("Getting a room by id:{}",id);
       Room room=roomRepository.findById(id)
               .orElseThrow(()->new ResourceNotFoundException("Room with Id not found:"+ id));

       return modelMapper.map(room,RoomDto.class);
    }

    @Override
    @Transactional
    public void deleteRoomById(Long id) {
     log.info("Deleting the room with id:{}",id);
        Room room=roomRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Room with Id not found:"+ id));

        //TODO: delete all future inventory for this room
        inventoryService.deleteFutureInventories(room);

        roomRepository.deleteById(id);

    }
}
