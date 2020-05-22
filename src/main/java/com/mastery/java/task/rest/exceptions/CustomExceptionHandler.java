package com.mastery.java.task.rest.exceptions;

import com.mastery.java.task.service.exceptions.ThereIsNoSuchEmployeeException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ThereIsNoSuchEmployeeException.class)
    ResponseEntity<AwesomeException> handleThereIsNoSuchEmployeeException() {
        return new ResponseEntity<>(new AwesomeException("There is no such employee"), HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    private static class AwesomeException {
        private String message;
    }
}
