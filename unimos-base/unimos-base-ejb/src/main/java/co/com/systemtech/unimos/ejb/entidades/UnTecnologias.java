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
@Table(name = "un_tecnologias", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"codigo"}),
    @UniqueConstraint(columnNames = {"codigo_propio"}),
    @UniqueConstraint(columnNames = {"nombre"})})
@NamedQueries({
    @NamedQuery(name = "UnTecnologias.findAll", query = "SELECT u FROM UnTecnologias u")})
public class UnTecnologias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private short tipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(nullable = false, length = 16)
    private String codigo;
    @Size(max = 16)
    @Column(name = "codigo_propio", length = 16)
    private String codigoPropio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(nullable = false, length = 128)
    private String nombre;
    @Size(max = 512)
    @Column(length = 512)
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "mae_servicio_habilitacion_tipo", nullable = false, length = 4)
    private String maeServicioHabilitacionTipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mae_servicio_habilitacion_id", nullable = false)
    private int maeServicioHabilitacionId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "mae_servicio_habilitacion_codigo", nullable = false, length = 16)
    private String maeServicioHabilitacionCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "mae_servicio_habilitacion_valor", nullable = false, length = 512)
    private String maeServicioHabilitacionValor;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean activa;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unTecnologiasId", fetch = FetchType.LAZY)
    private List<UnFidelizacionTecnologias> unFidelizacionTecnologiasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maTecnologiasId", fetch = FetchType.LAZY)
    private List<UnAgendas> unAgendasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unTecnologiasId", fetch = FetchType.LAZY)
    private List<UnContratoDetalles> unContratoDetallesList;

    public UnTecnologias() {
    }

    public UnTecnologias(Integer id) {
        this.id = id;
    }

    public UnTecnologias(Integer id, short tipo, String codigo, String nombre, String maeServicioHabilitacionTipo, int maeServicioHabilitacionId, String maeServicioHabilitacionCodigo, String maeServicioHabilitacionValor, boolean activa, String usuarioCrea, String terminalCrea, Date fechaHoraCrea) {
        this.id = id;
        this.tipo = tipo;
        this.codigo = codigo;
        this.nombre = nombre;
        this.maeServicioHabilitacionTipo = maeServicioHabilitacionTipo;
        this.maeServicioHabilitacionId = maeServicioHabilitacionId;
        this.maeServicioHabilitacionCodigo = maeServicioHabilitacionCodigo;
        this.maeServicioHabilitacionValor = maeServicioHabilitacionValor;
        this.activa = activa;
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

    public short getTipo() {
        return tipo;
    }

    public void setTipo(short tipo) {
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoPropio() {
        return codigoPropio;
    }

    public void setCodigoPropio(String codigoPropio) {
        this.codigoPropio = codigoPropio;
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

    public String getMaeServicioHabilitacionTipo() {
        return maeServicioHabilitacionTipo;
    }

    public void setMaeServicioHabilitacionTipo(String maeServicioHabilitacionTipo) {
        this.maeServicioHabilitacionTipo = maeServicioHabilitacionTipo;
    }

    public int getMaeServicioHabilitacionId() {
        return maeServicioHabilitacionId;
    }

    public void setMaeServicioHabilitacionId(int maeServicioHabilitacionId) {
        this.maeServicioHabilitacionId = maeServicioHabilitacionId;
    }

    public String getMaeServicioHabilitacionCodigo() {
        return maeServicioHabilitacionCodigo;
    }

    public void setMaeServicioHabilitacionCodigo(String maeServicioHabilitacionCodigo) {
        this.maeServicioHabilitacionCodigo = maeServicioHabilitacionCodigo;
    }

    public String getMaeServicioHabilitacionValor() {
        return maeServicioHabilitacionValor;
    }

    public void setMaeServicioHabilitacionValor(String maeServicioHabilitacionValor) {
        this.maeServicioHabilitacionValor = maeServicioHabilitacionValor;
    }

    public boolean getActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
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

    public List<UnFidelizacionTecnologias> getUnFidelizacionTecnologiasList() {
        return unFidelizacionTecnologiasList;
    }

    public void setUnFidelizacionTecnologiasList(List<UnFidelizacionTecnologias> unFidelizacionTecnologiasList) {
        this.unFidelizacionTecnologiasList = unFidelizacionTecnologiasList;
    }

    public List<UnAgendas> getUnAgendasList() {
        return unAgendasList;
    }

    public void setUnAgendasList(List<UnAgendas> unAgendasList) {
        this.unAgendasList = unAgendasList;
    }

    public List<UnContratoDetalles> getUnContratoDetallesList() {
        return unContratoDetallesList;
    }

    public void setUnContratoDetallesList(List<UnContratoDetalles> unContratoDetallesList) {
        this.unContratoDetallesList = unContratoDetallesList;
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
        if (!(object instanceof UnTecnologias)) {
            return false;
        }
        UnTecnologias other = (UnTecnologias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.systemtech.unimos.ejb.entidades.UnTecnologias[ id=" + id + " ]";
    }
    
}
