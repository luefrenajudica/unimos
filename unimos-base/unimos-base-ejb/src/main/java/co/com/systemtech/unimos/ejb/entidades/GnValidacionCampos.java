/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.ejb.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author luefr
 */
@Entity
@Table(name = "gn_validacion_campos")
@NamedQueries({
    @NamedQuery(name = "GnValidacionCampos.findAll", query = "SELECT g FROM GnValidacionCampos g")})
public class GnValidacionCampos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Size(max = 32)
    @Column(length = 32)
    private String validador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(nullable = false, length = 32)
    private String nombre;
    @Size(max = 128)
    @Column(name = "expresion_regular", length = 128)
    private String expresionRegular;
    @JoinColumn(name = "gn_maestro_tipos_tipo", referencedColumnName = "tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private GnMaestroTipos gnMaestroTiposTipo;

    public GnValidacionCampos() {
    }

    public GnValidacionCampos(Integer id) {
        this.id = id;
    }

    public GnValidacionCampos(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValidador() {
        return validador;
    }

    public void setValidador(String validador) {
        this.validador = validador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getExpresionRegular() {
        return expresionRegular;
    }

    public void setExpresionRegular(String expresionRegular) {
        this.expresionRegular = expresionRegular;
    }

    public GnMaestroTipos getGnMaestroTiposTipo() {
        return gnMaestroTiposTipo;
    }

    public void setGnMaestroTiposTipo(GnMaestroTipos gnMaestroTiposTipo) {
        this.gnMaestroTiposTipo = gnMaestroTiposTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GnValidacionCampos)) {
            return false;
        }
        GnValidacionCampos other = (GnValidacionCampos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.systemtech.unimos.ejb.entidades.GnValidacionCampos[ id=" + id + " ]";
    }
    
}
