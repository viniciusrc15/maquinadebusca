/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.controller;

import com.maquinadebusca.app.model.Mensagem;
import com.maquinadebusca.app.service.IndexadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author vinicius
 */
@RestController
@RequestMapping("indexador")
public class IndexadorController {
    
    @Autowired
    IndexadorService indexadorService;
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity criaIndice(){
        if(indexadorService.criarIndice()){
           return ResponseEntity.ok(new Mensagem("sucesso", ""));
        }
        return ResponseEntity.ok(new Mensagem("erro", ""));
    }
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDocumento(){
        return ResponseEntity.ok(indexadorService.getDocumentos());
    }
}
