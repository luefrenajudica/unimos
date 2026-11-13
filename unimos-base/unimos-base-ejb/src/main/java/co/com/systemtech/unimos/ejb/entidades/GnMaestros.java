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
@Table(name = "gn_maestros", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"tipo", "valor", "nombre"})})
@NamedQueries({
    @NamedQuery(name = "GnMaestros.findAll", query = "SELECT g FROM GnMaestros g")})
public class GnMaestros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(nullable = false, length = 512)
    private String nombre;
    @Size(max = 16)
    @Column(length = 16)
    private String valor;
    @Size(max = 512)
    @Column(length = 512)
    private String descripcion;
    private Integer orden;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean activo;
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
    @JoinColumn(name = "tipo", referencedColumnName = "tipo", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GnMaestroTipos tipo;
    @JoinColumn(name = "gn_perfiles_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private GnPerfiles gnPerfilesId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnMaestrosId", fetch = FetchType.LAZY)
    private List<GnMaestroAccionRelaciones> gnMaestroAccionRelacionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnMaestrosIdPadre", fetch = FetchType.LAZY)
    private List<GnMaestroRelaciones> gnMaestroRelacionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnMaestrosIdHijo", fetch = FetchType.LAZY)
    private List<GnMaestroRelaciones> gnMaestroRelacionesList1;

    public GnMaestros() {
    }

    public GnMaestros(Integer id) {
        this.id = id;
    }

    public GnMaestros(Integer id, String nombre, boolean activo, String usuarioCrea, String terminalCrea, Date fechaHoraCrea) {
        this.id = id;
        this.nombre = nombre;
        this.activo = activo;
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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

    public GnMaestroTipos getTipo() {
        return tipo;
    }

    public void setTipo(GnMaestroTipos tipo) {
        this.tipo = tipo;
    }

    public GnPerfiles getGnPerfilesId() {
        return gnPerfilesId;
    }

    public void setGnPerfilesId(GnPerfiles gnPerfilesId) {
        this.gnPerfilesId = gnPerfilesId;
    }

    public List<GnMaestroAccionRelaciones> getGnMaestroAccionRelacionesList() {
        return gnMaestroAccionRelacionesList;
    }

    public void setGnMaestroAccionRelacionesList(List<GnMaestroAccionRelaciones> gnMaestroAccionRelacionesList) {
        this.gnMaestroAccionRelacionesList = gnMaestroAccionRelacionesList;
    }

    public List<GnMaestroRelaciones> getGnMaestroRelacionesList() {
        return gnMaestroRelacionesList;
    }

    public void setGnMaestroRelacionesList(List<GnMaestroRelaciones> gnMaestroRelacionesList) {
        this.gnMaestroRelacionesList = gnMaestroRelacionesList;
    }

    public List<GnMaestroRelaciones> getGnMaestroRelacionesList1() {
        return gnMaestroRelacionesList1;
    }

    public void setGnMaestroRelacionesList1(List<GnMaestroRelaciones> gnMaestroRelacionesList1) {
        this.gnMaestroRelacionesList1 = gnMaestroRelacionesList1;
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
        if (!(object instanceof GnMaestros)) {
            return false;
        }
        GnMaestros other = (GnMaestros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.systemtech.unimos.ejb.entidades.GnMaestros[ id=" + id + " ]";
    }
    
}
