package com.Cibertec.CattleFyApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cibertec.CattleFyApi.models.Usuario;

import java.util.Optional;

public interface IUsuarioRepository  extends JpaRepository<Usuario, Integer>{
    Optional<Usuario> findByFirebaseUid(String firebaseUid);
    boolean existsByEmail(String email);
    boolean existsByDocumento(String documento);

    Optional<Usuario> findByEmail(String correoUsuario);
    Optional<Usuario> findByTelefono(String telefonoUsu);
    Optional<Usuario> findByDocumento(String documentoUsu);
    Optional<Usuario> findByApePat(String apePatUsu);
    Optional<Usuario> findByApeMat(String apeMatUsu);

}
