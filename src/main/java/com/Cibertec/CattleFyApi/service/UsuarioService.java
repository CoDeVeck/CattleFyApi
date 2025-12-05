package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    IUsuarioRepository usuarioRepository;
}
