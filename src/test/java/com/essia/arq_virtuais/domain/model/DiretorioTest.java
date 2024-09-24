package com.essia.arq_virtuais.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiretorioTest {

    private Diretorio diretorio;
    private Diretorio subDiretorio;
    private Arquivo arquivo;

    @BeforeEach
    void setUp() {
        diretorio = new Diretorio();
        diretorio.setId(1L);
        diretorio.setNome("Diretorio Principal");

        subDiretorio = new Diretorio();
        subDiretorio.setId(2L);
        subDiretorio.setNome("Subdiretorio");
        subDiretorio.setDiretorioPai(diretorio);

        arquivo = new Arquivo();
        arquivo.setId(1L);
        arquivo.setNome("Arquivo1");
        arquivo.setDiretorio(diretorio);
    }

    @Test
    void testDiretorioEqualityWithSameId() {
        Diretorio diretorio2 = new Diretorio();
        diretorio2.setId(1L);
        diretorio2.setNome("Outro Diretorio");

        assertEquals(diretorio, diretorio2);
    }

    @Test
    void testDiretorioEqualityWithDifferentId() {
        Diretorio diretorio2 = new Diretorio();
        diretorio2.setId(3L);
        diretorio2.setNome("Diretorio Diferente");

        assertNotEquals(diretorio, diretorio2);
    }

    @Test
    void testSubDiretoriosInitialization() {
        assertNotNull(diretorio.getSubDiretorios());
        assertTrue(diretorio.getSubDiretorios().isEmpty());
    }

    @Test
    void testAddSubDiretorio() {
        diretorio.getSubDiretorios().add(subDiretorio);

        assertEquals(1, diretorio.getSubDiretorios().size());
        assertEquals(diretorio, subDiretorio.getDiretorioPai());
    }

    @Test
    void testAddArquivo() {
        diretorio.getArquivos().add(arquivo);

        assertEquals(1, diretorio.getArquivos().size());
        assertEquals(diretorio, arquivo.getDiretorio());
    }

    @Test
    void testRemoveSubDiretorio() {
        diretorio.getSubDiretorios().add(subDiretorio);
        assertEquals(1, diretorio.getSubDiretorios().size());

        diretorio.getSubDiretorios().remove(subDiretorio);
        assertTrue(diretorio.getSubDiretorios().isEmpty());
    }

    @Test
    void testRemoveArquivo() {
        diretorio.getArquivos().add(arquivo);
        assertEquals(1, diretorio.getArquivos().size());

        diretorio.getArquivos().remove(arquivo);
        assertTrue(diretorio.getArquivos().isEmpty());
    }

    @Test
    void testSetNome() {
        diretorio.setNome("Novo Diretorio");
        assertEquals("Novo Diretorio", diretorio.getNome());
    }

    @Test
    void testDiretorioWithoutIdShouldNotBeEqual() {
        Diretorio diretorio1 = new Diretorio();
        Diretorio diretorio2 = new Diretorio();

        assertNotEquals(diretorio1, diretorio2);
    }

    @Test
    void testDiretorioComSubDiretorios() {
        diretorio.getSubDiretorios().add(subDiretorio);
        assertTrue(diretorio.getSubDiretorios().contains(subDiretorio));
        assertEquals(diretorio, subDiretorio.getDiretorioPai());
    }
}
