package com.spring.synctracker.task_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenAuthorizationException extends RuntimeException {
    public ForbiddenAuthorizationException(String message) {
        super(message);
    }
}
