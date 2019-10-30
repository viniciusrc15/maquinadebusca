/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author vinicius
 */
@AllArgsConstructor
@Data
public class Consulta {

    private String texto;
    private String visao;
    private List<TermoConsulta> termosConsulta = new LinkedList<>();
    private List<EntradaRanking> ranking = new LinkedList<>();

    public Consulta(String texto, String visao) {
        this.texto = texto;
        this.visao = visao.trim();
    }

    public List<String> getListaTermos() {
        List<String> listaTermos = new LinkedList<>();

        String[] termos = this.texto.split(" ");
        for (String termo : termos) {
            listaTermos.add(termo);
        }

        return listaTermos;
    }

    public double getSomaQuadradosPesos() {
        double somaQuadradosPesos = 0;
        List<TermoConsulta> termosConsulta = this.getTermosConsulta();
        for (TermoConsulta termoConsulta : termosConsulta) {
            somaQuadradosPesos += Math.pow(termoConsulta.getPeso(), 2);
        }
        return somaQuadradosPesos;
    }

}
