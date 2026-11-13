/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.dominio.gestion;

import co.com.systemtech.unimos.dominio.contrato.UnSuscripcion;
import co.com.systemtech.unimos.dominio.generico.Auditoria;
import co.com.systemtech.unimos.dominio.registro.UnPersona;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class UnFidelizacion extends Auditoria {

    public static final int ESTADO_PROGRAMADO = 0;
    public static final int ESTADO_EN_EJECUCION = 1;
    public static final int ESTADO_FINALIZADO = 2;

    private Integer id;
    private UnPersona unPersona;
    private UnSuscripcion unSuscripcion;
    private Double valorSuscriptor;
    private Double valorConvenio;
    private int estado;
    private List<UnFidelizacionTecnologia> listaUnFidelizacionTecnologias;

    private boolean personaAsignada;

    public UnFidelizacion() {
        this.valorSuscriptor = Double.valueOf(0);
        this.valorConvenio = Double.valueOf(0);
        this.estado = ESTADO_PROGRAMADO;
    }

    public UnFidelizacion(Integer id) {
        this.id = id;
    }

    public UnFidelizacion(Integer id, Double valorSuscriptor, Double valorConvenio, int estado) {
        this.id = id;
        this.valorSuscriptor = valorSuscriptor;
        this.valorConvenio = valorConvenio;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UnPersona getUnPersona() {
        return unPersona;
    }

    public void setUnPersona(UnPersona unPersona) {
        this.unPersona = unPersona;
    }

    public UnSuscripcion getUnSuscripcion() {
        return unSuscripcion;
    }

    public void setUnSuscripcion(UnSuscripcion unSuscripcion) {
        this.unSuscripcion = unSuscripcion;
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

    public int getEstado() {
        return estado;
    }

    public String getEstadoStr() {
        try {
            switch (estado) {
                case ESTADO_PROGRAMADO:
                    return "Programado";
                case ESTADO_EN_EJECUCION:
                    return "En Ejecución";
                case ESTADO_FINALIZADO:
                    return "Finalizado";
                default:
                    return "N/A";
            }
        } catch (Exception e) {
            return "N/A";
        }
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public List<UnFidelizacionTecnologia> getListaUnFidelizacionTecnologias() {
        return listaUnFidelizacionTecnologias;
    }

    public void setListaUnFidelizacionTecnologias(List<UnFidelizacionTecnologia> listaUnFidelizacionTecnologias) {
        this.listaUnFidelizacionTecnologias = listaUnFidelizacionTecnologias;
    }

    public void addUnFidelizacionTecnologia(UnFidelizacionTecnologia unFidelizacionTecnologia) {
        if(listaUnFidelizacionTecnologias == null){
            listaUnFidelizacionTecnologias = new ArrayList();
        }
        this.listaUnFidelizacionTecnologias.add(unFidelizacionTecnologia);
    }

    public boolean isPersonaAsignada() {
        return personaAsignada;
    }

    public void setPersonaAsignada(boolean personaAsignada) {
        this.personaAsignada = personaAsignada;
    }

    @Override
    public String toString() {
        return "UnFidelizacion{" + "id=" + id + ", unPersona=" + unPersona + ", unSuscripcion=" + unSuscripcion + ", valorSuscriptor=" + valorSuscriptor + ", valorConvenio=" + valorConvenio + ", estado=" + estado + '}';
    }

}
