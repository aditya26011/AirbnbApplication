package com.aditya.pawar.AirbnbApplication.repository;

import com.aditya.pawar.AirbnbApplication.entity.Inventory;
import com.aditya.pawar.AirbnbApplication.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    void deleteByRoom(Room room);
}
