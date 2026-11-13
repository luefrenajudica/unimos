/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.registro.servicio;

import co.com.systemtech.unimos.web.registro.bean.EntidadBean;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public interface EntidadServicioIface {

    void Accion(EntidadBean bean);
    
    /**
     * 
     * @param bean 
     */
    public void cargasInicial(EntidadBean bean);
    
}
