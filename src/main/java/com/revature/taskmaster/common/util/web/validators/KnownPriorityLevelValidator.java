package com.revature.taskmaster.common.util.web.validators;

import com.revature.taskmaster.common.util.web.validators.annotations.KnownPriorityLevel;
import com.revature.taskmaster.task.Task;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class KnownPriorityLevelValidator implements ConstraintValidator<KnownPriorityLevel, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return Task.Priority.fromValue(value) != null;
    }
}
