package com.revature.taskmaster.common.util.web.security;

import com.revature.taskmaster.user.User;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PostAuthorize {
    User.Role[] allowedRoles() default {};
    boolean allowResourceOwner() default true;
}
