/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.controller;

import com.maquinadebusca.app.model.Mensagem;
import com.maquinadebusca.app.service.IndexadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
@RequestMapping ("/indexador") // URL: http://localhost:8080/indexador
public class IndexadorController {

  @Autowired
  IndexadorService indexadorService;

  // URL: http://localhost:8080/indexador/indice
    @PostMapping (value = "/indice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity criarIndice () {
    boolean confirmacao = indexadorService.limpaCriaIndice();
    ResponseEntity resp;

    if (confirmacao) {
      resp = new ResponseEntity (new Mensagem ("sucesso", "o índice invertido foi criado com sucesso"), HttpStatus.CREATED);
    } else {
      resp = new ResponseEntity (new Mensagem ("erro", "o índice invertido não pode ser criado"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return resp;
  }

  // URL: http://localhost:8080/indexador/documento
  @GetMapping (value = "/documento", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity getDocumento () {
    return new ResponseEntity (indexadorService.getDocumentos(), HttpStatus.CREATED);
  }

}
