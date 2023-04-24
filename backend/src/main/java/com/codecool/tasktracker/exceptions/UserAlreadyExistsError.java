package com.codecool.tasktracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsError extends RuntimeException {

    public UserAlreadyExistsError(String message) {
        super(message);
    }

}
