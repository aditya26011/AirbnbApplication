package com.aditya.pawar.AirbnbApplication.service;


import com.aditya.pawar.AirbnbApplication.dto.HotelDto;
import com.aditya.pawar.AirbnbApplication.entity.Hotel;
import com.aditya.pawar.AirbnbApplication.exception.ResourceNotFoundException;
import com.aditya.pawar.AirbnbApplication.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

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
    public void deleteHotelById(Long id) {
        boolean exists=hotelRepository.existsById(id);
        if(!exists){
            throw  new ResourceNotFoundException("Hotel with Id not found:"+id);
        }

            hotelRepository.deleteById(id);
            //TODO: delete the future inventories for the hotel
    }
}
