package com.Cibertec.CattleFyApi.repository;

import com.Cibertec.CattleFyApi.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Cibertec.CattleFyApi.models.Granja;

public interface IGranjaRepository  extends JpaRepository<Granja, Integer>{

    boolean existsByUsuario(Usuario usuario);

}
