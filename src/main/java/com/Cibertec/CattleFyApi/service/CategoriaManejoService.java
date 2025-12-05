package com.Cibertec.CattleFyApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cibertec.CattleFyApi.repository.ICategoriaManejoRepository;

@Service
public class CategoriaManejoService {

    @Autowired
    ICategoriaManejoRepository categoriaManejoRepository;
}
