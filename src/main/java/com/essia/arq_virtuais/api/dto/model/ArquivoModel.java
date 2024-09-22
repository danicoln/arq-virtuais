package com.essia.arq_virtuais.api.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArquivoModel {

    private Long id;
    private String nome;
    @JsonIgnore
    private DiretorioModel diretorio;
}
