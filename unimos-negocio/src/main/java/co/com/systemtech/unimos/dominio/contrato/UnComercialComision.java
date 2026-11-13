/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.dominio.contrato;

import co.com.systemtech.unimos.dominio.generico.Auditoria;
import co.com.systemtech.unimos.dominio.registro.UnComercial;
import java.util.Date;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class UnComercialComision extends Auditoria {

    public static final double COMISION = 27;
    
    private Integer id;
    private Date fecha;
    private Double porcentaje;
    private Double valor;    
    private UnComercial unComercial;
    private UnSuscripcionCartera unSuscripcionCartera;
    private UnSuscripcion unSuscripcion;

    public UnComercialComision() {
    }

    public UnComercialComision(Integer id) {
        this.id = id;
    }

    public UnComercialComision(Integer id, Date fecha, Double valor) {
        this.id = id;
        this.fecha = fecha;
        this.valor = valor;
    }

    public UnComercialComision(Integer id, int idComercial, int idSuscripcion, int idSuscripcionCartera, Date fecha, Double porcentaje, Double valor) {
        this.id = id;
        this.unComercial = new UnComercial(idComercial);
        this.unSuscripcion = new UnSuscripcion(idSuscripcion);
        this.unSuscripcionCartera = new UnSuscripcionCartera(idSuscripcionCartera);
        this.fecha = fecha;
        this.porcentaje = porcentaje;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public UnComercial getUnComercial() {
        return unComercial;
    }

    public void setUnComercial(UnComercial unComercial) {
        this.unComercial = unComercial;
    }

    public UnSuscripcionCartera getUnSuscripcionCartera() {
        return unSuscripcionCartera;
    }

    public void setUnSuscripcionCartera(UnSuscripcionCartera unSuscripcionCartera) {
        this.unSuscripcionCartera = unSuscripcionCartera;
    }

    public UnSuscripcion getUnSuscripcion() {
        return unSuscripcion;
    }

    public void setUnSuscripcion(UnSuscripcion unSuscripcion) {
        this.unSuscripcion = unSuscripcion;
    }

    @Override
    public String toString() {
        return "UnComercialComision{" + "id=" + id + ", fecha=" + fecha + ", porcentaje=" + porcentaje + ", valor=" + valor + ", unComercial=" + unComercial + ", unSuscripcionCartera=" + unSuscripcionCartera + ", unSuscripcion=" + unSuscripcion + '}';
    }
    
}
