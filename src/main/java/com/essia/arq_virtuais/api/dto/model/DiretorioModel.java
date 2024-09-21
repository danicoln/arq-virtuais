package com.essia.arq_virtuais.api.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DiretorioModel {

    private Long id;
    private String nome;
    private DiretorioModel diretorioPai;
    private List<DiretorioModel> subDiretorios;
    private List<ArquivoModel> arquivos;
}