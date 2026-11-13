/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.dominio.contrato;

import co.com.systemtech.unimos.dominio.generico.Auditoria;
import co.com.systemtech.unimos.dominio.registro.UnTecnologia;
import java.util.Date;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class UnContratoDetalle extends Auditoria {
    
    private Integer id;
    private Date fechaInicio;
    private Date fechaFin;
    private Double valorSuscriptor;
    private Double valorConvenio;
    private boolean activo;
    private UnContrato unContrato;
    private UnTecnologia unTecnologia;

    public UnContratoDetalle() {
    }

    public UnContratoDetalle(Integer id) {
        this.id = id;
    }

    public UnContratoDetalle(Integer id, Date fechaInicio, Date fechaFin, Double valorSuscriptor, Double valorConvenio) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.valorSuscriptor = valorSuscriptor;
        this.valorConvenio = valorConvenio;
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

    public boolean isActivo() {
        return activo;
    }
    
    public String getActivoStr() {
        return (activo) ? "SI" : "NO";
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public UnContrato getUnContrato() {
        return unContrato;
    }

    public void setUnContrato(UnContrato unContrato) {
        this.unContrato = unContrato;
    }

    public UnTecnologia getUnTecnologia() {
        return unTecnologia;
    }

    public void setUnTecnologia(UnTecnologia unTecnologia) {
        this.unTecnologia = unTecnologia;
    }

    @Override
    public String toString() {
        return "UnContratoDetalle{" + "id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", valorSuscriptor=" + valorSuscriptor + ", valorConvenio=" + valorConvenio + ", activo=" + activo + ", unContrato=" + unContrato + ", unTecnologia=" + unTecnologia + '}';
    }
    
}
