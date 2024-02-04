package com.findar.bookkeeper.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class EnumValidator implements ConstraintValidator<Enum, String> {
    private Enum annotation;

    @Override
    public void initialize(Enum annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(valueForValidation)) {
            return true;
        }

        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (valueMatchesEnumValue(valueForValidation, enumValue)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean valueMatchesEnumValue(String valueForValidation, Object enumValue) {
        String enumValueString = enumValue.toString();
        return this.annotation.ignoreCase()
                ? valueForValidation.equalsIgnoreCase(enumValueString)
                : valueForValidation.equals(enumValueString);
    }
}
