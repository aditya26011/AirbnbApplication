package com.aditya.pawar.AirbnbApplication.controllers;

import com.aditya.pawar.AirbnbApplication.dto.HotelDto;
import com.aditya.pawar.AirbnbApplication.dto.HotelInfoDto;
import com.aditya.pawar.AirbnbApplication.dto.HotelSearchRequest;
import com.aditya.pawar.AirbnbApplication.service.HotelService;
import com.aditya.pawar.AirbnbApplication.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotels")
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @GetMapping(value = "/search")
    public ResponseEntity<Page<HotelDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest){
        Page<HotelDto> page= inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);

    }

    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId){
        return  ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }


}
