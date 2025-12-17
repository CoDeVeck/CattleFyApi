package com.Cibertec.CattleFyApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cibertec.CattleFyApi.models.RegistroMovilidad;

public interface IRegistroMovilidadRepository  extends JpaRepository<RegistroMovilidad, Integer>{

    List<RegistroMovilidad> findByAnimal_AnimalId(Integer AnimalId);

}
