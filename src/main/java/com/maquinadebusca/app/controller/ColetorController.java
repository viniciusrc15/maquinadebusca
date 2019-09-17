package com.maquinadebusca.app.controller;

import com.maquinadebusca.app.entity.Documento;
import com.maquinadebusca.app.model.ColetorModel;
import com.maquinadebusca.app.model.DocumentoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.maquinadebusca.app.service.ColetorService;
//import com.maquinadebusca.app.model.ColetorService;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

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
    private ColetorService coletorSevice;
    
    @Autowired
    private ColetorModel cs;

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> starter() {
        com.maquinadebusca.app.model.DocumentoModel dataColletor = coletorSevice.getDataColletor();
        return ResponseEntity.ok(dataColletor.getVisao());
    }

    // URL: http://localhost:8080/coletor/iniciar
    @GetMapping(value = "/iniciar", produces
            = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Documento>> iniciar() {
        List<Documento> documentos = cs.executar();
        return ResponseEntity.ok(documentos);
    }
    // URL: http://localhost:8080/coletor/listar

    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Documento>> listar() {
        return ResponseEntity.ok(cs.getDocumentos());
    }
    // Request for: http://localhost:8080/coletor/listar/{id}

    @GetMapping(value = "/listar/{id}", produces
            = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Documento> listar(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok(cs.getDocumento(id));
    }

}
