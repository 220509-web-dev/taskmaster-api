package com.revature.taskmaster.common.util.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

    // TODO: implement security aspect advice logic after JWT implementation
    @Before("")
    public void requireAuthentication() {

    }

}
