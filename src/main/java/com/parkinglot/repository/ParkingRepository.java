package com.parkinglot.repository;

import com.parkinglot.entity.Parking;
import com.parkinglot.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Repository
@Transactional
public interface ParkingRepository extends JpaRepository<Parking, Long> {

    @Query( "SELECT parking " +
            "FROM Parking as parking , Vehicle as vehicle " +
            "WHERE vehicle.plate = :plate and parking.inTime >= :inTime and (parking.outTime <= :outTime or parking.outTime is null)" +
            "ORDER BY parking.inTime desc")
    List<Parking> findByVehicleOrderByInTimeAndByOutTime(@Param("plate") String plate , @Param("inTime") Date inTime, @Param("outTime") Date outTime);

    @Query( "SELECT parking " +
            "FROM Parking as parking , Vehicle as vehicle " +
            "WHERE vehicle.plate = :plate " +
            "ORDER BY parking.inTime desc")
    List<Parking> findByVehicleOrderByInTimeDesc(@Param("plate") String plate);


}
