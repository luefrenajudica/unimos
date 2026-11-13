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
@Table(name = "gn_usuarios", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"usuario"})})
@NamedQueries({
    @NamedQuery(name = "GnUsuarios.findAll", query = "SELECT g FROM GnUsuarios g")})
public class GnUsuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Column(name = "mae_tipo_documento_id")
    private Integer maeTipoDocumentoId;
    @Size(max = 16)
    @Column(name = "mae_tipo_documento_codigo", length = 16)
    private String maeTipoDocumentoCodigo;
    @Size(max = 128)
    @Column(name = "mae_tipo_documento_valor", length = 128)
    private String maeTipoDocumentoValor;
    @Size(max = 16)
    @Column(name = "numero_documento", length = 16)
    private String numeroDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(nullable = false, length = 128)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(nullable = false, length = 12)
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "correo_electronico", nullable = false, length = 128)
    private String correoElectronico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(nullable = false, length = 512)
    private String contrasena;
    @Size(max = 16)
    @Column(length = 16)
    private String telefono;
    @Size(max = 16)
    @Column(length = 16)
    private String celular;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean activo;
    @Column(name = "fecha_ultimo_ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimoIngreso;
    private Integer intentos;
    private Boolean bloqueado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
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
    @OneToMany(mappedBy = "gnUsuariosId", fetch = FetchType.LAZY)
    private List<UnComerciales> unComercialesList;
    @JoinColumn(name = "gn_empresas_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GnEmpresas gnEmpresasId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnUsuariosId", fetch = FetchType.LAZY)
    private List<GnRolesUsuario> gnRolesUsuarioList;

    public GnUsuarios() {
    }

    public GnUsuarios(Integer id) {
        this.id = id;
    }

    public GnUsuarios(Integer id, String nombre, String usuario, String correoElectronico, String contrasena, boolean activo, Date fechaInicio, String usuarioCrea, String terminalCrea, Date fechaHoraCrea) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.activo = activo;
        this.fechaInicio = fechaInicio;
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

    public Integer getMaeTipoDocumentoId() {
        return maeTipoDocumentoId;
    }

    public void setMaeTipoDocumentoId(Integer maeTipoDocumentoId) {
        this.maeTipoDocumentoId = maeTipoDocumentoId;
    }

    public String getMaeTipoDocumentoCodigo() {
        return maeTipoDocumentoCodigo;
    }

    public void setMaeTipoDocumentoCodigo(String maeTipoDocumentoCodigo) {
        this.maeTipoDocumentoCodigo = maeTipoDocumentoCodigo;
    }

    public String getMaeTipoDocumentoValor() {
        return maeTipoDocumentoValor;
    }

    public void setMaeTipoDocumentoValor(String maeTipoDocumentoValor) {
        this.maeTipoDocumentoValor = maeTipoDocumentoValor;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Date getFechaUltimoIngreso() {
        return fechaUltimoIngreso;
    }

    public void setFechaUltimoIngreso(Date fechaUltimoIngreso) {
        this.fechaUltimoIngreso = fechaUltimoIngreso;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }

    public Boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
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

    public List<UnComerciales> getUnComercialesList() {
        return unComercialesList;
    }

    public void setUnComercialesList(List<UnComerciales> unComercialesList) {
        this.unComercialesList = unComercialesList;
    }

    public GnEmpresas getGnEmpresasId() {
        return gnEmpresasId;
    }

    public void setGnEmpresasId(GnEmpresas gnEmpresasId) {
        this.gnEmpresasId = gnEmpresasId;
    }

    public List<GnRolesUsuario> getGnRolesUsuarioList() {
        return gnRolesUsuarioList;
    }

    public void setGnRolesUsuarioList(List<GnRolesUsuario> gnRolesUsuarioList) {
        this.gnRolesUsuarioList = gnRolesUsuarioList;
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
        if (!(object instanceof GnUsuarios)) {
            return false;
        }
        GnUsuarios other = (GnUsuarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.systemtech.unimos.ejb.entidades.GnUsuarios[ id=" + id + " ]";
    }
    
}
