/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.ejb.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "un_suscripcion_pagos")
@NamedQueries({
    @NamedQuery(name = "UnSuscripcionPagos.findAll", query = "SELECT u FROM UnSuscripcionPagos u")})
public class UnSuscripcionPagos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "mae_forma_pago_tipo", nullable = false, length = 4)
    private String maeFormaPagoTipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mae_forma_pago_id", nullable = false)
    private int maeFormaPagoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "mae_forma_pago_codigo", nullable = false, length = 16)
    private String maeFormaPagoCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "mae_forma_pago_valor", nullable = false, length = 512)
    private String maeFormaPagoValor;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valor;
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
    @JoinColumn(name = "un_suscripciones_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UnSuscripciones unSuscripcionesId;

    public UnSuscripcionPagos() {
    }

    public UnSuscripcionPagos(Integer id) {
        this.id = id;
    }

    public UnSuscripcionPagos(Integer id, Date fecha, String maeFormaPagoTipo, int maeFormaPagoId, String maeFormaPagoCodigo, String maeFormaPagoValor, BigDecimal valor, String usuarioCrea, String terminalCrea, Date fechaHoraCrea) {
        this.id = id;
        this.fecha = fecha;
        this.maeFormaPagoTipo = maeFormaPagoTipo;
        this.maeFormaPagoId = maeFormaPagoId;
        this.maeFormaPagoCodigo = maeFormaPagoCodigo;
        this.maeFormaPagoValor = maeFormaPagoValor;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMaeFormaPagoTipo() {
        return maeFormaPagoTipo;
    }

    public void setMaeFormaPagoTipo(String maeFormaPagoTipo) {
        this.maeFormaPagoTipo = maeFormaPagoTipo;
    }

    public int getMaeFormaPagoId() {
        return maeFormaPagoId;
    }

    public void setMaeFormaPagoId(int maeFormaPagoId) {
        this.maeFormaPagoId = maeFormaPagoId;
    }

    public String getMaeFormaPagoCodigo() {
        return maeFormaPagoCodigo;
    }

    public void setMaeFormaPagoCodigo(String maeFormaPagoCodigo) {
        this.maeFormaPagoCodigo = maeFormaPagoCodigo;
    }

    public String getMaeFormaPagoValor() {
        return maeFormaPagoValor;
    }

    public void setMaeFormaPagoValor(String maeFormaPagoValor) {
        this.maeFormaPagoValor = maeFormaPagoValor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
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

    public UnSuscripciones getUnSuscripcionesId() {
        return unSuscripcionesId;
    }

    public void setUnSuscripcionesId(UnSuscripciones unSuscripcionesId) {
        this.unSuscripcionesId = unSuscripcionesId;
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
        if (!(object instanceof UnSuscripcionPagos)) {
            return false;
        }
        UnSuscripcionPagos other = (UnSuscripcionPagos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.systemtech.unimos.ejb.entidades.UnSuscripcionPagos[ id=" + id + " ]";
    }
    
}
