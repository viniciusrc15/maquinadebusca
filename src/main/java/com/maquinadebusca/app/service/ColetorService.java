package com.maquinadebusca.app.service;

import com.maquinadebusca.app.model.Documento;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.apache.tomcat.jni.Time;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vinicius
 */
@Service
public class ColetorService {

    public Documento getDataColletor() {
        URL url;
        Documento d = new Documento();
        try {
            List<String> urls = new LinkedList<String>();
            urls.addAll(Arrays.asList("http://journals.ecs.soton.ac.uk/java/tutorial/networking/urls/readingWriting.html",
                    "https://www.baeldung.com/java-string-remove-stopwords", 
                    "https://www.youtube.com/watch?v=MGWJbaYdy-Y&list=PLZTjHbp2Y7812axMiHkbXTYt9IDCSYgQz", 
                    "https://www.guj.com.br/t/verficar-duplicata-num-array-unidimensional/35422/9",
                    "http://journals.ecs.soton.ac.uk/java/tutorial/networking/urls/readingWriting.html"
                    ));

            d.setUrls(verifyDuplicate(urls));

            for (String urlSimple : d.getUrls()) {
                url = new URL(urlSimple);
                Document doc = Jsoup.connect(url.toString()).get();
                Elements links = doc.select ("a[href]");
                
                for (Element link : links)
                    if ((! link.attr("abs:href").equals ("") && (link.attr("abs:href") != null)))
                    urls.add (link.attr("abs:href"));
                
                // Time.sleep(10);
                d.setVisao(removeTrash(doc.text()).toLowerCase());
            }
        } catch (Exception e) {
            System.out.println("Erro ao coletar a página.");
            e.printStackTrace();
        }
        return d;

    }

    private List<String> verifyDuplicate(List<String> urls) {
        List<String> urlsNew = new ArrayList<String>();
        for (int i = 0; i < urls.size() - 1; i++) {
            for (int j = 0; j < urls.size(); j++) {
                try {
                     if (!urls.get(i).equals(urls.get(j)) && verifyRobotsDisalow(urls.get(i))) {
                      urlsNew.add(urls.get(i));
                    }
                } catch (Exception e) {
                }
               
            }
        }
        return urlsNew;
    }

    private String removeTrash(String texto)  throws IOException{
        List<String> stopwords = Files.readAllLines(Paths.get("stopwords.txt"));
        String builder = new String();
        String[] allWords = texto.toLowerCase().split(" ");
        for(String word : allWords) {
            if(!stopwords.contains(word)) {
                builder = builder.concat(word);
                builder = builder.concat(" ");
            }
        }
        return builder; 
    }

    private boolean verifyRobotsDisalow(String url) throws MalformedURLException, IOException {
        URL urlSeparated = new URL(url);
        
        String coletor = urlSeparated.getProtocol().concat("://");
        coletor = coletor.concat(urlSeparated.getHost());
        Document doc = Jsoup.connect(coletor.concat("/robots.txt").toString()).get();
        String disallow = doc.text().replace("Disallow: ", "!@#Disallow:");
        String allow = disallow.replace("Allow: ", "!@#Allow:");
        String trata_espaco = allow.replace(" ", "");
        String[] caminho_tratado = trata_espaco.split("!@#");
        List<String> aux_url = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        //Fim 

        // Adiciona o array de string em um ArrayList
        for (int i = 0; i <= (caminho_tratado.length - 1); i++) {
            aux_url.add(caminho_tratado[i]);
        }
        //Fim

        //Adiciona do ArraList aux_url para o ArrayList url somente os caminhos Disallow
        for (int i = 0; i <= (aux_url.size() - 1); i++) {
            if (aux_url.get(i).contains("Disallow")) {
                urls.add(aux_url.get(i));
            }
        }
            //Fim
        return !urls.contains(url);
    }

}
