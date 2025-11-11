package com.aditya.pawar.AirbnbApplication.service;


import com.aditya.pawar.AirbnbApplication.dto.HotelDto;
import com.aditya.pawar.AirbnbApplication.dto.HotelInfoDto;
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
public class HotelServiceImpl implements HotelService{

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new Hotel with name:{}",hotelDto.getName());
        Hotel hotel=modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        log.info("Created a new Hotel with Id:{}",hotelDto.getId());
        return modelMapper.map(hotelRepository.save(hotel),HotelDto.class);


    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting the hotel with id:{}",id);
        Hotel hotel=hotelRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel with Id not found:"+id));
        return modelMapper.map(hotel,HotelDto.class) ;
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("Updating the hotel with id:{}",id);
        Hotel hotel=hotelRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel with Id not found:"+id));
        modelMapper.map(hotelDto,hotel);
        hotel.setId(id);
       return modelMapper.map(hotelRepository.save(hotel),HotelDto.class);
    }

    @Override
    @Transactional //add transactional where we are making 2 db calls
    public void deleteHotelById(Long id) {
        Hotel hotel=hotelRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel with Id not found:"+id));

            //TODO: delete the future inventories for the hotel
        for (Room room: hotel.getRooms()){
            inventoryService.deleteFutureInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(id);

    }

    @Override
    @Transactional
    public void activateHotel(Long id) {
        log.info("Activating the hotel with id:{}",id);
        Hotel hotel=hotelRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel with Id not found:"+id));
        hotel.setActive(true);
        //TODO: create inventory for all the rooms for this hotel
        //assuming only do it once
        for (Room room: hotel.getRooms()){
            inventoryService.initalizeInventoryForAYear(room);
        }
    }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel=hotelRepository.findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel with Id not found:"+hotelId));

        List<RoomDto> rooms= hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class)).
                toList();

        return new HotelInfoDto(modelMapper.map(hotel,HotelDto.class),rooms);
    }
}
