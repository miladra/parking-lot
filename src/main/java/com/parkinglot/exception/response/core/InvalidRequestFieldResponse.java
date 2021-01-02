package com.parkinglot.exception.response.core;

import org.springframework.validation.Errors;

import java.util.List;

public class InvalidRequestFieldResponse extends InvalidFieldResponse {

    public InvalidRequestFieldResponse(String msg, List<InvalidField> field) {
        super(msg, 400, field);
    }

    public InvalidRequestFieldResponse(String msg, InvalidField field) {
        super(msg, 400, field);
    }

    public InvalidRequestFieldResponse(String msg, Errors errors) {
        super(msg, 400, errors);
    }
}
