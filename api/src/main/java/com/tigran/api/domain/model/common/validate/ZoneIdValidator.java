package com.tigran.api.domain.model.common.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 7:26 PM
 */
@Documented
@Constraint(validatedBy = ZoneIdConstraintValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZoneIdValidator {

    String message() default "Invalid notification field composition";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
