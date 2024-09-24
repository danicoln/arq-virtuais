package com.essia.arq_virtuais.api.dto.input;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArquivoInputTest {

    private ArquivoInput arquivoInput;
    private DiretorioInput diretorioInput;

    @BeforeEach
    void setUp() {
        arquivoInput = new ArquivoInput();
        arquivoInput.setId(1L);
        arquivoInput.setNome("Arquivo Teste");

        diretorioInput = new DiretorioInput();
        diretorioInput.setId(10L);
        diretorioInput.setNome("Diretório Teste");

        arquivoInput.setDiretorio(diretorioInput);
    }

    @Test
    void testArquivoInputAttributes() {
        assertNotNull(arquivoInput);
        assertEquals(1L, arquivoInput.getId());
        assertEquals("Arquivo Teste", arquivoInput.getNome());
    }

    @Test
    void testArquivoComDiretorioAssociado() {
        assertNotNull(arquivoInput.getDiretorio());
        assertEquals(10L, arquivoInput.getDiretorio().getId());
        assertEquals("Diretório Teste", arquivoInput.getDiretorio().getNome());
    }

    @Test
    void testSetDiretorio() {
        DiretorioInput novoDiretorio = new DiretorioInput();
        novoDiretorio.setId(20L);
        novoDiretorio.setNome("Novo Diretório");

        arquivoInput.setDiretorio(novoDiretorio);

        assertNotNull(arquivoInput.getDiretorio());
        assertEquals(20L, arquivoInput.getDiretorio().getId());
        assertEquals("Novo Diretório", arquivoInput.getDiretorio().getNome());
    }

    @Test
    void testArquivoIdENomeNulos() {
        ArquivoInput arquivoVazio = new ArquivoInput();

        assertNull(arquivoVazio.getId());
        assertNull(arquivoVazio.getNome());
        assertNull(arquivoVazio.getDiretorio());
    }
}
