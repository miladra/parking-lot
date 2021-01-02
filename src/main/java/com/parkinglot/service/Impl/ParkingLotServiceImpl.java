package com.parkinglot.service.Impl;

import com.parkinglot.entity.Payment;
import com.parkinglot.entity.PriceRate;
import com.parkinglot.service.ParkingLotService;
import com.parkinglot.entity.Parking;
import com.parkinglot.entity.Vehicle;
import com.parkinglot.repository.ParkingRepository;
import com.parkinglot.repository.PaymentRepository;
import com.parkinglot.repository.PriceRateRepository;
import com.parkinglot.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {

    private static final Logger logger = LoggerFactory.getLogger(ParkingLotServiceImpl.class);

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private PriceRateRepository priceRateRepository;

    @Autowired
    private PaymentRepository paymentRepository;


    @Override
    public Parking park(Vehicle vehicle) {
        if (StringUtils.isEmpty(vehicle.getPlate())) {
            throw new RuntimeException("Empty Plate");
        }

        if (Objects.isNull(vehicle.getType())) {
            throw new RuntimeException("Invalid type");
        }

        Vehicle existVehicle = vehicleRepository.findByPlate(vehicle.getPlate());
        if (Objects.nonNull(existVehicle)) {
           Parking lastParking = parkingRepository.findByVehicleOrderByInTimeDesc(existVehicle.getPlate()).get(0);

            if (Objects.isNull(lastParking.getOutTime())) {
                throw new RuntimeException("Vehicle departure is not recorded.");
            }

        } else {
            existVehicle = vehicleRepository.save(vehicle);
        }

        Parking newParking = new Parking();
        newParking.setInTime(new Date());
        newParking.setVehicle(existVehicle);
        newParking = parkingRepository.save(newParking);

        return newParking;
    }

    @Override
    public Parking unPark (String plate) {

        if (StringUtils.isEmpty(plate)) {
            throw new RuntimeException("Empty Plate");
        }

        Vehicle existVehicle = vehicleRepository.findByPlate(plate);

        Parking lastParking;

        if (Objects.nonNull(existVehicle)) {
            lastParking = parkingRepository.findByVehicleOrderByInTimeDesc(existVehicle.getPlate()).get(0);

            if (Objects.isNull(lastParking.getOutTime())) {

                LocalDateTime now = LocalDateTime.now();
                PriceRate priceRate = priceRateRepository.findTopByToDateIsNull();

                Period period = Period.between(lastParking.getInTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),now.toLocalDate());

                int months = period.getMonths();
                int days = period.getDays();
                int hours = now.toLocalTime().getHour() - lastParking.getInTime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime().getHour();

                int price=0;

                if(months>0){

                    price =  priceRate.getMonthly() * months;
                }

                if(days>0){
                    price +=  priceRate.getDaily() * days;
                }

                if(hours>0){
                    price +=  priceRate.getHourly() * hours;
                }



                lastParking.setOutTime(new Date());
                lastParking.setPrice(price);
                lastParking.setPriceRate(priceRate);
                lastParking = parkingRepository.save(lastParking);

            } else {
                throw new RuntimeException("The car has already been taken out.");
            }

        } else {
            throw new RuntimeException("Vehicle Never Parked");
        }

        return lastParking;
    }

    @Override
    public Long payment(Long parkingId) {

        Optional<Parking> parking = parkingRepository.findById(parkingId);
        Payment payment  = new Payment();

        if (parking.isPresent()) {
            Random rand = new Random();
            payment.setTransactionCode(rand.nextInt(9999999));
            payment.setIsSuccess(Math.random() < 0.5);
            payment.setParking(parking.get());
            payment = paymentRepository.save(payment);
        } else{
            throw new RuntimeException("Parking Not Found");
        }

        return payment.getId();
    }

}
