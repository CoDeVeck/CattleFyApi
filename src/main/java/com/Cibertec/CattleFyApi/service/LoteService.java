package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.dto.LoteListadoDTO;
import com.Cibertec.CattleFyApi.dto.ResultadoResponse;
import com.Cibertec.CattleFyApi.models.Lote;
import com.Cibertec.CattleFyApi.dto.LoteRequest;
import com.Cibertec.CattleFyApi.dto.LoteResponse;
import com.Cibertec.CattleFyApi.repository.IAnimalRepository;
import com.Cibertec.CattleFyApi.repository.ICategoriaManejoRepository;
import com.Cibertec.CattleFyApi.repository.IEspecieRepository;
import com.Cibertec.CattleFyApi.repository.IGranjaRepository;
import com.Cibertec.CattleFyApi.repository.ILoteRepository;
import com.Cibertec.CattleFyApi.util.GeneradorQRS;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoteService {

    @Autowired
    private ILoteRepository loteRepository;

    @Autowired
    private IAnimalRepository animalRepository;

    @Autowired
    private GeneradorQRS generadorQRS;

    @Autowired
    private IEspecieRepository especieRepository;

    @Autowired
    private ICategoriaManejoRepository categoriaManejoRepository;

    @Autowired
    private IGranjaRepository granjaRepository;

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

    public Long totalLotesActivos(Integer granjaId) {
        Long lotes = loteRepository.contarLotesActivos(granjaId);
        return lotes;
    }

    private LoteResponse convertToDto(Lote lote) {
        LoteResponse dto = new LoteResponse();
        dto.setIdLote(lote.getLoteId());
        dto.setCodigoQr(lote.getCodigoQr());
        dto.setNombre(lote.getNombre());
        dto.setIdEspecie(lote.getEspecie().getEspecieId());
        dto.setEspecie(lote.getEspecie().getNombre());
        dto.setIdCategoria(lote.getCategoria().getCategoriaId());
        dto.setCategoria(lote.getCategoria().getNombre());
        dto.setTipoLote(lote.getCategoria().getTipoLote());
        dto.setFechaCreacion(lote.getFechaCreacion());

        long dias = java.time.temporal.ChronoUnit.DAYS.between(lote.getFechaCreacion().toLocalDate(), java.time.LocalDate.now());
        dto.setDiasDesdeCreacion((int) dias);

        dto.setEstado(lote.getEstado());
        dto.setCapacidadMax(lote.getCapacidadMax());

        Long animalesVivos = animalRepository.contarAnimalesVivosPorLote(lote.getLoteId());
        dto.setAnimalesVivos(animalesVivos.intValue());
        return dto;
    }

    public List<LoteResponse> listarLotes(Integer granjaId, Integer especieId, String tipoLote) {
        List<Lote> lotes = loteRepository.findLotesByFilters(granjaId, especieId, tipoLote);
        return lotes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResultadoResponse<LoteResponse> registrarLote(LoteRequest req) {
        try {
            Lote lote = new Lote();

            String codigoQr = generadorQRS.generarCodigoQrLote();
            lote.setCodigoQr(codigoQr);

            lote.setGranja(granjaRepository.findById(req.getIdGranja())
                    .orElseThrow(() -> new EntityNotFoundException("Granja no encontrada con ID: " + req.getIdGranja())));

            lote.setEspecie(especieRepository.findById(req.getIdEspecie())
                    .orElseThrow(() -> new EntityNotFoundException("Especie no encontrada con ID: " + req.getIdEspecie())));

            lote.setCategoria(categoriaManejoRepository.findById(req.getIdCategoria())
                    .orElseThrow(() -> new EntityNotFoundException("Categoría de Manejo no encontrada con ID: " + req.getIdCategoria())));

            lote.setNombre(req.getNombre());
            lote.setCapacidadMax(req.getCapacidadMax());

            lote.setFechaCreacion(LocalDateTime.now());
            lote.setEstado("Inactivo");

            Lote loteGuardado = loteRepository.save(lote);

            LoteResponse loteResponse = convertToDto(loteGuardado);

            return ResultadoResponse.success("Lote registrado exitosamente.", loteResponse);

        } catch (EntityNotFoundException e) {
            return ResultadoResponse.error("Error al registrar lote: " + e.getMessage());
        } catch (Exception e) {
            return ResultadoResponse.error("Ocurrió un error inesperado al registrar el lote.");
        }
    }

    public LoteResponse obtenerLotePorId(Integer loteId) {
        Lote lote = loteRepository.findById(loteId)
                .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado con ID: " + loteId));
        return convertToDto(lote);
    }


    public LoteResponse obtenerLotePorQr(String codigoQr) {
        Lote lote = loteRepository.findByCodigoQr(codigoQr)
                .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado con código QR: " + codigoQr));

        return convertToDto(lote);
    }
}
