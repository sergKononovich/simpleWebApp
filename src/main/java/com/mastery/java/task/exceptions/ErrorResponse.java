package com.mastery.java.task.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private List<String> details;

    public ErrorResponse(final String msg, final List<String> detailsList) {
        super();
        this.message = msg;
        this.details = detailsList;
    }
}
