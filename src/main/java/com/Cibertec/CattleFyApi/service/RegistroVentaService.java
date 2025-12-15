package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.repository.IRegistroVentaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroVentaService {

    @Autowired
    IRegistroVentaRepository ventaRepository;
    
    public BigDecimal totalVentasPorMes(int anio, int mes) {

        YearMonth yearMonth = YearMonth.of(anio, mes);

        LocalDateTime inicio = yearMonth
                .atDay(1)
                .atStartOfDay();

        LocalDateTime fin = yearMonth
                .plusMonths(1)
                .atDay(1)
                .atStartOfDay();

        return ventaRepository.sumarVentasPorRango(inicio, fin);
    }
    
    

}
