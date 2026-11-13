/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.administracion.servicio;

import co.com.systemtech.unimos.web.administracion.bean.UsuarioBean;

/**
 *
 * @author raul-palacios
 */
public interface UsuarioServicioIface {

    void Accion(UsuarioBean bean);
    
    void cargasInicial(UsuarioBean bean);
    
}
