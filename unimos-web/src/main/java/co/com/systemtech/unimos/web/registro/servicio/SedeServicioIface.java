/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.registro.servicio;

import co.com.systemtech.unimos.dominio.registro.Sede;
import co.com.systemtech.unimos.web.registro.bean.SedeBean;

/**
 *
 * @author raul-palacios
 */
public interface SedeServicioIface {

    void Accion(SedeBean bean);
    
    /**
     * 
     * @param id
     * @return 
     */
    Sede consultarSede(int id);
    
    /**
     * 
     * @param bean 
     */
    public void cargasInicial(SedeBean bean);
    
}
