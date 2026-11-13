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
@Table(name = "un_fidelizacion_tecnologias")
@NamedQueries({
    @NamedQuery(name = "UnFidelizacionTecnologias.findAll", query = "SELECT u FROM UnFidelizacionTecnologias u")})
public class UnFidelizacionTecnologias implements Serializable {

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
    @Column(nullable = false)
    @Temporal(TemporalType.TIME)
    private Date hora;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_suscriptor", nullable = false, precision = 18, scale = 2)
    private BigDecimal valorSuscriptor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_convenio", nullable = false, precision = 18, scale = 2)
    private BigDecimal valorConvenio;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int estado;
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
    @Size(max = 128)
    @Column(name = "usuario_cancela", length = 128)
    private String usuarioCancela;
    @Column(name = "fecha_hora_cancela")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCancela;
    @Size(max = 16)
    @Column(name = "terminal_cancela", length = 16)
    private String terminalCancela;
    @Size(max = 128)
    @Column(name = "usuario_presta", length = 128)
    private String usuarioPresta;
    @Column(name = "fecha_hora_presta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraPresta;
    @Size(max = 16)
    @Column(name = "terminal_presta", length = 16)
    private String terminalPresta;
    @JoinColumn(name = "un_agendas_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UnAgendas unAgendasId;
    @JoinColumn(name = "un_contratos_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UnContratos unContratosId;
    @JoinColumn(name = "un_entidades_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UnEntidades unEntidadesId;
    @JoinColumn(name = "un_fidelizaciones_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UnFidelizaciones unFidelizacionesId;
    @JoinColumn(name = "un_tecnologias_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UnTecnologias unTecnologiasId;

    public UnFidelizacionTecnologias() {
    }

    public UnFidelizacionTecnologias(Integer id) {
        this.id = id;
    }

    public UnFidelizacionTecnologias(Integer id, Date fecha, Date hora, BigDecimal valorSuscriptor, BigDecimal valorConvenio, int estado, String usuarioCrea, String terminalCrea, Date fechaHoraCrea) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.valorSuscriptor = valorSuscriptor;
        this.valorConvenio = valorConvenio;
        this.estado = estado;
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

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public BigDecimal getValorSuscriptor() {
        return valorSuscriptor;
    }

    public void setValorSuscriptor(BigDecimal valorSuscriptor) {
        this.valorSuscriptor = valorSuscriptor;
    }

    public BigDecimal getValorConvenio() {
        return valorConvenio;
    }

    public void setValorConvenio(BigDecimal valorConvenio) {
        this.valorConvenio = valorConvenio;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
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

    public String getUsuarioCancela() {
        return usuarioCancela;
    }

    public void setUsuarioCancela(String usuarioCancela) {
        this.usuarioCancela = usuarioCancela;
    }

    public Date getFechaHoraCancela() {
        return fechaHoraCancela;
    }

    public void setFechaHoraCancela(Date fechaHoraCancela) {
        this.fechaHoraCancela = fechaHoraCancela;
    }

    public String getTerminalCancela() {
        return terminalCancela;
    }

    public void setTerminalCancela(String terminalCancela) {
        this.terminalCancela = terminalCancela;
    }

    public String getUsuarioPresta() {
        return usuarioPresta;
    }

    public void setUsuarioPresta(String usuarioPresta) {
        this.usuarioPresta = usuarioPresta;
    }

    public Date getFechaHoraPresta() {
        return fechaHoraPresta;
    }

    public void setFechaHoraPresta(Date fechaHoraPresta) {
        this.fechaHoraPresta = fechaHoraPresta;
    }

    public String getTerminalPresta() {
        return terminalPresta;
    }

    public void setTerminalPresta(String terminalPresta) {
        this.terminalPresta = terminalPresta;
    }

    public UnAgendas getUnAgendasId() {
        return unAgendasId;
    }

    public void setUnAgendasId(UnAgendas unAgendasId) {
        this.unAgendasId = unAgendasId;
    }

    public UnContratos getUnContratosId() {
        return unContratosId;
    }

    public void setUnContratosId(UnContratos unContratosId) {
        this.unContratosId = unContratosId;
    }

    public UnEntidades getUnEntidadesId() {
        return unEntidadesId;
    }

    public void setUnEntidadesId(UnEntidades unEntidadesId) {
        this.unEntidadesId = unEntidadesId;
    }

    public UnFidelizaciones getUnFidelizacionesId() {
        return unFidelizacionesId;
    }

    public void setUnFidelizacionesId(UnFidelizaciones unFidelizacionesId) {
        this.unFidelizacionesId = unFidelizacionesId;
    }

    public UnTecnologias getUnTecnologiasId() {
        return unTecnologiasId;
    }

    public void setUnTecnologiasId(UnTecnologias unTecnologiasId) {
        this.unTecnologiasId = unTecnologiasId;
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
        if (!(object instanceof UnFidelizacionTecnologias)) {
            return false;
        }
        UnFidelizacionTecnologias other = (UnFidelizacionTecnologias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.systemtech.unimos.ejb.entidades.UnFidelizacionTecnologias[ id=" + id + " ]";
    }
    
}
