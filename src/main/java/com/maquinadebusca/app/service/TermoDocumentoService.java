/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.service;

import com.maquinadebusca.app.entity.TermoDocumento;
import com.maquinadebusca.app.repository.TermoDocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vinicius
 */
@Service
public class TermoDocumentoService {

    @Autowired
    TermoDocumentoRepository termoDocumentoRepository;

    public TermoDocumento save(TermoDocumento termoDocumento) {
        return termoDocumentoRepository.save(termoDocumento);
    }

    public double getIdf(String termoDocumento) {
        try {
            return Double.parseDouble(termoDocumentoRepository.getIdf(termoDocumento));
        } catch (Exception e) {
            return 0.0;
        }
    }

    public void deleteAllNativeQuery() {
        termoDocumentoRepository.deleteAll();
    }
}
