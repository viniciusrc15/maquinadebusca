package com.maquinadebusca.app.service;

import com.maquinadebusca.app.model.Documento;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
                Elements links = doc.select("a[href]");
                URLConnection url_connection = url.openConnection();
                InputStream is = url_connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(is);
                BufferedReader buffer = new BufferedReader(reader);
                String linha;
                while ((linha = buffer.readLine()) != null) {
                    linha = Jsoup.parse(linha).text();
                    d.setTexto(d.getTexto().concat(linha));
                }
                Time.sleep(10);
                d.setVisao(removeTrash(linha).toLowerCase());
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
            for (int j = 0; j < 10; j++) {
                if (!urls.get(i).equals(urls.get(j)) && !urls.get(i).equals("http://www.robotstxt.org/orig.html")) {
                   urlsNew.add(urls.get(i));
                }
            }
        }
        return urlsNew;
    }

    private List<String> inserir() {
        return null;
    }

    private String removeTrash(String texto)  throws IOException{
        List<String> stopwords = Files.readAllLines(Paths.get("stopwords.txt"));
        String builder = new String();
        String[] allWords = texto.toLowerCase().split(" ");
        for(String word : allWords) {
            if(!stopwords.contains(word)) {
                builder.concat(word);
                builder.concat(" ");
            }
        }
        return builder; 
    }

}
