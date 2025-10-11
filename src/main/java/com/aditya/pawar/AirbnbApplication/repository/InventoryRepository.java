package com.aditya.pawar.AirbnbApplication.repository;

import com.aditya.pawar.AirbnbApplication.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
}
