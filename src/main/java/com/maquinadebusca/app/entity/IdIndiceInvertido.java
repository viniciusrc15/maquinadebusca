/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.entity;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author vinicius
 */
@Embeddable
@Data
@AllArgsConstructor
public class IdIndiceInvertido {

    private Long idTermo;
    private Long idDocumento;
}
