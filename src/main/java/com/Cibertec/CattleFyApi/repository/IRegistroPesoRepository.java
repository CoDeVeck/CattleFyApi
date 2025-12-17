package com.Cibertec.CattleFyApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cibertec.CattleFyApi.models.RegistroPeso;

public interface IRegistroPesoRepository  extends JpaRepository<RegistroPeso, Integer>{

    List<RegistroPeso> findByAnimal_AnimalId(Integer AnimalId);

}
