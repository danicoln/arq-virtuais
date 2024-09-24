package com.essia.arq_virtuais.domain.repository;

import com.essia.arq_virtuais.domain.model.Diretorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DiretorioRepositoryTest {

    @Autowired
    private DiretorioRepository diretorioRepository;

    @Test
    @Rollback(false)
    void testExistsByNomeAndDiretorioPai() {
        Diretorio diretorioPai = new Diretorio();
        diretorioPai.setNome("Diretorio Pai");
        diretorioPai = diretorioRepository.save(diretorioPai);

        Diretorio subDiretorio = new Diretorio();
        subDiretorio.setNome("Subdiretorio");
        subDiretorio.setDiretorioPai(diretorioPai);
        diretorioRepository.save(subDiretorio);

        boolean exists = diretorioRepository.existsByNomeAndDiretorioPai("Subdiretorio", diretorioPai.getId());
        assertTrue(exists);
    }

    @Test
    @Rollback(false)
    void testDoesNotExistByNomeAndDiretorioPai() {
        Diretorio diretorioPai = new Diretorio();
        diretorioPai.setNome("Diretorio Pai Teste");
        diretorioPai = diretorioRepository.save(diretorioPai);

        boolean exists = diretorioRepository.existsByNomeAndDiretorioPai("Diretorio Inexistente", diretorioPai.getId());
        assertFalse(exists);
    }

    @Test
    @Rollback(false)
    void testDiretorioRepositorySave() {
        Diretorio diretorio = new Diretorio();
        diretorio.setNome("Novo Diretorio");
        Diretorio savedDiretorio = diretorioRepository.save(diretorio);

        assertNotNull(savedDiretorio.getId());
        assertEquals("Novo Diretorio", savedDiretorio.getNome());
    }
}
