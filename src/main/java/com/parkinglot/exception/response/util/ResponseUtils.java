package com.parkinglot.exception.response.util;

import com.parkinglot.exception.response.core.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

public class ResponseUtils {

    public static CoreResponse successResponse(String msg, int status, Object data) {
        return new SuccessResponse(msg, status, data);
    }

    public static CoreResponse errorResponse(String msg, int status) {
        return new ErrorResponse(msg, status);
    }

    public static CoreResponse errorResponse(String msg, HttpStatus status) {
        return new ErrorResponse(msg, status.value());
    }

    public static CoreResponse invalidRequstFieldResponse(String msg, List<InvalidField> fields) {
        return new InvalidRequestFieldResponse(msg, fields);
    }

    public static CoreResponse invalidRequstFieldResponse(String msg, InvalidField field) {
        return new InvalidFieldResponse(msg, 400, field);
    }

    public static List<InvalidField> buildFieldErrors(Errors errors){
        List<InvalidField> errorFields = new ArrayList<>();
        errors.getFieldErrors().forEach(error ->
            errorFields.add(new InvalidField(error.getField(), error.getCode())));
        return errorFields;
    }

}
