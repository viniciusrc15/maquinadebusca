/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author vinicius
 */
@Entity
@AllArgsConstructor
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class TermoDocumento implements Serializable {

    private static final long serialVersionUID = -6026930898355882965L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @NotBlank
    private String texto;

    private Long n; //qtd doc com o termo

    @OneToMany(mappedBy = "termo", // Nome do atributo na classe IndiceInvertido.
            cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<IndiceInvertido> indiceInvertido;

    
    public void inserirEntradaIndiceInvertido(Documento documento, int frequencia, double peso) {
        IndiceInvertido entradaIndiceInvertido = new IndiceInvertido(this, documento, frequencia, peso); // Cria uma nova entrada para o índice invertido com o termo corrente, o documento informado como parâmetro e a frequencia do termo no documento.
        this.indiceInvertido.add(entradaIndiceInvertido); // Insere a nova entrada no índice invertido do termo corrente.
        documento.getIndiceInvertido().add(entradaIndiceInvertido); // Insere a nova entrada no índice invertido do documento que foi informado como parâmetro.
    }

    public void removeDocumento(Documento documento) {
        Iterator<IndiceInvertido> iterator = this.indiceInvertido.iterator();
        while (iterator.hasNext()) {
            IndiceInvertido entradaIndiceInvertido = iterator.next();
            if (entradaIndiceInvertido.getTermo().equals(this) && entradaIndiceInvertido.getDocumento().equals(documento)) {
                iterator.remove(); // Remoção no Banco de Dados a partir da tabela TermoDocumento.
                entradaIndiceInvertido.getDocumento().getIndiceInvertido().remove(entradaIndiceInvertido); // Remoção no Banco de Dados a partir da tabela Documento.
                entradaIndiceInvertido.setDocumento(null); // Remoção na memória RAM.
                entradaIndiceInvertido.setTermo(null); // Remoção na memória RAM.
            }
        }
    }

    public void setFrequencia(int frequencia, Documento documento) {
        Iterator<IndiceInvertido> iterator = this.indiceInvertido.iterator();
        while (iterator.hasNext()) {
            IndiceInvertido entradaIndiceInvertido = iterator.next();
            if (entradaIndiceInvertido.getTermo().equals(this)
                    && entradaIndiceInvertido.getDocumento().equals(documento)) {
                entradaIndiceInvertido.setFrequencia(frequencia);
                break;
            }
        }
    }

    public void setPeso(double peso, Documento documento) {
        Iterator<IndiceInvertido> iterator = this.indiceInvertido.iterator();
        while (iterator.hasNext()) {
            IndiceInvertido entradaIndiceInvertido = iterator.next();
            if (entradaIndiceInvertido.getTermo().equals(this)
                    && entradaIndiceInvertido.getDocumento().equals(documento)) {
                entradaIndiceInvertido.setPeso(peso);
                break;
            }
        }
    }

    public void inserirEntradaIndiceInvertido(Documento documento, Integer frequencia, Double peso) {
        IndiceInvertido entradaIndiceInvertido = new IndiceInvertido(this, documento, frequencia, peso); // Cria uma nova entrada para o índice invertido com o termo corrente, o documento informado como parâmetro e a frequencia do termo no documento.
        this.indiceInvertido.add(entradaIndiceInvertido); // Insere a nova entrada no índice invertido do termo corrente.
        documento.getIndiceInvertido().add(entradaIndiceInvertido); // Insere a nova entrada no índice invertido do documento que foi informado como parâmetro.
    }
}
