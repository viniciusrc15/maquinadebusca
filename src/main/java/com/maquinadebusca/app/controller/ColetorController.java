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

    // URL: http://localhost:8080/coletor/link
    @GetMapping(value = "/link", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity listarLink() {
        return ResponseEntity.ok(cs.getLink());
    }
    // Request for: http://localhost:8080/coletor/link/{id}

    @GetMapping(value = "/link/{id}", produces
            = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity listarLink(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok(cs.getLink(id));
    }

    @GetMapping(value = "/host/{id}", produces
            = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HostModel> listarHost(@PathVariable(value = "id") long id) {
        HostModel host = cs.getHost(id);
        if (host != null) {
            return ResponseEntity.ok(host);
        }
        return (ResponseEntity<HostModel>) ResponseEntity.notFound();

    }

    @GetMapping(value = "/host/{id}/links", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HostModel> listarHostWithLinks(@PathVariable(value = "id") long id) {
        HostModel host = cs.getHostLinks(id);
        if (host != null) {
            return ResponseEntity.ok(host);
        }
        return (ResponseEntity<HostModel>) ResponseEntity.notFound();

    }

    @GetMapping(value = "/host/", produces
            = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<HostModel>> listarHosts(@PathVariable(value = "id") long id) {
        List<HostModel> hosts = cs.getAlltHost();
        return ResponseEntity.ok(hosts);

    }

// Request for: http://localhost:8080/coletor/link
    @PostMapping(value = "/link", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
    @PutMapping(value = "/link", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
    @GetMapping(value = "/link/ordemAlfabetica", produces
            = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity listarEmOrdemAlfabetica() {
        return ResponseEntity.ok(cs.listarEmOrdemAlfabetica());
    }

    // Request for: http://localhost:8080/coletor/link/pagina
    @GetMapping(value = "/link/pagina",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity listarPagina(@RequestParam("size") Integer size, @RequestParam("page") Integer page, @RequestParam("url") String url) {
        return ResponseEntity.ok(cs.buscarPagina(size, page, url));
    }

    // Request for: http://localhost:8080/coletor/link/intervalo/{id1}/{id2}
    @GetMapping(value = "/link/intervalo/{id1}/{id2}", produces
            = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity encontrarLinkPorIntervaloDeId(@PathVariable(value = "id1") Long id1, @PathVariable(value = "id2") Long id2, @RequestParam("host") String host) {
        return ResponseEntity.ok(cs.pesquisarLinkPorIntervaloDeIdentificacao(id1, id2, host));
    }
    // Request for: http://localhost:8080/coletor/link/intervalo/contar/{id1}/{id2}

    @GetMapping(value = "/link/intervalo/contar/{id1}/{id2}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity contarLinkPorIntervaloDeId(@PathVariable(value = "id1") Long id1, @PathVariable(value = "id2") Long id2) {
        return ResponseEntity.ok(cs.contarLinkPorIntervaloDeIdentificacao(id1, id2));
    }

    @GetMapping(value = "/link/intervalo/data/{id1}/{id2}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity contarLinkPorIntervaloDeId(
            @PathVariable(value = "dateStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateStart, 
            @PathVariable(value = "dateEnd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateEnd) {
        return ResponseEntity.ok(cs.contarLinkPorIntervaloDeData(dateStart, dateEnd));
    }

// Request for: http://localhost:8080/coletor/link/ultima/coleta/{host}/{data}
    @PutMapping(value = "/link/ultima/coleta/{host}/{data}", produces
            = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity atualizarUltimaColeta(@PathVariable(value = "host") String host,
            @PathVariable(value = "data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime data) {
        int n = cs.atualizarDataUltimaColeta(host, data);
        return ResponseEntity.ok("sucesso número de registros atualizados  : " + n);
    }
    
}
