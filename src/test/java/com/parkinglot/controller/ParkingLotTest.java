package com.parkinglot.controller;

import com.parkinglot.constant.enums.VehicleType;
import com.parkinglot.entity.Parking;
import com.parkinglot.entity.PriceRate;
import com.parkinglot.entity.Vehicle;
import com.parkinglot.repository.PriceRateRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient(registerRestTemplate = true)
public class ParkingLotTest {

    @LocalServerPort
    private int serverPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PriceRateRepository priceRateRepository;


    @Before
    public void setUp() {

        Calendar calendar = Calendar.getInstance();

        PriceRate priceRate = new PriceRate();
        calendar.set(2020,7,1);
        priceRate.setFromDate(calendar.getTime());
        priceRate.setMonthly(400000);
        priceRate.setDaily(17000);
        priceRate.setHourly(800);


        priceRateRepository.save(priceRate);
    }

    @Test
    public void park() {

        String saveUrl = "http://localhost:" + serverPort + "/rest/v1/vehicle/park";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        Vehicle vehicle = new Vehicle("22ب655ایران۲۰");
        vehicle.setType(VehicleType.general);

        HttpEntity<Vehicle> request = new HttpEntity<>(vehicle, headers);

        ResponseEntity<Parking> result = restTemplate.exchange(saveUrl, HttpMethod.PUT, request, Parking.class);

        assertNotNull(result);
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);

    }

    @Test
    public void unPark() {

        String saveUrl = "http://localhost:" + serverPort + "/rest/v1";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        Vehicle vehicle = new Vehicle("22ب65895ایران۲۰");
        vehicle.setType(VehicleType.general);

        HttpEntity<Vehicle> request = new HttpEntity<>(vehicle ,headers);

        ResponseEntity<Parking> parkResult = restTemplate.exchange(saveUrl+"/vehicle/park", HttpMethod.PUT, request, Parking.class);


        HttpEntity<?> unparkRequest = new HttpEntity<>(headers);
        Map<String, String > params = new HashMap<>();
        params.put("plate", vehicle.getPlate());

        ResponseEntity<Parking> upparkResult = restTemplate.exchange(saveUrl+"/vehicle/unpark/{plate}", HttpMethod.DELETE, unparkRequest, Parking.class , params);

        assertNotNull(upparkResult);
        assertEquals(upparkResult.getStatusCode(), HttpStatus.ACCEPTED);

    }

    @Test
    public void payment() {

        String saveUrl = "http://localhost:" + serverPort + "/rest/v1";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        Vehicle vehicle = new Vehicle("22ب65895ایران۲۰");
        vehicle.setType(VehicleType.general);

        HttpEntity<Vehicle> request = new HttpEntity<>(vehicle ,headers);

        ResponseEntity<Parking> parkResult = restTemplate.exchange(saveUrl+"/vehicle/park", HttpMethod.PUT, request, Parking.class);


        HttpEntity<?> unparkRequest = new HttpEntity<>(headers);
        Map<String, String > params = new HashMap<>();
        params.put("plate", vehicle.getPlate());

        ResponseEntity<Parking> upparkResult = restTemplate.exchange(saveUrl+"/vehicle/unpark/{plate}", HttpMethod.DELETE, unparkRequest, Parking.class , params);


        HttpEntity<?> paymentRequest = new HttpEntity<>(headers);
        Map<String, String > paymentParams = new HashMap<>();
        paymentParams.put("parkingId", String.valueOf(upparkResult.getBody().getId()));

        ResponseEntity<Long> paymentResult = restTemplate.exchange(saveUrl+"/vehicle/payment/{parkingId}", HttpMethod.PUT, paymentRequest, Long.class , paymentParams);

        assertNotNull(paymentResult);
        assertEquals(paymentResult.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void getTravelByPlate() {
        String saveUrl = "http://localhost:" + serverPort + "/rest/v1";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        Vehicle vehicle = new Vehicle("22ب65454ایران۲۰");
        vehicle.setType(VehicleType.general);

        HttpEntity<Vehicle> request = new HttpEntity<>(vehicle ,headers);

        ResponseEntity<Parking> parkResult = restTemplate.exchange(saveUrl+"/vehicle/park", HttpMethod.PUT, request, Parking.class);


        HttpEntity<?> unparkRequest = new HttpEntity<>(headers);
        Map<String, String > params = new HashMap<>();
        params.put("plate", vehicle.getPlate());


        ResponseEntity<Parking> unparkResult = restTemplate.exchange(saveUrl+"/vehicle/unpark/{plate}", HttpMethod.DELETE, unparkRequest, Parking.class , params);

        LocalDateTime ldt = LocalDateTime.now().minusDays(1);
        DateTimeFormatter formmat1 = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        String formatter = formmat1.format(ldt);

        LocalDateTime ldt2 = LocalDateTime.now().plusDays(1);
        DateTimeFormatter formmat2 = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        String formatter2 = formmat1.format(ldt2);

        Map<String, String > travelParams = new HashMap<>();
        travelParams.put("plate", vehicle.getPlate());
        travelParams.put("inTime", formatter);
        travelParams.put("outTime", formatter2);

        ResponseEntity<List<Parking>> travelResult = restTemplate.exchange(saveUrl+"/vehicle/travel/plate/{plate}/inTime/{inTime}/outTime/{outTime}" ,HttpMethod.GET, unparkRequest, new ParameterizedTypeReference<List<Parking>>(){},travelParams);

        assertNotNull(travelResult);
        assertEquals(travelResult.getStatusCode(), HttpStatus.FOUND);
    }

}