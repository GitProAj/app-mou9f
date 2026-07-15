package com.mou9f.exceptions;


import org.springframework.security.core.AuthenticationException;

public class SubscriptionExpiredException  extends AuthenticationException {

    public SubscriptionExpiredException(String msg) {
        super(msg);
    }

    public SubscriptionExpiredException(String msg, Throwable cause) {
        super(msg, cause);
    }
}



