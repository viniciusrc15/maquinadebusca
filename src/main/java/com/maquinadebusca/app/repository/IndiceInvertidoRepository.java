/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.repository;

import com.maquinadebusca.app.entity.IdIndiceInvertido;
import com.maquinadebusca.app.entity.IndiceInvertido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author vinicius
 */
@Repository
public interface IndiceInvertidoRepository extends JpaRepository<IndiceInvertido, IdIndiceInvertido> {

    @Query(value
            = " select  i.* "
            + " from TermoDocumento t, IndiceInvertido i, Documento d "
            + " where t.id = i.termo_id and "
            + " i.documento_id = d.id and "
            + " t.texto = :termoConsulta ",
            nativeQuery = true)
    List<IndiceInvertido> getEntradasIndiceInvertido(@Param("termoConsulta") String termo);
}
