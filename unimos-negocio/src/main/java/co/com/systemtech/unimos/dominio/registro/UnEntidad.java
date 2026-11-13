/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.dominio.registro;

import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.dominio.generico.Auditoria;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class UnEntidad extends Auditoria {

    private Integer id;
    private String maeTipoTipo;
    private int maeTipoId;
    private String maeTipoCodigo;
    private String maeTipoValor;
    private String nit;
    private String razonSocial;
    private String nombreComercial;
    private String direccion;
    private Ubicacion gnUbicacion;
    private List<UnEntidadContacto> listaUnEntidadContactos;

    public UnEntidad() {
    }

    public UnEntidad(Integer id) {
        this.id = id;
    }

    public UnEntidad(Integer id, String maeTipoTipo, int maeTipoId, String maeTipoCodigo, String maeTipoValor, String nit, String razonSocial, String nombreComercial) {
        this.id = id;
        this.maeTipoTipo = maeTipoTipo;
        this.maeTipoId = maeTipoId;
        this.maeTipoCodigo = maeTipoCodigo;
        this.maeTipoValor = maeTipoValor;
        this.nit = nit;
        this.razonSocial = razonSocial;
        this.nombreComercial = nombreComercial;
    }

//    public UnEntidad(Integer id, String maeTipoTipo, int maeTipoId, String maeTipoCodigo, String maeTipoValor, String nit, String razonSocial, String nombreComercial, String direccion) {
//        this.id = id;
//        this.maeTipoTipo = maeTipoTipo;
//        this.maeTipoId = maeTipoId;
//        this.maeTipoCodigo = maeTipoCodigo;
//        this.maeTipoValor = maeTipoValor;
//        this.nit = nit;
//        this.razonSocial = razonSocial;
//        this.nombreComercial = nombreComercial;
//        this.direccion = direccion;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaeTipoTipo() {
        return maeTipoTipo;
    }

    public void setMaeTipoTipo(String maeTipoTipo) {
        this.maeTipoTipo = maeTipoTipo;
    }

    public int getMaeTipoId() {
        return maeTipoId;
    }

    public void setMaeTipoId(int maeTipoId) {
        this.maeTipoId = maeTipoId;
    }

    public String getMaeTipoCodigo() {
        return maeTipoCodigo;
    }

    public void setMaeTipoCodigo(String maeTipoCodigo) {
        this.maeTipoCodigo = maeTipoCodigo;
    }

    public String getMaeTipoValor() {
        return maeTipoValor;
    }

    public void setMaeTipoValor(String maeTipoValor) {
        this.maeTipoValor = maeTipoValor;
    }

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

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Ubicacion getGnUbicacion() {
        return gnUbicacion;
    }

    public void setGnUbicacion(Ubicacion gnUbicacion) {
        this.gnUbicacion = gnUbicacion;
    }

    public List<UnEntidadContacto> getListaUnEntidadContactos() {
        return listaUnEntidadContactos;
    }

    public void addUnEntidadContactos(UnEntidadContacto cont) {
        if(listaUnEntidadContactos == null){
            listaUnEntidadContactos = new ArrayList();
        }
        this.listaUnEntidadContactos.add(cont);
    }

    public void setListaUnEntidadContactos(List<UnEntidadContacto> listaUnEntidadContactos) {
        this.listaUnEntidadContactos = listaUnEntidadContactos;
    }

    @Override
    public String toString() {
        return "UnEntidad{" + "id=" + id + ", maeTipoTipo=" + maeTipoTipo + ", maeTipoId=" + maeTipoId + ", maeTipoCodigo=" + maeTipoCodigo + ", maeTipoValor=" + maeTipoValor + ", nit=" + nit + ", razonSocial=" + razonSocial + ", nombreComercial=" + nombreComercial + ", direccion=" + direccion + ", gnUbicacion=" + gnUbicacion + ", listaUnEntidadContactos=" + listaUnEntidadContactos + '}';
    }

}
