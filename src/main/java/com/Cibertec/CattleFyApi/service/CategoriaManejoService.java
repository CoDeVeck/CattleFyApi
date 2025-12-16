package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.dto.CategoriaManejoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cibertec.CattleFyApi.repository.ICategoriaManejoRepository;

import java.util.List;

@Service
public class CategoriaManejoService {

    @Autowired
    ICategoriaManejoRepository categoriaManejoRepository;

    public List<CategoriaManejoDTO> listarPorEspecie(Integer especieId) {
        return categoriaManejoRepository.findByEspecie_EspecieId(especieId)
                .stream()
                .map(cat -> CategoriaManejoDTO.builder()
                        .categoriaId(cat.getCategoriaId())
                        .nombre(cat.getNombre())
                        .tipoLote(cat.getTipoLote())
                        .build())
                .toList();
    }

}
