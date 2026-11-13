/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.ejb.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author luefr
 */
@Entity
@Table(name = "gn_maestro_acciones")
@NamedQueries({
    @NamedQuery(name = "GnMaestroAcciones.findAll", query = "SELECT g FROM GnMaestroAcciones g")})
public class GnMaestroAcciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "maestros_tipo", nullable = false, length = 4)
    private String maestrosTipo;
    @Column(name = "grupo_id")
    private Integer grupoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String nombre;
    @Size(max = 512)
    @Column(length = 512)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnMaestroAccionesId", fetch = FetchType.LAZY)
    private List<GnMaestroAccionRelaciones> gnMaestroAccionRelacionesList;

    public GnMaestroAcciones() {
    }

    public GnMaestroAcciones(Integer id) {
        this.id = id;
    }

    public GnMaestroAcciones(Integer id, String maestrosTipo, String nombre) {
        this.id = id;
        this.maestrosTipo = maestrosTipo;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaestrosTipo() {
        return maestrosTipo;
    }

    public void setMaestrosTipo(String maestrosTipo) {
        this.maestrosTipo = maestrosTipo;
    }

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
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

    public List<GnMaestroAccionRelaciones> getGnMaestroAccionRelacionesList() {
        return gnMaestroAccionRelacionesList;
    }

    public void setGnMaestroAccionRelacionesList(List<GnMaestroAccionRelaciones> gnMaestroAccionRelacionesList) {
        this.gnMaestroAccionRelacionesList = gnMaestroAccionRelacionesList;
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
        if (!(object instanceof GnMaestroAcciones)) {
            return false;
        }
        GnMaestroAcciones other = (GnMaestroAcciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.systemtech.unimos.ejb.entidades.GnMaestroAcciones[ id=" + id + " ]";
    }
    
}
