package com.revature.taskmaster.common.util.web.security;

import com.revature.taskmaster.user.User;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreAuthorize {
    User.Role[] allowedRoles() default {};
}
