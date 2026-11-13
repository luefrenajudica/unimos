/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.dominio.facturaelectronica;

/**
 * 
 * @author Raúl Ignacio Palacios Correa
 */
public class EntidadPagadora {

    private String nit;
    private String razonSocial;
    private String codigoEAPB;

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCodigoEAPB() {
        return codigoEAPB;
    }

    public void setCodigoEAPB(String codigoEAPB) {
        this.codigoEAPB = codigoEAPB;
    }
}
