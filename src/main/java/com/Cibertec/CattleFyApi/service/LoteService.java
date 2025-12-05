package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.repository.ILoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoteService {

    @Autowired
    ILoteRepository loteRepository;
}
