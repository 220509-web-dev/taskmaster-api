package com.revature.taskmaster.common.util.web.security;

public @interface Secured {
    String[] allowedRoles();
}
