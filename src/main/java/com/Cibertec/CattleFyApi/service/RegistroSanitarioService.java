package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.repository.IRegistroSanitarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroSanitarioService {

    @Autowired
    IRegistroSanitarioRepository registroSanitarioRepository;
}
