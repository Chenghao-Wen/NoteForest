package com.noteforest.validation;

import com.noteforest.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * State validation rules
 * < Provide validation rules for the annotation, validate the data type >
 */
public class StateValidation implements ConstraintValidator<State, String> {
    /**
     * @param s
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        // Pass through these two explanations
        return s.equals("Draft") || s.equals("Published");
    }
}
