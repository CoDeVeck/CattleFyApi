package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.dto.RegistrarAlimentacionHistorialDTO;
import com.Cibertec.CattleFyApi.dto.RegistroAlimentacionRequestDTO;
import com.Cibertec.CattleFyApi.dto.RegistroAlimentacionResponseDTO;
import com.Cibertec.CattleFyApi.dto.ResultadoResponse;
import com.Cibertec.CattleFyApi.models.Lote;
import com.Cibertec.CattleFyApi.models.RegistroAlimentacion;
import com.Cibertec.CattleFyApi.repository.ILoteRepository;
import com.Cibertec.CattleFyApi.repository.IRegistroAlimentacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistroAlimentacionService {

    private final IRegistroAlimentacionRepository registroAlimentacionRepository;
    private final ILoteRepository loteRepository;

    public ResultadoResponse<RegistroAlimentacionResponseDTO> crearAlimentacion(RegistroAlimentacionRequestDTO dtoRequest) {
        try {
            RegistroAlimentacion nuevaAlimentacion = new RegistroAlimentacion();
            nuevaAlimentacion.setCantidadKg(dtoRequest.getCantidadKg());
            nuevaAlimentacion.setCostoPorKg(dtoRequest.getCostoPorKg());
            nuevaAlimentacion.setDietaTipo(dtoRequest.getDietaTipo());


            Lote lote = loteRepository.findById(dtoRequest.getLoteId())
                    .orElseThrow(() -> new Exception("Lote no encontrado"));
            nuevaAlimentacion.setLote(lote);
            nuevaAlimentacion.setFechaRegistro(LocalDateTime.now());
            RegistroAlimentacion guardada = registroAlimentacionRepository.save(nuevaAlimentacion);
            BigDecimal total = guardada.getCantidadKg().multiply(guardada.getCostoPorKg());
            String costoTotalFormateado = String.format("S/ %.2f", total);
            RegistroAlimentacionResponseDTO response = RegistroAlimentacionResponseDTO.builder()
                    .alimentacionId(guardada.getAlimentacionId())
                    .loteId(guardada.getLote().getLoteId())
                    .fechaRegistro(guardada.getFechaRegistro())
                    .cantidadKg(guardada.getCantidadKg())
                    .costoPorKg(guardada.getCostoPorKg())
                    .dietaTipo(guardada.getDietaTipo())
                    .costoTotal(costoTotalFormateado)
                    .build();

            return ResultadoResponse.success("Alimentación registrada correctamente", response);

        } catch (Exception ex) {
            return ResultadoResponse.error("Error: " + ex.getMessage());
        }
    }


    public List<RegistrarAlimentacionHistorialDTO> listarHistorialAlimentacion() {

        List<RegistroAlimentacion> registros = registroAlimentacionRepository.findAll();

        return registros.stream()
                .map(entidad -> {

                    BigDecimal costoTotal = entidad.getCantidadKg()
                            .multiply(entidad.getCostoPorKg())
                            .setScale(2, RoundingMode.HALF_UP);

                    return RegistrarAlimentacionHistorialDTO.builder()
                            .alimentacionId(entidad.getAlimentacionId())
                            .fecha(entidad.getFechaRegistro())
                            .dieta(entidad.getDietaTipo())
                            .cantidadKg(entidad.getCantidadKg())
                            .costoTotal(costoTotal)
                            .loteId(entidad.getLote().getLoteId())
                            .nombreLote(entidad.getLote().getNombre())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
