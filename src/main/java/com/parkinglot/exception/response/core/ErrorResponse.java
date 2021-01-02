package com.parkinglot.exception.response.core;

public class ErrorResponse extends CoreResponse {

    public ErrorResponse(String msg, int status) {
        super(msg, status);
    }

}
