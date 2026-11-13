/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.dominio.contrato;

import co.com.systemtech.unimos.dominio.generico.Auditoria;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class UnSuscripcionPago extends Auditoria {

    private Integer id;
    private Date fecha;
    private String maeFormaPagoTipo;
    private int maeFormaPagoId;
    private String maeFormaPagoCodigo;
    private String maeFormaPagoValor;
    private Double valor;
    private List<UnComercialComision> listaUnComercialComisiones;
    private UnSuscripcion unSuscripcion;

    public UnSuscripcionPago() {
    }

    public UnSuscripcionPago(Integer id) {
        this.id = id;
    }

    public UnSuscripcionPago(Integer id, Date fecha, String maeFormaPagoTipo, int maeFormaPagoId, String maeFormaPagoCodigo, String maeFormaPagoValor, Double valor) {
        this.id = id;
        this.fecha = fecha;
        this.maeFormaPagoTipo = maeFormaPagoTipo;
        this.maeFormaPagoId = maeFormaPagoId;
        this.maeFormaPagoCodigo = maeFormaPagoCodigo;
        this.maeFormaPagoValor = maeFormaPagoValor;
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

    public String getMaeFormaPagoTipo() {
        return maeFormaPagoTipo;
    }

    public void setMaeFormaPagoTipo(String maeFormaPagoTipo) {
        this.maeFormaPagoTipo = maeFormaPagoTipo;
    }

    public int getMaeFormaPagoId() {
        return maeFormaPagoId;
    }

    public void setMaeFormaPagoId(int maeFormaPagoId) {
        this.maeFormaPagoId = maeFormaPagoId;
    }

    public String getMaeFormaPagoCodigo() {
        return maeFormaPagoCodigo;
    }

    public void setMaeFormaPagoCodigo(String maeFormaPagoCodigo) {
        this.maeFormaPagoCodigo = maeFormaPagoCodigo;
    }

    public String getMaeFormaPagoValor() {
        return maeFormaPagoValor;
    }

    public void setMaeFormaPagoValor(String maeFormaPagoValor) {
        this.maeFormaPagoValor = maeFormaPagoValor;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public List<UnComercialComision> getListaUnComercialComisiones() {
        return listaUnComercialComisiones;
    }

    public void setListaUnComercialComisiones(List<UnComercialComision> listaUnComercialComisiones) {
        this.listaUnComercialComisiones = listaUnComercialComisiones;
    }

    public UnSuscripcion getUnSuscripcion() {
        return unSuscripcion;
    }

    public void setUnSuscripcion(UnSuscripcion unSuscripcion) {
        this.unSuscripcion = unSuscripcion;
    }

    @Override
    public String toString() {
        return "UnSuscripcionPago{" + "id=" + id + ", fecha=" + fecha + ", maeFormaPagoTipo=" + maeFormaPagoTipo + ", maeFormaPagoId=" + maeFormaPagoId + ", maeFormaPagoCodigo=" + maeFormaPagoCodigo + ", maeFormaPagoValor=" + maeFormaPagoValor + ", valor=" + valor + ", listaUnComercialComisiones=" + listaUnComercialComisiones + ", unSuscripcion=" + unSuscripcion + '}';
    }

    
}
