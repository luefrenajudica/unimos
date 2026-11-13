/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.administracion.servicio;

import co.com.systemtech.unimos.web.administracion.bean.UbicacionBean;
import co.com.systemtech.unimos.dominio.administracion.Ubicacion;

/**
 *
 * @author raul-palacios
 */
public interface UbicacionServicioIface {
    void Accion(UbicacionBean bean);

    void cargarPadreTipo(UbicacionBean bean);
    
    public Ubicacion objetoConverter(Ubicacion obj);
}
