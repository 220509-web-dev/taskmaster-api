package com.revature.taskmaster.common.util.web.validators.annotations;

import com.revature.taskmaster.common.util.web.validators.KnownTaskStateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = KnownTaskStateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface KnownTaskState {
    String message() default "The provided task state is unknown";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
