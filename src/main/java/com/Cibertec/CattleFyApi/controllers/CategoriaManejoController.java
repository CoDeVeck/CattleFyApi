package com.Cibertec.CattleFyApi.controllers;

import com.Cibertec.CattleFyApi.dto.CategoriaManejoDTO;
import com.Cibertec.CattleFyApi.dto.ResultadoResponse;
import com.Cibertec.CattleFyApi.service.CategoriaManejoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/categoriamanejo")
@RequiredArgsConstructor
public class CategoriaManejoController {
    private final CategoriaManejoService categoriaManejoService;

    @GetMapping("/por-especie/{especieId}")
    public ResponseEntity<ResultadoResponse<List<CategoriaManejoDTO>>> listarPorEspecie(
            @PathVariable Integer especieId) {

        List<CategoriaManejoDTO> lista = categoriaManejoService.listarPorEspecie(especieId);

        return ResponseEntity.ok(
                ResultadoResponse.success("Categorías encontradas", lista)
        );
    }

}
