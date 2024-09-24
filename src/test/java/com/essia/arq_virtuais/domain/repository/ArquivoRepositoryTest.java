package com.essia.arq_virtuais.domain.repository;

import com.essia.arq_virtuais.domain.model.Arquivo;
import com.essia.arq_virtuais.domain.model.Diretorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArquivoRepositoryTest {

    @Autowired
    private ArquivoRepository arquivoRepository;

    @Autowired
    private DiretorioRepository diretorioRepository;

    @Test
    @Rollback(false)
    void testExistsByNomeAndDiretorio() {
        Diretorio diretorio = new Diretorio();
        diretorio.setNome("Diretorio Teste");
        diretorio = diretorioRepository.save(diretorio);

        Arquivo arquivo = new Arquivo();
        arquivo.setNome("Arquivo Teste");
        arquivo.setDiretorio(diretorio);
        arquivoRepository.save(arquivo);

        boolean exists = arquivoRepository.existsByNomeAndDiretorio("Arquivo Teste", diretorio.getId());
        assertTrue(exists);
    }

    @Test
    @Rollback(false)
    void testDoesNotExistByNomeAndDiretorio() {
        Diretorio diretorio = new Diretorio();
        diretorio.setNome("Diretorio Teste 2");
        diretorio = diretorioRepository.save(diretorio);

        boolean exists = arquivoRepository.existsByNomeAndDiretorio("Arquivo Inexistente", diretorio.getId());
        assertFalse(exists);
    }

    @Test
    @Rollback(false)
    void testArquivoRepositorySave() {
        Diretorio diretorio = new Diretorio();
        diretorio.setNome("Diretorio Teste");
        diretorio = diretorioRepository.save(diretorio);

        Arquivo arquivo = new Arquivo();
        arquivo.setNome("Novo Arquivo");
        arquivo.setDiretorio(diretorio);
        Arquivo savedArquivo = arquivoRepository.save(arquivo);

        assertNotNull(savedArquivo.getId());
        assertEquals("Novo Arquivo", savedArquivo.getNome());
    }
}
