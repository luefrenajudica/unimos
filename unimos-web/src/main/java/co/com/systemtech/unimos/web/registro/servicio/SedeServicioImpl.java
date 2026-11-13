/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.registro.servicio;

import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.web.registro.bean.SedeBean;
import co.com.systemtech.unimos.dominio.registro.Sede;
import co.com.systemtech.unimos.negocio.administracion.UbicacionRemoto;
import co.com.systemtech.unimos.negocio.registro.SedeRemoto;
import co.com.systemtech.unimos.web.utilidades.AccionesBO;
import co.com.systemtech.unimos.web.utilidades.RemotoEJB;
import co.com.systemtech.unimos.web.utilidades.Url;

/**
 *
 * @author raul-palacios
 */
public class SedeServicioImpl extends AccionesBO implements SedeServicioIface {

    private SedeRemoto getSedeRemoto() throws Exception {
        return (SedeRemoto) RemotoEJB.getEJBRemoto("SedeServicio", SedeRemoto.class.getName());
    }

    private UbicacionRemoto getUbicacionRemoto() throws Exception {
        return (UbicacionRemoto) RemotoEJB.getEJBRemoto("UbicacionServicio", UbicacionRemoto.class.getName());
    }

    @Override
    public void Accion(SedeBean bean) {
        if (super.ValidarSesion(bean)) {
            switch (bean.getAccion()) {
                case Url.ACCION_LISTAR:
                    listar(bean);
                    break;
                case Url.ACCION_VER:
                    ver(bean);
                    break;
                case Url.ACCION_CREAR:
                    crear(bean);
                    break;
                case Url.ACCION_GUARDAR:
                    guardar(bean);
                    break;
                case Url.ACCION_EDITAR:
                    editar(bean);
                    break;
                case Url.ACCION_MODIFICAR:
                    modificar(bean);
                    break;
                case Url.ACCION_BORRAR:
                    borrar(bean);
                    break;
                default:
                    break;
            }
            cargas(bean);
        } else {
            System.err.println("Sesion inactiva");
        }
    }

    private void listar(SedeBean bean) {
        try {
            bean.getParamConsulta().setCantidadRegistros(getSedeRemoto().consultarCantidadLista(bean.getParamConsulta()));
            bean.setRegistros(getSedeRemoto().consultarLista(bean.getParamConsulta()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void ver(SedeBean bean) {
        try {
            bean.setObjeto(getSedeRemoto().consultar(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void crear(SedeBean bean) {
        try {
            bean.setObjeto(new Sede());
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void editar(SedeBean bean) {
        try {
            bean.setObjeto(getSedeRemoto().consultar(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void guardar(SedeBean bean) {
        try {
            bean.auditoriaGuardar(bean.getObjeto());
            bean.getObjeto().setId(getSedeRemoto().insertar(bean.getObjeto()));
            bean.addMensaje("Se creo un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void modificar(SedeBean bean) {
        try {
            bean.auditoriaModificar(bean.getObjeto());
            getSedeRemoto().actualizar(bean.getObjeto());
            bean.addMensaje("Se actualizó el registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void borrar(SedeBean bean) {
        try {
            bean.setObjeto(getSedeRemoto().eliminar(bean.getObjeto().getId()));
            bean.addMensaje("Se eliminó un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    @Override
    public Sede consultarSede(int id) {
        Sede sede = null;
        try {
            sede = getSedeRemoto().consultar(id);
        } catch (Exception e) {

        }
        return sede;
    }

    private void cargas(SedeBean bean) {
        try {
            switch (bean.getAccion()) {
                case Url.ACCION_LISTAR:
                    bean.setUbicacionesRecursiva(getUbicacionRemoto().consultarMunicipios());
                    bean.setUbicaciones(getUbicacionRemoto().consultarPorTipo(Ubicacion.TIPO_MUNICIPIO));
                    break;
                case Url.ACCION_VER:
                    break;
                case Url.ACCION_CREAR:
                case Url.ACCION_EDITAR:
                    bean.setUbicacionesRecursiva(getUbicacionRemoto().consultarMunicipios());
                    bean.setUbicaciones(getUbicacionRemoto().consultarPorTipo(Ubicacion.TIPO_MUNICIPIO));
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {

        }
    }
    
    @Override
    public void cargasInicial(SedeBean bean) {
        try {
            
        } catch (Exception e) {
            bean.addError("No fue posible cargar las listas de apoyo");
        }
    }

}
