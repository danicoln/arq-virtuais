package com.essia.arq_virtuais.domain.service;

import com.essia.arq_virtuais.domain.exception.ArquivoNaoEncontradoException;
import com.essia.arq_virtuais.domain.exception.NegocioException;
import com.essia.arq_virtuais.domain.model.Arquivo;
import com.essia.arq_virtuais.domain.repository.ArquivoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class ArquivoService {

    public static final String ARQUIVO_NAO_ENCONTRADO = "Arquivo com id %d não encontrado";
    public static final String NOME_JA_EXISTENTE_NO_DIRETORIO = "Já existe um arquivo com este nome no diretório";

    private final ArquivoRepository repository;

    public List<Arquivo> listarArquivos() {
        return repository.findAll();
    }

    public Arquivo buscarPorIdOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ArquivoNaoEncontradoException(ARQUIVO_NAO_ENCONTRADO, id));
    }

    @Transactional
    public Arquivo salvar(Arquivo arquivo) {
        if(repository.existsByNomeAndDiretorio(arquivo.getNome(), arquivo.getDiretorio().getId())) {
            throw new NegocioException(NOME_JA_EXISTENTE_NO_DIRETORIO);
        }
        return repository.save(arquivo);
    }

    @Transactional
    public Arquivo atualizar(Long id, Arquivo arquivo) {
        Arquivo arquivoExistente = buscarPorIdOuFalhar(id);
        BeanUtils.copyProperties(arquivo, arquivoExistente, "id");
        return repository.save(arquivoExistente);
    }

    @Transactional
    public void remover(Long id) {
        Arquivo arquivo = buscarPorIdOuFalhar(id);
        if (arquivo == null) {
            throw new ArquivoNaoEncontradoException(ARQUIVO_NAO_ENCONTRADO, id);
        }
        repository.deleteById(id);
    }
}
