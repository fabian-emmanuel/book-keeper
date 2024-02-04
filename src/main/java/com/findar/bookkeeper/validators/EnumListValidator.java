package com.findar.bookkeeper.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class EnumListValidator implements ConstraintValidator<Enum, List<String>> {
    private Enum annotation;

    @Override
    public void initialize(Enum annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(List<String> valuesForValidation, ConstraintValidatorContext constraintValidatorContext) {
        if (valuesForValidation == null || valuesForValidation.isEmpty()) {
            return true;
        }

        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if (enumValues != null) {
            for (String value : valuesForValidation) {
                boolean valueMatchesEnum = false;
                for (Object enumValue : enumValues) {
                    if (valueMatchesEnumValue(value, enumValue)) {
                        valueMatchesEnum = true;
                        break;
                    }
                }
                if (!valueMatchesEnum) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean valueMatchesEnumValue(String valueForValidation, Object enumValue) {
        String enumValueString = enumValue.toString();
        return this.annotation.ignoreCase()
                ? valueForValidation.equalsIgnoreCase(enumValueString)
                : valueForValidation.equals(enumValueString);
    }
}
