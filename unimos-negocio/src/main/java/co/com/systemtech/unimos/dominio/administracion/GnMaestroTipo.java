/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.dominio.administracion;

import co.com.systemtech.unimos.dominio.generico.Auditoria;
import java.util.List;

/**
 *
 * @author sgiraldov
 */
public class GnMaestroTipo extends Auditoria  {
    
    //General
    public static final String GN_NINGUNO = "00";
    public static final String GN_SEXO = "01";
    public static final String GN_TIPO_DOCUMENTO = "02";
    public static final String GN_ORIENTACION_SEXUAL = "03";
    public static final String GN_ESTADO_CIVIL = "04";
    public static final String GN_GRUPO_SANGUINEO = "05";
    public static final String GN_TIPO_CONTACTO = "06";
    //Registros
    public static final String REG_TIPO_ENTIDAD = "07";
    public static final String REG_SERVICIO_HABILITACION = "08";
    //Suscripcion
    public static final String SUS_TIPO_SUSCRIPCION = "09";
    public static final String SUS_PLAN = "10";
    public static final String SUS_FORMA_PAGO = "11";
    //Cartera
    public static final String CAR_ESTADO_CARTERA = "12";
    
    private String tipo;
    private String nombre;
    private boolean activo;
    List<GnMaestroTipo> maestroTipo;
    private Perfil perfil;

    public GnMaestroTipo() {
    }

    public GnMaestroTipo(String tipo) {
        this.tipo = tipo;
    }

    public GnMaestroTipo(String tipo, String nombre, boolean activo) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.activo = activo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public List<GnMaestroTipo> getMaestroTipo() {
        return maestroTipo;
    }

    public void setMaestroTipo(List<GnMaestroTipo> maestroTipo) {
        this.maestroTipo = maestroTipo;
    }

    public Perfil getPerfil() {
        if (perfil == null) {
            perfil = new Perfil();
        }
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public String toString() {
        return "GnMaestroTipo{" + "tipo=" + tipo + ", nombre=" + nombre + ", activo=" + activo + ", maestroTipo=" + maestroTipo + ", perfil=" + perfil + '}';
    }
    
}
