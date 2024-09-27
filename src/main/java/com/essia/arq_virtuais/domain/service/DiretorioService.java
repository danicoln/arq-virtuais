package com.essia.arq_virtuais.domain.service;

import com.essia.arq_virtuais.domain.exception.DiretorioNaoEncontradoException;
import com.essia.arq_virtuais.domain.exception.EntidadeNaoEncontradaException;
import com.essia.arq_virtuais.domain.exception.NegocioException;
import com.essia.arq_virtuais.domain.model.Diretorio;
import com.essia.arq_virtuais.domain.repository.DiretorioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DiretorioService {

    public static final String DIRETORIO_NAO_ENCONTRADO = "Diretorio com id %d não encontrado";
    public static final String DIRETORIO_COM_MESMO_NOME_EXISTENTE = "Já existe um diretório com este nome no diretório atual";
    public static final String O_DIRETORIO_NAO_PODE_PENTERCER_A_ELE_MESMO = "O diretório não pode pode pertencer a ele mesmo";
    public static final String DIRETORIO_PERTENCENTE_AO_ATUAL = "O diretório %s pertence ao diretório atual";

    private final DiretorioRepository repository;

    public List<Diretorio> listarDiretorios() {
        return repository.findAll();
    }

    public Diretorio buscarPorIdOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DiretorioNaoEncontradoException(String.format(DIRETORIO_NAO_ENCONTRADO, id)));
    }

    @Transactional
    public Diretorio inserir(Diretorio diretorio) {
        if(diretorio.getDiretorioPai() != null) {
            verificarNomeDuplicadoNoDiretorioPai(diretorio.getDiretorioPai().getId(), diretorio);
        }
        associarDiretorioPai(diretorio);
        associarSubDiretorios(diretorio);
        return repository.save(diretorio);
    }

    @Transactional
    public Diretorio atualizar(Long id, Diretorio diretorio) {
        Diretorio diretorioAtual = buscarPorIdOuFalhar(id);
        validarDiretorio(id, diretorio);
        verificaDiretorioComMesmoNome(diretorio, diretorioAtual);
        verificarSeDiretorioAtualEhPaiDoDiretorioRemetente(diretorio, diretorioAtual);
        BeanUtils.copyProperties(
                diretorio, diretorioAtual,
                "id", "subDiretorios", "arquivos"
        );
        return repository.save(diretorioAtual);
    }

    @Transactional
    public void remover(Long id) {
        Diretorio diretorio = repository.findById(id)
                .orElseThrow(() -> new DiretorioNaoEncontradoException(String.format(DIRETORIO_NAO_ENCONTRADO, id)));
        repository.delete(diretorio);
    }

    private void verificarSeDiretorioAtualEhPaiDoDiretorioRemetente(Diretorio diretorio, Diretorio diretorioAtual) {
        Diretorio diretorioPai = buscarPorIdOuFalhar(diretorio.getId());

        if (ehDiretorioAtualPai(diretorioPai, diretorioAtual)) {
            throw new NegocioException(String.format(
                    DIRETORIO_PERTENCENTE_AO_ATUAL, diretorioPai.getNome()));
        }
    }

    private boolean ehDiretorioAtualPai(Diretorio diretorioPai, Diretorio diretorioAtual) {
        return Optional.ofNullable(diretorioPai.getDiretorioPai())
                .map(Diretorio::getId)
                .filter(id -> id.equals(diretorioAtual.getId()))
                .isPresent();
    }

    private static void verificaDiretorioComMesmoNome(Diretorio diretorio, Diretorio diretorioAtual) {
        for (Diretorio pasta : diretorioAtual.getSubDiretorios()) {
            if (pasta.equals(diretorio)) {
                throw new NegocioException(String.format(DIRETORIO_COM_MESMO_NOME_EXISTENTE));
            }
        }
    }

    private void validarDiretorio(Long id, Diretorio diretorio) {
        verificarSeDiretorioEhSeuProprioPai(id, diretorio);
        verificarNomeDuplicadoNoDiretorioPai(id, diretorio);
    }

    private void verificarSeDiretorioEhSeuProprioPai(Long id, Diretorio diretorio) {
        Diretorio diretorioAtual = buscarPorIdOuFalhar(id);
        if (diretorio.getDiretorioPai() != null &&
                diretorioAtual.getId().equals(diretorio.getDiretorioPai().getId())) {
            throw new NegocioException(O_DIRETORIO_NAO_PODE_PENTERCER_A_ELE_MESMO);
        }
    }

    private void verificarNomeDuplicadoNoDiretorioPai(Long idAtual, Diretorio diretorio) {
        if (diretorio.getDiretorioPai() != null) {
            boolean nomeDuplicado = repository.existsByNomeAndDiretorioPai(
                    diretorio.getNome(),
                    diretorio.getDiretorioPai().getId()
            );

            if (nomeDuplicado) {
                throw new NegocioException(DIRETORIO_COM_MESMO_NOME_EXISTENTE);
            }
        }
    }

    private void associarDiretorioPai(Diretorio diretorio) {
        if (diretorio.getDiretorioPai() != null) {
            Diretorio diretorioPai = repository.findById(diretorio.getDiretorioPai().getId())
                    .orElseThrow(() -> new DiretorioNaoEncontradoException(String.format(
                            DIRETORIO_NAO_ENCONTRADO, diretorio.getDiretorioPai().getId())));
            diretorio.setDiretorioPai(diretorioPai);
        }
    }

    private void associarSubDiretorios(Diretorio diretorio) {
        if (diretorio.getSubDiretorios() != null && !diretorio.getSubDiretorios().isEmpty()) {
            for (Diretorio subDiretorio : diretorio.getSubDiretorios()) {
                if (subDiretorio.getDiretorioPai() == null) {
                    subDiretorio.setDiretorioPai(diretorio);
                }
            }
        }
    }

}
