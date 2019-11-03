/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.service;

import com.maquinadebusca.app.entity.Link;
import com.maquinadebusca.app.repository.LinkRepository;
import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author vinicius
 */
@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public List<Link> salvarLink(List<Link> links) {
        return linkRepository.saveAll(links);
    }

    public Link atualizarLink(Link link) {
        return linkRepository.save(link);
    }

    public List<Link> encontrarLinkUrl(String url) {
        return linkRepository.findByUrlIgnoreCaseContaining(url);
    }

    public List<Link> listarEmOrdemAlfabetica() {
        return linkRepository.getInLexicalOrder();
    }

    public List<Link> pesquisarLinkPorIntervaloDeIdentificacao(Long id1, Long id2, String host) {
        return linkRepository.findLinkByIdRange(id1, id2, host);
    }

    public Long contarLinkPorIntervaloDeIdentificacao(Long id1, Long id2) {
        return linkRepository.countLinkByIdRange(id1, id2);
    }

    public Link contarLinkPorIntervaloDeData(LocalDateTime dateStart, LocalDateTime dateEnd) {
        return linkRepository.contarLinkPorIntervaloDeData(dateStart, dateEnd);
    }

    public int atualizarDataUltimaColeta(String host, LocalDateTime dataUltimaColeta) {
        return linkRepository.updateLastCrawlingDate(dataUltimaColeta, host);
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

    public String buscarPagina(Integer size, Integer page, String url) {
        Slice<Link> pagina = null;
        Pageable pageable = (Pageable) PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, url));
        while (true) {
            pagina = linkRepository.getPage(pageable);
            int numeroDaPagina = pagina.getNumber();
            int numeroDeElementosNaPagina = pagina.getNumberOfElements();
            int tamanhoDaPagina = pagina.getSize();
            System.out.println("\n\nPágina: " + numeroDaPagina + " Número de Elementos: "
                    + numeroDeElementosNaPagina + " Tamaho da Página: " + tamanhoDaPagina);
            List<Link> links = pagina.getContent();
            links.forEach(System.out::println);
            if (!pagina.hasNext()) {
                break;
            }
            pageable = (Pageable) pagina.nextPageable();
        }
        return "{\"resposta\": \"Ok\"}";
    }
}
