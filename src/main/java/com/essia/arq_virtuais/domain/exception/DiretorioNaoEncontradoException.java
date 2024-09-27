package com.essia.arq_virtuais.domain.exception;

public class DiretorioNaoEncontradoException extends EntidadeNaoEncontradaException {

    public DiretorioNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiretorioNaoEncontradoException(String msg) {
        super(msg);
    }

    public DiretorioNaoEncontradoException(String message, Long id) {
        super(message);
    }
}
