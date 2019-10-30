/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author vinicius
 */
@Entity
@AllArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class IndiceInvertido implements Serializable {

    private static final long serialVersionUID = -1305758116565937681L;

    @EmbeddedId
    private IdIndiceInvertido id;

    private int frequencia;

    private double peso;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idTermo")
    private TermoDocumento termo;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idDocumento")
    private Documento documento;

    
    public IndiceInvertido(TermoDocumento termo, Documento documento) {
        this.termo = termo;
        this.documento = documento;
        this.id = new IdIndiceInvertido(termo.getId(), documento.getId());
    }

    public IndiceInvertido(TermoDocumento termo, Documento documento, int frequencia, double peso) {
        this.termo = termo;
        this.documento = documento;
        this.frequencia = frequencia;
        this.id = new IdIndiceInvertido(termo.getId(), documento.getId());
        this.peso = peso;
    }
}
