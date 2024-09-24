package com.essia.arq_virtuais.api.dto.input;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiretorioInputTest {

    private DiretorioInput diretorioInput;
    private DiretorioInput subDiretorio;
    private ArquivoInput arquivoInput;

    @BeforeEach
    void setUp() {
        diretorioInput = new DiretorioInput();
        diretorioInput.setId(1L);
        diretorioInput.setNome("Diretório Principal");

        subDiretorio = new DiretorioInput();
        subDiretorio.setId(2L);
        subDiretorio.setNome("Subdiretório");

        arquivoInput = new ArquivoInput();
        arquivoInput.setId(1L);
        arquivoInput.setNome("Arquivo 1");

        // Adicionando subdiretório e arquivos
        List<DiretorioInput> subDiretorios = new ArrayList<>();
        subDiretorios.add(subDiretorio);
        diretorioInput.setSubDiretorios(subDiretorios);

        List<ArquivoInput> arquivos = new ArrayList<>();
        arquivos.add(arquivoInput);
        diretorioInput.setArquivos(arquivos);
    }

    @Test
    void testDiretorioInputAttributes() {
        assertNotNull(diretorioInput);
        assertEquals(1L, diretorioInput.getId());
        assertEquals("Diretório Principal", diretorioInput.getNome());
    }

    @Test
    void testDiretorioComSubDiretorios() {
        assertNotNull(diretorioInput.getSubDiretorios());
        assertEquals(1, diretorioInput.getSubDiretorios().size());
        assertEquals("Subdiretório", diretorioInput.getSubDiretorios().get(0).getNome());
    }

    @Test
    void testDiretorioComArquivos() {
        assertNotNull(diretorioInput.getArquivos());
        assertEquals(1, diretorioInput.getArquivos().size());
        assertEquals("Arquivo 1", diretorioInput.getArquivos().get(0).getNome());
    }

    @Test
    void testDiretorioComDiretorioPai() {
        DiretorioInput diretorioPai = new DiretorioInput();
        diretorioPai.setId(0L);
        diretorioPai.setNome("Diretório Pai");

        diretorioInput.setDiretorioPai(diretorioPai);

        assertNotNull(diretorioInput.getDiretorioPai());
        assertEquals(0L, diretorioInput.getDiretorioPai().getId());
        assertEquals("Diretório Pai", diretorioInput.getDiretorioPai().getNome());
    }
}
