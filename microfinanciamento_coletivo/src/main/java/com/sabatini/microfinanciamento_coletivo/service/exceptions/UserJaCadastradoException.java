package com.sabatini.microfinanciamento_coletivo.service.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UserJaCadastradoException extends RuntimeException {

    public UserJaCadastradoException() {
    }

    public UserJaCadastradoException(String message) {
        super(message);
    }

}
