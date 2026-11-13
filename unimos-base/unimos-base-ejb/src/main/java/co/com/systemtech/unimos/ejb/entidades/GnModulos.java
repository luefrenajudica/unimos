/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.ejb.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author luefr
 */
@Entity
@Table(name = "gn_modulos", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"tipo", "nombre", "gn_modulos_id"})})
@NamedQueries({
    @NamedQuery(name = "GnModulos.findAll", query = "SELECT g FROM GnModulos g")})
public class GnModulos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(nullable = false, length = 32)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(nullable = false, length = 1)
    private String tipo;
    @Size(max = 512)
    @Column(length = 512)
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean activo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(nullable = false, length = 16)
    private String privilegios;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int orden;
    @Size(max = 128)
    @Column(length = 128)
    private String url;
    @Size(max = 32)
    @Column(length = 32)
    private String icono;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int ambiente;
    @Size(max = 8)
    @Column(length = 8)
    private String version;
    @Column(name = "fecha_version")
    @Temporal(TemporalType.DATE)
    private Date fechaVersion;
    @Size(max = 32)
    @Column(name = "adicional1_nombre", length = 32)
    private String adicional1Nombre;
    @Size(max = 32)
    @Column(name = "adicional2_nombre", length = 32)
    private String adicional2Nombre;
    @Size(max = 32)
    @Column(name = "adicional3_nombre", length = 32)
    private String adicional3Nombre;
    @Size(max = 32)
    @Column(name = "adicional4_nombre", length = 32)
    private String adicional4Nombre;
    @Size(max = 32)
    @Column(name = "adicional5_nombre", length = 32)
    private String adicional5Nombre;
    @Size(max = 32)
    @Column(name = "adicional6_nombre", length = 32)
    private String adicional6Nombre;
    @Size(max = 32)
    @Column(name = "adicional7_nombre", length = 32)
    private String adicional7Nombre;
    @Size(max = 32)
    @Column(name = "adicional8_nombre", length = 32)
    private String adicional8Nombre;
    @Size(max = 32)
    @Column(name = "adicional9_nombre", length = 32)
    private String adicional9Nombre;
    @Size(max = 32)
    @Column(name = "adicional10_nombre", length = 32)
    private String adicional10Nombre;
    @Size(max = 32)
    @Column(name = "adicional11_nombre", length = 32)
    private String adicional11Nombre;
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
    @Size(max = 128)
    @Column(name = "usuario_modifica", length = 128)
    private String usuarioModifica;
    @Size(max = 16)
    @Column(name = "terminal_modifica", length = 16)
    private String terminalModifica;
    @Column(name = "fecha_hora_modifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraModifica;
    @OneToMany(mappedBy = "gnModulosId", fetch = FetchType.LAZY)
    private List<GnModulos> gnModulosList;
    @JoinColumn(name = "gn_modulos_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private GnModulos gnModulosId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnModulosId", fetch = FetchType.LAZY)
    private List<GnModuloVersiones> gnModuloVersionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnModulos", fetch = FetchType.LAZY)
    private List<GnPermisos> gnPermisosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnModulosId", fetch = FetchType.LAZY)
    private List<GnModuloManuales> gnModuloManualesList;

    public GnModulos() {
    }

    public GnModulos(Integer id) {
        this.id = id;
    }

    public GnModulos(Integer id, String nombre, String tipo, boolean activo, String privilegios, int orden, int ambiente, String usuarioCrea, String terminalCrea, Date fechaHoraCrea) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.activo = activo;
        this.privilegios = privilegios;
        this.orden = orden;
        this.ambiente = ambiente;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(String privilegios) {
        this.privilegios = privilegios;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public int getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(int ambiente) {
        this.ambiente = ambiente;
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

    public String getAdicional1Nombre() {
        return adicional1Nombre;
    }

    public void setAdicional1Nombre(String adicional1Nombre) {
        this.adicional1Nombre = adicional1Nombre;
    }

    public String getAdicional2Nombre() {
        return adicional2Nombre;
    }

    public void setAdicional2Nombre(String adicional2Nombre) {
        this.adicional2Nombre = adicional2Nombre;
    }

    public String getAdicional3Nombre() {
        return adicional3Nombre;
    }

    public void setAdicional3Nombre(String adicional3Nombre) {
        this.adicional3Nombre = adicional3Nombre;
    }

    public String getAdicional4Nombre() {
        return adicional4Nombre;
    }

    public void setAdicional4Nombre(String adicional4Nombre) {
        this.adicional4Nombre = adicional4Nombre;
    }

    public String getAdicional5Nombre() {
        return adicional5Nombre;
    }

    public void setAdicional5Nombre(String adicional5Nombre) {
        this.adicional5Nombre = adicional5Nombre;
    }

    public String getAdicional6Nombre() {
        return adicional6Nombre;
    }

    public void setAdicional6Nombre(String adicional6Nombre) {
        this.adicional6Nombre = adicional6Nombre;
    }

    public String getAdicional7Nombre() {
        return adicional7Nombre;
    }

    public void setAdicional7Nombre(String adicional7Nombre) {
        this.adicional7Nombre = adicional7Nombre;
    }

    public String getAdicional8Nombre() {
        return adicional8Nombre;
    }

    public void setAdicional8Nombre(String adicional8Nombre) {
        this.adicional8Nombre = adicional8Nombre;
    }

    public String getAdicional9Nombre() {
        return adicional9Nombre;
    }

    public void setAdicional9Nombre(String adicional9Nombre) {
        this.adicional9Nombre = adicional9Nombre;
    }

    public String getAdicional10Nombre() {
        return adicional10Nombre;
    }

    public void setAdicional10Nombre(String adicional10Nombre) {
        this.adicional10Nombre = adicional10Nombre;
    }

    public String getAdicional11Nombre() {
        return adicional11Nombre;
    }

    public void setAdicional11Nombre(String adicional11Nombre) {
        this.adicional11Nombre = adicional11Nombre;
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

    public String getUsuarioModifica() {
        return usuarioModifica;
    }

    public void setUsuarioModifica(String usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }

    public String getTerminalModifica() {
        return terminalModifica;
    }

    public void setTerminalModifica(String terminalModifica) {
        this.terminalModifica = terminalModifica;
    }

    public Date getFechaHoraModifica() {
        return fechaHoraModifica;
    }

    public void setFechaHoraModifica(Date fechaHoraModifica) {
        this.fechaHoraModifica = fechaHoraModifica;
    }

    public List<GnModulos> getGnModulosList() {
        return gnModulosList;
    }

    public void setGnModulosList(List<GnModulos> gnModulosList) {
        this.gnModulosList = gnModulosList;
    }

    public GnModulos getGnModulosId() {
        return gnModulosId;
    }

    public void setGnModulosId(GnModulos gnModulosId) {
        this.gnModulosId = gnModulosId;
    }

    public List<GnModuloVersiones> getGnModuloVersionesList() {
        return gnModuloVersionesList;
    }

    public void setGnModuloVersionesList(List<GnModuloVersiones> gnModuloVersionesList) {
        this.gnModuloVersionesList = gnModuloVersionesList;
    }

    public List<GnPermisos> getGnPermisosList() {
        return gnPermisosList;
    }

    public void setGnPermisosList(List<GnPermisos> gnPermisosList) {
        this.gnPermisosList = gnPermisosList;
    }

    public List<GnModuloManuales> getGnModuloManualesList() {
        return gnModuloManualesList;
    }

    public void setGnModuloManualesList(List<GnModuloManuales> gnModuloManualesList) {
        this.gnModuloManualesList = gnModuloManualesList;
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
        if (!(object instanceof GnModulos)) {
            return false;
        }
        GnModulos other = (GnModulos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.systemtech.unimos.ejb.entidades.GnModulos[ id=" + id + " ]";
    }
    
}
