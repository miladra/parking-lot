package com.parkinglot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "priceRate")
@Data
@NoArgsConstructor
public class PriceRate extends BaseEntity{

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name= "fromDate", nullable = false)
    private Date fromDate;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name= "toDate")
    private Date toDate;

    @Column(name= "daily", nullable = false)
    private Integer daily;

    @Column(name= "monthly", nullable = false)
    private Integer monthly;

    @Column(name= "hourly", nullable = false)
    private Integer hourly;


}
