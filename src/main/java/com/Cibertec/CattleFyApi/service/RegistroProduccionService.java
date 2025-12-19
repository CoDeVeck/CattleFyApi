package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.dto.*;
import com.Cibertec.CattleFyApi.models.Lote;
import com.Cibertec.CattleFyApi.models.RegistroProduccion;
import com.Cibertec.CattleFyApi.repository.ILoteRepository;
import com.Cibertec.CattleFyApi.repository.IRegistroProduccionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistroProduccionService {

    private final IRegistroProduccionRepository registroProduccionRepository;
    private final ILoteRepository loteRepository;

    public List<RegistroProduccion> getAll(){
        return registroProduccionRepository.findAll();
    }

    @Transactional
    public ResultadoResponse<RegistroProduccionResponse> registrarProduccion(RegistroProduccionRequest req) {
        try {

            RegistroProduccion produccion = new RegistroProduccion();

            Lote lote = loteRepository.findById(req.getLoteId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Lote no encontrado con ID: " + req.getLoteId()
                    ));

            produccion.setLote(lote);
            produccion.setTipoProduccion(req.getTipoProduccion());
            produccion.setCantidad(req.getCantidad());
            produccion.setFechaRegistro(LocalDateTime.now());

            RegistroProduccion produccionGuardada = registroProduccionRepository.save(produccion);
            RegistroProduccionResponse response = convertToDto(produccionGuardada);

            return ResultadoResponse.success("Registro de producción creado correctamente.", response);

        } catch (EntityNotFoundException e) {
            return ResultadoResponse.error("Error al registrar producción: " + e.getMessage());
        } catch (Exception e) {
            return ResultadoResponse.error("Ocurrió un error inesperado al registrar la producción.");
        }
    }
    private RegistroProduccionResponse convertToDto(RegistroProduccion p) {
        RegistroProduccionResponse dto = new RegistroProduccionResponse();

        dto.setProduccionId(p.getProduccionId());
        dto.setLoteId(p.getLote().getLoteId());
        dto.setFechaRegistro(p.getFechaRegistro());
        dto.setTipoProduccion(p.getTipoProduccion());
        dto.setCantidad(p.getCantidad());

        return dto;
    }


    //Reportes

    public List<ReporteProduccionEngordeDTO> ListaReporteProduccion(
            Integer granja_id, Integer lote_id, Integer categoria_id, String fecha_inicio, String fecha_fin){
        return registroProduccionRepository.listaDeProduccion(granja_id, lote_id, categoria_id, fecha_inicio, fecha_fin);
    }

    public List<ReporteGrafico1> GraficoReporte(
            Integer granja_id, Integer lote_id, Integer categoria_id, String fecha_inicio, String fecha_fin
    ){
        return registroProduccionRepository.graficoUnoReporteProduccio(granja_id, lote_id, categoria_id, fecha_inicio, fecha_fin);
    }

    public List<ReporteTotalAnimalesLecheDto>ListaAnimalLeche(Integer granjaId){
        return registroProduccionRepository.totalAnimalesLeches(granjaId);
    }

}
