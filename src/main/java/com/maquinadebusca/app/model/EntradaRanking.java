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
import lombok.NoArgsConstructor;

/**
 *
 * @author vinicius
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntradaRanking {

    private String url;
    private List<Double> produtoPesos = new LinkedList<>();
    private double somaQuadradosPesosDocumento;
    private double somaQuadradosPesosConsulta;
    private double similaridade;

    public EntradaRanking(String url, double produtoPesos, double somaQuadradosPesosDocumento,
            double somaQuadradosPesosConsulta) {
        this.url = url;
        this.produtoPesos.add(produtoPesos);
        this.somaQuadradosPesosDocumento = somaQuadradosPesosDocumento;
        this.somaQuadradosPesosConsulta = somaQuadradosPesosConsulta;
    }

    public void adicionarProdutoPesos(double produtoPesos) {
        this.produtoPesos.add(produtoPesos);
    }

    public void computarSimilaridade() {
        int i;
        double numerador = 0, denominador;

        if ((this.somaQuadradosPesosDocumento > 0) && (this.somaQuadradosPesosConsulta > 0)) {
            for (i = 0; i < this.produtoPesos.size(); i++) {
                numerador += this.produtoPesos.get(i);
            }
            denominador = Math.sqrt(this.somaQuadradosPesosDocumento) * Math.sqrt(this.somaQuadradosPesosConsulta);

            this.similaridade = numerador / denominador;
        } else {
            this.similaridade = 0;
        }
    }

    public void computarSimilaridadeSemiNormalizada() {
        int i;
        double numerador = 0, denominador;

        if ((this.somaQuadradosPesosDocumento > 0) && (this.somaQuadradosPesosConsulta > 0)) {
            for (i = 0; i < this.produtoPesos.size(); i++) {
                numerador += this.produtoPesos.get(i);
            }
            denominador = Math.sqrt(this.somaQuadradosPesosDocumento);

            this.similaridade = numerador / denominador;
        } else {
            this.similaridade = 0;
        }
    }

    public EntradaRanking clone() {
        EntradaRanking retorno = new EntradaRanking();
        retorno.setProdutoPesos(this.produtoPesos);
        retorno.setSimilaridade(this.similaridade);
        retorno.setSomaQuadradosPesosConsulta(this.somaQuadradosPesosConsulta);
        retorno.setSomaQuadradosPesosDocumento(this.somaQuadradosPesosDocumento);
        retorno.setUrl(this.url);
        return retorno;
    }

}
