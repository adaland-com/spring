package com.adaland.springsecurity.exception;

public class EntityNotDeletedException extends RuntimeException {

    public static final String NOT_DELETED_MESSAGE = "Couldn't delete %s";

    public EntityNotDeletedException(String message, String name) {
        super(String.format(message, name));
    }
}