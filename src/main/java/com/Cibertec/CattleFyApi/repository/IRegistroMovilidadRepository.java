package com.Cibertec.CattleFyApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cibertec.CattleFyApi.models.RegistroMovilidad;
import org.springframework.stereotype.Repository;

public interface IRegistroMovilidadRepository  extends JpaRepository<RegistroMovilidad, Integer>{

}
