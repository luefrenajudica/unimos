/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.dominio.contrato;

import co.com.systemtech.unimos.dominio.generico.Auditoria;
import co.com.systemtech.unimos.dominio.registro.UnEntidad;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class UnContrato extends Auditoria {
    
    private Integer id;
    private String contrato;
    private Date fechaInicial;
    private Date fechaFinal;
    private boolean activo;
    private UnEntidad unEntidad;
    private List<UnContratoDetalle> listaUnContratoDetalles;

    public UnContrato() {
    }

    public UnContrato(Integer id) {
        this.id = id;
    }

    public UnContrato(Integer id, String contrato, Date fechaInicial, Date fechaFinal, boolean activo) {
        this.id = id;
        this.contrato = contrato;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.activo = activo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
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

    public UnEntidad getUnEntidad() {
        return unEntidad;
    }

    public void setUnEntidad(UnEntidad unEntidad) {
        this.unEntidad = unEntidad;
    }

    public List<UnContratoDetalle> getListaUnContratoDetalles() {
        return listaUnContratoDetalles;
    }

    public void setListaUnContratoDetalles(List<UnContratoDetalle> listaUnContratoDetalles) {
        this.listaUnContratoDetalles = listaUnContratoDetalles;
    }
    
    @Override
    public String toString() {
        return "UnContrato{" + "id=" + id + ", contrato=" + contrato + ", fechaInicial=" + fechaInicial + ", fechaFinal=" + fechaFinal + ", activo=" + activo + ", unEntidad=" + unEntidad + ", listaUnContratoDetalles=" + listaUnContratoDetalles + '}';
    }
    
}
