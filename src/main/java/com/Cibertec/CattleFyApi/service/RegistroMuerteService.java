package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.repository.IRegistroMuerteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroMuerteService {

    @Autowired
    IRegistroMuerteRepository registroMuerteRepository;
}
