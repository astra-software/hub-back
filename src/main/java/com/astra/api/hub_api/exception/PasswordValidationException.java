package com.astra.api.hub_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordValidationException extends RuntimeException {
    public PasswordValidationException(String ex) { super(ex); }
}
