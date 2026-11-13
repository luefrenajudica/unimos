/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.dominio.administracion;

import co.com.systemtech.unimos.dominio.generico.Auditoria;
import java.util.Date;

/**
 *
 * @author sgiraldov
 */
public class GnLog extends Auditoria {

    private Integer id;
    private String modulo;
    private String opcion;
    private String accion;
    private String descripcion;
    private String empresaEjecucion;
    private String empresaUsuario;
    private String usuarioUsuario;
    private String usuarioNombre;
    private Date fechaHora;
    private String ipPublica;
    private String ipPrivada;
    
    public GnLog() {
    }

    public GnLog(String modulo, String opcion, String accion, String descripcion, String empresaEjecucion, String empresaUsuario, String usuarioUsuario, String usuarioNombre, Date fechaHora, String ipPublica, String ipPrivada) {
        this.modulo = modulo;
        this.opcion = opcion;
        this.accion = accion;
        this.descripcion = descripcion;
        this.empresaEjecucion = empresaEjecucion;
        this.empresaUsuario = empresaUsuario;
        this.usuarioUsuario = usuarioUsuario;
        this.usuarioNombre = usuarioNombre;
        this.fechaHora = fechaHora;
        this.ipPublica = ipPublica;
        this.ipPrivada = ipPrivada;
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
    public String toString() {
        return "GnLog{" + "id=" + id + ", modulo=" + modulo + ", opcion=" + opcion + ", accion=" + accion + ", descripcion=" + descripcion + ", empresaEjecucion=" + empresaEjecucion + ", empresaUsuario=" + empresaUsuario + ", usuarioUsuario=" + usuarioUsuario + ", usuarioNombre=" + usuarioNombre + ", fechaHora=" + fechaHora + ", ipPublica=" + ipPublica + ", ipPrivada=" + ipPrivada + '}';
    }
    
}
