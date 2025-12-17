package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.models.RegistroProduccion;
import com.Cibertec.CattleFyApi.repository.IRegistroProduccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroProduccionService {

    @Autowired
    IRegistroProduccionRepository registroProduccionRepository;

    public List<RegistroProduccion> getAll(){
        return registroProduccionRepository.findAll();
    }
}
