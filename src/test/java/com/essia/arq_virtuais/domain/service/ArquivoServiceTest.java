package com.essia.arq_virtuais.domain.service;

import com.essia.arq_virtuais.domain.exception.ArquivoNaoEncontradoException;
import com.essia.arq_virtuais.domain.exception.NegocioException;
import com.essia.arq_virtuais.domain.model.Arquivo;
import com.essia.arq_virtuais.domain.model.Diretorio;
import com.essia.arq_virtuais.domain.repository.ArquivoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ArquivoServiceTest {

    @Mock
    private ArquivoRepository arquivoRepository;

    @InjectMocks
    private ArquivoService arquivoService;

    private Arquivo arquivo;
    private Diretorio diretorio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        diretorio = new Diretorio();
        diretorio.setId(1L);
        diretorio.setNome("Diretorio Teste");

        arquivo = new Arquivo();
        arquivo.setId(1L);
        arquivo.setNome("Arquivo Teste");
        arquivo.setDiretorio(diretorio);
    }

    @Test
    void listarArquivos_DeveRetornarListaDeArquivos() {
        when(arquivoRepository.findAll()).thenReturn(List.of(arquivo));

        List<Arquivo> arquivos = arquivoService.listarArquivos();

        assertNotNull(arquivos);
        assertFalse(arquivos.isEmpty());
        assertEquals(1, arquivos.size());
        assertEquals("Arquivo Teste", arquivos.get(0).getNome());
    }

    @Test
    void buscarPorIdOuFalhar_DeveRetornarArquivoQuandoEncontrado() {
        when(arquivoRepository.findById(anyLong())).thenReturn(Optional.of(arquivo));

        Arquivo arquivoEncontrado = arquivoService.buscarPorIdOuFalhar(1L);

        assertNotNull(arquivoEncontrado);
        assertEquals("Arquivo Teste", arquivoEncontrado.getNome());
    }

    @Test
    void buscarPorIdOuFalhar_DeveLancarExcecaoQuandoNaoEncontrado() {
        when(arquivoRepository.findById(anyLong())).thenReturn(Optional.empty());

        ArquivoNaoEncontradoException exception = assertThrows(
                ArquivoNaoEncontradoException.class,
                () -> arquivoService.buscarPorIdOuFalhar(1L)
        );

        assertEquals("Arquivo com id 1 não encontrado", exception.getMessage());
    }

    @Test
    void salvar_DeveSalvarArquivo() {
        when(arquivoRepository.existsByNomeAndDiretorio(anyString(), anyLong())).thenReturn(false);
        when(arquivoRepository.save(any(Arquivo.class))).thenReturn(arquivo);

        Arquivo arquivoSalvo = arquivoService.salvar(arquivo);

        assertNotNull(arquivoSalvo);
        assertEquals("Arquivo Teste", arquivoSalvo.getNome());
        verify(arquivoRepository, times(1)).save(arquivo);
    }

    @Test
    void salvar_DeveLancarExcecaoQuandoNomeJaExistenteNoDiretorio() {
        when(arquivoRepository.existsByNomeAndDiretorio(anyString(), anyLong())).thenReturn(true);

        NegocioException exception = assertThrows(
                NegocioException.class,
                () -> arquivoService.salvar(arquivo)
        );

        assertEquals("Já existe um arquivo com este nome no diretório", exception.getMessage());
        verify(arquivoRepository, never()).save(any(Arquivo.class));
    }

    @Test
    void atualizar_DeveAtualizarArquivo() {
        when(arquivoRepository.findById(anyLong())).thenReturn(Optional.of(arquivo));
        when(arquivoRepository.save(any(Arquivo.class))).thenReturn(arquivo);

        Arquivo arquivoAtualizado = arquivoService.atualizar(1L, arquivo);

        assertNotNull(arquivoAtualizado);
        assertEquals("Arquivo Teste", arquivoAtualizado.getNome());
        verify(arquivoRepository, times(1)).save(arquivo);
    }

    @Test
    void remover_DeveDeletarArquivo() {
        when(arquivoRepository.findById(anyLong())).thenReturn(Optional.of(arquivo));

        arquivoService.remover(1L);

        verify(arquivoRepository, times(1)).deleteById(1L);
    }

    @Test
    void remover_DeveLancarExcecaoQuandoArquivoNaoEncontrado() {
        when(arquivoRepository.findById(anyLong())).thenReturn(Optional.empty());

        ArquivoNaoEncontradoException exception = assertThrows(
                ArquivoNaoEncontradoException.class,
                () -> arquivoService.remover(1L)
        );

        assertEquals("Arquivo com id 1 não encontrado", exception.getMessage());
        verify(arquivoRepository, never()).deleteById(anyLong());
    }
}
