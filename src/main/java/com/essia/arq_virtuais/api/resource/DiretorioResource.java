package com.essia.arq_virtuais.api.resource;

import com.essia.arq_virtuais.api.dto.input.DiretorioInput;
import com.essia.arq_virtuais.api.dto.model.DiretorioModel;
import com.essia.arq_virtuais.api.mapper.DiretorioMapper;
import com.essia.arq_virtuais.domain.exception.DiretorioNaoEncontradoException;
import com.essia.arq_virtuais.domain.model.Diretorio;
import com.essia.arq_virtuais.domain.service.DiretorioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/diretorios")
public class DiretorioResource {

    private final DiretorioService service;
    private final DiretorioMapper mapper;

    @GetMapping
    public ResponseEntity<List<DiretorioModel>> listarDiretorios() {
        List<Diretorio> diretorios = service.listarDiretorios();
        return ResponseEntity.ok(mapper.toModelList(diretorios));
    }

    @PostMapping
    public ResponseEntity<DiretorioModel> inserir(@RequestBody DiretorioInput input) {
        Diretorio diretorio = mapper.toEntity(input);
        DiretorioModel model = mapper.toModel(service.salvar(diretorio));
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiretorioModel> atualizar(@PathVariable Long id,
                                                    @RequestBody DiretorioInput input) {
        Diretorio diretorio = mapper.toEntity(input);
        Diretorio diretorioAtualizado = service.atualizar(id, diretorio);
        DiretorioModel model = mapper.toModel(diretorioAtualizado);
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DiretorioModel> remover(@PathVariable Long id) {
        try {
            service.remover(id);
            return ResponseEntity.noContent().build();
        } catch (DiretorioNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
