/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.dominio.administracion;

import co.com.systemtech.unimos.dominio.generico.Auditoria;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sgiraldov
 */
public class GnMaestroTipoRelacion extends Auditoria {
    
    private int id;
    GnMaestroTipo maestroTipoPadre;
    GnMaestroTipo maestroTipoHijo;
    List<GnMaestro> maestros;
    List<Integer> seleccion;

    public GnMaestroTipoRelacion() {
        setSeleccion(new ArrayList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GnMaestroTipo getMaestroTipoPadre() {
        return maestroTipoPadre;
    }

    public void setMaestroTipoPadre(GnMaestroTipo maestroTipoPadre) {
        this.maestroTipoPadre = maestroTipoPadre;
    }

    public GnMaestroTipo getMaestroTipoHijo() {
        return maestroTipoHijo;
    }

    public void setMaestroTipoHijo(GnMaestroTipo maestroTipoHijo) {
        this.maestroTipoHijo = maestroTipoHijo;
    }

    public List<GnMaestro> getMaestros() {
        return maestros;
    }

    public void setMaestros(List<GnMaestro> maestros) {
        this.maestros = maestros;
    }

    public List<Integer> getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(List<Integer> seleccion) {
        this.seleccion = seleccion;
    }

    @Override
    public String toString() {
        return "GnMaestroTipoRelacion{" + "id=" + id + ", maestroTipoPadre=" + maestroTipoPadre + ", maestroTipoHijo=" + maestroTipoHijo + '}';
    }
    
}
