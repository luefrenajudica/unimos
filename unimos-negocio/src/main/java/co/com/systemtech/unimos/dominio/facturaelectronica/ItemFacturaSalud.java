/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.dominio.facturaelectronica;

/**
 *
 * @author Raúl Ignacio Palacios Correa
 */
import java.math.BigDecimal;

public class ItemFacturaSalud {

    private String codigoCUPS;
    private String descripcionServicio;
    private int cantidad;
    private BigDecimal valorUnitario;
    private String numeroAutorizacion;
    private String idDetalleRIPS;

    public String getCodigoCUPS() {
        return codigoCUPS;
    }

    public void setCodigoCUPS(String codigoCUPS) {
        this.codigoCUPS = codigoCUPS;
    }

    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    public String getIdDetalleRIPS() {
        return idDetalleRIPS;
    }

    public void setIdDetalleRIPS(String idDetalleRIPS) {
        this.idDetalleRIPS = idDetalleRIPS;
    }
}
