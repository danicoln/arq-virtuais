package com.essia.arq_virtuais.api.dto.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArquivoInput {

    private Long id;
    private String nome;
    private DiretorioInput diretorio;
}
