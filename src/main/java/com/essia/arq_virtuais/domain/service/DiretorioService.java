package com.essia.arq_virtuais.domain.service;

import com.essia.arq_virtuais.domain.exception.DiretorioNaoEncontradoException;
import com.essia.arq_virtuais.domain.model.Diretorio;
import com.essia.arq_virtuais.domain.repository.DiretorioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class DiretorioService {

    public static final String DIRETORIO_NAO_ENCONTRADO = "Diretorio com id %d não encontrado";

    private final DiretorioRepository repository;

    public List<Diretorio> listarDiretorios() {
        return repository.findAll();
    }

    public Diretorio buscarPorIdOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DiretorioNaoEncontradoException(DIRETORIO_NAO_ENCONTRADO, id));
    }

    @Transactional
    public Diretorio salvar(Diretorio diretorio) {
        return repository.save(diretorio);
    }

    @Transactional
    public Diretorio atualizar(Long id, Diretorio diretorio) {
        Diretorio diretorioExistente = buscarPorIdOuFalhar(id);
        BeanUtils.copyProperties(diretorio, diretorioExistente, "id");
        return repository.save(diretorioExistente);
    }

    @Transactional
    public void remover(Long id) {
        Diretorio diretorio = buscarPorIdOuFalhar(id);
        if (diretorio == null) {
            throw new DiretorioNaoEncontradoException(DIRETORIO_NAO_ENCONTRADO, id);
        }
        repository.deleteById(id);
    }

}
