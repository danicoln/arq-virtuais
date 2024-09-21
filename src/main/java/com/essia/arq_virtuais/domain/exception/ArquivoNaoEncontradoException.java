package com.essia.arq_virtuais.domain.exception;

public class ArquivoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public ArquivoNaoEncontradoException(String msg) {
        super(msg);
    }

    public ArquivoNaoEncontradoException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ArquivoNaoEncontradoException(String msg, Long id) {
        this(String.format(msg, id));
    }
}
