package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.dto.ResultadoResponse;
import com.Cibertec.CattleFyApi.models.Lote;
import com.Cibertec.CattleFyApi.repository.ILoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoteService {

    private final ILoteRepository loteRepository;

    public ResultadoResponse<Lote> crearLote(Lote lote) {

        try {
            lote.setEstado("Activo");
            lote.setFechaCreacion(LocalDateTime.now());
            Lote loteGuardado = loteRepository.save(lote);
            return ResultadoResponse.success("El lote fue creado exitosamente.", loteGuardado);

        } catch (Exception e) {
            String mensajeError = "Ocurrió un error al crear el lote: " + e.getMessage();
            return ResultadoResponse.error(mensajeError);
        }
    }
}
