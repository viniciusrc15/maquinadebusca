/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.service;

import com.maquinadebusca.app.entity.Documento;
import com.maquinadebusca.app.entity.TermoDocumento;
import java.util.Hashtable;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vinicius
 */
@Service
public class IndexadorService {

    private Hashtable<String, TermoDocumento> hashTermos;

    @Autowired
    IndiceInvertidoService indiceInvertidoService;

    @Autowired
    ColetorService coletorService;

    @Autowired
    TermoDocumentoService termoDocumentoService;

    List<Documento> documentos;


    @Transactional
    public boolean limpaCriaIndice() {
        this.hashTermos = new Hashtable<>();
        indiceInvertidoService.deleteAllNativeQuery();
        termoDocumentoService.deleteAllNativeQuery();
        return criarIndice();
    }

    public boolean criarIndice() {
        this.hashTermos = new Hashtable<>();
        documentos = coletorService.getDocumentos();
        // MockupTestes mock = new MockupTestes();
        // documentos = mock.getDocumentosExercicio1();
        for (Documento documento : documentos) {
            try {
                documento.setFrequenciaMaxima(0L);
                documento.setSomaQuadradosPesos(0L);
                documento = coletorService.save(documento);
                this.indexar(documento, documentos.size());
            } catch (Exception e) {
                System.out.println("Falha ao indexar o documento id:[" + documento.getId() + "]");
            }
        }

        return true;
    }

    public void indexar(Documento documento, int N) {
        int i;
        String visaoDocumento = documento.getVisao();
        String[] termos = visaoDocumento.split(" ");
        for (i = 0; i < termos.length; i++) {
            if (!termos[i].equals("")) {
                TermoDocumento termo = this.getTermo(termos[i]);
                System.out.println("Processando o termo: [" + termo.getTexto() + "] - " + i + " de " + termos.length);
                int f = this.frequencia(termo.getTexto(), termos);
                if (f > documento.getFrequenciaMaxima()) {
                    documento.setFrequenciaMaxima(f);
                }
                double peso = calcularPeso(N, termo.getN(), f);
                documento.adicionarPeso(peso);
                termo.inserirEntradaIndiceInvertido(documento, f, peso);
            }
        }
    }

    private double calcularPeso(int N, Long n, int frequencia) {
        double tf = calcularTf(frequencia);
        double idf = calculaIdf(N, n);
        return tf * idf;
    }

    private double log(double x, int base) {
        double a = Math.log(x);
        double b = Math.log(base);
        if (a == 0 || b == 0) {
            return 0;
        }
        return (a / b);
    }

    private double calcularTf(int frequencia) {
        return 1 + log(frequencia, 2);
    }

    private double calculaIdf(Integer N, Long n) {
        if (N == 0 || n == 0L) {
            return 0;
        }
        return log((N.doubleValue() / n.doubleValue()), 2);
    }

    public TermoDocumento getTermo(String texto) {
        TermoDocumento termo;

        if (this.hashTermos.containsKey(texto)) {
            termo = (TermoDocumento) this.hashTermos.get(texto);
        } else {
            termo = new TermoDocumento();
            termo.setTexto(texto);
            termo.setN(quantDocPorTermo(texto));
            termo = termoDocumentoService.save(termo);
            this.hashTermos.put(texto, termo);
        }

        return termo;
    }

    private Long quantDocPorTermo(String texto) {
        Long n = 0L;
        for (Documento documento : documentos) {
            if (documento.getVisao().contains(texto.toLowerCase())) {
                n++;
            }
        }
        return n;
    }

    public int frequencia(String termo, String[] termos) {
        int i, contador = 0;

        for (i = 0; i < termos.length; i++) {
            if (!termos[i].equals("")) {
                if (termos[i].equalsIgnoreCase(termo)) {
                    contador++;
                    termos[i] = "";
                }
            }
        }

        return contador;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

}
