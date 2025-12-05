package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.repository.IEspecieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EspecieService {

    @Autowired
    IEspecieRepository especieRepository;
}
