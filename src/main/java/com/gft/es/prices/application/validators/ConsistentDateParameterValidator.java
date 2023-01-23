package com.gft.es.prices.application.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.time.LocalDateTime;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class ConsistentDateParameterValidator implements ConstraintValidator<ConsistentDateParameters, Object[]> {

    private static final String ILEGAL_PARAMETER_DATE_ERROR = "Illegal method signature.";

    @Override
    public boolean isValid(Object[] value, ConstraintValidatorContext context) {
        if (value[0] == null) {
            return false;
        }

        if (!(value[0] instanceof LocalDateTime)) {
            throw new IllegalArgumentException(ILEGAL_PARAMETER_DATE_ERROR);
        }else return true;

    }
}