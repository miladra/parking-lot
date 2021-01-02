package com.parkinglot.repository;

import com.parkinglot.entity.Parking;
import com.parkinglot.entity.Payment;
import com.parkinglot.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query( "SELECT distinct payment " +
            "FROM Vehicle as vehicle ,Parking as parking , Payment as payment " +
            "WHERE vehicle.plate = :plate ")
    List<Payment> findAllByVehicle(@Param("plate") String plate);

}
