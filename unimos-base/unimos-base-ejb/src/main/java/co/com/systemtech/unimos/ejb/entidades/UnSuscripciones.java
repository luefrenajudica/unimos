/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.ejb.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author luefr
 */
@Entity
@Table(name = "un_suscripciones")
@NamedQueries({
    @NamedQuery(name = "UnSuscripciones.findAll", query = "SELECT u FROM UnSuscripciones u")})
public class UnSuscripciones implements Serializable {

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
    @Size(min = 1, max = 4)
    @Column(name = "mae_plan_tipo", nullable = false, length = 4)
    private String maePlanTipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mae_plan_id", nullable = false)
    private int maePlanId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "mae_plan_codigo", nullable = false, length = 16)
    private String maePlanCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "mae_plan_valor", nullable = false, length = 512)
    private String maePlanValor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_fin", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valor;
    @Column(name = "valor_pagado", precision = 18, scale = 2)
    private BigDecimal valorPagado;
    @Column(name = "valor_pendiente", precision = 18, scale = 2)
    private BigDecimal valorPendiente;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unSuscripcionesId", fetch = FetchType.LAZY)
    private List<UnComercialComisiones> unComercialComisionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unSuscripcionesId", fetch = FetchType.LAZY)
    private List<UnSuscripcionPersonas> unSuscripcionPersonasList;
    @JoinColumn(name = "gn_sedes_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GnSedes gnSedesId;
    @JoinColumn(name = "un_comerciales_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UnComerciales unComercialesId;
    @JoinColumn(name = "un_personas_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UnPersonas unPersonasId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unSuscripcionesId", fetch = FetchType.LAZY)
    private List<UnSuscripcionPagos> unSuscripcionPagosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unSuscripcionesId", fetch = FetchType.LAZY)
    private List<UnSuscripcionCarteras> unSuscripcionCarterasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unSuscripcionesId", fetch = FetchType.LAZY)
    private List<UnFidelizaciones> unFidelizacionesList;

    public UnSuscripciones() {
    }

    public UnSuscripciones(Integer id) {
        this.id = id;
    }

    public UnSuscripciones(Integer id, String maeTipoTipo, int maeTipoId, String maeTipoCodigo, String maeTipoValor, String maePlanTipo, int maePlanId, String maePlanCodigo, String maePlanValor, Date fechaInicio, Date fechaFin, BigDecimal valor, String usuarioCrea, String terminalCrea, Date fechaHoraCrea) {
        this.id = id;
        this.maeTipoTipo = maeTipoTipo;
        this.maeTipoId = maeTipoId;
        this.maeTipoCodigo = maeTipoCodigo;
        this.maeTipoValor = maeTipoValor;
        this.maePlanTipo = maePlanTipo;
        this.maePlanId = maePlanId;
        this.maePlanCodigo = maePlanCodigo;
        this.maePlanValor = maePlanValor;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.valor = valor;
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

    public String getMaePlanTipo() {
        return maePlanTipo;
    }

    public void setMaePlanTipo(String maePlanTipo) {
        this.maePlanTipo = maePlanTipo;
    }

    public int getMaePlanId() {
        return maePlanId;
    }

    public void setMaePlanId(int maePlanId) {
        this.maePlanId = maePlanId;
    }

    public String getMaePlanCodigo() {
        return maePlanCodigo;
    }

    public void setMaePlanCodigo(String maePlanCodigo) {
        this.maePlanCodigo = maePlanCodigo;
    }

    public String getMaePlanValor() {
        return maePlanValor;
    }

    public void setMaePlanValor(String maePlanValor) {
        this.maePlanValor = maePlanValor;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(BigDecimal valorPagado) {
        this.valorPagado = valorPagado;
    }

    public BigDecimal getValorPendiente() {
        return valorPendiente;
    }

    public void setValorPendiente(BigDecimal valorPendiente) {
        this.valorPendiente = valorPendiente;
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

    public List<UnComercialComisiones> getUnComercialComisionesList() {
        return unComercialComisionesList;
    }

    public void setUnComercialComisionesList(List<UnComercialComisiones> unComercialComisionesList) {
        this.unComercialComisionesList = unComercialComisionesList;
    }

    public List<UnSuscripcionPersonas> getUnSuscripcionPersonasList() {
        return unSuscripcionPersonasList;
    }

    public void setUnSuscripcionPersonasList(List<UnSuscripcionPersonas> unSuscripcionPersonasList) {
        this.unSuscripcionPersonasList = unSuscripcionPersonasList;
    }

    public GnSedes getGnSedesId() {
        return gnSedesId;
    }

    public void setGnSedesId(GnSedes gnSedesId) {
        this.gnSedesId = gnSedesId;
    }

    public UnComerciales getUnComercialesId() {
        return unComercialesId;
    }

    public void setUnComercialesId(UnComerciales unComercialesId) {
        this.unComercialesId = unComercialesId;
    }

    public UnPersonas getUnPersonasId() {
        return unPersonasId;
    }

    public void setUnPersonasId(UnPersonas unPersonasId) {
        this.unPersonasId = unPersonasId;
    }

    public List<UnSuscripcionPagos> getUnSuscripcionPagosList() {
        return unSuscripcionPagosList;
    }

    public void setUnSuscripcionPagosList(List<UnSuscripcionPagos> unSuscripcionPagosList) {
        this.unSuscripcionPagosList = unSuscripcionPagosList;
    }

    public List<UnSuscripcionCarteras> getUnSuscripcionCarterasList() {
        return unSuscripcionCarterasList;
    }

    public void setUnSuscripcionCarterasList(List<UnSuscripcionCarteras> unSuscripcionCarterasList) {
        this.unSuscripcionCarterasList = unSuscripcionCarterasList;
    }

    public List<UnFidelizaciones> getUnFidelizacionesList() {
        return unFidelizacionesList;
    }

    public void setUnFidelizacionesList(List<UnFidelizaciones> unFidelizacionesList) {
        this.unFidelizacionesList = unFidelizacionesList;
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
        if (!(object instanceof UnSuscripciones)) {
            return false;
        }
        UnSuscripciones other = (UnSuscripciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.systemtech.unimos.ejb.entidades.UnSuscripciones[ id=" + id + " ]";
    }
    
}
