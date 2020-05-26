package com.mastery.java.task.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String NO_SUCH_EMPLOYEE = "There is no such employee";
    public static final String VALIDATION_ERROR = "Validation error";

    @ExceptionHandler(ThereIsNoSuchEmployeeException.class)
    ResponseEntity<ErrorResponse> handleThereIsNoSuchEmployeeException(ThereIsNoSuchEmployeeException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(NO_SUCH_EMPLOYEE, details, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ErrorResponse errorResponse = new ErrorResponse(VALIDATION_ERROR, errors, HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, errorResponse, headers, errorResponse.getHttpStatus(), request);
    }
}