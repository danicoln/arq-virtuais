package com.essia.arq_virtuais.api.resource;

import com.essia.arq_virtuais.api.dto.input.DiretorioInput;
import com.essia.arq_virtuais.api.dto.model.DiretorioModel;
import com.essia.arq_virtuais.api.mapper.DiretorioMapper;
import com.essia.arq_virtuais.domain.exception.DiretorioNaoEncontradoException;
import com.essia.arq_virtuais.domain.model.Diretorio;
import com.essia.arq_virtuais.domain.service.DiretorioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DiretorioResourceTest {

    private MockMvc mockMvc;

    @Mock
    private DiretorioService diretorioService;

    @Mock
    private DiretorioMapper diretorioMapper;

    @InjectMocks
    private DiretorioResource diretorioResource;

    @BeforeEach
    void setUp() {
        openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(diretorioResource).build();
    }

    @Test
    void listarDiretorios_DeveRetornarListaVazia() throws Exception {
        when(diretorioService.listarDiretorios()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/diretorios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)));

        verify(diretorioService, times(1)).listarDiretorios();
    }

    @Test
    void inserir_DeveRetornarStatusCreated() throws Exception {
        Diretorio diretorio = new Diretorio();
        DiretorioModel model = new DiretorioModel();

        when(diretorioMapper.toEntity(any(DiretorioInput.class))).thenReturn(diretorio);
        when(diretorioMapper.toModel(any(Diretorio.class))).thenReturn(model);
        when(diretorioService.salvar(any(Diretorio.class))).thenReturn(diretorio);

        mockMvc.perform(post("/api/diretorios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Diretório Teste\"}"))
                .andExpect(status().isCreated());

        verify(diretorioService, times(1)).salvar(any(Diretorio.class));
    }

    @Test
    void atualizar_DeveRetornarStatusOk() throws Exception {
        Diretorio diretorio = new Diretorio();
        DiretorioModel model = new DiretorioModel();

        when(diretorioMapper.toEntity(any(DiretorioInput.class))).thenReturn(diretorio);
        when(diretorioMapper.toModel(any(Diretorio.class))).thenReturn(model);
        when(diretorioService.atualizar(anyLong(), any(Diretorio.class))).thenReturn(diretorio);

        mockMvc.perform(put("/api/diretorios/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Diretório Atualizado\"}"))
                .andExpect(status().isOk());

        verify(diretorioService, times(1)).atualizar(anyLong(), any(Diretorio.class));
    }

    @Test
    void remover_DeveRetornarStatusNoContent() throws Exception {
        doNothing().when(diretorioService).remover(anyLong());

        mockMvc.perform(delete("/api/diretorios/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(diretorioService, times(1)).remover(anyLong());
    }

    @Test
    void remover_DeveRetornarStatusNotFound_QuandoDiretorioNaoExistir() throws Exception {
        doThrow(new DiretorioNaoEncontradoException("Diretório não encontrado", 1L))
                .when(diretorioService).remover(anyLong());

        mockMvc.perform(delete("/api/diretorios/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(diretorioService, times(1)).remover(anyLong());
    }
}
