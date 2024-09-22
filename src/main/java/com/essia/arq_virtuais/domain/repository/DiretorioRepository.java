package com.essia.arq_virtuais.domain.repository;

import com.essia.arq_virtuais.domain.model.Diretorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiretorioRepository extends JpaRepository<Diretorio, Long> {

    @Query("SELECT COUNT(d) > 0 FROM Diretorio d WHERE d.nome = :nome AND d.diretorioPai.id = :diretorioPaiId")
    boolean existsByNomeAndDiretorioPai(@Param("nome") String nome, @Param("diretorioPaiId") Long diretorioPaiId);

}
