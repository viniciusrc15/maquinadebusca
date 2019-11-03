/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.controller;

import com.maquinadebusca.app.model.HostModel;
import com.maquinadebusca.app.service.ColetorService;
import com.maquinadebusca.app.service.HostService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author vinicius
 */
@RestController
@RequestMapping("/host")
public class HostController {
    
     @Autowired
    private HostService hostService;
    
    @GetMapping(value = "/{id}", produces
            = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HostModel> listarHost(@PathVariable(value = "id") long id) {
        HostModel host = hostService.getHost(id);
        if (host != null) {
            return ResponseEntity.ok(host);
        }
        return (ResponseEntity<HostModel>) ResponseEntity.notFound();

    }

    @GetMapping(value = "/{id}/links", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HostModel> listarHostWithLinks(@PathVariable(value = "id") long id) {
        HostModel host = hostService.getHostLinks(id);
        if (host != null) {
            return ResponseEntity.ok(host);
        }
        return (ResponseEntity<HostModel>) ResponseEntity.notFound();

    }

    @GetMapping(value = "/host/{id}", produces
            = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<HostModel>> listarHosts(@PathVariable(value = "id") long id) {
        List<HostModel> hosts = hostService.getAlltHost();
        return ResponseEntity.ok(hosts);
    }
    
}
