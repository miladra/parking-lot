package com.parkinglot.repository;

import com.parkinglot.entity.Parking;
import com.parkinglot.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Vehicle findByPlate(String plate);

}
