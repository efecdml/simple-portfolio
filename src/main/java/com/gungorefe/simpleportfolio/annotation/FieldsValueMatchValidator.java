package com.gungorefe.simpleportfolio.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {
    private String field;
    private String fieldToMatch;

    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldToMatch = constraintAnnotation.fieldToMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldToMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldToMatch);

        if (fieldValue != null)
            return fieldValue.equals(fieldToMatchValue);
        else
            return fieldToMatchValue == null;
    }
}
