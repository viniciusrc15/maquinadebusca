/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author vinicius
 */
@Repository
public interface TermoDocumentoRepository extends JpaRepository<Object, Object> {

    @Query(value
            = " select log (2, (select count(distinct d.id) from Documento d)/ t.n) "
            + " from TermoDocumento t "
            + " where t.texto = :termo limit 1",
            nativeQuery = true)
    public String getIdf(@Param("termo") String termo);

}
