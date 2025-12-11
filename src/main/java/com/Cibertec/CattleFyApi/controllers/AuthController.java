package com.Cibertec.CattleFyApi.controllers;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.Cibertec.CattleFyApi.dto.*;
import com.Cibertec.CattleFyApi.models.Usuario;
import com.Cibertec.CattleFyApi.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<ResultadoResponse<AuthResponseDTO>> registrar(
            @RequestBody RegistroRequestDTO request) {
        try {
            AuthResponseDTO auth = authService.registrarUsuario(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResultadoResponse.success("Usuario registrado exitosamente", auth));

        } catch (FirebaseAuthException e) {
            log.error("Error de Firebase al registrar: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ResultadoResponse.error("Error en Firebase: " + e.getMessage()));

        } catch (IllegalArgumentException e) {
            log.error("Error de validación: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ResultadoResponse.error(e.getMessage()));

        } catch (Exception e) {
            log.error("Error inesperado al registrar: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResultadoResponse.error("Error interno del servidor"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResultadoResponse<AuthResponseDTO>> login(
            @RequestBody LoginRequestDTO request) {
        try {
            AuthResponseDTO auth = authService.loginUsuario(request);
            return ResponseEntity.ok(ResultadoResponse.success("Login exitoso", auth));

        } catch (FirebaseAuthException e) {
            log.error("Error de Firebase al hacer login: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ResultadoResponse.error("Error en Firebase: " + e.getMessage()));

        } catch (IllegalArgumentException e) {
            log.error("Credenciales inválidas: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResultadoResponse.error(e.getMessage()));

        } catch (Exception e) {
            log.error("Error inesperado al hacer login: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResultadoResponse.error("Error interno del servidor"));
        }
    }

    @PostMapping("/fcm-token")
    public ResponseEntity<ResultadoResponse<Void>> actualizarFcmToken(
            @AuthenticationPrincipal String firebaseUid,
            @RequestBody Map<String, String> payload) {
        try {
            String fcmToken = payload.get("fcmToken");
            if (fcmToken == null || fcmToken.isBlank()) {
                return ResponseEntity.badRequest()
                        .body(ResultadoResponse.error("El FCM token es requerido"));
            }

            authService.actualizarFcmToken(firebaseUid, fcmToken);
            return ResponseEntity.ok(ResultadoResponse.success("FCM token actualizado"));

        } catch (Exception e) {
            log.error("Error al actualizar FCM token: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ResultadoResponse.error(e.getMessage()));
        }
    }
}