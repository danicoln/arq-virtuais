package com.essia.arq_virtuais.api.dto.input;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DiretorioInput {

    private Long id;
    private String nome;
    private DiretorioInput diretorioPai;
    private List<DiretorioInput> subDiretorios;
    private List<ArquivoInput> arquivos;

}
