package com.aditya.pawar.AirbnbApplication.repository;

import com.aditya.pawar.AirbnbApplication.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
