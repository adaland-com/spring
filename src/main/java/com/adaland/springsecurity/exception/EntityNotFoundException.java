package com.adaland.springsecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends NoSuchElementException {

    public static final String ENTITY_NOT_FOUND_MESSAGE = "Not found %s";
    public static final String ENTITY_CLIENT_NOT_FOUND = "Client not found";

    public static final String ENTITY_GAME_NOT_FOUND_BY_ID = "Not found game with id: ";
    public static final String ENTITY_GAME_NOT_FOUND_BY_TITLE = "Not found game with title: ";

    public static final String ENTITY_GAME_CATEGORY_NOT_FOUND_BY_ID = "Not found game category with id: ";
    public static final String ENTITY_GAME_CATEGORY_NOT_FOUND_BY_NAME = "Not found game category with name: ";
    public static final String ENTITY_USER_NOT_FOUND = "User not found";

    public EntityNotFoundException(String message, String name) {
        super(String.format(message, name));
    }
    public EntityNotFoundException(String message){};
}