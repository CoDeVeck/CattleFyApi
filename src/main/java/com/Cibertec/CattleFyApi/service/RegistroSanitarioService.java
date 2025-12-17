package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.dto.RegistroSanitarioListResponse;
import com.Cibertec.CattleFyApi.dto.RegistroSanitarioRequest;
import com.Cibertec.CattleFyApi.dto.RegistroSanitarioResponse;
import com.Cibertec.CattleFyApi.dto.ResultadoResponse;
import com.Cibertec.CattleFyApi.models.Animal;
import com.Cibertec.CattleFyApi.models.Lote;
import com.Cibertec.CattleFyApi.models.RegistroSanitario;
import com.Cibertec.CattleFyApi.repository.IAnimalRepository;
import com.Cibertec.CattleFyApi.repository.ILoteRepository;
import com.Cibertec.CattleFyApi.repository.IRegistroSanitarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistroSanitarioService {
    private final IRegistroSanitarioRepository registroSanitarioRepository;
    private final ILoteRepository loteRepository;
    private final IAnimalRepository animalRepository;

    @Transactional
    public ResultadoResponse<RegistroSanitarioResponse> crearRegistroUnificado(
            RegistroSanitarioRequest request) {

        if (request.getQrAnimal() == null && request.getQrLote() == null && request.getIdLote() == null) {
            return ResultadoResponse.error("Se requiere el QR del Animal, o el QR/ID del Lote.");
        }

        try {
            Lote lote = null;
            Animal animal = null;

            if (request.getQrAnimal() != null) {
                animal = animalRepository.findByCodigoQr(request.getQrAnimal())
                        .orElseThrow(() -> new RuntimeException("Animal no encontrado"));
            }
            else if (request.getIdLote() != null) {
                lote = loteRepository.findById(request.getIdLote())
                        .orElseThrow(() -> new RuntimeException("Lote por ID no encontrado"));
            }
            else if (request.getQrLote() != null) {
                lote = loteRepository.findByCodigoQr(request.getQrLote())
                        .orElseThrow(() -> new RuntimeException("Lote por QR no encontrado"));
            }

            RegistroSanitario nuevoRegistro = new RegistroSanitario();
            nuevoRegistro.setLote(lote);
            nuevoRegistro.setAnimal(animal);
            nuevoRegistro.setTipoAplicacion(request.getTipoAplicacion());
            nuevoRegistro.setProtocoloTipo(request.getProtocoloTipo());
            nuevoRegistro.setNombreProducto(request.getNombreProducto());
            nuevoRegistro.setCostoPorDosis(request.getCostoPorDosis());
            nuevoRegistro.setCantidadDosis(request.getCantidadDosis());
            nuevoRegistro.setAnimalesTratados(request.getAnimalesTratados());
            nuevoRegistro.setFechaAplicacion(LocalDateTime.now());

            RegistroSanitario registroGuardado = registroSanitarioRepository.save(nuevoRegistro);

            RegistroSanitarioResponse responseDto = mapToResponse(registroGuardado);

            String mensajeExito = (request.getQrAnimal() != null)
                    ? "Registro sanitario para el animal creado exitosamente."
                    : "Registro sanitario masivo para el lote creado exitosamente.";

            return ResultadoResponse.success(mensajeExito, responseDto);

        } catch (Exception e) {
            String mensajeError = "Ocurrió un error al crear el registro sanitario: " + e.getMessage();
            return ResultadoResponse.error(mensajeError);
        }
    }

    @Transactional(readOnly = true)
    public ResultadoResponse<RegistroSanitarioListResponse> listarTodos() {
        try {
            List<RegistroSanitario> registros = registroSanitarioRepository.findAll();

            List<RegistroSanitarioResponse> listaDto = registros.stream()
                    .map(this::mapToResponse)
                    .toList();

            return ResultadoResponse.success(
                    "Listado de registros sanitarios",
                    new RegistroSanitarioListResponse(listaDto)
            );

        } catch (Exception e) {
            return ResultadoResponse.error("Ocurrió un error al listar los registros: " + e.getMessage());
        }
    }

    private RegistroSanitarioResponse mapToResponse(RegistroSanitario registro) {
        RegistroSanitarioResponse response = new RegistroSanitarioResponse();

        response.setSanitarioId(registro.getSanitarioId());

        if (registro.getLote() != null) {
            response.setIdLote(registro.getLote().getLoteId());
            response.setNombreLote(registro.getLote().getNombre());
        }

        if (registro.getAnimal() != null) {
            response.setIdAnimal(registro.getAnimal().getAnimalId());
            response.setCodigoQrAnimal(registro.getAnimal().getCodigoQr());
        }

        response.setTipoAplicacion(registro.getTipoAplicacion());
        response.setProtocoloTipo(registro.getProtocoloTipo());
        response.setNombreProducto(registro.getNombreProducto());
        if (registro.getCostoPorDosis() != null && registro.getCantidadDosis() != null) {
            int cantidadAnimales = registro.getAnimalesTratados() != null ? registro.getAnimalesTratados() : 1;
            response.setCostoTotal(
                    registro.getCostoPorDosis()
                            .multiply(registro.getCantidadDosis())
                            .multiply(new BigDecimal(cantidadAnimales))
            );
        } else {
            response.setCostoTotal(BigDecimal.ZERO);
        }
        response.setCostoPorDosis(registro.getCostoPorDosis());
        response.setCantidadDosis(registro.getCantidadDosis());
        response.setAnimalesTratados(registro.getAnimalesTratados());
        response.setFechaAplicacion(registro.getFechaAplicacion());

        return response;
    }
}
