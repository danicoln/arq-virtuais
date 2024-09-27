package com.essia.arq_virtuais.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntidadeNaoEncontradaException extends RuntimeException {

    public EntidadeNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntidadeNaoEncontradaException(String msg) {
        super(msg);
    }
}
