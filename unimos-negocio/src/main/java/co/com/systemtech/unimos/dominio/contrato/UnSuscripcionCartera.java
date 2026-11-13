/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.dominio.contrato;

import co.com.systemtech.unimos.dominio.generico.Auditoria;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class UnSuscripcionCartera extends Auditoria implements Cloneable {
    
    public static final int ESTADO_PENDIENTE = 0;
    public static final int ESTADO_PAGO_PARCIAL = 1;
    public static final int ESTADO_PAGO_TOTAL = 2;

    private Integer id;
    private int estado;
    private Date fechaGeneracion;
    private Date fechaMaxima;
    private Date fechaPago;
    private Double valorAPagar;
    private Double valorPagado;
    private Double valorPendiente;
    private UnSuscripcion unSuscripcion;

    public UnSuscripcionCartera() {
    }

    public UnSuscripcionCartera(Integer id) {
        this.id = id;
    }

//    public UnSuscripcionCartera(Integer id, int estado, Date fechaGeneracion, Date fechaMaxima, Double valorAPagar) {
//        this.id = id;
//        this.estado = estado;
//        this.fechaGeneracion = fechaGeneracion;
//        this.fechaMaxima = fechaMaxima;
//        this.valorAPagar = valorAPagar;
//    }
    
    public UnSuscripcionCartera(Integer idSuscripcion, int estado, Date fechaGeneracion, Date fechaMaxima, Double valorAPagar, Double valorPagado, Double valorPendiente) {
        this.unSuscripcion = new UnSuscripcion(idSuscripcion);
        this.estado = estado;
        this.fechaGeneracion = fechaGeneracion;
        this.fechaMaxima = fechaMaxima;
        this.valorAPagar = valorAPagar;
        this.valorPagado = valorPagado;
        this.valorPendiente = valorPendiente;
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

    public String getEstadoStr() {
        switch (estado) {
            case ESTADO_PENDIENTE:
                return "Pendiente";
            case ESTADO_PAGO_PARCIAL:
                return "Pago Parcial";
            case ESTADO_PAGO_TOTAL:
                return "Pago Total";
            default:
                return "";
        }
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public Date getFechaMaxima() {
        return fechaMaxima;
    }

    public void setFechaMaxima(Date fechaMaxima) {
        this.fechaMaxima = fechaMaxima;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Double getValorAPagar() {
        return valorAPagar;
    }

    public void setValorAPagar(Double valorAPagar) {
        this.valorAPagar = valorAPagar;
    }

    public Double getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(Double valorPagado) {
        this.valorPagado = valorPagado;
    }

    public Double getValorPendiente() {
        return valorPendiente;
    }

    public void setValorPendiente(Double valorPendiente) {
        this.valorPendiente = valorPendiente;
    }

    public UnSuscripcion getUnSuscripcion() {
        return unSuscripcion;
    }

    public void setUnSuscripcion(UnSuscripcion unSuscripcion) {
        this.unSuscripcion = unSuscripcion;
    }

    @Override
    public String toString() {
        return "UnSuscripcionCartera{" + "id=" + id + ", estado=" + estado + ", fechaGeneracion=" + fechaGeneracion + ", fechaMaxima=" + fechaMaxima + ", fechaPago=" + fechaPago + ", valorAPagar=" + valorAPagar + ", valorPagado=" + valorPagado + ", valorPendiente=" + valorPendiente + ", unSuscripcion=" + unSuscripcion + '}';
    }
    
//    /**
//     *
//     * @return
//     * @throws CloneNotSupportedException
//     */
//    @Override
//    public UnSuscripcionCartera clone() throws CloneNotSupportedException {
//        try {
//            return (UnSuscripcionCartera) super.clone();
//        } catch (CloneNotSupportedException e) {
//            throw new AssertionError(); // Nunca debería ocurrir si implementamos Cloneable
//        }
//    }
    
    // Sobrescribimos clone() haciéndolo público
    @Override
    public Object clone() throws CloneNotSupportedException {
        // super.clone() se encarga de la magia de duplicar el objeto
        return super.clone();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UnSuscripcionCartera other = (UnSuscripcionCartera) obj;
        if (this.estado != other.estado) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fechaGeneracion, other.fechaGeneracion)) {
            return false;
        }
        if (!Objects.equals(this.fechaMaxima, other.fechaMaxima)) {
            return false;
        }
        if (!Objects.equals(this.fechaPago, other.fechaPago)) {
            return false;
        }
        if (!Objects.equals(this.valorAPagar, other.valorAPagar)) {
            return false;
        }
        if (!Objects.equals(this.valorPagado, other.valorPagado)) {
            return false;
        }
        return Objects.equals(this.valorPendiente, other.valorPendiente);
    }
    
}
