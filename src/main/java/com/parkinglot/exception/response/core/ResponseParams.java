package com.parkinglot.exception.response.core;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseParams implements Serializable {

    public static final long serialVersionUID = -986456864789643975L;

    private String resMsgId;
    private String msgId;
    private String err;
    private String status;
    private String errMsg;

}
