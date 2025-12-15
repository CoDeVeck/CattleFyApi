package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.dto.LoteListadoDTO;
import com.Cibertec.CattleFyApi.dto.ResultadoResponse;
import com.Cibertec.CattleFyApi.models.Lote;
import com.Cibertec.CattleFyApi.models.RegistroAlimentacion;
import com.Cibertec.CattleFyApi.repository.ILoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoteService {

    private final ILoteRepository loteRepository;

    public List<LoteListadoDTO> list(){
        List<Lote> lotes = loteRepository.findAll();
        return lotes.stream().map( entidad ->{
            return LoteListadoDTO.builder()
                    .loteId(entidad.getLoteId())
                    .estado(entidad.getEstado())
                    .fechaCreacion(entidad.getFechaCreacion())
                    .nombreCategoria(entidad.getCategoria().getNombre())
                    .nombreLote(entidad.getNombre())
                    .nombreEspecie(entidad.getEspecie().getNombre())
                    .capacidadMax(entidad.getCapacidadMax())
                    .nombreGranja(entidad.getGranja().getNombre())
                    .build();
        }).collect(Collectors.toList());
    }

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
