/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.negocio.administracion;

import co.com.systemtech.unimos.dominio.administracion.GnMaestroAccion;

/**
 *
 * @author rpalacic
 */
public interface MaestroAccionRemoto {
    

    /**
     * Consulta por id
     * @param id
     * @return
     * @throws Exception 
     */
    GnMaestroAccion consultar(int id) throws Exception;
    
}
