package com.revature.taskmaster.common.util.web.validators.annotations;

import com.revature.taskmaster.common.util.web.validators.KnownPriorityLevelValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = KnownPriorityLevelValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface KnownPriorityLevel {
    String message() default "The provided priority level is unknown or invalid.";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
