package com.revature.taskmaster.common.util.web.validators;

import com.revature.taskmaster.common.util.web.validators.annotations.KnownTaskState;
import com.revature.taskmaster.task.Task;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class KnownTaskStateValidator implements ConstraintValidator<KnownTaskState, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Task.State.fromValue(value) != null;
    }
}
