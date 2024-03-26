package com.example.Javada.validator;

import com.example.Javada.entity.user;
import com.example.Javada.validator.annotation.ValidUserId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidUserIdValidator implements ConstraintValidator<ValidUserId, user> {
    @Override
    public boolean isValid(user user, ConstraintValidatorContext context){
    if(user == null)
        return true;
    return user.getId() != null;
    }
}
