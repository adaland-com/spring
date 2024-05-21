package com.adaland.springsecurity.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    public static final String ENTITY_AlREADY_EXISTS_MESSAGE = "%s already exists";
    public static final String USER_AlREADY_EXISTS_MESSAGE = "User already exists";

    public EntityAlreadyExistsException(String message, String name) {
        super(String.format(message, name));
    }
}