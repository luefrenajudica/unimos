/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.prueba.servicio;

import co.com.systemtech.unimos.web.prueba.bean.CrudBean;

/**
 *
 * @author raul-palacios
 */
public interface CrudServicioIface {

    void Accion(CrudBean bean);
    
    void cargasInicial(CrudBean bean);
    
}
