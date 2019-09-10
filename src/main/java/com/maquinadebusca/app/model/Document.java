/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.model;

import java.net.URL;
import java.util.List;

/**
 *
 * @author vinicius
 */
public class Documento {
    private URL url;
    private String texto;
    private String visao;
    private List<String> urls;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public Documento(URL url, String texto, String visao) {
        this.url = url;
        this.texto = texto;
        this.visao = visao;
    }
    
    public Documento() {}

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getVisao() {
        return visao;
    }

    public void setVisao(String visao) {
        this.visao = visao;
    }
    
    
    
}
