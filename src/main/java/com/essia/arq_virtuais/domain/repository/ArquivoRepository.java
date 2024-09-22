package com.essia.arq_virtuais.domain.repository;

import com.essia.arq_virtuais.domain.model.Arquivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

    @Query("SELECT COUNT(a) > 0 FROM Arquivo a WHERE a.nome = :nome AND a.diretorio.id = :diretorioId")
    boolean existsByNomeAndDiretorio(@Param("nome") String nome, @Param("diretorioId") Long diretorioId);
}
