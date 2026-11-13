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
@Table(name = "gn_empresas", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nit"}),
    @UniqueConstraint(columnNames = {"razon_social"})})
@NamedQueries({
    @NamedQuery(name = "GnEmpresas.findAll", query = "SELECT g FROM GnEmpresas g")})
public class GnEmpresas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "razon_social", nullable = false, length = 128)
    private String razonSocial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(nullable = false, length = 16)
    private String nit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "nombre_comercial", nullable = false, length = 64)
    private String nombreComercial;
    @Size(max = 512)
    @Column(length = 512)
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean administradora;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean activa;
    @Size(max = 13)
    @Column(name = "codigo_habilitacion", length = 13)
    private String codigoHabilitacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contrasena_longitud_minimo", nullable = false)
    private int contrasenaLongitudMinimo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contrasena_longitud_maximo", nullable = false)
    private int contrasenaLongitudMaximo;
    @Column(name = "contrasena_dias_vencimiento")
    private Integer contrasenaDiasVencimiento;
    @Column(name = "contrasena_dias_notificacion")
    private Integer contrasenaDiasNotificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contrasena_intentos", nullable = false)
    private int contrasenaIntentos;
    @Size(max = 8)
    @Column(name = "receptor_usuario", length = 8)
    private String receptorUsuario;
    @Size(max = 8)
    @Column(name = "receptor_contrasena", length = 8)
    private String receptorContrasena;
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
    @JoinColumn(name = "gn_ubicaciones_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GnUbicaciones gnUbicacionesId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnEmpresasId", fetch = FetchType.LAZY)
    private List<GnUsuarios> gnUsuariosList;

    public GnEmpresas() {
    }

    public GnEmpresas(Integer id) {
        this.id = id;
    }

    public GnEmpresas(Integer id, String razonSocial, String nit, String nombreComercial, boolean administradora, boolean activa, int contrasenaLongitudMinimo, int contrasenaLongitudMaximo, int contrasenaIntentos, String usuarioCrea, String terminalCrea, Date fechaHoraCrea) {
        this.id = id;
        this.razonSocial = razonSocial;
        this.nit = nit;
        this.nombreComercial = nombreComercial;
        this.administradora = administradora;
        this.activa = activa;
        this.contrasenaLongitudMinimo = contrasenaLongitudMinimo;
        this.contrasenaLongitudMaximo = contrasenaLongitudMaximo;
        this.contrasenaIntentos = contrasenaIntentos;
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

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getAdministradora() {
        return administradora;
    }

    public void setAdministradora(boolean administradora) {
        this.administradora = administradora;
    }

    public boolean getActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public String getCodigoHabilitacion() {
        return codigoHabilitacion;
    }

    public void setCodigoHabilitacion(String codigoHabilitacion) {
        this.codigoHabilitacion = codigoHabilitacion;
    }

    public int getContrasenaLongitudMinimo() {
        return contrasenaLongitudMinimo;
    }

    public void setContrasenaLongitudMinimo(int contrasenaLongitudMinimo) {
        this.contrasenaLongitudMinimo = contrasenaLongitudMinimo;
    }

    public int getContrasenaLongitudMaximo() {
        return contrasenaLongitudMaximo;
    }

    public void setContrasenaLongitudMaximo(int contrasenaLongitudMaximo) {
        this.contrasenaLongitudMaximo = contrasenaLongitudMaximo;
    }

    public Integer getContrasenaDiasVencimiento() {
        return contrasenaDiasVencimiento;
    }

    public void setContrasenaDiasVencimiento(Integer contrasenaDiasVencimiento) {
        this.contrasenaDiasVencimiento = contrasenaDiasVencimiento;
    }

    public Integer getContrasenaDiasNotificacion() {
        return contrasenaDiasNotificacion;
    }

    public void setContrasenaDiasNotificacion(Integer contrasenaDiasNotificacion) {
        this.contrasenaDiasNotificacion = contrasenaDiasNotificacion;
    }

    public int getContrasenaIntentos() {
        return contrasenaIntentos;
    }

    public void setContrasenaIntentos(int contrasenaIntentos) {
        this.contrasenaIntentos = contrasenaIntentos;
    }

    public String getReceptorUsuario() {
        return receptorUsuario;
    }

    public void setReceptorUsuario(String receptorUsuario) {
        this.receptorUsuario = receptorUsuario;
    }

    public String getReceptorContrasena() {
        return receptorContrasena;
    }

    public void setReceptorContrasena(String receptorContrasena) {
        this.receptorContrasena = receptorContrasena;
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

    public GnUbicaciones getGnUbicacionesId() {
        return gnUbicacionesId;
    }

    public void setGnUbicacionesId(GnUbicaciones gnUbicacionesId) {
        this.gnUbicacionesId = gnUbicacionesId;
    }

    public List<GnUsuarios> getGnUsuariosList() {
        return gnUsuariosList;
    }

    public void setGnUsuariosList(List<GnUsuarios> gnUsuariosList) {
        this.gnUsuariosList = gnUsuariosList;
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
        if (!(object instanceof GnEmpresas)) {
            return false;
        }
        GnEmpresas other = (GnEmpresas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.systemtech.unimos.ejb.entidades.GnEmpresas[ id=" + id + " ]";
    }
    
}
