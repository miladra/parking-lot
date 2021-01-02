package com.parkinglot.repository;

import com.parkinglot.constant.enums.VehicleType;
import com.parkinglot.entity.Vehicle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class VehicleRepositoryTest {


    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    public void findByPlate() {

        // given
        Vehicle vehicle = new Vehicle("22ب655ایران۲۰");
        vehicle.setType(VehicleType.general);
        vehicleRepository.save(vehicle);


        // when
        Vehicle foundVehicle = vehicleRepository.findByPlate(vehicle.getPlate());

        // then
        assertEquals(foundVehicle.getPlate() , vehicle.getPlate());
    }
}