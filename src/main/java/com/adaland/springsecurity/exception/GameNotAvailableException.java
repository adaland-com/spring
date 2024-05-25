package com.adaland.springsecurity.exception;

public class GameNotAvailableException extends RuntimeException {
    public static final String NOT_AVAILABLE_MESSAGE = "Game %s already is not available";

    public GameNotAvailableException(String message, String name) {
        super(String.format(message, name));
    }
}