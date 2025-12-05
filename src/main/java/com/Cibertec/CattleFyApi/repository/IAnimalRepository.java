package com.Cibertec.CattleFyApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cibertec.CattleFyApi.models.Animal;

public interface IAnimalRepository extends JpaRepository<Animal, Integer>{

}
