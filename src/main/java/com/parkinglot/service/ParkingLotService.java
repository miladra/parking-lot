package com.parkinglot.service;

import com.parkinglot.entity.*;

public interface ParkingLotService {

    Parking park(Vehicle vehicle);

    Parking unPark(String plate);

    Long payment(Long parkingId);


}
