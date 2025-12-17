package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.models.Especie;
import com.Cibertec.CattleFyApi.repository.IEspecieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecieService {

    @Autowired
    IEspecieRepository especieRepository;

    public List<Especie> list(){
        return especieRepository.findAll();
    }
}
