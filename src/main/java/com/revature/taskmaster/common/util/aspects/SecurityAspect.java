package com.revature.taskmaster.common.util.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

    // TODO: implement security aspect advice logic after JWT implementation
    public void requireRequesterToHaveRole() {

    }

}
