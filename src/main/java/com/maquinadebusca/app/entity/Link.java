/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author vinicius
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Link implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String url;

    @Basic
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime ultimaColeta;

    @ManyToOne
    private Host host;

    @ManyToMany(
            mappedBy = "links", //Nome do atributo na classe Documento.
            fetch = FetchType.LAZY
    )
    private Set<Documento> documentos;

    public Link(String url, Documento documento) {
        this.url = url;
        this.ultimaColeta = null;
        this.documentos.add(documento);
    }

    public void addDocumento(Documento documento) {
        this.documentos.add(documento);
    }

    public void removeDocumento(Documento documento) {
        this.documentos.remove(documento);
    }
}
