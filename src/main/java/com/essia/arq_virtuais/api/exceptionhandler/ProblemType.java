package com.essia.arq_virtuais.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
    ERRO_DE_NEGOCIO("/erro-de-negocio","Violação de regra de negócio" ),
    FALHA_NA_REQUISICAO("/falha-na-requisicao", "Falha ao ler a requisição" );

    private String uri;
    private String title;

    ProblemType(String path, String title) {
        this.uri = "https://essia.com" + path;
        this.title = title;
    }
}
