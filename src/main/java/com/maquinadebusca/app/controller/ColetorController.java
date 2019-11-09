package com.maquinadebusca.app.controller;

import com.maquinadebusca.app.entity.Documento;
import com.maquinadebusca.app.service.ColetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.maquinadebusca.app.service.ColetorServiceTest;
//import com.maquinadebusca.app.model.ColetorServiceTest;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vinicius
 */
@RestController
@RequestMapping("/coletor")
public class ColetorController {

    @Autowired
    private ColetorServiceTest coletorSeviceTest;

    @Autowired
    private ColetorService coletorSevice;

    @GetMapping(value = "/listar", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> starter() {
        com.maquinadebusca.app.model.DocumentoModel dataColletor = coletorSeviceTest.getDataColletor();
        return ResponseEntity.ok(dataColletor.getVisao());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Documento>> iniciar(@RequestParam("urls") List<String> links) {
        List<Documento> documentos = coletorSevice.executar(links);
        return ResponseEntity.ok(documentos);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Documento>> listar() {
        return ResponseEntity.ok(coletorSevice.getDocumentos());
    }

    @GetMapping(value = "/{id}", produces
            = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Documento> listar(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(coletorSevice.getDocumento(id));
    }    
}
