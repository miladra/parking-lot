package com.parkinglot.controller;

import com.parkinglot.entity.Vehicle;
import com.parkinglot.service.ParkingLotRepostService;
import com.parkinglot.service.ParkingLotService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/rest/v1")
@Api(value="Parking Lot System")
@ApiResponses(value = {@ApiResponse(code = 200, message = "Success|OK"), @ApiResponse(code = 401, message = "not authorized!"), @ApiResponse(code = 403, message = "forbidden!!!"), @ApiResponse(code = 404, message = "not found!!!"), @ApiResponse(code = 500, message = "Resource not found")})
public class ParkingLot {

    private static final Logger logger = LoggerFactory.getLogger(ParkingLot.class);

    @Autowired
    protected ParkingLotService parkingLotService;

    @Autowired
    protected ParkingLotRepostService parkingLotRepostService;

    @PutMapping(value = "/vehicle/park")
    public ResponseEntity<?> park (@RequestBody Vehicle vehicle) {
        try {
            return new ResponseEntity<>(parkingLotService.park(vehicle), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping(value = "/vehicle/unpark/{plate:.+}")
    public ResponseEntity<?> unPark (@PathVariable("plate") String plate) {
        try {
            return new ResponseEntity<>(parkingLotService.unPark(plate), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping(value = "/vehicle/payment/{parkingId:.+}")
    public ResponseEntity<?> payment (@PathVariable("parkingId") String parkingId) {
        try {
            return new ResponseEntity<>(parkingLotService.payment(Long.valueOf(parkingId)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(value = "/vehicle/travel/plate/{plate}/inTime/{inTime}/outTime/{outTime}")
    public ResponseEntity<?> getTravelByPlate (@PathVariable(value = "inTime" , required = false) @DateTimeFormat(pattern="dd-MM-yyyy") Date inTime,
                                               @PathVariable(value = "outTime", required = false) @DateTimeFormat(pattern="dd-MM-yyyy") Date outTime,
                                               @PathVariable(value = "plate") String plate) {
        try {

            return new ResponseEntity<>(parkingLotRepostService.getAllVehicleTravel(plate , inTime , outTime), HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }


}
