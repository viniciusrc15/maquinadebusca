/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.service;

import com.maquinadebusca.app.entity.Host;
import com.maquinadebusca.app.entity.Link;
import com.maquinadebusca.app.model.HostModel;
import com.maquinadebusca.app.repository.HostReprository;
import com.maquinadebusca.app.repository.LinkRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vinicius
 */
@Service
public class HostService {

    @Autowired
    private HostReprository hostReprository;

    @Autowired
    private LinkRepository linkRepository;

    public List<HostModel> getAlltHost() {
        List<Host> hosts = hostReprository.findAll();
        List<HostModel> hostModels = new ArrayList<>();
        for (Host host : hosts) {
            List<Link> findByHost = linkRepository.findByHost(host);
            hostModels.add(new HostModel(host.getId(), findByHost));
        }
        return hostModels;
    }

    public List<HostModel> getAlltHostAll() {
        List<Host> hosts = hostReprository.findAll();
        List<HostModel> hostModels = new ArrayList<>();
        for (Host host : hosts) {
            List<Link> findByHost = linkRepository.findByHost(host);
            hostModels.add(new HostModel(host.getId(), host.getHostName(), host.getUltimaColeta(), Long.valueOf(findByHost.size()), findByHost));
        }
        return hostModels;
    }

    public HostModel getHostLinks(long id) {
        Optional<Host> host = hostReprository.findById(id);
        if (host.isPresent()) {
            List<Link> findByHost = linkRepository.findByHost(host.get());
            return new HostModel(host.get().getId(), findByHost);
        }
        return null;
    }
    
    
    public HostModel getHost(Long id) {
        Optional<Host> host = hostReprository.findById(id);
        if (host.isPresent()) {
            List<Link> findByHost = linkRepository.findByHost(host.get());
            return new HostModel(host.get().getId(), host.get().getHostName(), host.get().getUltimaColeta(), Long.valueOf(findByHost.size()));
        }
        return null;
    }

}
