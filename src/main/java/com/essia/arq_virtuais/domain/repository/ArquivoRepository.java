package com.essia.arq_virtuais.domain.repository;

import com.essia.arq_virtuais.domain.model.Arquivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {
}
