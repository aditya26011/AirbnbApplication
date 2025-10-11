package com.aditya.pawar.AirbnbApplication.repository;

import com.aditya.pawar.AirbnbApplication.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {
}
