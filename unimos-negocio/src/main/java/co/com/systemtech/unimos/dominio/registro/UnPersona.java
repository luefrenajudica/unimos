/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.dominio.registro;

import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.dominio.contrato.UnSuscripcion;
import co.com.systemtech.unimos.dominio.generico.Auditoria;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class UnPersona extends Auditoria {
    
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
    private String maeSexoTipo;
    private int maeSexoId;
    private String maeSexoCodigo;
    private String maeSexoValor;
    private String maeEstadoCivilTipo;
    private Integer maeEstadoCivilId;
    private String maeEstadoCivilCodigo;
    private String maeEstadoCivilValor;    
    private Date fechaNacimiento;
    private String direccion;
    private Ubicacion gnNacimientoUbicacion;
    private Ubicacion gnNacionalidadUbicacion;
    private Ubicacion gnDireccionUbicacion;
    private List<UnSuscripcion> ListaUnSuscripciones;
    private List<UnPersonaContacto> listaUnPersonaContactos;
    //Información suscripción
    private UnSuscripcion unSuscripcion;

    public UnPersona() {
    }

    public UnPersona(Integer id) {
        this.id = id;
    }

    public UnPersona(Integer id, String maeTipoDocumentoTipo, int maeTipoDocumentoId, String maeTipoDocumentoCodigo, String maeTipoDocumentoValor, String documentoNumero, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido) {
        this.id = id;
        this.maeTipoDocumentoTipo = maeTipoDocumentoTipo;
        this.maeTipoDocumentoId = maeTipoDocumentoId;
        this.maeTipoDocumentoCodigo = maeTipoDocumentoCodigo;
        this.maeTipoDocumentoValor = maeTipoDocumentoValor;
        this.documentoNumero = documentoNumero;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
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

    public Ubicacion getGnNacimientoUbicacion() {
        return gnNacimientoUbicacion;
    }

    public void setGnNacimientoUbicacion(Ubicacion gnNacimientoUbicacion) {
        this.gnNacimientoUbicacion = gnNacimientoUbicacion;
    }

    public Ubicacion getGnNacionalidadUbicacion() {
        return gnNacionalidadUbicacion;
    }

    public void setGnNacionalidadUbicacion(Ubicacion gnNacionalidadUbicacion) {
        this.gnNacionalidadUbicacion = gnNacionalidadUbicacion;
    }

    public Ubicacion getGnDireccionUbicacion() {
        return gnDireccionUbicacion;
    }

    public void setGnDireccionUbicacion(Ubicacion gnDireccionUbicacion) {
        this.gnDireccionUbicacion = gnDireccionUbicacion;
    }

    public List<UnSuscripcion> getListaUnSuscripciones() {
        return ListaUnSuscripciones;
    }

    public void setListaUnSuscripciones(List<UnSuscripcion> ListaUnSuscripciones) {
        this.ListaUnSuscripciones = ListaUnSuscripciones;
    }

    public List<UnPersonaContacto> getListaUnPersonaContactos() {
        return listaUnPersonaContactos;
    }

    public void addUnPersonaContactos(UnPersonaContacto cont) {
        if(listaUnPersonaContactos == null){
            listaUnPersonaContactos = new ArrayList();
        }
        this.listaUnPersonaContactos.add(cont);
    }

    public void setListaUnPersonaContactos(List<UnPersonaContacto> listaUnPersonaContactos) {
        this.listaUnPersonaContactos = listaUnPersonaContactos;
    }

    public UnSuscripcion getUnSuscripcion() {
        return unSuscripcion;
    }

    public void setUnSuscripcion(UnSuscripcion unSuscripcion) {
        this.unSuscripcion = unSuscripcion;
    }

    @Override
    public String toString() {
        return "UnPersona{" + "id=" + id + ", maeTipoDocumentoTipo=" + maeTipoDocumentoTipo + ", maeTipoDocumentoId=" + maeTipoDocumentoId + ", maeTipoDocumentoCodigo=" + maeTipoDocumentoCodigo + ", maeTipoDocumentoValor=" + maeTipoDocumentoValor + ", documentoNumero=" + documentoNumero + ", fechaDocumento=" + fechaDocumento + ", primerNombre=" + primerNombre + ", segundoNombre=" + segundoNombre + ", primerApellido=" + primerApellido + ", segundoApellido=" + segundoApellido + ", fechaNacimiento=" + fechaNacimiento + ", direccion=" + direccion + ", gnNacimientoUbicacion=" + gnNacimientoUbicacion + ", gnNacionalidadUbicacion=" + gnNacionalidadUbicacion + ", gnDireccionUbicacion=" + gnDireccionUbicacion + ", ListaUnSuscripciones=" + ListaUnSuscripciones + '}';
    }
    
}
