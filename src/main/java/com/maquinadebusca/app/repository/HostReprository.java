/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.repository;

import com.maquinadebusca.app.entity.Host;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author vinicius
 */
public interface HostReprository extends JpaRepository<Host, Long>{

    // Optional<Host> findByHostName(String host);
    
    Host findByHostName(String host);
    
}
