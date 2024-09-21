package com.essia.arq_virtuais.domain.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

    public EntidadeNaoEncontradaException(String msg) {
        super(msg);
    }
    public EntidadeNaoEncontradaException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
