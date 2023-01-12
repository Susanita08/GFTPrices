package com.gft.es.prices.validators.anotations;

import com.gft.es.prices.validators.ConsistentDateParameterValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.gft.es.prices.utils.ConstantsUtils.END_DATE_LESS_THAT_START_DATE_ERROR;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ConsistentDateParameterValidator.class)
@Target({ METHOD, CONSTRUCTOR })
@Retention(RUNTIME)
@Documented
public @interface ConsistentDateParameters {
    String message() default END_DATE_LESS_THAT_START_DATE_ERROR;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
