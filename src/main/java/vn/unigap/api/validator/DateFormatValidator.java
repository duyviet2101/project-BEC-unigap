package vn.unigap.api.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {
    String pattern = "yyyy-MM-dd";

    @Override
    public void initialize(DateFormat constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null)
            return true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.pattern);
        try {
            LocalDate.parse(s, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
