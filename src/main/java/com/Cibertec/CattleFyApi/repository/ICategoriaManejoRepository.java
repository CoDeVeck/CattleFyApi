package com.Cibertec.CattleFyApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cibertec.CattleFyApi.models.CategoriaManejo;

import java.util.List;

public interface ICategoriaManejoRepository  extends JpaRepository<CategoriaManejo, Integer>{
    List<CategoriaManejo> findByEspecie_EspecieId(Integer especieId);
}
