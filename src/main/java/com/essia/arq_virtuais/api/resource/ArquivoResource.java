package com.essia.arq_virtuais.api.resource;

import com.essia.arq_virtuais.api.dto.input.ArquivoInput;
import com.essia.arq_virtuais.api.dto.model.ArquivoModel;
import com.essia.arq_virtuais.api.mapper.ArquivoMapper;
import com.essia.arq_virtuais.domain.exception.ArquivoNaoEncontradoException;
import com.essia.arq_virtuais.domain.model.Arquivo;
import com.essia.arq_virtuais.domain.service.ArquivoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/arquivos")
public class ArquivoResource {

    private final ArquivoService service;
    private final ArquivoMapper mapper;

    @GetMapping
    public ResponseEntity<List<ArquivoModel>> listarArquivos() {
        List<Arquivo> arquivos = service.listarArquivos();
        return ResponseEntity.ok(mapper.toModelList(arquivos));
    }

    @PostMapping
    public ResponseEntity<ArquivoModel> inserir(@PathVariable ArquivoInput input) {
        Arquivo arquivo = mapper.toEntity(input);
        ArquivoModel model = mapper.toModel(service.salvar(arquivo));
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArquivoModel> atualizar(@PathVariable Long id,
                                                    @RequestBody ArquivoInput input) {
        Arquivo arquivo = mapper.toEntity(input);
        Arquivo arquivoAtualizado = service.atualizar(id, arquivo);
        ArquivoModel model = mapper.toModel(arquivoAtualizado);
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ArquivoModel> remover(@PathVariable Long id) {
        try {
            service.remover(id);
            return ResponseEntity.noContent().build();
        } catch (ArquivoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}