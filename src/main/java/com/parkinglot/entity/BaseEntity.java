package com.parkinglot.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Cloneable, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;
}