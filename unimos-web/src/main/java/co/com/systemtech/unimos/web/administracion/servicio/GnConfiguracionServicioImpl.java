/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.administracion.servicio;

import co.com.systemtech.unimos.negocio.administracion.ConfiguracionRemoto;
import co.com.systemtech.unimos.web.administracion.bean.GnConfiguracionBean;
import co.com.systemtech.unimos.web.utilidades.AccionesBO;
import co.com.systemtech.unimos.web.utilidades.RemotoEJB;
import co.com.systemtech.unimos.web.utilidades.Url;

/**
 *
 * @author Jaime Andres Olarte
 */
public class GnConfiguracionServicioImpl extends AccionesBO implements GnConfiguracionServicioIface {
    
    private ConfiguracionRemoto getConfiguracionRemoto() throws Exception {
        return (ConfiguracionRemoto) RemotoEJB.getEJBRemoto("ConfiguracionServicio", ConfiguracionRemoto.class.getName());
    }

    @Override
    public void Accion(GnConfiguracionBean bean) {
        if (super.ValidarSesion(bean)) {
            switch (bean.getAccion()) {
                case Url.ACCION_LISTAR:
                    listar(bean);
                    break;
                case Url.ACCION_EDITAR:
                    modificar(bean);
                    break;
            }
        }
    }
    
    private void listar(GnConfiguracionBean bean) {
        try {
            bean.getParamConsulta().setCantidadRegistros(getConfiguracionRemoto().consultarCantidadLista(bean.getParamConsulta()));
            bean.setRegistros(getConfiguracionRemoto().consultarLista(bean.getParamConsulta()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }
    
    private void modificar(GnConfiguracionBean bean) {
        try {
            getConfiguracionRemoto().actualizar(bean.getObjeto());
            bean.addMensaje("Se actualizó el registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }
    
}
