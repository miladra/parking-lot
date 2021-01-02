package com.parkinglot.repository;

import com.parkinglot.entity.PriceRate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Calendar;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class PriceRateRepositoryTest {

    @Autowired
    private PriceRateRepository priceRateRepository;

    @Test
    public void findTopByToDateIsNull() {

        // given
        Calendar calendar = Calendar.getInstance();


        PriceRate priceRate = new PriceRate();
        calendar.set(2020,12,1);
        priceRate.setFromDate(calendar.getTime());
        calendar.set(2020,12,10);
        priceRate.setToDate(calendar.getTime());
        priceRate.setMonthly(300000);
        priceRate.setDaily(12000);
        priceRate.setHourly(500);

        priceRateRepository.save(priceRate);


        PriceRate priceRate2 = new PriceRate();
        calendar.set(2020,12,11);
        priceRate2.setFromDate(calendar.getTime());
        calendar.set(2020,12,20);
        priceRate2.setToDate(calendar.getTime());
        priceRate2.setMonthly(350000);
        priceRate2.setDaily(15000);
        priceRate2.setHourly(600);


        priceRateRepository.save(priceRate2);

        PriceRate priceRate3 = new PriceRate();
        calendar.set(2020,12,21);
        priceRate3.setFromDate(calendar.getTime());
        priceRate3.setMonthly(400000);
        priceRate3.setDaily(17000);
        priceRate3.setHourly(800);


        priceRateRepository.save(priceRate3);



        // when
        PriceRate foundPriceRate = priceRateRepository.findTopByToDateIsNull();

        // then
        assertEquals(foundPriceRate.getHourly(), priceRate3.getHourly());


    }
}