/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.ejb.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "gn_logs")
@NamedQueries({
    @NamedQuery(name = "GnLogs.findAll", query = "SELECT g FROM GnLogs g")})
public class GnLogs implements Serializable {

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
    private String modulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(nullable = false, length = 32)
    private String opcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(nullable = false, length = 32)
    private String accion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16384)
    @Column(nullable = false, length = 16384)
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "empresa_ejecucion", nullable = false, length = 256)
    private String empresaEjecucion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "empresa_usuario", nullable = false, length = 256)
    private String empresaUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "usuario_usuario", nullable = false, length = 16)
    private String usuarioUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "usuario_nombre", nullable = false, length = 128)
    private String usuarioNombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_hora", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Size(max = 64)
    @Column(name = "ip_publica", length = 64)
    private String ipPublica;
    @Size(max = 64)
    @Column(name = "ip_privada", length = 64)
    private String ipPrivada;

    public GnLogs() {
    }

    public GnLogs(Integer id) {
        this.id = id;
    }

    public GnLogs(Integer id, String modulo, String opcion, String accion, String descripcion, String empresaEjecucion, String empresaUsuario, String usuarioUsuario, String usuarioNombre, Date fechaHora) {
        this.id = id;
        this.modulo = modulo;
        this.opcion = opcion;
        this.accion = accion;
        this.descripcion = descripcion;
        this.empresaEjecucion = empresaEjecucion;
        this.empresaUsuario = empresaUsuario;
        this.usuarioUsuario = usuarioUsuario;
        this.usuarioNombre = usuarioNombre;
        this.fechaHora = fechaHora;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEmpresaEjecucion() {
        return empresaEjecucion;
    }

    public void setEmpresaEjecucion(String empresaEjecucion) {
        this.empresaEjecucion = empresaEjecucion;
    }

    public String getEmpresaUsuario() {
        return empresaUsuario;
    }

    public void setEmpresaUsuario(String empresaUsuario) {
        this.empresaUsuario = empresaUsuario;
    }

    public String getUsuarioUsuario() {
        return usuarioUsuario;
    }

    public void setUsuarioUsuario(String usuarioUsuario) {
        this.usuarioUsuario = usuarioUsuario;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getIpPublica() {
        return ipPublica;
    }

    public void setIpPublica(String ipPublica) {
        this.ipPublica = ipPublica;
    }

    public String getIpPrivada() {
        return ipPrivada;
    }

    public void setIpPrivada(String ipPrivada) {
        this.ipPrivada = ipPrivada;
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
        if (!(object instanceof GnLogs)) {
            return false;
        }
        GnLogs other = (GnLogs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.systemtech.unimos.ejb.entidades.GnLogs[ id=" + id + " ]";
    }
    
}
