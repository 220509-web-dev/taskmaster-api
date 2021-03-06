package com.revature.taskmaster.common.util.web.validators.annotations;

import com.revature.taskmaster.common.util.web.validators.KnownRoleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = KnownRoleValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface KnownUserRole {
    String message() default "The provided role is unknown.";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
