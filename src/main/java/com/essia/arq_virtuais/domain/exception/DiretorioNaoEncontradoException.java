package com.essia.arq_virtuais.domain.exception;

public class DiretorioNaoEncontradoException extends EntidadeNaoEncontradaException {


    public DiretorioNaoEncontradoException(String msg) {
        super(msg);
    }

    public DiretorioNaoEncontradoException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public DiretorioNaoEncontradoException(String msg, Long id) {
        this(String.format(msg, id));
    }
}
