package com.aditya.pawar.AirbnbApplication.service;


import com.aditya.pawar.AirbnbApplication.dto.HotelDto;
import com.aditya.pawar.AirbnbApplication.dto.HotelSearchRequest;
import com.aditya.pawar.AirbnbApplication.entity.Hotel;
import com.aditya.pawar.AirbnbApplication.entity.Inventory;
import com.aditya.pawar.AirbnbApplication.entity.Room;
import com.aditya.pawar.AirbnbApplication.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;

    private final ModelMapper modelMapper;


    @Override
    public void initalizeInventoryForAYear(Room room) {
        LocalDate today= LocalDate.now();
        LocalDate endDate=today.plusYears(1);
        for(;!today.isAfter(endDate);today=today.plusDays(1)){
            Inventory inventory=Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(0)
                    .city(room.getHotel().getCity())
                    .date(today)
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .build();
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public void deleteFutureInventories(Room room) {
        inventoryRepository.deleteByRoom(room);
    }

    @Override
    public Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest) {

        Pageable pageable= PageRequest.of(hotelSearchRequest.getPage(),hotelSearchRequest.getSize());

        // we have to get all the hotels in the city which have atleast 1 room between the start date and end date with the
        //given room type
        long dateCount= ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate())+1;

        Page<Hotel>hotelPage=inventoryRepository.findHotelsWithAvailableInventory(hotelSearchRequest.getCity(),hotelSearchRequest.getStartDate(),
                hotelSearchRequest.getEndDate(),hotelSearchRequest.getRoomCount(),dateCount,pageable);

        return hotelPage.map((element) -> modelMapper.map(element, HotelDto.class));
    }
}
