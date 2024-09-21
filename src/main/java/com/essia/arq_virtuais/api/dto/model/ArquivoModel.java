package com.essia.arq_virtuais.api.dto.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArquivoModel {

    private Long id;
    private String nome;
    private DiretorioModel diretorio;
}
