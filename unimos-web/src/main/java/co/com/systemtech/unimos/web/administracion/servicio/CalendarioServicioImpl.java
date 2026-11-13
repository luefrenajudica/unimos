/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.administracion.servicio;

import co.com.systemtech.unimos.dominio.administracion.GnDiaHabil;
import co.com.systemtech.unimos.negocio.administracion.CalendarioRemoto;
import co.com.systemtech.unimos.web.administracion.bean.CalendarioBean;
import co.com.systemtech.unimos.web.utilidades.AccionesBO;
import co.com.systemtech.unimos.web.utilidades.RemotoEJB;
import co.com.systemtech.unimos.web.utilidades.Url;
import java.util.List;

/**
 *
 * @author raul-palacios
 */
public class CalendarioServicioImpl extends AccionesBO implements CalendarioServicioIface{
    
    private CalendarioRemoto getCalendarioRemoto() throws Exception {
        return (CalendarioRemoto) RemotoEJB.getEJBRemoto("CalendarioServicio", CalendarioRemoto.class.getName());
    }
    
    @Override
    public void Accion(CalendarioBean bean) {
        if(super.ValidarSesion(bean)){
            switch(bean.getAccion()){
                case Url.ACCION_LISTAR:
                    listar(bean);
                    break;
                case Url.ACCION_VER:
                    break;
                case Url.ACCION_CREAR:
                    break;
                case Url.ACCION_GUARDAR:                    
                    break;
                case Url.ACCION_EDITAR:
                    break;
                case Url.ACCION_MODIFICAR:
                    modificar(bean);
                    break;
                case Url.ACCION_BORRAR:
                    break;
                case Url.ACCION_ADICIONAL_1:
                    break;
                case Url.ACCION_ADICIONAL_2:
                    break;
                case Url.ACCION_ADICIONAL_3:
                    break;
                default:
                    break;
            }
        }else{
            System.err.println("Sesion inactiva");
        }
    }
    
    public void listar(CalendarioBean bean){
        try{
            List<GnDiaHabil> result = getCalendarioRemoto().consultarTodos(bean.getParamConsulta());
            bean.setRegistros(result);
        }catch(Exception e){
            System.err.println("CalendarioServicioImpl - Error in listar(): " + e.getMessage());
            e.printStackTrace();
            bean.addError(e.getMessage());
        }
    }
    
    private void modificar(CalendarioBean bean){
        try{
            bean.auditoriaGuardar(bean.getObjeto());
            getCalendarioRemoto().actualizar(bean.getObjeto());
//            if (getCalendarioRemoto().actualizar(bean.getObjeto())){
//                bean.addMensaje("Se creo un registro de manera exitosa");
//            } else {
//                bean.addMensaje("Se eliminó un registro de manera exitosa");
//            }
        }catch(Exception e){
            bean.addError(e.getMessage());
        }
    }
    
}
