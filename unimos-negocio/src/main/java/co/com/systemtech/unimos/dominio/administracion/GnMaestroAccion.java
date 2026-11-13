/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.dominio.administracion;

import java.io.Serializable;

/**
 *
 * @author sgiraldov
 */
public class GnMaestroAccion implements Serializable {
    
    public static final int TIPO_PRODUCTO_PRODUCTO = 8;
    public static final int TIPO_PRODUCTO_SERVICIO = 9;

    private Integer id;
    private GnMaestroTipo maestrosTipo;
    private String nombre;
    private String descripcion;
    private Integer idGrupo;
    
    public GnMaestroAccion() {
        
    }
    
    public GnMaestroAccion(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GnMaestroTipo getMaestrosTipo() {
        return maestrosTipo;
    }

    public void setMaestrosTipo(GnMaestroTipo maestrosTipo) {
        this.maestrosTipo = maestrosTipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }
    
}
