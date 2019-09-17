/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.model;

import com.maquinadebusca.app.entity.Documento;
import com.maquinadebusca.app.repository.DocumentoRepository;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vinicius
 */
@Service
public class ColetorModel {

    @Autowired
    private DocumentoRepository dr;

    public List<Documento> executar() {
        List<Documento> documentos = new LinkedList();
        List<String> sementes = new ArrayList<>();
        try {
            sementes.add("https://www.youtube.com/");
            sementes.add("https://www.facebook.com/");
            sementes.add("https://www.twitter.com/");
            for (String url : sementes) {
                documentos.add(this.coletar(url));
            }
        } catch (Exception e) {
            System.out.println("Erro ao executar o serviço de coleta!");
            e.printStackTrace();
        }
        return documentos;
    }

    public Documento coletar(String urlDocumento) {
        Documento documento = new Documento();
        try {
            Document d = Jsoup.connect(urlDocumento).get();
            Elements urls = d.select("a[href]");
            documento.setUrl(urlDocumento);
            documento.setTexto(d.html());
            documento.setVisao(d.text());
            for (Element url : urls) {
                String u = url.attr("abs:href");
                if ((!u.equals("")) && (u != null)) {
                    System.out.println(u);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao coletar a página.");
            e.printStackTrace();
        }
        documento = dr.save(documento);
        return documento;
    }

    public List<Documento> getDocumentos() {
        List<Documento> documentos = dr.findAll();
        List<Documento> resposta = new ArrayList<>();
        for (Documento documento : documentos) {
            resposta.add(documento);
        }
        return resposta;
    }

    public Documento getDocumento(long id) {
        Documento documento = dr.findById(id);
        return documento;
    }

}
