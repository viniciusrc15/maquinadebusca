/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.model;

import com.maquinadebusca.app.entity.Link;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author vinicius
 */
@Data
public class HostModel {

    private Long id;
    private String hostName;   
     private LocalDateTime ultimaColeta;
     private Long qtdLinks;
     private List<Link> links;

    public HostModel(Long id, List<Link> links) {
        this.id = id;
        this.links = links;
    }

    public HostModel(Long id, String hostName, LocalDateTime ultimaColeta, Long qtdLinks, List<Link> links) {
        this.id = id;
        this.hostName = hostName;
        this.ultimaColeta = ultimaColeta;
        this.qtdLinks = qtdLinks;
        this.links = links;
    }
    
    public HostModel(Long id, String hostName, LocalDateTime ultimaColeta, Long qtdLinks) {
        this.id = id;
        this.hostName = hostName;
        this.ultimaColeta = ultimaColeta;
        this.qtdLinks = qtdLinks;
    }
}
