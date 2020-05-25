package com.mastery.java.task.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    /**
     * Error message.
     */
    private String message;
    /**
     * Error details.
     */
    private List<String> errors;

    private HttpStatus httpStatus;

    public ErrorResponse(String msg, final List<String> detailsList, final HttpStatus httpStatus) {
        super();
        this.message = msg;
        this.errors = detailsList;
        this.httpStatus = httpStatus;
    }

    public ErrorResponse(String msg, final String details, final HttpStatus httpStatus) {
        super();
        this.message = msg;
        this.errors = Collections.singletonList(details);
        this.httpStatus = httpStatus;
    }
}
