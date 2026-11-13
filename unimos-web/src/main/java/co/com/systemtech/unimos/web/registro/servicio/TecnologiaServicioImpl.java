/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.registro.servicio;

import co.com.systemtech.unimos.dominio.administracion.GnMaestro;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroTipo;
import co.com.systemtech.unimos.web.registro.bean.TecnologiaBean;
import co.com.systemtech.unimos.dominio.registro.UnTecnologia;
import co.com.systemtech.unimos.negocio.administracion.MaestroRemoto;
import co.com.systemtech.unimos.negocio.registro.TecnologiaRemoto;
import co.com.systemtech.unimos.web.utilidades.AccionesBO;
import co.com.systemtech.unimos.web.utilidades.RemotoEJB;
import co.com.systemtech.unimos.web.utilidades.Url;
import java.util.ArrayList;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class TecnologiaServicioImpl extends AccionesBO implements TecnologiaServicioIface {

    private TecnologiaRemoto getTecnologiaRemoto() throws Exception {
        return (TecnologiaRemoto) RemotoEJB.getEJBRemoto("TecnologiaServicio", TecnologiaRemoto.class.getName());
    }

    private MaestroRemoto getMaestroRemoto() throws Exception {
        return (MaestroRemoto) RemotoEJB.getEJBRemoto("MaestroServicio", MaestroRemoto.class.getName());
    }
    

    @Override
    public void Accion(TecnologiaBean bean) {
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

    private void listar(TecnologiaBean bean) {
        try {
            bean.getParamConsulta().setCantidadRegistros(getTecnologiaRemoto().consultarCantidadLista(bean.getParamConsulta()));
            bean.setRegistros(getTecnologiaRemoto().consultarLista(bean.getParamConsulta()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void ver(TecnologiaBean bean) {
        try {
            bean.setObjeto(getTecnologiaRemoto().consultar(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void crear(TecnologiaBean bean) {
        try {
            bean.setObjeto(new UnTecnologia());
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void editar(TecnologiaBean bean) {
        try {
            bean.setObjeto(getTecnologiaRemoto().consultar(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void guardar(TecnologiaBean bean) {
        try {
            mapearMaestros(bean.getObjeto(), bean);
            bean.auditoriaGuardar(bean.getObjeto());
            bean.getObjeto().setId(getTecnologiaRemoto().insertar(bean.getObjeto()));
            bean.addMensaje("Se creo un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void modificar(TecnologiaBean bean) {
        try {
            mapearMaestros(bean.getObjeto(), bean);
            bean.auditoriaModificar(bean.getObjeto());
            getTecnologiaRemoto().actualizar(bean.getObjeto());
            bean.addMensaje("Se actualizó el registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void borrar(TecnologiaBean bean) {
        try {
            bean.setObjeto(getTecnologiaRemoto().eliminar(bean.getObjeto().getId()));
            bean.addMensaje("Se eliminó un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    @Override
    public UnTecnologia consultarTecnologia(int id) {
        UnTecnologia tecnologia = null;
        try {
            tecnologia = getTecnologiaRemoto().consultar(id);
        } catch (Exception e) {

        }
        return tecnologia;
    }

    private void cargas(TecnologiaBean bean) {
        try {
            switch (bean.getAccion()) {
                case Url.ACCION_LISTAR:
                    break;
                case Url.ACCION_VER:
                    break;
                case Url.ACCION_CREAR:
                case Url.ACCION_EDITAR:
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {

        }
    }
    
    @Override
    public void cargasInicial(TecnologiaBean bean) {
        try {
            //Servicios de habilitación
            bean.setMapMaeServicioHabilitacion(getMaestroRemoto().consultarHashPorTipo(GnMaestroTipo.REG_SERVICIO_HABILITACION));
            bean.setListaMaeServicioHabilitacion(new ArrayList<>(bean.getMapMaeServicioHabilitacion().values()));
        } catch (Exception e) {
            bean.addError("No fue posible cargar las listas de apoyo");
        }
    }
    
    private void mapearMaestros(UnTecnologia obj, TecnologiaBean bean) {
        // Tipo Documento
        if (bean.getMapMaeServicioHabilitacion()!= null) {
            GnMaestro servHab = bean.getMapMaeServicioHabilitacion().get(obj.getMaeServicioHabilitacionId());
            obj.setMaeServicioHabilitacionTipo(servHab.getTipo());
            obj.setMaeServicioHabilitacionCodigo(servHab.getValor());
            obj.setMaeServicioHabilitacionValor(servHab.getDescripcion());
        }
    }

}
