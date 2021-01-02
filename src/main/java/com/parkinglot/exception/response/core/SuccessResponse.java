package com.parkinglot.exception.response.core;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class SuccessResponse extends CoreResponse {

    private Object data;

    public SuccessResponse(String msg) {
        super(msg, HttpStatus.OK.value());
    }

    public SuccessResponse(String msg, int status, Object data) {
        super(msg, status);
        this.data = data;
    }

}
