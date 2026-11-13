/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.contrato.servicio;

import co.com.systemtech.unimos.web.contrato.bean.ContratoBean;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public interface ContratoServicioIface {

    void Accion(ContratoBean bean);
    
    /**
     * 
     * @param bean 
     */
    public void cargasInicial(ContratoBean bean);
    
}
