/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author vinicius
 */

@Embeddable
public class IdIndiceInvertido implements Serializable {

	private static final long serialVersionUID = 4778887795838393082L;
	private Long idTermo;
	private Long idDocumento;


}
