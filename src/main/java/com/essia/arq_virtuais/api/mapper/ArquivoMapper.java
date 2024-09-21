package com.essia.arq_virtuais.api.mapper;

import com.essia.arq_virtuais.api.dto.input.ArquivoInput;
import com.essia.arq_virtuais.api.dto.model.ArquivoModel;
import com.essia.arq_virtuais.domain.model.Arquivo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ArquivoMapper {

    private final ModelMapper mapper;

    public Arquivo toEntity(ArquivoInput input) {
        return mapper.map(input, Arquivo.class);
    }

    public ArquivoModel toModel(Arquivo obj) {
        return mapper.map(obj, ArquivoModel.class);
    }

    public List<ArquivoModel> toModelList(List<Arquivo> objetos) {
        return objetos.stream()
                .map(this::toModel)
                .toList();
    }
}
