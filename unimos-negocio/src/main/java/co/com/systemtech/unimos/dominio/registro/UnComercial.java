/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.dominio.registro;

import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.dominio.administracion.Usuario;
import co.com.systemtech.unimos.dominio.generico.Auditoria;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class UnComercial extends Auditoria {

    private Integer id;
    private String maeTipoDocumentoTipo;
    private int maeTipoDocumentoId;
    private String maeTipoDocumentoCodigo;
    private String maeTipoDocumentoValor;
    private String documentoNumero;
    private Date fechaDocumento;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private Date fechaNacimiento;
    private String direccion;
    private boolean activo;
    private boolean comisiona;
    private Sede sede;
    private Ubicacion nacimientoUbicacion;
    private Ubicacion nacionalidadUbicacion;
    private Ubicacion direccionUbicacion;
    private Usuario usuario;
//    private boolean usuarioAsignado;

    public UnComercial() {
    }

    public UnComercial(Integer id) {
        this.id = id;
    }

    public UnComercial(Integer id, String maeTipoDocumentoValor, String documentoNumero, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido) {
        this.id = id;
        this.maeTipoDocumentoValor = maeTipoDocumentoValor;
        this.documentoNumero = documentoNumero;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
    }

    public UnComercial(Integer id, String maeTipoDocumentoTipo, int maeTipoDocumentoId, String maeTipoDocumentoCodigo, String maeTipoDocumentoValor, String documentoNumero, String primerNombre, String primerApellido, Date fechaNacimiento, String direccion, boolean comisiona, boolean activo) {
        this.id = id;
        this.maeTipoDocumentoTipo = maeTipoDocumentoTipo;
        this.maeTipoDocumentoId = maeTipoDocumentoId;
        this.maeTipoDocumentoCodigo = maeTipoDocumentoCodigo;
        this.maeTipoDocumentoValor = maeTipoDocumentoValor;
        this.documentoNumero = documentoNumero;
        this.primerNombre = primerNombre;
        this.primerApellido = primerApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.comisiona = comisiona;
        this.activo = activo;
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
        this.primerNombre = (primerNombre != null) ? primerNombre.trim().toUpperCase() : null;
//        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = (segundoNombre != null) ? segundoNombre.trim().toUpperCase() : null;
//        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = (primerApellido != null) ? primerApellido.trim().toUpperCase() : null;
//        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = (segundoApellido != null) ? segundoApellido.trim().toUpperCase() : null;
//        this.segundoApellido = segundoApellido;
    }
    
    public String getNombresApellidos() {
        String str = primerNombre;
        if (segundoNombre != null && !segundoNombre.isEmpty()){
            str += " " + segundoNombre;
        }
        str += " " + primerApellido;
        if (segundoApellido != null && !segundoApellido.isEmpty()){
            str += " " + segundoApellido;
        }
        return str;
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

    public boolean isActivo() {
        return activo;
    }

    public String getActivoStr() {
        return (activo) ? "SI" : "NO";
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isComisiona() {
        return comisiona;
    }

    public String getComisionaStr() {
        return (comisiona) ? "SI" : "NO";
    }

    public void setComisiona(boolean comisiona) {
        this.comisiona = comisiona;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Ubicacion getNacimientoUbicacion() {
        return nacimientoUbicacion;
    }

    public void setNacimientoUbicacion(Ubicacion nacimientoUbicacion) {
        this.nacimientoUbicacion = nacimientoUbicacion;
    }

    public Ubicacion getNacionalidadUbicacion() {
        return nacionalidadUbicacion;
    }

    public void setNacionalidadUbicacion(Ubicacion nacionalidadUbicacion) {
        this.nacionalidadUbicacion = nacionalidadUbicacion;
    }

    public Ubicacion getDireccionUbicacion() {
        return direccionUbicacion;
    }

    public void setDireccionUbicacion(Ubicacion direccionUbicacion) {
        this.direccionUbicacion = direccionUbicacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

//    public boolean isUsuarioAsignado() {
//        return usuarioAsignado;
//    }
//
//    public void setUsuarioAsignado(boolean usuarioAsignado) {
//        this.usuarioAsignado = usuarioAsignado;
//    }

    @Override
    public String toString() {
        return "UnComercial{" + "id=" + id + ", maeTipoDocumentoTipo=" + maeTipoDocumentoTipo + ", maeTipoDocumentoId=" + maeTipoDocumentoId + ", maeTipoDocumentoCodigo=" + maeTipoDocumentoCodigo + ", maeTipoDocumentoValor=" + maeTipoDocumentoValor + ", documentoNumero=" + documentoNumero + ", fechaDocumento=" + fechaDocumento + ", primerNombre=" + primerNombre + ", segundoNombre=" + segundoNombre + ", primerApellido=" + primerApellido + ", segundoApellido=" + segundoApellido + ", fechaNacimiento=" + fechaNacimiento + ", direccion=" + direccion + ", comisiona=" + comisiona + ", activo=" + activo + ", sede=" + sede + ", nacimientoUbicacion=" + nacimientoUbicacion + ", nacionalidadUbicacion=" + nacionalidadUbicacion + ", direccionUbicacion=" + direccionUbicacion + ", usuario=" + usuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UnComercial other = (UnComercial) obj;
        return Objects.equals(this.id, other.id);
    }

}
