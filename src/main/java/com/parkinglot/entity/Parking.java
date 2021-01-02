package com.parkinglot.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "parking")
@Data
@NoArgsConstructor
public class Parking extends BaseEntity {


    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name= "inTime", nullable = false)
    private Date inTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name= "outTime")
    private Date outTime;

    @Column(name= "price")
    private Integer price;

    @ManyToOne
    @JoinColumn(name="vehicleId")
    private Vehicle vehicle;

    @OneToOne
    @JoinColumn(name="priceRateId")
    private PriceRate priceRate;

    @OneToMany(mappedBy="parking" , cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Payment> payments;

}

