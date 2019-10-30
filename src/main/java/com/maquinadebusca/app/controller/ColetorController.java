package com.maquinadebusca.app.controller;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.maquinadebusca.app.entity.Documento;
import com.maquinadebusca.app.entity.Link;
import com.maquinadebusca.app.service.ColetorService;
import com.maquinadebusca.app.model.HostModel;
import com.maquinadebusca.app.model.Mensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.maquinadebusca.app.service.ColetorServiceTest;
import java.time.LocalDateTime;
//import com.maquinadebusca.app.model.ColetorServiceTest;
import java.util.List;
import javax.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private ColetorServiceTest coletorSevice;

    @Autowired
    private ColetorService cs;

    @GetMapping(value = "/listar", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> starter() {
        com.maquinadebusca.app.model.DocumentoModel dataColletor = coletorSevice.getDataColletor();
        return ResponseEntity.ok(dataColletor.getVisao());
    }

    // URL: http://localhost:8080/coletor
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Documento>> iniciar(@RequestParam("urls") List<String> links) {
        List<Documento> documentos = cs.executar(links);
        return ResponseEntity.ok(documentos);
    }
    // URL: http://localhost:8080/coletor

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Documento>> listar() {
        return ResponseEntity.ok(cs.getDocumentos());
    }
    // Request for: http://localhost:8080/coletor/{id}

    @GetMapping(value = "/{id}", produces
            = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Documento> listar(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok(cs.getDocumento(id));
    }    
}
