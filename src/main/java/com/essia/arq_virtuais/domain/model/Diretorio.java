package com.essia.arq_virtuais.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Diretorio extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "diretorio_id")
    @JsonIgnore
    private Diretorio diretorioPai;

    @OneToMany(mappedBy = "diretorioPai", cascade = CascadeType.ALL)
    private List<Diretorio> subDiretorios = new ArrayList<>();

    @OneToMany(mappedBy = "diretorio", cascade = CascadeType.ALL)
    private List<Arquivo> arquivos = new ArrayList<>();




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diretorio diretorio = (Diretorio) o;

        if (id == null && diretorio.id == null) return false;
        return id != null && id.equals(diretorio.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
