package com.parkinglot.exception.response.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class CoreResponse {

    protected String message;
    protected int status;

}
