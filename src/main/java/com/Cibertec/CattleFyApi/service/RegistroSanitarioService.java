package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.dto.ResultadoResponse;
import com.Cibertec.CattleFyApi.models.Lote;
import com.Cibertec.CattleFyApi.models.RegistroSanitario;
import com.Cibertec.CattleFyApi.repository.IRegistroSanitarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistroSanitarioService {

    private final IRegistroSanitarioRepository registroSanitarioRepository;
    private final CloudinaryService cloudinaryService;
    public ResultadoResponse<RegistroSanitario> crearRegistroMasivo(RegistroSanitario rs){
        try{
            rs.setAnimal(null);
            rs.setFechaAplicacion(LocalDateTime.now());
            RegistroSanitario registroGuardado = registroSanitarioRepository.save(rs);
            return ResultadoResponse.success("El lote fue creado exitosamente.", registroGuardado);
        } catch (Exception e) {
            String mensajeError = "Ocurrió un error al crear registro masivo: " + e.getMessage();
            return ResultadoResponse.error(mensajeError);
        }
    }
}
