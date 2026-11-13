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
@Table(name = "gn_ubicaciones", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"tipo", "nombre", "gn_ubicaciones_id"})})
@NamedQueries({
    @NamedQuery(name = "GnUbicaciones.findAll", query = "SELECT g FROM GnUbicaciones g")})
public class GnUbicaciones implements Serializable {

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
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int tipo;
    private Boolean cobertura;
    @Size(max = 512)
    @Column(length = 512)
    private String descripcion;
    @Size(max = 5)
    @Column(length = 5)
    private String prefijo;
    @Size(max = 5)
    @Column(length = 5)
    private String indicativo;
    @Size(max = 8)
    @Column(name = "codigo_postal", length = 8)
    private String codigoPostal;
    @Size(max = 64)
    @Column(name = "gps_coordenadas", length = 64)
    private String gpsCoordenadas;
    @Column(name = "gps_profundidad")
    private Integer gpsProfundidad;
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
    @Size(max = 15)
    @Column(name = "terminal_modifica", length = 15)
    private String terminalModifica;
    @Column(name = "fecha_hora_modifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraModifica;
    @OneToMany(mappedBy = "gnNacimientoUbicacionesId", fetch = FetchType.LAZY)
    private List<UnComerciales> unComercialesList;
    @OneToMany(mappedBy = "gnNacionalidadUbicacionesId", fetch = FetchType.LAZY)
    private List<UnComerciales> unComercialesList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnDireccionUbicacionesId", fetch = FetchType.LAZY)
    private List<UnComerciales> unComercialesList2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnUbicacionesId", fetch = FetchType.LAZY)
    private List<GnEmpresas> gnEmpresasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnUbicacionesId", fetch = FetchType.LAZY)
    private List<GnSedes> gnSedesList;
    @OneToMany(mappedBy = "gnNacimientoUbicacionesId", fetch = FetchType.LAZY)
    private List<UnPersonas> unPersonasList;
    @OneToMany(mappedBy = "gnNacionalidadUbicacionesId", fetch = FetchType.LAZY)
    private List<UnPersonas> unPersonasList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnDireccionUbicacionesId", fetch = FetchType.LAZY)
    private List<UnPersonas> unPersonasList2;
    @OneToMany(mappedBy = "gnUbicacionesId", fetch = FetchType.LAZY)
    private List<GnUbicaciones> gnUbicacionesList;
    @JoinColumn(name = "gn_ubicaciones_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private GnUbicaciones gnUbicacionesId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnUbicacionesId", fetch = FetchType.LAZY)
    private List<UnEntidades> unEntidadesList;

    public GnUbicaciones() {
    }

    public GnUbicaciones(Integer id) {
        this.id = id;
    }

    public GnUbicaciones(Integer id, String nombre, int tipo, String usuarioCrea, String terminalCrea, Date fechaHoraCrea) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Boolean getCobertura() {
        return cobertura;
    }

    public void setCobertura(Boolean cobertura) {
        this.cobertura = cobertura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getIndicativo() {
        return indicativo;
    }

    public void setIndicativo(String indicativo) {
        this.indicativo = indicativo;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getGpsCoordenadas() {
        return gpsCoordenadas;
    }

    public void setGpsCoordenadas(String gpsCoordenadas) {
        this.gpsCoordenadas = gpsCoordenadas;
    }

    public Integer getGpsProfundidad() {
        return gpsProfundidad;
    }

    public void setGpsProfundidad(Integer gpsProfundidad) {
        this.gpsProfundidad = gpsProfundidad;
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

    public List<UnComerciales> getUnComercialesList1() {
        return unComercialesList1;
    }

    public void setUnComercialesList1(List<UnComerciales> unComercialesList1) {
        this.unComercialesList1 = unComercialesList1;
    }

    public List<UnComerciales> getUnComercialesList2() {
        return unComercialesList2;
    }

    public void setUnComercialesList2(List<UnComerciales> unComercialesList2) {
        this.unComercialesList2 = unComercialesList2;
    }

    public List<GnEmpresas> getGnEmpresasList() {
        return gnEmpresasList;
    }

    public void setGnEmpresasList(List<GnEmpresas> gnEmpresasList) {
        this.gnEmpresasList = gnEmpresasList;
    }

    public List<GnSedes> getGnSedesList() {
        return gnSedesList;
    }

    public void setGnSedesList(List<GnSedes> gnSedesList) {
        this.gnSedesList = gnSedesList;
    }

    public List<UnPersonas> getUnPersonasList() {
        return unPersonasList;
    }

    public void setUnPersonasList(List<UnPersonas> unPersonasList) {
        this.unPersonasList = unPersonasList;
    }

    public List<UnPersonas> getUnPersonasList1() {
        return unPersonasList1;
    }

    public void setUnPersonasList1(List<UnPersonas> unPersonasList1) {
        this.unPersonasList1 = unPersonasList1;
    }

    public List<UnPersonas> getUnPersonasList2() {
        return unPersonasList2;
    }

    public void setUnPersonasList2(List<UnPersonas> unPersonasList2) {
        this.unPersonasList2 = unPersonasList2;
    }

    public List<GnUbicaciones> getGnUbicacionesList() {
        return gnUbicacionesList;
    }

    public void setGnUbicacionesList(List<GnUbicaciones> gnUbicacionesList) {
        this.gnUbicacionesList = gnUbicacionesList;
    }

    public GnUbicaciones getGnUbicacionesId() {
        return gnUbicacionesId;
    }

    public void setGnUbicacionesId(GnUbicaciones gnUbicacionesId) {
        this.gnUbicacionesId = gnUbicacionesId;
    }

    public List<UnEntidades> getUnEntidadesList() {
        return unEntidadesList;
    }

    public void setUnEntidadesList(List<UnEntidades> unEntidadesList) {
        this.unEntidadesList = unEntidadesList;
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
        if (!(object instanceof GnUbicaciones)) {
            return false;
        }
        GnUbicaciones other = (GnUbicaciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.systemtech.unimos.ejb.entidades.GnUbicaciones[ id=" + id + " ]";
    }
    
}
