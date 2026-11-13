/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.administracion.servicio;

import co.com.systemtech.unimos.web.administracion.bean.MaestroTipoBean;

/**
 *
 * @author rpalacic (SystemTech Integral)
 */
public interface MaestroTipoServicioIface {

    void Accion(MaestroTipoBean bean);
    
    /**
     * Carga inicial de la opción
     * @param bean 
     */
    void cargasInicial(MaestroTipoBean bean);
    
}
