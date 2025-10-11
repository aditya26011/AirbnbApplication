package com.aditya.pawar.AirbnbApplication.controllers;

import com.aditya.pawar.AirbnbApplication.dto.HotelDto;
import com.aditya.pawar.AirbnbApplication.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto){
        log.info("Attempting to create a new hotel with name:{}",hotelDto.getName());
        HotelDto createdHotel=hotelService.createNewHotel(hotelDto);
        return new ResponseEntity<>(createdHotel, HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}")
    ResponseEntity<HotelDto> getHotelById(@PathVariable("hotelId") Long id){
        HotelDto hotelDto=hotelService.getHotelById(id);
        return new ResponseEntity<>(hotelDto,HttpStatus.FOUND);
    }

    @PutMapping("/{hotelId}")
    ResponseEntity<HotelDto> updateHotelById(@PathVariable("hotelId")Long id, @RequestBody HotelDto hotelDto){
        HotelDto hotel=hotelService.updateHotelById(id,hotelDto);
        return ResponseEntity.ok(hotel);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable("hotelId") Long id){
         hotelService.deleteHotelById(id);
         return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{hotelId}")
    public ResponseEntity<Void> activateHotel(@PathVariable("hotelId") Long id){
        hotelService.activateHotel(id);
        return ResponseEntity.noContent().build();
    }
}
