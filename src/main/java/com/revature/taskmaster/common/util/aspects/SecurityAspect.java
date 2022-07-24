package com.revature.taskmaster.common.util.aspects;

import com.revature.taskmaster.auth.TokenService;
import com.revature.taskmaster.auth.dtos.Principal;
import com.revature.taskmaster.common.util.exceptions.AuthenticationException;
import com.revature.taskmaster.common.util.exceptions.TokenParseException;
import com.revature.taskmaster.common.util.web.security.SecurityContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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

    private void checkToken() {
        tokenService.checkToken(getCurrentRequest().getHeader("Authorization"));
    }

    private Principal getTokenHolderDetails() {
        return tokenService.extractTokenDetails(getCurrentRequest().getHeader("Authorization"));
    }

    private HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

}
