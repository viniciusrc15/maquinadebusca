/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.repository;

import com.maquinadebusca.app.entity.Documento;
import com.maquinadebusca.app.entity.Link;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author vinicius
 */
@Repository
public interface LinkRepository extends JpaRepository<Link, Long>{
    @Override
    List<Link> findAll();
    
}
