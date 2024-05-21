package com.adaland.springsecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccessDeniedException extends RuntimeException {

    public static final String ENTITY_NOT_FOUND_MESSAGE = "Not found %s";
    public static final String ENTITY_CLIENT_NOT_FOUND = "Client not found";
    public static final String ENTITY_ACCOUNT_NOT_FOUND = "Account not found";
    public static final String ENTITY_USER_NOT_FOUND = "User not found";

    public AccessDeniedException(String message, String name) {
        super(String.format(message, name));
    }
}