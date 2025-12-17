package com.Cibertec.CattleFyApi.controllers;

import com.Cibertec.CattleFyApi.dto.GranjaRequest;
import com.Cibertec.CattleFyApi.dto.GranjaResponse;
import com.Cibertec.CattleFyApi.service.GranjaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/granja")
public class GranjaController {

    @Autowired
    private GranjaService granjaService;

    @PostMapping(value = "/registrar", consumes = {"multipart/form-data"})
    public ResponseEntity<?> registrarGranja(
            @ModelAttribute GranjaRequest request) {

        try {
            GranjaResponse response = granjaService.registrarGranja(request);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar granja: " + e.getMessage());
        }
    }
}
