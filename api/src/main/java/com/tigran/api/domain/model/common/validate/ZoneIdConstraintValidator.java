package com.tigran.api.domain.model.common.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Objects;
import java.util.TimeZone;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 7:26 PM
 */
public class ZoneIdConstraintValidator implements ConstraintValidator<ZoneIdValidator, String> {

    @Override
    public void initialize(final ZoneIdValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final String zoneId, final ConstraintValidatorContext context) {
        String errorMessage = "";
        if (Objects.isNull(zoneId) || zoneId.isBlank() || zoneId.isEmpty()) {
            errorMessage = "required";
        } else if (!Arrays.asList(TimeZone.getAvailableIDs()).contains(zoneId)) {
            errorMessage = "Wrong zone id";
        }
        if (!errorMessage.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
            return false;
        }
        return true;
    }
}
