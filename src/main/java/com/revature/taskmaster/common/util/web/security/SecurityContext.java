package com.revature.taskmaster.common.util.web.security;

import com.revature.taskmaster.auth.dtos.Principal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class SecurityContext {

    private Principal requester;

    public Principal getRequester() {
        return requester;
    }

    public void setRequester(Principal requester) {
        this.requester = requester;
    }

    @Override
    public String toString() {
        return "SecurityContext{" +
                "requester=" + requester +
                '}';
    }

}
