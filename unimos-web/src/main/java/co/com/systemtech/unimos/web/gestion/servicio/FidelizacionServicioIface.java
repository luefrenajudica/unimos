/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.gestion.servicio;

import co.com.systemtech.unimos.web.gestion.bean.FidelizacionBean;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public interface FidelizacionServicioIface {

    void Accion(FidelizacionBean bean);
    
    /**
     * 
     * @param bean 
     */
    public void cargasInicial(FidelizacionBean bean);
    
    /**
     * Procesos de gestion de Personas
     * @param bean
     */
    public void listarPersonas(FidelizacionBean bean);
    
//    /**
//     * Seleccionar una persona
//     * @param bean 
//     */
//    public void seleccionarPersona(FidelizacionBean bean);
    
    /**
     * Listado de Detalles de contrato
     * @param bean 
     */
    public void listarContratoDetalles(FidelizacionBean bean);
    
    /**
     * Seleccionar un Detalle de contrato
     * @param bean 
     */
    public void seleccionarContratoDetalle(FidelizacionBean bean);
    
}
