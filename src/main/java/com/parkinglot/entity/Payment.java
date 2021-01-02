package com.parkinglot.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
public class Payment extends BaseEntity {


    @Column(name= "transactionCode")
    private Integer transactionCode;

    @Column(name= "isSuccess")
    private Boolean isSuccess;

    @ManyToOne
    @JoinColumn(name="parkingId")
    private Parking parking;

}

