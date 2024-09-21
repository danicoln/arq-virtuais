package com.essia.arq_virtuais.api.mapper;

import com.essia.arq_virtuais.api.dto.input.DiretorioInput;
import com.essia.arq_virtuais.api.dto.model.DiretorioModel;
import com.essia.arq_virtuais.domain.model.Diretorio;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DiretorioMapper {

    private final ModelMapper mapper;

    public Diretorio toEntity(DiretorioInput input) {
        return mapper.map(input, Diretorio.class);
    }

    public DiretorioModel toModel(Diretorio obj) {
        return mapper.map(obj, DiretorioModel.class);
    }

    public List<DiretorioModel> toModelList(List<Diretorio> objetos) {
        return objetos.stream()
                .map(this::toModel)
                .toList();
    }
}
