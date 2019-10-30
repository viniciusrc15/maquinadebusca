/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquinadebusca.app.model.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author vinicius
 */
@AllArgsConstructor
@Getter
public enum RolesEnum {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String label;

}
