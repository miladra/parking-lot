package com.parkinglot.exception.response.core;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidField {

    String field;
    String message;

}
