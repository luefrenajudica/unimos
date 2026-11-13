/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.dominio.gestion;

import co.com.systemtech.unimos.dominio.contrato.UnContrato;
import co.com.systemtech.unimos.dominio.generico.Auditoria;
import co.com.systemtech.unimos.dominio.registro.UnTecnologia;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class UnAgenda extends Auditoria {

    private Integer id;
    private int estado;
    private Date fecha;
    private Date hora;
    private short tiempo;    
    private List<UnFidelizacionTecnologia> listaUnFidelizacionTecnologias;
    private UnTecnologia maTecnologia;
    private UnContrato unContrato;
    private List<UnFidelizacion> listaUnFidelizaciones;

    public UnAgenda() {
    }

    public UnAgenda(Integer id) {
        this.id = id;
    }

    public UnAgenda(Integer id, int estado, Date fecha, Date hora, short tiempo) {
        this.id = id;
        this.estado = estado;
        this.fecha = fecha;
        this.hora = hora;
        this.tiempo = tiempo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
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

    public short getTiempo() {
        return tiempo;
    }

    public void setTiempo(short tiempo) {
        this.tiempo = tiempo;
    }

    public List<UnFidelizacionTecnologia> getListaUnFidelizacionTecnologias() {
        return listaUnFidelizacionTecnologias;
    }

    public void setListaUnFidelizacionTecnologias(List<UnFidelizacionTecnologia> listaUnFidelizacionTecnologias) {
        this.listaUnFidelizacionTecnologias = listaUnFidelizacionTecnologias;
    }

    public UnTecnologia getMaTecnologia() {
        return maTecnologia;
    }

    public void setMaTecnologia(UnTecnologia maTecnologia) {
        this.maTecnologia = maTecnologia;
    }

    public UnContrato getUnContrato() {
        return unContrato;
    }

    public void setUnContrato(UnContrato unContrato) {
        this.unContrato = unContrato;
    }

    public List<UnFidelizacion> getListaUnFidelizaciones() {
        return listaUnFidelizaciones;
    }

    public void setListaUnFidelizaciones(List<UnFidelizacion> listaUnFidelizaciones) {
        this.listaUnFidelizaciones = listaUnFidelizaciones;
    }

    @Override
    public String toString() {
        return "UnAgenda{" + "id=" + id + ", estado=" + estado + ", fecha=" + fecha + ", hora=" + hora + ", tiempo=" + tiempo + ", maTecnologia=" + maTecnologia + ", unContrato=" + unContrato + '}';
    }
}
