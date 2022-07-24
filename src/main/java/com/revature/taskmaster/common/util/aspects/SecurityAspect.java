package com.revature.taskmaster.common.util.aspects;

import com.revature.taskmaster.auth.TokenService;
import com.revature.taskmaster.auth.dtos.Principal;
import com.revature.taskmaster.common.util.exceptions.AuthenticationException;
import com.revature.taskmaster.common.util.exceptions.AuthorizationException;
import com.revature.taskmaster.common.util.exceptions.TokenParseException;
import com.revature.taskmaster.common.util.web.security.PreAuthorize;
import com.revature.taskmaster.common.util.web.security.SecurityContext;
import com.revature.taskmaster.user.User;
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
import java.util.Arrays;
import java.util.List;

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

    @Before(value = "@annotation(com.revature.taskmaster.common.util.web.security.Authenticated)")
    public void requireAuthentication() {
        try {
            checkToken();
            securityContext.setRequester(getTokenHolderDetails());
        } catch (TokenParseException e) {
            throw new AuthenticationException("Could not parse provided token", e);
        }
    }

    @Before(value = "@annotation(com.revature.taskmaster.common.util.web.security.PreAuthorize)")
    public void preAuthorizeRequest(JoinPoint jp) {
        if (!sessionExists()) throw new AuthenticationException("No active session found");
        List<User.Role> allowedRoles = Arrays.asList(getAnnotationFromJoinPoint(jp, PreAuthorize.class).allowedRoles());
        Principal requester = securityContext.getRequester();
        User.Role requesterRole = User.Role.valueOf(requester.getAuthUserRole());
        if (!requester.isAdmin() && !allowedRoles.contains(requesterRole)) {
            throw new AuthorizationException();
        }
    }

    private <T extends Annotation> T getAnnotationFromJoinPoint(JoinPoint jp, Class<T> annotationType) {
        return ((MethodSignature) jp.getSignature()).getMethod().getAnnotation(annotationType);
    }

    private void checkToken() {
        tokenService.checkToken(getCurrentRequest().getHeader("Authorization"));
    }

    private Principal getTokenHolderDetails() {
        return tokenService.extractTokenDetails(getCurrentRequest().getHeader("Authorization"));
    }

    private boolean sessionExists() {
        return securityContext.getRequester() != null;
    }

    private HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

}
