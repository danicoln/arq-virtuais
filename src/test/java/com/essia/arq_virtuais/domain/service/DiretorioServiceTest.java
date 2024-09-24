package com.essia.arq_virtuais.domain.service;

import com.essia.arq_virtuais.domain.exception.DiretorioNaoEncontradoException;
import com.essia.arq_virtuais.domain.exception.NegocioException;
import com.essia.arq_virtuais.domain.model.Arquivo;
import com.essia.arq_virtuais.domain.model.Diretorio;
import com.essia.arq_virtuais.domain.repository.DiretorioRepository;
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

class DiretorioServiceTest {

    @Mock
    private DiretorioRepository diretorioRepository;

    @InjectMocks
    private DiretorioService diretorioService;

    private Diretorio diretorio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        diretorio = new Diretorio();
        diretorio.setId(1L);
        diretorio.setNome("Diretorio Teste");
    }

    @Test
    void listarDiretorios_DeveRetornarListaDeDiretorios() {
        when(diretorioRepository.findAll()).thenReturn(List.of(diretorio));

        List<Diretorio> diretorios = diretorioService.listarDiretorios();

        assertNotNull(diretorios);
        assertFalse(diretorios.isEmpty());
        assertEquals(1, diretorios.size());
        assertEquals("Diretorio Teste", diretorios.get(0).getNome());
    }

    @Test
    void buscarPorIdOuFalhar_DeveRetornarDiretorioQuandoEncontrado() {
        when(diretorioRepository.findById(anyLong())).thenReturn(Optional.of(diretorio));

        Diretorio diretorioEncontrado = diretorioService.buscarPorIdOuFalhar(1L);

        assertNotNull(diretorioEncontrado);
        assertEquals("Diretorio Teste", diretorioEncontrado.getNome());
    }

    @Test
    void buscarPorIdOuFalhar_DeveLancarExcecaoQuandoNaoEncontrado() {
        when(diretorioRepository.findById(anyLong())).thenReturn(Optional.empty());

        DiretorioNaoEncontradoException exception = assertThrows(
                DiretorioNaoEncontradoException.class,
                () -> diretorioService.buscarPorIdOuFalhar(1L)
        );

        assertEquals("Diretorio com id 1 não encontrado", exception.getMessage());
    }

    @Test
    void salvar_DeveSalvarDiretorio() {
        when(diretorioRepository.existsByNomeAndDiretorioPai(anyString(), anyLong())).thenReturn(false);
        when(diretorioRepository.save(any(Diretorio.class))).thenReturn(diretorio);

        Diretorio diretorioSalvo = diretorioService.salvar(diretorio);

        assertNotNull(diretorioSalvo);
        assertEquals("Diretorio Teste", diretorioSalvo.getNome());
        verify(diretorioRepository, times(1)).save(diretorio);
    }

    @Test
    void salvar_DeveLancarExcecaoQuandoNomeJaExistenteNoDiretorioPai() {
        when(diretorioRepository.existsByNomeAndDiretorioPai(anyString(), anyLong())).thenReturn(true);

        Diretorio diretorioPai = new Diretorio();
        diretorioPai.setId(2L);
        diretorio.setDiretorioPai(diretorioPai);

        diretorio.setNome("Nome Existente");

        NegocioException exception = assertThrows(
                NegocioException.class,
                () -> diretorioService.salvar(diretorio)
        );

        assertEquals("Já existe um diretório com este nome no diretório atual", exception.getMessage());

        verify(diretorioRepository, never()).save(any(Diretorio.class));
    }

    @Test
    void atualizar_DeveAtualizarDiretorio() {
        when(diretorioRepository.findById(anyLong())).thenReturn(Optional.of(diretorio));
        when(diretorioRepository.save(any(Diretorio.class))).thenReturn(diretorio);

        Diretorio diretorioAtualizado = diretorioService.atualizar(1L, diretorio);

        assertNotNull(diretorioAtualizado);
        assertEquals("Diretorio Teste", diretorioAtualizado.getNome());
        verify(diretorioRepository, times(1)).save(diretorio);
    }

    @Test
    void remover_DeveDeletarDiretorio() {
        when(diretorioRepository.findById(anyLong())).thenReturn(Optional.of(diretorio));

        diretorioService.remover(1L);

        verify(diretorioRepository, times(1)).deleteById(1L);
    }

    @Test
    void remover_DeveLancarExcecaoQuandoDiretorioNaoEncontrado() {
        when(diretorioRepository.findById(anyLong())).thenReturn(Optional.empty());

        DiretorioNaoEncontradoException exception = assertThrows(
                DiretorioNaoEncontradoException.class,
                () -> diretorioService.remover(1L)
        );

        assertEquals("Diretorio com id 1 não encontrado", exception.getMessage());
        verify(diretorioRepository, never()).deleteById(anyLong());
    }
}
