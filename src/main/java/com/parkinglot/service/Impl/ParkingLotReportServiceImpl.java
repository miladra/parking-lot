package com.parkinglot.service.Impl;

import com.parkinglot.service.ParkingLotRepostService;
import com.parkinglot.entity.Parking;
import com.parkinglot.entity.Vehicle;
import com.parkinglot.repository.ParkingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ParkingLotReportServiceImpl implements ParkingLotRepostService {

    private static final Logger logger = LoggerFactory.getLogger(ParkingLotReportServiceImpl.class);

    @Autowired
    private ParkingRepository parkingRepository;

    @Override
    public List<Parking> getAllVehicleTravel(String plate , Date inTime , Date outTime) {

        return parkingRepository.findByVehicleOrderByInTimeAndByOutTime(plate,inTime , outTime);
    }
}
