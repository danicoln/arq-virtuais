package com.essia.arq_virtuais.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ArquivoTest {

    private Arquivo arquivo;

    @Mock
    private Diretorio diretorioMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        arquivo = new Arquivo();
    }

    @Test
    void testGettersAndSetters() {
        Long id = 1L;
        String nome = "arquivo.txt";

        arquivo.setId(id);
        arquivo.setNome(nome);
        arquivo.setDiretorio(diretorioMock);

        assertEquals(id, arquivo.getId());
        assertEquals(nome, arquivo.getNome());
        assertEquals(diretorioMock, arquivo.getDiretorio());
    }

    @Test
    void testEqualsAndHashCode() {
        Arquivo arquivo1 = new Arquivo();
        arquivo1.setId(1L);
        Arquivo arquivo2 = new Arquivo();
        arquivo2.setId(1L);

        assertEquals(arquivo1, arquivo2);

        assertEquals(arquivo1.hashCode(), arquivo2.hashCode());
    }

    @Test
    void testArquivoWithoutIdShouldNotBeEqual() {
        Arquivo arquivo1 = new Arquivo();
        Arquivo arquivo2 = new Arquivo();

        assertNotEquals(arquivo1, arquivo2);
    }

    @Test
    void testArquivoWithDifferentIdsShouldNotBeEqual() {
        Arquivo arquivo1 = new Arquivo();
        arquivo1.setId(1L);
        Arquivo arquivo2 = new Arquivo();
        arquivo2.setId(2L);

        assertNotEquals(arquivo1, arquivo2);
    }
}
