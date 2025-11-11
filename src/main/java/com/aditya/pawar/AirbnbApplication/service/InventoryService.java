package com.aditya.pawar.AirbnbApplication.service;

import com.aditya.pawar.AirbnbApplication.dto.HotelDto;
import com.aditya.pawar.AirbnbApplication.dto.HotelSearchRequest;
import com.aditya.pawar.AirbnbApplication.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {
    void initalizeInventoryForAYear(Room room);

    void deleteFutureInventories(Room room);

    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
