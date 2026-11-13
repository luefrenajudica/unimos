/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.dominio.registro;

import co.com.systemtech.unimos.dominio.administracion.GnMaestro;
import co.com.systemtech.unimos.dominio.generico.Auditoria;
import java.util.Objects;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class UnEntidadContacto extends Auditoria {
    
    public static final int ESTADO_REGISTRO_CREADO = 0;
    public static final int ESTADO_REGISTRO_CREAR = 1;
    public static final int ESTADO_REGISTRO_BORRAR = 2;
    
    private Integer id;
    private UnEntidad unEntidad;
    private String maeTipoTipo;
    private int maeTipoId;
    private String maeTipoCodigo;
    private String maeTipoValor;
    private String nombre;
    private String contacto;
    private int estadoRegistro;

    public UnEntidadContacto() {
    }

    public UnEntidadContacto(Integer id) {
        this.id = id;
    }

    public UnEntidadContacto(Integer id, Integer IdEntidad, String maeTipoTipo, int maeTipoId, String maeTipoCodigo, String maeTipoValor, String nombre, String contacto) {
        this.id = id;
        this.unEntidad = new UnEntidad(IdEntidad);
        this.maeTipoTipo = maeTipoTipo;
        this.maeTipoId = maeTipoId;
        this.maeTipoCodigo = maeTipoCodigo;
        this.maeTipoValor = maeTipoValor;
        this.nombre = nombre;
        this.contacto = contacto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UnEntidad getUnEntidad() {
        return unEntidad;
    }

    public void setUnEntidad(UnEntidad unEntidad) {
        this.unEntidad = unEntidad;
    }

    public String getMaeTipoTipo() {
        return maeTipoTipo;
    }

    public void setMaeTipoTipo(String maeTipoTipo) {
        this.maeTipoTipo = maeTipoTipo;
    }

    public int getMaeTipoId() {
        return maeTipoId;
    }

    public void setMaeTipoId(int maeTipoId) {
        this.maeTipoId = maeTipoId;
    }

    public String getMaeTipoCodigo() {
        return maeTipoCodigo;
    }

    public void setMaeTipoCodigo(String maeTipoCodigo) {
        this.maeTipoCodigo = maeTipoCodigo;
    }

    public String getMaeTipoValor() {
        return maeTipoValor;
    }

    public void setMaeTipoValor(String maeTipoValor) {
        this.maeTipoValor = maeTipoValor;
    }
    
    public void setMaeTipo(GnMaestro maestro) {
        if(maestro != null){            
            this.maeTipoTipo = maestro.getTipo();
            this.maeTipoId = maestro.getId();
            this.maeTipoCodigo = maestro.getValor();
            this.maeTipoValor = maestro.getNombre();
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    @Override
    public String toString() {
        return "UnEntidadContacto{" + "id=" + id + ", maeTipoTipo=" + maeTipoTipo + ", maeTipoId=" + maeTipoId + ", maeTipoCodigo=" + maeTipoCodigo + ", maeTipoValor=" + maeTipoValor + ", nombre=" + nombre + ", contacto=" + contacto + ", unEntidad=" + unEntidad + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final UnEntidadContacto other = (UnEntidadContacto) obj;
        if (this.maeTipoId != other.maeTipoId) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.contacto, other.contacto)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }
    
}
