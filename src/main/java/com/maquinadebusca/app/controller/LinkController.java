/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.controller;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.maquinadebusca.app.entity.Link;
import com.maquinadebusca.app.model.Mensagem;
import com.maquinadebusca.app.service.ColetorService;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author vinicius
 */
@RestController
@RequestMapping("/link")
public class LinkController {
    
     @Autowired
    private ColetorService cs;
    
    // URL: http://localhost:8080/coletor/link
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity listarLink() {
        return ResponseEntity.ok(cs.getLink());
    }
    // Request for: http://localhost:8080/coletor/link/{id}

    @GetMapping(value = "/{id}", produces
            = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity listarLink(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok(cs.getLink(id));
    }

    

// Request for: http://localhost:8080/coletor/link
    @PostMapping( produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public ResponseEntity inserirLink(@RequestBody @Valid List<Link> links, BindingResult resultado) {
        ResponseEntity resposta = null;
        if (resultado.hasErrors()) {
            return ResponseEntity.badRequest().body(new Mensagem("erro", "os dados sobre o link não foram informados corretamente "));
        } else {
            links = cs.salvarLink(links);
            if ((links != null) && (!links.isEmpty())) {
                return ResponseEntity.ok(links);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Mensagem("erro", "Não foi possível inserir o link informado no banco de dados"));
            }
        }
    }

    // Request for: http://localhost:8080/coletor/link
    @PutMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity atualizarLink(@RequestBody @Valid Link link, BindingResult resultado) {
        ResponseEntity resposta = null;
        if (resultado.hasErrors()) {
            return ResponseEntity.badRequest().body(new Mensagem("erro", "Os dados sobre o link não foram informados corretamente"));
        } else {
            link = cs.atualizarLink(link);
            if ((link != null) && (link.getId() > 0)) {
                return ResponseEntity.ok(link);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Mensagem("erro","Não foi possível atualizar o link informado no banco de dados"));
            }
        }
    }

    // Request for: http://localhost:8080/coletor/encontrar/{url}
    @GetMapping(value = "/encontrar/{url}", produces
            = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity encontrarLink(@PathVariable(value = "url") String url) {
        return ResponseEntity.ok(cs.encontrarLinkUrl(url));
    }

    // Request for: http://localhost:8080/coletor/link/ordemAlfabetica
    @GetMapping(value = "/ordemAlfabetica", produces
            = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity listarEmOrdemAlfabetica() {
        return ResponseEntity.ok(cs.listarEmOrdemAlfabetica());
    }

    // Request for: http://localhost:8080/coletor/link/pagina
    @GetMapping(value = "/pagina",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity listarPagina(@RequestParam("size") Integer size, @RequestParam("page") Integer page, @RequestParam("url") String url) {
        return ResponseEntity.ok(cs.buscarPagina(size, page, url));
    }

    // Request for: http://localhost:8080/coletor/link/intervalo/{id1}/{id2}
    @GetMapping(value = "/intervalo/{id1}/{id2}", produces
            = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity encontrarLinkPorIntervaloDeId(@PathVariable(value = "id1") Long id1, @PathVariable(value = "id2") Long id2, @RequestParam("host") String host) {
        return ResponseEntity.ok(cs.pesquisarLinkPorIntervaloDeIdentificacao(id1, id2, host));
    }
    // Request for: http://localhost:8080/coletor/link/intervalo/contar/{id1}/{id2}

    @GetMapping(value = "/intervalo/contar/{id1}/{id2}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity contarLinkPorIntervaloDeId(@PathVariable(value = "id1") Long id1, @PathVariable(value = "id2") Long id2) {
        return ResponseEntity.ok(cs.contarLinkPorIntervaloDeIdentificacao(id1, id2));
    }

    @GetMapping(value = "/intervalo/data/{id1}/{id2}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity contarLinkPorIntervaloDeId(
            @PathVariable(value = "dateStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateStart, 
            @PathVariable(value = "dateEnd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateEnd) {
        return ResponseEntity.ok(cs.contarLinkPorIntervaloDeData(dateStart, dateEnd));
    }

// Request for: http://localhost:8080/coletor/link/ultima/coleta/{host}/{data}
    @PutMapping(value = "/ultima/coleta/{host}/{data}", produces
            = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity atualizarUltimaColeta(@PathVariable(value = "host") String host,
            @PathVariable(value = "data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime data) {
        int n = cs.atualizarDataUltimaColeta(host, data);
        return ResponseEntity.ok("sucesso número de registros atualizados  : " + n);
    }
}
