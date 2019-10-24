/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.controller;

import com.maquinadebusca.app.entity.Usuario;
import com.maquinadebusca.app.model.Mensagem;
import com.maquinadebusca.app.service.UsuarioService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author vinicius
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody @Valid Usuario usuario, BindingResult result){
        if(result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new Mensagem("error", "Usuario não pode ser cadastrado"));
        } else {
            return ResponseEntity.ok(usuarioService.create(usuario));
        }
    }
    
    @PostMapping(value = "/login/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity loginUser(@PathVariable Long id){
        Usuario userLogin = usuarioService.userLogin(id);
        if(userLogin != null){
            return ResponseEntity.ok(userLogin);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Mensagem("error", "Usuario não existe"));
    }
    
    @PostMapping(value = "/logout/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity logoutUser(@PathVariable Long id){
        Usuario userLogin = usuarioService.userLogout(id);
        if(userLogin != null){
            return ResponseEntity.ok(userLogin);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Mensagem("error", "Usuario não existe"));
    }
}
