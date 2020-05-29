package com.mastery.java.task.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class DateValidator implements ConstraintValidator<ValidateDate, String> {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        DateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

        sdf.setLenient(false);
        try {
            sdf.parse(value);
        } catch (ParseException e) {
            return false;
        }
        return LocalDate.parse(value).isBefore(LocalDate.now());
    }
}
