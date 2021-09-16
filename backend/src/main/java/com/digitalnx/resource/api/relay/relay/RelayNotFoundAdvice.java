package com.digitalnx.resource.api.relay.relay;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RelayNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(RelayNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(RelayNotFoundException ex) {
        return ex.getMessage();
    }
}
