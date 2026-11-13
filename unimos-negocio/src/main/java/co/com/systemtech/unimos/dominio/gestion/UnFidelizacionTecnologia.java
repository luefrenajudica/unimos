/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.dominio.gestion;

import co.com.systemtech.unimos.dominio.contrato.UnContrato;
import co.com.systemtech.unimos.dominio.generico.Auditoria;
import co.com.systemtech.unimos.dominio.registro.UnEntidad;
import co.com.systemtech.unimos.dominio.registro.UnTecnologia;
import java.util.Date;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class UnFidelizacionTecnologia extends Auditoria {

    public static final int ESTADO_REGISTRO_CREADO = 0;
    public static final int ESTADO_REGISTRO_CREAR = 1;
    public static final int ESTADO_REGISTRO_RETIRAR = 2;
    public static final int ESTADO_REGISTRO_GESTIONADO = 3;

    public static final int ESTADO_AGENDADO = 0;
    public static final int ESTADO_CANCELADO = 1;
    public static final int ESTADO_PRESTADO = 2;

    private Integer id;
    private UnFidelizacion unFidelizacion;
    private Date fecha;
    private Date hora;
    private Double valorSuscriptor;
    private Double valorConvenio;
    private int estado;
    private int estadoRegistro;

    private UnEntidad unEntidad;
    private UnContrato unContrato;
    private UnAgenda unAgenda;
    private UnTecnologia unTecnologia;

    public UnFidelizacionTecnologia() {
    }

    public UnFidelizacionTecnologia(Integer id) {
        this.id = id;
    }

    public UnFidelizacionTecnologia(Integer id, Date fecha, Date hora, Double valorSuscriptor, Double valorConvenio, int estado, UnFidelizacion unFidelizacion, UnEntidad unEntidad, UnContrato unContrato, UnAgenda unAgenda, UnTecnologia unTecnologia) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.valorSuscriptor = valorSuscriptor;
        this.valorConvenio = valorConvenio;
        this.estado = estado;
        this.unFidelizacion = unFidelizacion;
        this.unEntidad = unEntidad;
        this.unContrato = unContrato;
        this.unAgenda = unAgenda;
        this.unTecnologia = unTecnologia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UnFidelizacion getUnFidelizacion() {
        return unFidelizacion;
    }

    public void setUnFidelizacion(UnFidelizacion unFidelizacion) {
        this.unFidelizacion = unFidelizacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Double getValorSuscriptor() {
        return valorSuscriptor;
    }

    public void setValorSuscriptor(Double valorSuscriptor) {
        this.valorSuscriptor = valorSuscriptor;
    }

    public Double getValorConvenio() {
        return valorConvenio;
    }

    public void setValorConvenio(Double valorConvenio) {
        this.valorConvenio = valorConvenio;
    }

    public int getEstado() {
        return estado;
    }

    public String getEstadoStr() {
        try {
            switch (estado) {
                case ESTADO_AGENDADO:
                    return "Agendado";
                case ESTADO_PRESTADO:
                    return "Prestado";
                case ESTADO_CANCELADO:
                    return "Cancelado";
                default:
                    return "N/A";
            }
        } catch (Exception e) {
            return "N/A";
        }
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public UnEntidad getUnEntidad() {
        return unEntidad;
    }

    public void setUnEntidad(UnEntidad unEntidad) {
        this.unEntidad = unEntidad;
    }

    public UnContrato getUnContrato() {
        return unContrato;
    }

    public void setUnContrato(UnContrato unContrato) {
        this.unContrato = unContrato;
    }

    public UnAgenda getUnAgenda() {
        return unAgenda;
    }

    public void setUnAgenda(UnAgenda unAgenda) {
        this.unAgenda = unAgenda;
    }

    public UnTecnologia getUnTecnologia() {
        return unTecnologia;
    }

    public void setUnTecnologia(UnTecnologia unTecnologia) {
        this.unTecnologia = unTecnologia;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    @Override
    public String toString() {
        return "UnFidelizacionTecnologia{" + "id=" + id + ", unFidelizacion=" + unFidelizacion + ", fecha=" + fecha + ", hora=" + hora + ", valorSuscriptor=" + valorSuscriptor + ", valorConvenio=" + valorConvenio + ", estado=" + estado + ", unEntidad=" + unEntidad + ", unContrato=" + unContrato + ", unAgenda=" + unAgenda + ", unTecnologia=" + unTecnologia + '}';
    }

}
