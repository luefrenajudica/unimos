/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.administracion.servicio;

import co.com.systemtech.unimos.web.administracion.bean.EmpresaBean;
import co.com.systemtech.unimos.dominio.administracion.GnEmpresa;

/**
 *
 * @author raul-palacios
 */
public interface EmpresaServicioIface {
    
    void Accion(EmpresaBean bean);

    public GnEmpresa objetoConverter(GnEmpresa per);
}
