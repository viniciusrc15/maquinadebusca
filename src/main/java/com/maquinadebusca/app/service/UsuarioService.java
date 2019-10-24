/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.service;

import com.maquinadebusca.app.entity.Usuario;
import com.maquinadebusca.app.repository.UsuarioRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vinicius
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public Usuario create(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    public Usuario userLogin(Long id) {
        Optional<Usuario> findById = usuarioRepository.findById(id);
        if(findById.isPresent()){
            return loginOrLogout(findById, Boolean.TRUE);
        } else {
            return null;
        }
    }
    
    public Usuario userLogout(Long id) {
        Optional<Usuario> findById = usuarioRepository.findById(id);
        if(findById.isPresent()){
            return loginOrLogout(findById, Boolean.FALSE);
        } else {
            return null;
        }
    }
    
    private Usuario loginOrLogout(Optional<Usuario> findById, Boolean status) {
        findById.get().setIsLogged(status);
        return usuarioRepository.save(findById.get());
    }
    
}
