/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.dominio.registro;

import co.com.systemtech.unimos.dominio.administracion.GnEmpresa;
import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.dominio.generico.Auditoria;
import java.util.Objects;

/**
 *
 * @author ripalacios
 */

public class Sede extends Auditoria {
    
    private Integer id;
    private Ubicacion ubicacion;
    private String nombre;
    private String descripcion;
    private String codigoHabilitacion;
    private String direccion;
    private String telefono;

    public Sede() {
    }

    public Sede(Integer id) {
        this.id = id;
    }

    public Sede(Integer id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
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

    public String getCodigoHabilitacion() {
        return codigoHabilitacion;
    }

    public void setCodigoHabilitacion(String codigoHabilitacion) {
        this.codigoHabilitacion = codigoHabilitacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Sede{" + "id=" + id + ", empresa=" +  ubicacion + ", nombre=" + nombre + ", descripcion=" + descripcion + ", codigoHabilitacion=" + codigoHabilitacion + ", direccion=" + direccion + ", telefono=" + telefono + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sede other = (Sede) obj;
        return Objects.equals(this.id, other.id);
    }
    
}
