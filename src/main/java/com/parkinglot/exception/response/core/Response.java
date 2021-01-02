package com.parkinglot.exception.response.core;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Data
public class Response implements Serializable {

    public static final long serialVersionUID = -7654569861234987654L;

    private String ts;
    private String msg;
    private ResponseParams params;
    private String responseCode;
    private Map<String, Object> result = new HashMap<>();

    public Response() {
        this.ts = new Timestamp(System.currentTimeMillis()).toString();
    }

    public void put(String k, Object v) { result.put(k, v); }

    public void putAll(Map<String, Object> result) { this.result.putAll(result); }
}
