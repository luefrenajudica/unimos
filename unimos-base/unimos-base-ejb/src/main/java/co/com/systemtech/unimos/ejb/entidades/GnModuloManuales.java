/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.ejb.entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author luefr
 */
@Entity
@Table(name = "gn_modulo_manuales")
@NamedQueries({
    @NamedQuery(name = "GnModuloManuales.findAll", query = "SELECT g FROM GnModuloManuales g")})
public class GnModuloManuales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(nullable = false, length = 8)
    private String version;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_version", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaVersion;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean actual;
    @Size(max = 124)
    @Column(length = 124)
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(nullable = false, length = 256)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(nullable = false, length = 512)
    private String ruta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(nullable = false, length = 128)
    private String archivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "usuario_crea", nullable = false, length = 128)
    private String usuarioCrea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "terminal_crea", nullable = false, length = 16)
    private String terminalCrea;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_hora_crea", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCrea;
    @JoinColumn(name = "gn_modulos_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GnModulos gnModulosId;

    public GnModuloManuales() {
    }

    public GnModuloManuales(Integer id) {
        this.id = id;
    }

    public GnModuloManuales(Integer id, String version, Date fechaVersion, boolean actual, String nombre, String ruta, String archivo, String usuarioCrea, String terminalCrea, Date fechaHoraCrea) {
        this.id = id;
        this.version = version;
        this.fechaVersion = fechaVersion;
        this.actual = actual;
        this.nombre = nombre;
        this.ruta = ruta;
        this.archivo = archivo;
        this.usuarioCrea = usuarioCrea;
        this.terminalCrea = terminalCrea;
        this.fechaHoraCrea = fechaHoraCrea;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getFechaVersion() {
        return fechaVersion;
    }

    public void setFechaVersion(Date fechaVersion) {
        this.fechaVersion = fechaVersion;
    }

    public boolean getActual() {
        return actual;
    }

    public void setActual(boolean actual) {
        this.actual = actual;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(String usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    public String getTerminalCrea() {
        return terminalCrea;
    }

    public void setTerminalCrea(String terminalCrea) {
        this.terminalCrea = terminalCrea;
    }

    public Date getFechaHoraCrea() {
        return fechaHoraCrea;
    }

    public void setFechaHoraCrea(Date fechaHoraCrea) {
        this.fechaHoraCrea = fechaHoraCrea;
    }

    public GnModulos getGnModulosId() {
        return gnModulosId;
    }

    public void setGnModulosId(GnModulos gnModulosId) {
        this.gnModulosId = gnModulosId;
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
        if (!(object instanceof GnModuloManuales)) {
            return false;
        }
        GnModuloManuales other = (GnModuloManuales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.systemtech.unimos.ejb.entidades.GnModuloManuales[ id=" + id + " ]";
    }
    
}
