/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author vinicius
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TermoConsulta {

    private String texto;
    private int frequencia;
    private double tf;
    private double idf;
    private double peso;

    public TermoConsulta(String texto, int frequencia, double idf) {
        this.texto = texto;
        this.frequencia = frequencia;
        this.tf = 1 + Math.log(this.frequencia) / Math.log(2);
        this.idf = idf;
        this.peso = this.tf * this.idf;
    }
}
