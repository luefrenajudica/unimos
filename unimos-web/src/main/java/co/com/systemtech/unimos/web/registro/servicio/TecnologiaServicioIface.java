/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.registro.servicio;

import co.com.systemtech.unimos.dominio.registro.UnTecnologia;
import co.com.systemtech.unimos.web.registro.bean.TecnologiaBean;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public interface TecnologiaServicioIface {

    void Accion(TecnologiaBean bean);
    
    /**
     * 
     * @param id
     * @return 
     */
    UnTecnologia consultarTecnologia(int id);
    
    /**
     * 
     * @param bean 
     */
    public void cargasInicial(TecnologiaBean bean);
    
}
