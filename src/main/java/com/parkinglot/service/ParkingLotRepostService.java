package com.parkinglot.service;

import com.parkinglot.entity.Parking;
import com.parkinglot.entity.Vehicle;

import java.util.Date;
import java.util.List;

public interface ParkingLotRepostService {

    List<Parking> getAllVehicleTravel(String plate , Date inTime , Date outTime);
}
