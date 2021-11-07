package com.ead.authuser.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class UsernameConstraintImpl implements ConstraintValidator<UsernameConstraint, String> {

    @Override
    public boolean isValid(final String username, final ConstraintValidatorContext constraintValidatorContext) {
        return username != null
                && !username.trim().isEmpty()
                && !username.contains(" ")
                && (username.length() >= 4 && username.length() <= 50);
    }
}
