package com.parkinglot.service.Impl;

import com.parkinglot.constant.enums.VehicleType;
import com.parkinglot.entity.Parking;
import com.parkinglot.entity.Payment;
import com.parkinglot.entity.PriceRate;
import com.parkinglot.entity.Vehicle;
import com.parkinglot.repository.ParkingRepository;
import com.parkinglot.repository.PaymentRepository;
import com.parkinglot.repository.PriceRateRepository;
import com.parkinglot.repository.VehicleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class ParkingLotServiceImplTest {

    @MockBean
    private ParkingRepository parkingRepository;

    @MockBean
    private VehicleRepository vehicleRepository;

    @MockBean
    private PriceRateRepository priceRateRepository;

    @MockBean
    private PaymentRepository paymentRepository;

    @Autowired
    private ParkingLotServiceImpl parkingLotService;


    @Before
    public void setUp() {

        Calendar calendar = Calendar.getInstance();

        Vehicle vehicle = new Vehicle("22ب655ایران۲۰");
        vehicle.setType(VehicleType.general);


        Parking parking = new Parking();
        parking.setInTime(new Date());
        parking.setOutTime(new Date());


        Parking newParking = new Parking();
        newParking.setInTime(new Date());
        newParking.setVehicle(vehicle);


        PriceRate priceRate3 = new PriceRate();
        calendar.set(2020,12,21);
        priceRate3.setFromDate(calendar.getTime());
        priceRate3.setMonthly(400000);
        priceRate3.setDaily(17000);
        priceRate3.setHourly(800);


        Payment payment = new Payment();
        payment.setId(new Long(20));

        Optional<Parking> optionalNewParking = Optional.of(newParking);


        Mockito.when(vehicleRepository.findByPlate(Mockito.anyString()))
                .thenReturn(vehicle);

        Mockito.when(parkingRepository.findByVehicleOrderByInTimeDesc(Mockito.anyString()))
                .thenReturn(Arrays.asList(parking , parking ));


        Mockito.when(parkingRepository.save(Mockito.any(Parking.class)))
                .thenReturn(newParking);

        Mockito.when(priceRateRepository.findTopByToDateIsNull())
                .thenReturn(priceRate3);

        Mockito.when(paymentRepository.save(Mockito.any(Payment.class)))
                .thenReturn(payment);

        Mockito.when(parkingRepository.findById(Mockito.anyLong()))
                .thenReturn(optionalNewParking);

    }

    @Test
    public void park() {

        Vehicle vehicle = new Vehicle("22ب655ایران۲۰");
        vehicle.setType(VehicleType.general);

        Parking savedParking = parkingLotService.park(vehicle);

        assertNotNull(savedParking);
    }

    @Test
    public void unPark() {

        Vehicle vehicle = new Vehicle("22ب655ایران۲۰");
        vehicle.setType(VehicleType.general);

        Calendar cal = Calendar.getInstance();
        cal.add( Calendar.DAY_OF_WEEK_IN_MONTH, -10);
        cal.add( Calendar.DAY_OF_YEAR, -5);
        cal.add( Calendar.HOUR_OF_DAY, -3);

        Parking parking = new Parking();
        parking.setInTime(cal.getTime());

        Parking lastParking = new Parking();
        lastParking.setOutTime(new Date());
        lastParking.setPrice(1040400);

        Mockito.when(parkingRepository.findByVehicleOrderByInTimeDesc(Mockito.anyString()))
                .thenReturn(Arrays.asList(parking , parking ));

        Mockito.when(parkingRepository.save(Mockito.any(Parking.class)))
                .thenReturn(lastParking);


        Parking savedParking = parkingLotService.unPark(vehicle.getPlate());

        assertNotNull(savedParking);
    }

    @Test
    public void payment() {
        Parking parking = new Parking();

        Long transactionId = parkingLotService.payment(parking.getId());

        assertEquals(transactionId , new Long(20));

    }
}