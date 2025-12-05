package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.repository.IRegistroAlimentacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroAlimentacionService {

    @Autowired
    IRegistroAlimentacionRepository registroAlimentacionRepository;
}
