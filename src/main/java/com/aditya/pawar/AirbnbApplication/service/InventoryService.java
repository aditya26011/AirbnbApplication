package com.aditya.pawar.AirbnbApplication.service;

import com.aditya.pawar.AirbnbApplication.entity.Room;

public interface InventoryService {
    void initalizeInventoryForAYear(Room room);

    void deleteFutureInventories(Room room);
}
