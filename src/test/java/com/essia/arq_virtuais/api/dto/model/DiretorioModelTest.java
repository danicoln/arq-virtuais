package com.essia.arq_virtuais.api.dto.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiretorioModelTest {

    private DiretorioModel diretorioModel;
    private DiretorioModel diretorioPai;
    private List<DiretorioModel> subDiretorios;
    private List<ArquivoModel> arquivos;

    @BeforeEach
    void setUp() {
        diretorioPai = new DiretorioModel();
        diretorioPai.setId(1L);
        diretorioPai.setNome("Diretório Pai");

        DiretorioModel subDiretorio = new DiretorioModel();
        subDiretorio.setId(2L);
        subDiretorio.setNome("Subdiretório");

        subDiretorios = new ArrayList<>();
        subDiretorios.add(subDiretorio);

        ArquivoModel arquivo = new ArquivoModel();
        arquivo.setId(10L);
        arquivo.setNome("Arquivo no Diretório");

        arquivos = new ArrayList<>();
        arquivos.add(arquivo);

        diretorioModel = new DiretorioModel();
        diretorioModel.setId(3L);
        diretorioModel.setNome("Diretório Principal");
        diretorioModel.setDiretorioPai(diretorioPai);
        diretorioModel.setSubDiretorios(subDiretorios);
        diretorioModel.setArquivos(arquivos);
    }

    @Test
    void testDiretorioModelAttributes() {
        assertNotNull(diretorioModel);
        assertEquals(3L, diretorioModel.getId());
        assertEquals("Diretório Principal", diretorioModel.getNome());
    }

    @Test
    void testDiretorioComPaiAssociado() {
        assertNotNull(diretorioModel.getDiretorioPai());
        assertEquals(1L, diretorioModel.getDiretorioPai().getId());
        assertEquals("Diretório Pai", diretorioModel.getDiretorioPai().getNome());
    }

    @Test
    void testSubDiretoriosAssociados() {
        assertNotNull(diretorioModel.getSubDiretorios());
        assertEquals(1, diretorioModel.getSubDiretorios().size());
        assertEquals(2L, diretorioModel.getSubDiretorios().get(0).getId());
        assertEquals("Subdiretório", diretorioModel.getSubDiretorios().get(0).getNome());
    }

    @Test
    void testArquivosAssociados() {
        assertNotNull(diretorioModel.getArquivos());
        assertEquals(1, diretorioModel.getArquivos().size());
        assertEquals(10L, diretorioModel.getArquivos().get(0).getId());
        assertEquals("Arquivo no Diretório", diretorioModel.getArquivos().get(0).getNome());
    }

    @Test
    void testDiretorioSemSubDiretoriosOuArquivos() {
        DiretorioModel diretorioVazio = new DiretorioModel();
        assertNull(diretorioVazio.getSubDiretorios());
        assertNull(diretorioVazio.getArquivos());
    }
}
