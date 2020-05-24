package com.mastery.java.task.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String NO_SUCH_EMPLOYEE = "There is no such employee";

    @ExceptionHandler(ThereIsNoSuchEmployeeException.class)
    ResponseEntity<ErrorResponse> handleThereIsNoSuchEmployeeException(
            ThereIsNoSuchEmployeeException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(NO_SUCH_EMPLOYEE, details);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    private static class AwesomeException {
        private String message;
    }
}
