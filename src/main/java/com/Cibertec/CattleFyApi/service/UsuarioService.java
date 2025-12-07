package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.dto.ResultadoResponse;
import com.Cibertec.CattleFyApi.models.Rol;
import com.Cibertec.CattleFyApi.models.Usuario;
import com.Cibertec.CattleFyApi.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    IUsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username)  throws UsernameNotFoundException{
        Usuario u = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no enconetrado"));

        return User.withUsername(u.getEmail())
                .password("{noop}" + u.getContra())
                .roles(u.getRol().getDescripcion().replace("ROLE_",""))
                .build();
    }


    public Optional<Usuario> obtenerDatos(String correo){
        return usuarioRepository.findByEmail(correo);
    }


    public ResultadoResponse createUser(Usuario usuario){

        ResultadoResponse response = new ResultadoResponse();

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()){
            response.setValor(false);
            response.setMensaje("El correo ingresado ya existe intente con otro");
        }
        if (usuarioRepository.findByTelefono(usuario.getTelefono()).isPresent()){
            response.setValor(false);
            response.setMensaje("El telefono ingresado ya existe intente con otro");
        }
        if (usuarioRepository.findByDocumento(usuario.getDocumento()).isPresent()){
            response.setValor(false);
            response.setMensaje("El documento ingresado ya existe intente con otro");
        }
        if (usuarioRepository.findByApePat(usuario.getEmail()).isPresent() && usuarioRepository.findByApeMat(usuario.getApeMat()).isPresent() ){
            response.setValor(false);
            response.setMensaje("Los apellidos ingresado ya existe intente con otros");
        }
        Rol roluDefault = new Rol();
        roluDefault.setRolId(2);
        usuario.setRol(roluDefault);
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setActivo(true);

        usuarioRepository.save(usuario);
        response.setValor(true);
        response.setMensaje("El usuario fue registrado correctamente");
        
        
        return response;
    }

}
