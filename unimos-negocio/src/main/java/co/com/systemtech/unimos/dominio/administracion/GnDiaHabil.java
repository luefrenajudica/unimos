/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.dominio.administracion;

import co.com.systemtech.unimos.dominio.generico.Auditoria;
import java.util.Date;

/**
 *
 * @author sgiraldov
 */
public class GnDiaHabil extends Auditoria {
    
    private Integer id;
    private int agno;
    private Date fecha;
    private Date fechaActual = new Date();
    private boolean habil;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAgno() {
        return agno;
    }

    public void setAgno(int agno) {
        this.agno = agno;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isHabil() {
        return habil;
    }

    public void setHabil(boolean habil) {
        this.habil = habil;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    @Override
    public String toString() {
        return "GnDiaHabil{" + "id=" + id + ", agno=" + agno + ", fecha=" + fecha + ", fechaActual=" + fechaActual + ", habil=" + habil + '}';
    }
    
}
