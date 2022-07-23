package com.revature.taskmaster.common.util.aspects;

import com.revature.taskmaster.auth.TokenService;
import com.revature.taskmaster.auth.dtos.Principal;
import com.revature.taskmaster.common.util.exceptions.AuthenticationException;
import com.revature.taskmaster.common.util.web.security.SecurityContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

@Aspect
@Component
public class SecurityAspect {

    private final TokenService tokenService;
    private SecurityContext securityContext;

    @Autowired
    public SecurityAspect(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @Before(value = "@annotation(com.revature.taskmaster.common.util.web.security.AuthenticationRequired)")
    public void requireAuthentication() {
        if (!sessionExists()) throw new AuthenticationException("No session token found.");
        securityContext.setRequester(getTokenHolderDetails());
    }

    private <T extends Annotation> T getAnnotationFromJoinPoint(JoinPoint jp, Class<T> annotationType) {
        return ((MethodSignature) jp.getSignature()).getMethod().getAnnotation(annotationType);
    }

    private boolean sessionExists() {
        return tokenService.isTokenValid(getCurrentRequest().getHeader("Authorization"));
    }

    private Principal getTokenHolderDetails() {
        return tokenService.extractTokenDetails(getCurrentRequest().getHeader("Authorization"));
    }

    private HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

}
