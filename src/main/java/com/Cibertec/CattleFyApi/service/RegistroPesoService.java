package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.repository.IRegistroPesoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroPesoService {

    @Autowired
    IRegistroPesoRepository registroPesoRepository;
}
