package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.dto.RegistrarAlimentacionHistorialDTO;
import com.Cibertec.CattleFyApi.dto.ResultadoResponse;
import com.Cibertec.CattleFyApi.models.RegistroAlimentacion;
import com.Cibertec.CattleFyApi.repository.IRegistroAlimentacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistroAlimentacionService {

    private final IRegistroAlimentacionRepository registroAlimentacionRepository;

    public ResultadoResponse<RegistroAlimentacion> crearAlimentacion(RegistroAlimentacion ra){
        try{
            RegistroAlimentacion guardada = registroAlimentacionRepository.save(ra);
            return ResultadoResponse.success("Alimetación registrada correctamente", guardada);
        }
        catch (Exception ex){
            String mensajeError = "Ocurrió un error al crear el lote: " + ex.getMessage();
            return ResultadoResponse.error(mensajeError);
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
