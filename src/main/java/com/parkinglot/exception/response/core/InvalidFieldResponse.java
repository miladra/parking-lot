package com.parkinglot.exception.response.core;

import com.parkinglot.exception.response.util.ResponseUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class InvalidFieldResponse extends ErrorResponse {

    private List<InvalidField> invalidFields;

    public InvalidFieldResponse(String msg, int status, List<InvalidField> field) {
        super(msg, status);
        this.invalidFields = field;
    }

    public InvalidFieldResponse(String msg, int status, InvalidField field) {
        this(msg, status, new ArrayList<>(Arrays.asList(field)));
    }

    public InvalidFieldResponse(String msg, int status, Errors errors) {
        this(msg, status, ResponseUtils.buildFieldErrors(errors));
    }

}
