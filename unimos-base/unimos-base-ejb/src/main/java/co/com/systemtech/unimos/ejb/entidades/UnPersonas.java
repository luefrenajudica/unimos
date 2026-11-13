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
@Table(name = "un_personas", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"mae_tipo_documento_codigo", "documento_numero"})})
@NamedQueries({
    @NamedQuery(name = "UnPersonas.findAll", query = "SELECT u FROM UnPersonas u")})
public class UnPersonas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "mae_tipo_documento_tipo", nullable = false, length = 4)
    private String maeTipoDocumentoTipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mae_tipo_documento_id", nullable = false)
    private int maeTipoDocumentoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "mae_tipo_documento_codigo", nullable = false, length = 16)
    private String maeTipoDocumentoCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "mae_tipo_documento_valor", nullable = false, length = 512)
    private String maeTipoDocumentoValor;
    @Size(max = 16)
    @Column(name = "documento_numero", length = 16)
    private String documentoNumero;
    @Column(name = "fecha_documento")
    @Temporal(TemporalType.DATE)
    private Date fechaDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "primer_nombre", nullable = false, length = 64)
    private String primerNombre;
    @Size(max = 64)
    @Column(name = "segundo_nombre", length = 64)
    private String segundoNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "primer_apellido", nullable = false, length = 64)
    private String primerApellido;
    @Size(max = 64)
    @Column(name = "segundo_apellido", length = 64)
    private String segundoApellido;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "mae_sexo_tipo", nullable = false, length = 4)
    private String maeSexoTipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mae_sexo_id", nullable = false)
    private int maeSexoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "mae_sexo_codigo", nullable = false, length = 16)
    private String maeSexoCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "mae_sexo_valor", nullable = false, length = 512)
    private String maeSexoValor;
    @Size(max = 45)
    @Column(name = "mae_estado_civil_tipo", length = 45)
    private String maeEstadoCivilTipo;
    @Column(name = "mae_estado_civil_id")
    private Integer maeEstadoCivilId;
    @Size(max = 16)
    @Column(name = "mae_estado_civil_codigo", length = 16)
    private String maeEstadoCivilCodigo;
    @Size(max = 512)
    @Column(name = "mae_estado_civil_valor", length = 512)
    private String maeEstadoCivilValor;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
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
    @JoinColumn(name = "gn_nacimiento_ubicaciones_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private GnUbicaciones gnNacimientoUbicacionesId;
    @JoinColumn(name = "gn_nacionalidad_ubicaciones_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private GnUbicaciones gnNacionalidadUbicacionesId;
    @JoinColumn(name = "gn_direccion_ubicaciones_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GnUbicaciones gnDireccionUbicacionesId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unPersonasId", fetch = FetchType.LAZY)
    private List<UnPersonaContactos> unPersonaContactosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unPersonasId", fetch = FetchType.LAZY)
    private List<UnSuscripcionPersonas> unSuscripcionPersonasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unPersonasId", fetch = FetchType.LAZY)
    private List<UnSuscripciones> unSuscripcionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unPersonasId", fetch = FetchType.LAZY)
    private List<UnFidelizaciones> unFidelizacionesList;

    public UnPersonas() {
    }

    public UnPersonas(Integer id) {
        this.id = id;
    }

    public UnPersonas(Integer id, String maeTipoDocumentoTipo, int maeTipoDocumentoId, String maeTipoDocumentoCodigo, String maeTipoDocumentoValor, String primerNombre, String primerApellido, String maeSexoTipo, int maeSexoId, String maeSexoCodigo, String maeSexoValor, String direccion, String usuarioCrea, String terminalCrea, Date fechaHoraCrea) {
        this.id = id;
        this.maeTipoDocumentoTipo = maeTipoDocumentoTipo;
        this.maeTipoDocumentoId = maeTipoDocumentoId;
        this.maeTipoDocumentoCodigo = maeTipoDocumentoCodigo;
        this.maeTipoDocumentoValor = maeTipoDocumentoValor;
        this.primerNombre = primerNombre;
        this.primerApellido = primerApellido;
        this.maeSexoTipo = maeSexoTipo;
        this.maeSexoId = maeSexoId;
        this.maeSexoCodigo = maeSexoCodigo;
        this.maeSexoValor = maeSexoValor;
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

    public String getMaeTipoDocumentoTipo() {
        return maeTipoDocumentoTipo;
    }

    public void setMaeTipoDocumentoTipo(String maeTipoDocumentoTipo) {
        this.maeTipoDocumentoTipo = maeTipoDocumentoTipo;
    }

    public int getMaeTipoDocumentoId() {
        return maeTipoDocumentoId;
    }

    public void setMaeTipoDocumentoId(int maeTipoDocumentoId) {
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

    public String getDocumentoNumero() {
        return documentoNumero;
    }

    public void setDocumentoNumero(String documentoNumero) {
        this.documentoNumero = documentoNumero;
    }

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getMaeSexoTipo() {
        return maeSexoTipo;
    }

    public void setMaeSexoTipo(String maeSexoTipo) {
        this.maeSexoTipo = maeSexoTipo;
    }

    public int getMaeSexoId() {
        return maeSexoId;
    }

    public void setMaeSexoId(int maeSexoId) {
        this.maeSexoId = maeSexoId;
    }

    public String getMaeSexoCodigo() {
        return maeSexoCodigo;
    }

    public void setMaeSexoCodigo(String maeSexoCodigo) {
        this.maeSexoCodigo = maeSexoCodigo;
    }

    public String getMaeSexoValor() {
        return maeSexoValor;
    }

    public void setMaeSexoValor(String maeSexoValor) {
        this.maeSexoValor = maeSexoValor;
    }

    public String getMaeEstadoCivilTipo() {
        return maeEstadoCivilTipo;
    }

    public void setMaeEstadoCivilTipo(String maeEstadoCivilTipo) {
        this.maeEstadoCivilTipo = maeEstadoCivilTipo;
    }

    public Integer getMaeEstadoCivilId() {
        return maeEstadoCivilId;
    }

    public void setMaeEstadoCivilId(Integer maeEstadoCivilId) {
        this.maeEstadoCivilId = maeEstadoCivilId;
    }

    public String getMaeEstadoCivilCodigo() {
        return maeEstadoCivilCodigo;
    }

    public void setMaeEstadoCivilCodigo(String maeEstadoCivilCodigo) {
        this.maeEstadoCivilCodigo = maeEstadoCivilCodigo;
    }

    public String getMaeEstadoCivilValor() {
        return maeEstadoCivilValor;
    }

    public void setMaeEstadoCivilValor(String maeEstadoCivilValor) {
        this.maeEstadoCivilValor = maeEstadoCivilValor;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public GnUbicaciones getGnNacimientoUbicacionesId() {
        return gnNacimientoUbicacionesId;
    }

    public void setGnNacimientoUbicacionesId(GnUbicaciones gnNacimientoUbicacionesId) {
        this.gnNacimientoUbicacionesId = gnNacimientoUbicacionesId;
    }

    public GnUbicaciones getGnNacionalidadUbicacionesId() {
        return gnNacionalidadUbicacionesId;
    }

    public void setGnNacionalidadUbicacionesId(GnUbicaciones gnNacionalidadUbicacionesId) {
        this.gnNacionalidadUbicacionesId = gnNacionalidadUbicacionesId;
    }

    public GnUbicaciones getGnDireccionUbicacionesId() {
        return gnDireccionUbicacionesId;
    }

    public void setGnDireccionUbicacionesId(GnUbicaciones gnDireccionUbicacionesId) {
        this.gnDireccionUbicacionesId = gnDireccionUbicacionesId;
    }

    public List<UnPersonaContactos> getUnPersonaContactosList() {
        return unPersonaContactosList;
    }

    public void setUnPersonaContactosList(List<UnPersonaContactos> unPersonaContactosList) {
        this.unPersonaContactosList = unPersonaContactosList;
    }

    public List<UnSuscripcionPersonas> getUnSuscripcionPersonasList() {
        return unSuscripcionPersonasList;
    }

    public void setUnSuscripcionPersonasList(List<UnSuscripcionPersonas> unSuscripcionPersonasList) {
        this.unSuscripcionPersonasList = unSuscripcionPersonasList;
    }

    public List<UnSuscripciones> getUnSuscripcionesList() {
        return unSuscripcionesList;
    }

    public void setUnSuscripcionesList(List<UnSuscripciones> unSuscripcionesList) {
        this.unSuscripcionesList = unSuscripcionesList;
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
        if (!(object instanceof UnPersonas)) {
            return false;
        }
        UnPersonas other = (UnPersonas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.systemtech.unimos.ejb.entidades.UnPersonas[ id=" + id + " ]";
    }
    
}
