package com.essia.arq_virtuais.api.resource;

import com.essia.arq_virtuais.api.dto.input.ArquivoInput;
import com.essia.arq_virtuais.api.dto.model.ArquivoModel;
import com.essia.arq_virtuais.api.mapper.ArquivoMapper;
import com.essia.arq_virtuais.domain.exception.ArquivoNaoEncontradoException;
import com.essia.arq_virtuais.domain.model.Arquivo;
import com.essia.arq_virtuais.domain.service.ArquivoService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArquivoResourceTest {

    private MockMvc mockMvc;

    @Mock
    private ArquivoService arquivoService;

    @Mock
    private ArquivoMapper arquivoMapper;

    @InjectMocks
    private ArquivoResource arquivoResource;

    @BeforeEach
    void setUp() {
        openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(arquivoResource).build();
    }

    @Test
    void listarArquivos_DeveRetornarListaVazia() throws Exception {
        when(arquivoService.listarArquivos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/arquivos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)));

        verify(arquivoService, times(1)).listarArquivos();
    }

    @Test
    void inserir_DeveRetornarStatusCreated() throws Exception {
        Arquivo arquivo = new Arquivo();
        ArquivoModel model = new ArquivoModel();

        when(arquivoMapper.toEntity(any(ArquivoInput.class))).thenReturn(arquivo);
        when(arquivoMapper.toModel(any(Arquivo.class))).thenReturn(model);
        when(arquivoService.salvar(any(Arquivo.class))).thenReturn(arquivo);

        mockMvc.perform(post("/api/arquivos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Arquivo Teste\"}"))
                .andExpect(status().isCreated());

        verify(arquivoService, times(1)).salvar(any(Arquivo.class));
    }

    @Test
    void atualizar_DeveRetornarStatusOk() throws Exception {
        Arquivo arquivo = new Arquivo();
        ArquivoModel model = new ArquivoModel();

        when(arquivoMapper.toEntity(any(ArquivoInput.class))).thenReturn(arquivo);
        when(arquivoMapper.toModel(any(Arquivo.class))).thenReturn(model);
        when(arquivoService.atualizar(anyLong(), any(Arquivo.class))).thenReturn(arquivo);

        mockMvc.perform(put("/api/arquivos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Arquivo Atualizado\"}"))
                .andExpect(status().isOk());

        verify(arquivoService, times(1)).atualizar(anyLong(), any(Arquivo.class));
    }

    @Test
    void remover_DeveRetornarStatusNoContent() throws Exception {
        doNothing().when(arquivoService).remover(anyLong());

        mockMvc.perform(delete("/api/arquivos/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(arquivoService, times(1)).remover(anyLong());
    }

    @Test
    void remover_DeveRetornarStatusNotFound_QuandoArquivoNaoExistir() throws Exception {
        doThrow(new ArquivoNaoEncontradoException("Arquivo não encontrado", 1L))
                .when(arquivoService).remover(anyLong());

        mockMvc.perform(delete("/api/arquivos/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(arquivoService, times(1)).remover(anyLong());
    }
}
