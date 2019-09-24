/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.model;

import com.maquinadebusca.app.entity.Documento;
import com.maquinadebusca.app.entity.Link;
import com.maquinadebusca.app.repository.DocumentoRepository;
import com.maquinadebusca.app.repository.LinkRepository;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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
    private DocumentoRepository documentRepository;

    @Autowired
    private LinkRepository linkRepository;

    public List<Documento> executar() {
        List<Documento> documentos = new LinkedList();
        List<String> sementes = new ArrayList<>();
        try {
            sementes.add("https://www.youtube.com/");
            sementes.add("https://www.facebook.com/");
            sementes.add("https://www.twitter.com/");

            for (String url : verifyDuplicate(sementes)) {
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
            Link link = new Link();
            link.setUrl(urlDocumento);
            link.setUltimaColeta(LocalDateTime.now());
            Document d = Jsoup.connect(urlDocumento).get();
            Elements urls = d.select("a[href]");
            documento.setUrl(urlDocumento);
            documento.setTexto(d.html());
            documento.setVisao(removeTrash(d.text()).toLowerCase().concat(documento.getVisao() != null ? documento.getVisao() : ""));
//            for (Element url : urls) {
//                String u = url.attr("abs:href");
//                if ((!u.equals("")) && (u != null)) {
//                    System.out.println(u);
//                }
//            }
            int i = 0;
            for (Element url : urls) {
                i++;
                String u = url.attr("abs:href");
                if ((!u.equals("")) && (u != null)) {
                    link = new Link();
                    link.setUrl(u);
                    link.setUltimaColeta(null);
                    documento.addLink(link);
                }
            }
            System.out.println("Número de links coletados: " + i);
            System.out.println("Tamanho da lista links: " + documento.getLinks().size());
        } catch (Exception e) {
            System.out.println("Erro ao coletar a página.");
            e.printStackTrace();
        }
        documento = documentRepository.save(documento);
        return documento;
    }

    public List<Documento> getDocumentos() {
        List<Documento> documentos = documentRepository.findAll();
        List<Documento> resposta = new ArrayList<>();
        for (Documento documento : documentos) {
            resposta.add(documento);
        }
        return resposta;
    }

    public Documento getDocumento(long id) {
        Documento documento = documentRepository.findById(id);
        return documento;
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

    private String removeTrash(String texto) throws IOException {
        List<String> stopwords = Files.readAllLines(Paths.get("stopwords.txt"));

        String builder = new String();
        String[] allWords = texto.toLowerCase().split(" ");
        for (String word : allWords) {
            //for (String stopword : stopwords) {
            if (!stopwords.toString().contains(word)) {
                builder = builder.concat(word);
                builder = builder.concat(" ");
            }
            //}
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

    public static void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }

    public List<Link> getLink() {
        Iterable<Link> links = linkRepository.findAll();
        List<Link> resposta = new LinkedList();
        for (Link link : links) {
            resposta.add(link);
        }
        return resposta;
    }

    public Optional<Link> getLink(long id) {
        Optional<Link> link = linkRepository.findById(id);
        return link;
    }
}
