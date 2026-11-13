/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.dominio.administracion;

import co.com.systemtech.unimos.dominio.generico.Auditoria;

/**
 *
 * @author sgiraldov
 */
public class GnMaestroRelacion extends Auditoria {
    
    private int id;
    GnMaestro maestroPadre;
    GnMaestro maestroHijo;

    public GnMaestroRelacion() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GnMaestro getMaestroPadre() {
        return maestroPadre;
    }

    public void setMaestroPadre(GnMaestro maestroPadre) {
        this.maestroPadre = maestroPadre;
    }

    public GnMaestro getMaestroHijo() {
        return maestroHijo;
    }

    public void setMaestroHijo(GnMaestro maestroHijo) {
        this.maestroHijo = maestroHijo;
    }

        
    @Override
    public String toString() {
        return "GnMaestroTipoRelacion{" + "id=" + id + ", maestroTipoPadre=" + maestroPadre + ", maestroTipoHijo=" + maestroHijo + '}';
    }
    
}
