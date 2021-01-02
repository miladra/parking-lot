package com.parkinglot.repository;

import com.parkinglot.constant.enums.VehicleType;
import com.parkinglot.entity.Parking;
import com.parkinglot.entity.Vehicle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class ParkingRepositoryTest {


    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ParkingRepository parkingRepository;


    @Test
    public void findTop1ByVehicleOrderByInTimeDesc() {
        // given
        Vehicle vehicle = new Vehicle("99ب655ایران۲۰");
        vehicle.setType(VehicleType.general);

        Parking parking = new Parking();
        parking.setInTime(new Date());
        parking.setOutTime(new Date());
        parking.setPrice(1);

        Parking parking2 = new Parking();
        parking2.setInTime(new Date());
        parking2.setOutTime(new Date());
        parking.setPrice(2);

        Parking parking3 = new Parking();
        parking3.setInTime(new Date());

        vehicle.setParkings(new HashSet<>(Arrays.asList(parking , parking2 , parking3)));
        vehicle = vehicleRepository.save(vehicle);
        vehicle.setParkings(null);

        List<Parking> foundParking = parkingRepository.findByVehicleOrderByInTimeDesc(vehicle.getPlate());

        assertEquals(foundParking.size() , 3);


    }


}