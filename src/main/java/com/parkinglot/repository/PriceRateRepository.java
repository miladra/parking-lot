package com.parkinglot.repository;

import com.parkinglot.entity.PriceRate;
import com.parkinglot.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Repository
@Transactional
public interface PriceRateRepository extends JpaRepository<PriceRate, Long> {

    PriceRate findTopByToDateIsNull();

}
