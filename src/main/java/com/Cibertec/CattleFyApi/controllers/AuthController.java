package com.Cibertec.CattleFyApi.controllers;

import com.Cibertec.CattleFyApi.dto.ResultadoResponse;
import com.Cibertec.CattleFyApi.models.Usuario;
import com.Cibertec.CattleFyApi.service.CloudinaryService;
import com.Cibertec.CattleFyApi.service.UsuarioService;
import com.Cibertec.CattleFyApi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    CloudinaryService cloudinaryService;



    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestParam("email")String email,
                                          @RequestParam("contra") String contra){

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, contra)
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String token = jwtUtil.generateToken(email, roles);
        return  ResponseEntity.ok(Map.of("token", token));

    }


    @PostMapping(value= "/register", consumes = {"multipart/form-data"})
    public ResponseEntity<?> registrarUsuario(@ModelAttribute Usuario usuario){

        try {
            String urlImagen = cloudinaryService.uploadImage(
                    usuario.getImagenMultipart(), "CattleFy/Usuario");

            usuario.setImagenUrl(urlImagen);

            ResultadoResponse resultado = usuarioService.createUser(usuario);

            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error registrando usuario: " + e.getMessage());
        }
    }
    
    
    @GetMapping("/me")
    public ResponseEntity<?> getUsuarioInfo(Authentication authentication){
        String correoUsu = authentication.getName();
        Optional<Usuario> usuarioOPT = usuarioService.obtenerDatos(correoUsu);

        if (usuarioOPT.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontardo");
        }
        Usuario usuario = usuarioOPT.get();

        return  ResponseEntity.ok(usuario);
    }
}
