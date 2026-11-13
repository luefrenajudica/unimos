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
@Table(name = "un_contratos", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"contrato"})})
@NamedQueries({
    @NamedQuery(name = "UnContratos.findAll", query = "SELECT u FROM UnContratos u")})
public class UnContratos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(nullable = false, length = 16)
    private String contrato;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicial", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicial;
    @Column(name = "fecha_final")
    @Temporal(TemporalType.DATE)
    private Date fechaFinal;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean activo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "usuario_crea", nullable = false, length = 45)
    private String usuarioCrea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "terminal_crea", nullable = false, length = 45)
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
    @OneToMany(mappedBy = "unContratosId", fetch = FetchType.LAZY)
    private List<UnFidelizacionTecnologias> unFidelizacionTecnologiasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unContratosId", fetch = FetchType.LAZY)
    private List<UnAgendas> unAgendasList;
    @JoinColumn(name = "un_entidades_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UnEntidades unEntidadesId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unContratosId", fetch = FetchType.LAZY)
    private List<UnContratoDetalles> unContratoDetallesList;

    public UnContratos() {
    }

    public UnContratos(Integer id) {
        this.id = id;
    }

    public UnContratos(Integer id, String contrato, Date fechaInicial, boolean activo, String usuarioCrea, String terminalCrea, Date fechaHoraCrea) {
        this.id = id;
        this.contrato = contrato;
        this.fechaInicial = fechaInicial;
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

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
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

    public UnEntidades getUnEntidadesId() {
        return unEntidadesId;
    }

    public void setUnEntidadesId(UnEntidades unEntidadesId) {
        this.unEntidadesId = unEntidadesId;
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
        if (!(object instanceof UnContratos)) {
            return false;
        }
        UnContratos other = (UnContratos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.systemtech.unimos.ejb.entidades.UnContratos[ id=" + id + " ]";
    }
    
}
