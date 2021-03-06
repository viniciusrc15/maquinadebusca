/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.model;

import java.net.URL;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author vinicius
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DocumentoModel {
    private URL url;
    private String texto;
    private String visao;
    private List<String> urls; 
}
