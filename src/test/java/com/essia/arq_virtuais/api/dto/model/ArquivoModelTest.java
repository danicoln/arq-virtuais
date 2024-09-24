package com.essia.arq_virtuais.api.dto.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArquivoModelTest {

    private ArquivoModel arquivoModel;
    private DiretorioModel diretorioModel;

    @BeforeEach
    void setUp() {
        arquivoModel = new ArquivoModel();
        arquivoModel.setId(1L);
        arquivoModel.setNome("Arquivo de Teste");

        diretorioModel = new DiretorioModel();
        diretorioModel.setId(10L);
        diretorioModel.setNome("Diretório de Teste");

        arquivoModel.setDiretorio(diretorioModel);
    }

    @Test
    void testArquivoModelAttributes() {
        assertNotNull(arquivoModel);
        assertEquals(1L, arquivoModel.getId());
        assertEquals("Arquivo de Teste", arquivoModel.getNome());
    }

    @Test
    void testArquivoComDiretorioAssociado() {
        assertNotNull(arquivoModel.getDiretorio());
        assertEquals(10L, arquivoModel.getDiretorio().getId());
        assertEquals("Diretório de Teste", arquivoModel.getDiretorio().getNome());
    }

    @Test
    void testSetDiretorio() {
        DiretorioModel novoDiretorio = new DiretorioModel();
        novoDiretorio.setId(20L);
        novoDiretorio.setNome("Novo Diretório");

        arquivoModel.setDiretorio(novoDiretorio);

        assertNotNull(arquivoModel.getDiretorio());
        assertEquals(20L, arquivoModel.getDiretorio().getId());
        assertEquals("Novo Diretório", arquivoModel.getDiretorio().getNome());
    }

    @Test
    void testArquivoIdENomeNulos() {
        ArquivoModel arquivoVazio = new ArquivoModel();

        assertNull(arquivoVazio.getId());
        assertNull(arquivoVazio.getNome());
        assertNull(arquivoVazio.getDiretorio());
    }
}
