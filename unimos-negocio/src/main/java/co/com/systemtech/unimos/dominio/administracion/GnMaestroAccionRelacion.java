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
public class GnMaestroAccionRelacion extends Auditoria {
    
    private Integer id;
    private GnMaestroAccion maestroAccionId;
    private GnMaestro maestroId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GnMaestroAccion getMaestroAccionId() {
        return maestroAccionId;
    }

    public void setMaestroAccionId(GnMaestroAccion maestroAccionId) {
        this.maestroAccionId = maestroAccionId;
    }

    public GnMaestro getMaestroId() {
        return maestroId;
    }

    public void setMaestroId(GnMaestro maestroId) {
        this.maestroId = maestroId;
    }
    
}
