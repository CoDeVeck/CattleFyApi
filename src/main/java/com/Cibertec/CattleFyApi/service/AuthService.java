package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.models.Rol;
import com.Cibertec.CattleFyApi.repository.IRolRepository;
import com.Cibertec.CattleFyApi.repository.IUsuarioRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.Cibertec.CattleFyApi.dto.AuthResponseDTO;
import com.Cibertec.CattleFyApi.dto.LoginRequestDTO;
import com.Cibertec.CattleFyApi.dto.RegistroRequestDTO;
import com.Cibertec.CattleFyApi.models.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUsuarioRepository usuarioRepository;
    private final IRolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponseDTO registrarUsuario(RegistroRequestDTO request) throws FirebaseAuthException {
        // 1. Validaciones
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        if (usuarioRepository.existsByDocumento(request.getDocumento())) {
            throw new IllegalArgumentException("El documento ya está registrado");
        }

        UserRecord.CreateRequest firebaseRequest = new UserRecord.CreateRequest()
                .setEmail(request.getEmail())
                .setPassword(request.getContra())
                .setDisplayName(request.getNombres() + " " + request.getApePat())
                .setEmailVerified(false)
                .setDisabled(false);

        if (request.getImagenUrl() != null && !request.getImagenUrl().isBlank()) {
            firebaseRequest.setPhotoUrl(request.getImagenUrl());
        }

        UserRecord firebaseUser = FirebaseAuth.getInstance().createUser(firebaseRequest);
        Integer rolId = request.getRolId() != null ? request.getRolId() : 2; Rol rol = rolRepository.findById(rolId) .orElseThrow(() -> new RuntimeException("El rol no existe"));

        Usuario usuario = new Usuario();
        usuario.setNombres(request.getNombres());
        usuario.setApePat(request.getApePat());
        usuario.setApeMat(request.getApeMat());
        usuario.setDocumento(request.getDocumento());
        usuario.setEmail(request.getEmail());
        usuario.setContra(passwordEncoder.encode(request.getContra()));
        usuario.setTelefono(request.getTelefono());
        usuario.setImagenUrl(request.getImagenUrl());
        usuario.setRol(rol);
        usuario.setFirebaseUid(firebaseUser.getUid());
        usuario.setActivo(true);
        usuario.setFechaRegistro(LocalDateTime.now());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        String customToken = FirebaseAuth.getInstance().createCustomToken(firebaseUser.getUid());

        return AuthResponseDTO.builder()
                .usuarioId(usuarioGuardado.getUsuarioId())
                .firebaseUid(usuarioGuardado.getFirebaseUid())
                .email(usuarioGuardado.getEmail())
                .nombres(usuarioGuardado.getNombres())
                .apellidos(usuarioGuardado.getApePat() + " " + usuarioGuardado.getApeMat())
                .token(customToken)
                .rol("ROLE_USER")
                .build();
    }

    @Transactional
    public AuthResponseDTO loginUsuario(LoginRequestDTO request) throws FirebaseAuthException {
        // 1. Buscar usuario en BD
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        // 2. Verificar contraseña
        if (!passwordEncoder.matches(request.getContra(), usuario.getContra())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        // 3. Verificar que el usuario esté activo
        if (!usuario.getActivo()) {
            throw new IllegalArgumentException("Usuario inactivo");
        }

        // 4. Generar custom token de Firebase
        String customToken = FirebaseAuth.getInstance().createCustomToken(usuario.getFirebaseUid());

        // 5. Retornar respuesta
        return AuthResponseDTO.builder()
                .usuarioId(usuario.getUsuarioId())
                .firebaseUid(usuario.getFirebaseUid())
                .email(usuario.getEmail())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApePat() + " " + usuario.getApeMat())
                .token(customToken)
                .rol(usuario.getRol().getDescripcion())
                .build();
    }

    public Usuario obtenerUsuarioPorFirebaseUid(String firebaseUid) {
        return usuarioRepository.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    @Transactional
    public void actualizarFcmToken(String firebaseUid, String fcmToken) {
        Usuario usuario = obtenerUsuarioPorFirebaseUid(firebaseUid);
        usuario.setFcmToken(fcmToken);
        usuario.setFcmTokenFecha(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }
}