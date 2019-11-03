/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.service;

import com.maquinadebusca.app.entity.IndiceInvertido;
import com.maquinadebusca.app.repository.IndiceInvertidoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vinicius
 */
@Service
public class IndiceInvertidoService {

    @Autowired
    IndiceInvertidoRepository indiceInvertidoRepository;

    public void deleteAllNativeQuery() {
        indiceInvertidoRepository.deleteAll();
    }

    public List<IndiceInvertido> getEntradasIndiceInvertido(String termo) {
        return indiceInvertidoRepository.getEntradasIndiceInvertido(termo);
    }
}
