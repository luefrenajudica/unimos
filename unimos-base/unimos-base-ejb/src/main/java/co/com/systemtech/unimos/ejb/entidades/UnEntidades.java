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
@Table(name = "un_entidades", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nit"}),
    @UniqueConstraint(columnNames = {"razon_social"})})
@NamedQueries({
    @NamedQuery(name = "UnEntidades.findAll", query = "SELECT u FROM UnEntidades u")})
public class UnEntidades implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "mae_tipo_tipo", nullable = false, length = 4)
    private String maeTipoTipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mae_tipo_id", nullable = false)
    private int maeTipoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "mae_tipo_codigo", nullable = false, length = 16)
    private String maeTipoCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "mae_tipo_valor", nullable = false, length = 512)
    private String maeTipoValor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(nullable = false, length = 16)
    private String nit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "razon_social", nullable = false, length = 512)
    private String razonSocial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "nombre_comercial", nullable = false, length = 128)
    private String nombreComercial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(nullable = false, length = 512)
    private String direccion;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unEntidadesId", fetch = FetchType.LAZY)
    private List<UnFidelizacionTecnologias> unFidelizacionTecnologiasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unEntidadesId", fetch = FetchType.LAZY)
    private List<UnContratos> unContratosList;
    @JoinColumn(name = "gn_ubicaciones_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GnUbicaciones gnUbicacionesId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unEntidadesId", fetch = FetchType.LAZY)
    private List<UnEntidadContactos> unEntidadContactosList;

    public UnEntidades() {
    }

    public UnEntidades(Integer id) {
        this.id = id;
    }

    public UnEntidades(Integer id, String maeTipoTipo, int maeTipoId, String maeTipoCodigo, String maeTipoValor, String nit, String razonSocial, String nombreComercial, String direccion, String usuarioCrea, String terminalCrea, Date fechaHoraCrea) {
        this.id = id;
        this.maeTipoTipo = maeTipoTipo;
        this.maeTipoId = maeTipoId;
        this.maeTipoCodigo = maeTipoCodigo;
        this.maeTipoValor = maeTipoValor;
        this.nit = nit;
        this.razonSocial = razonSocial;
        this.nombreComercial = nombreComercial;
        this.direccion = direccion;
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

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public List<UnContratos> getUnContratosList() {
        return unContratosList;
    }

    public void setUnContratosList(List<UnContratos> unContratosList) {
        this.unContratosList = unContratosList;
    }

    public GnUbicaciones getGnUbicacionesId() {
        return gnUbicacionesId;
    }

    public void setGnUbicacionesId(GnUbicaciones gnUbicacionesId) {
        this.gnUbicacionesId = gnUbicacionesId;
    }

    public List<UnEntidadContactos> getUnEntidadContactosList() {
        return unEntidadContactosList;
    }

    public void setUnEntidadContactosList(List<UnEntidadContactos> unEntidadContactosList) {
        this.unEntidadContactosList = unEntidadContactosList;
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
        if (!(object instanceof UnEntidades)) {
            return false;
        }
        UnEntidades other = (UnEntidades) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.systemtech.unimos.ejb.entidades.UnEntidades[ id=" + id + " ]";
    }
    
}
