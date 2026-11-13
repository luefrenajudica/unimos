/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.conexion.bean;

import co.com.systemtech.unimos.web.administracion.servicio.ConexionServicioImpl;
import co.com.systemtech.unimos.web.utilidades.Url;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import co.com.systemtech.unimos.web.administracion.servicio.ConexionServicioIface;

/**
 *
 * @author raul-palacios
 */
@ManagedBean
@ViewScoped
public class ContrasenaBean extends Url {

    private ConexionServicioIface conexionServicio;

    private int idUsuario = 0;
    private String usuario = "";
    private String nombre = "";
    private String correo = "";
    private String contrasenaActual = "";
    private String contrasenaNueva1 = "";
    private String contrasenaNueva2 = "";

    public ContrasenaBean() {
    }

    @PostConstruct
    public void postConstruct() {
        setIdUsuario(super.getConexion().getUsuario().getId());
        setUsuario(super.getConexion().getUsuario().getUsuario());
        setNombre(super.getConexion().getUsuario().getNombre());
        setCorreo(super.getConexion().getUsuario().getCorreoElectronico());
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public String getContrasenaActual() {
        return contrasenaActual;
    }

    public void setContrasenaActual(String contrasenaActual) {
        this.contrasenaActual = contrasenaActual;
    }

    public String getContrasenaNueva1() {
        return contrasenaNueva1;
    }

    public void setContrasenaNueva1(String contrasenaNueva1) {
        this.contrasenaNueva1 = contrasenaNueva1;
    }

    public String getContrasenaNueva2() {
        return contrasenaNueva2;
    }

    public void setContrasenaNueva2(String contrasenaNueva2) {
        this.contrasenaNueva2 = contrasenaNueva2;
    }

    public ConexionServicioIface getConexionServicio() {
        if (conexionServicio == null) {
            conexionServicio = new ConexionServicioImpl();
        }
        return conexionServicio;
    }

    public void setConexionServicio(ConexionServicioIface conexionServicio) {
        this.conexionServicio = conexionServicio;
    }

    public void cambiarContrasena() {
        getConexionServicio().cambiarContrasena(this);
        if (!isError()) {
            setContrasenaActual("");
            setContrasenaNueva1("");
            setContrasenaNueva2("");
        }
        super.crearLog("Cambio de Contraseña", toString());
        super.generarMensajes();
    }

    @Override
    public String toString() {
        return "Contraseña{" + "usuario=" + usuario + "nombre=" + nombre + ", correo=" + correo + '}';
    }

}
