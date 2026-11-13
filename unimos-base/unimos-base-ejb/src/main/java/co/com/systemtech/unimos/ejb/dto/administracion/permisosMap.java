/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.dto.administracion;

import co.com.systemtech.unimos.dominio.administracion.Modulo;

/**
 *
 * @author Fabian Coronel
 */
public class permisosMap {
    private String privilegios;
    private Modulo mod;

    public permisosMap(String privilegios, Modulo mod) {
        this.privilegios = privilegios;
        this.mod = mod;
    }
    
    public permisosMap() {
    }

    public String getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(String privilegios) {
        this.privilegios = privilegios;
    }

    public Modulo getMod() {
        return mod;
    }

    public void setMod(Modulo mod) {
        this.mod = mod;
    }
    
    
}
