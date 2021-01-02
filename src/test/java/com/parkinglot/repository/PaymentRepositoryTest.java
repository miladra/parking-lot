package com.parkinglot.repository;

import com.parkinglot.constant.enums.VehicleType;
import com.parkinglot.entity.Parking;
import com.parkinglot.entity.Payment;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class PaymentRepositoryTest {

    @Autowired
    private VehicleRepository vehicleRepository;


    @Autowired
    private PaymentRepository paymentRepository;


    @Test
    public void findAllByVehicle() {

        // given
        Vehicle vehicle = new Vehicle("99ب3445ایران۲۰");
        vehicle.setType(VehicleType.general);

        Parking parking = new Parking();
        parking.setInTime(new Date());
        parking.setOutTime(new Date());
        parking.setPrice(1);

        Payment payment = new Payment();
        payment.setTransactionCode(12334);
        payment.setIsSuccess(false);

        Payment payment2 = new Payment();
        payment2.setTransactionCode(12335);
        payment2.setIsSuccess(true);

        parking.setPayments(new HashSet<>(Arrays.asList(payment , payment2)));

        vehicle.setParkings(new HashSet<>(Arrays.asList(parking)));
        vehicle = vehicleRepository.save(vehicle);
        vehicle.setParkings(null);

        List<Payment> foundPayment = paymentRepository.findAllByVehicle(vehicle.getPlate());

        assertEquals(foundPayment.get(0).getIsSuccess() , false);
        assertEquals(foundPayment.get(1).getIsSuccess() , true);
    }
}