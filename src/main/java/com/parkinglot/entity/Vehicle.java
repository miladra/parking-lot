package com.parkinglot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parkinglot.constant.enums.VehicleType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Entity
@Table(name = "vehicle")
@Data
@NoArgsConstructor
public class Vehicle extends BaseEntity {

    @Column(name="plate", nullable = false)
    @NotNull
    private String plate;

    @NotNull
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private VehicleType type;

    @OneToMany(mappedBy="vehicle" , cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Parking> parkings;

    public Vehicle(String plate){
        setPlate(plate);
    }

}
