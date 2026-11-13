/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.dominio.contrato;

import co.com.systemtech.unimos.dominio.generico.Auditoria;
import co.com.systemtech.unimos.dominio.registro.UnPersona;
import java.util.Date;

public class UnSuscripcionPersona extends Auditoria {

    public static final int ESTADO_REGISTRO_CREADO = 0;
    public static final int ESTADO_REGISTRO_CREAR = 1;
    public static final int ESTADO_REGISTRO_RETIRAR = 2;

    private Integer id;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean activo;
    private boolean titular;
    private UnPersona unPersona;
    private UnSuscripcion unSuscripcion;
    private int estadoRegistro;

    public UnSuscripcionPersona() {
    }

    public UnSuscripcionPersona(Integer id) {
        this.id = id;
    }

    public UnSuscripcionPersona(Integer id, Date fechaInicio, Date fechaFin, boolean activo) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.activo = activo;
    }

    public UnSuscripcionPersona(Integer id, Date fechaInicio, Date fechaFin, boolean activo, boolean titular, UnSuscripcion unSuscripcion, UnPersona unPersona) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.activo = activo;
        this.titular = titular;
        this.unPersona = unPersona;
        this.unSuscripcion = unSuscripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean isActivo() {
        return activo;
    }

    public String getActivoStr() {
        return (activo) ? "SI" : "NO";
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isTitular() {
        return titular;
    }

    public void setTitular(boolean titular) {
        this.titular = titular;
    }

    public UnPersona getUnPersona() {
        return unPersona;
    }

    public void setUnPersona(UnPersona unPersona) {
        this.unPersona = unPersona;
    }

    public UnSuscripcion getUnSuscripcion() {
        return unSuscripcion;
    }

    public void setUnSuscripcion(UnSuscripcion unSuscripcion) {
        this.unSuscripcion = unSuscripcion;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    @Override
    public String toString() {
        return "UnSuscripcionPersona{" + "id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", activo=" + activo + ", unPersona=" + unPersona + ", unSuscripcion=" + unSuscripcion + '}';
    }

}
