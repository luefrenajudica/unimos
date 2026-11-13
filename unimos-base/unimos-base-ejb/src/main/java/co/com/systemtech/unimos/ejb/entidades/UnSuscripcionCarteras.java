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
@Table(name = "un_suscripcion_carteras")
@NamedQueries({
    @NamedQuery(name = "UnSuscripcionCarteras.findAll", query = "SELECT u FROM UnSuscripcionCarteras u")})
public class UnSuscripcionCarteras implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_generacion", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaGeneracion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_maxima", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaMaxima;
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_a_pagar", nullable = false, precision = 18, scale = 2)
    private BigDecimal valorAPagar;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unSuscripcionCarterasId", fetch = FetchType.LAZY)
    private List<UnComercialComisiones> unComercialComisionesList;
    @JoinColumn(name = "un_suscripciones_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UnSuscripciones unSuscripcionesId;

    public UnSuscripcionCarteras() {
    }

    public UnSuscripcionCarteras(Integer id) {
        this.id = id;
    }

    public UnSuscripcionCarteras(Integer id, int estado, Date fechaGeneracion, Date fechaMaxima, BigDecimal valorAPagar, String usuarioCrea, String terminalCrea, Date fechaHoraCrea) {
        this.id = id;
        this.estado = estado;
        this.fechaGeneracion = fechaGeneracion;
        this.fechaMaxima = fechaMaxima;
        this.valorAPagar = valorAPagar;
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public Date getFechaMaxima() {
        return fechaMaxima;
    }

    public void setFechaMaxima(Date fechaMaxima) {
        this.fechaMaxima = fechaMaxima;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getValorAPagar() {
        return valorAPagar;
    }

    public void setValorAPagar(BigDecimal valorAPagar) {
        this.valorAPagar = valorAPagar;
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
        if (!(object instanceof UnSuscripcionCarteras)) {
            return false;
        }
        UnSuscripcionCarteras other = (UnSuscripcionCarteras) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.systemtech.unimos.ejb.entidades.UnSuscripcionCarteras[ id=" + id + " ]";
    }
    
}
