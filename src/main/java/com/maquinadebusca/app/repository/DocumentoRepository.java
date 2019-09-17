/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.maquinadebusca.app.entity.Documento;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author vinicius
 */
@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    @Override
    List<Documento> findAll();

    Documento findById(long id);
}
