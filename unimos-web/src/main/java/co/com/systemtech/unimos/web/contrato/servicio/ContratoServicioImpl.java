/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.contrato.servicio;

import co.com.systemtech.unimos.dominio.contrato.UnContrato;
import co.com.systemtech.unimos.dominio.contrato.UnContratoDetalle;
import co.com.systemtech.unimos.dominio.registro.UnEntidad;
import co.com.systemtech.unimos.dominio.registro.UnTecnologia;
import co.com.systemtech.unimos.negocio.contrato.ContratoRemoto;
import co.com.systemtech.unimos.negocio.registro.EntidadRemoto;
import co.com.systemtech.unimos.negocio.registro.TecnologiaRemoto;
import co.com.systemtech.unimos.web.contrato.bean.ContratoBean;
import co.com.systemtech.unimos.web.utilidades.AccionesBO;
import co.com.systemtech.unimos.web.utilidades.RemotoEJB;
import co.com.systemtech.unimos.web.utilidades.Url;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class ContratoServicioImpl extends AccionesBO implements ContratoServicioIface {

    private ContratoRemoto getContratoRemoto() throws Exception {
        return (ContratoRemoto) RemotoEJB.getEJBRemoto("ContratoServicio", ContratoRemoto.class.getName());
    }

    private TecnologiaRemoto getTecnologiaRemoto() throws Exception {
        return (TecnologiaRemoto) RemotoEJB.getEJBRemoto("TecnologiaServicio", TecnologiaRemoto.class.getName());
    }

    private EntidadRemoto getEntidadRemoto() throws Exception {
        return (EntidadRemoto) RemotoEJB.getEJBRemoto("EntidadServicio", EntidadRemoto.class.getName());
    }

    @Override
    public void Accion(ContratoBean bean) {
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
                case Url.ACCION_ADICIONAL_1:
                    switch (bean.getDoAccion()) {
                        case Url.ACCION_LISTAR:
                            listarDetalles(bean);
                            break;
                        case Url.ACCION_VER:
                            verDetalle(bean);
                            break;
                        case Url.ACCION_CREAR:
                            crearDetalle(bean);
                            break;
                        case Url.ACCION_GUARDAR:
                            guardarDetalle(bean);
                            break;
                        case Url.ACCION_EDITAR:
                            editarDetalle(bean);
                            break;
                        case Url.ACCION_MODIFICAR:
                            modificarDetalle(bean);
                            break;
                        case Url.ACCION_BORRAR:
                            borrarDetalle(bean);
                            break;
                    }
                    break;
                default:
                    break;
            }
            cargas(bean);
        } else {
            System.err.println("Sesion inactiva");
        }
    }

    private void listar(ContratoBean bean) {
        try {
            bean.getParamConsulta().setCantidadRegistros(getContratoRemoto().consultarCantidadLista(bean.getParamConsulta()));
            bean.setRegistros(getContratoRemoto().consultarLista(bean.getParamConsulta()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void ver(ContratoBean bean) {
        try {
            bean.setObjeto(getContratoRemoto().consultar(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void crear(ContratoBean bean) {
        try {
            bean.setObjeto(new UnContrato());
            bean.getObjeto().setUnEntidad(new UnEntidad());
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void guardar(ContratoBean bean) {
        try {
            bean.auditoriaGuardar(bean.getObjeto());
            // Guardar Contrato
            bean.getObjeto().setId(getContratoRemoto().insertar(bean.getObjeto()));
            bean.addMensaje("Se creo un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void editar(ContratoBean bean) {
        try {
            bean.setObjeto(getContratoRemoto().consultar(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void modificar(ContratoBean bean) {
        try {
            bean.auditoriaModificar(bean.getObjeto());
            getContratoRemoto().actualizar(bean.getObjeto());
            bean.addMensaje("Se actualizó el registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void borrar(ContratoBean bean) {
        try {
            bean.setObjeto(getContratoRemoto().eliminar(bean.getObjeto().getId()));
            bean.addMensaje("Se eliminó un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void cargas(ContratoBean bean) {
        try {
            switch (bean.getAccion()) {
                case Url.ACCION_LISTAR:
                    break;
                case Url.ACCION_VER:
                    break;
                case Url.ACCION_CREAR:
                case Url.ACCION_EDITAR:
                    break;
                case Url.ACCION_BORRAR:
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void cargasInicial(ContratoBean bean) {
        try {
            //Tecnologias
            bean.setListaTecnologias(getTecnologiaRemoto().consultarTodas());
            //Entidades
            bean.setListaEntidades(getEntidadRemoto().consultarTodas());
        } catch (Exception e) {
            bean.addError("No fue posible cargar las listas de apoyo");
        }
    }

    /**
     * Procesos de gestion de Dettalle
     */
    private void listarDetalles(ContratoBean bean) {
        try {
            int idContrato = (int) bean.getParamConsultaDetalles().getParametroConsulta1();
            bean.setObjeto(getContratoRemoto().consultar(idContrato));
            bean.getParamConsulta().setCantidadRegistros(getContratoRemoto().consultarCantidadListaDetalles(bean.getParamConsultaDetalles()));
            bean.setListaDetalles(getContratoRemoto().consultarListaDetalles(bean.getParamConsultaDetalles()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void verDetalle(ContratoBean bean) {
        try {
            bean.setObjetoDetalle(getContratoRemoto().consultarDetalle(bean.getObjetoDetalle().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void crearDetalle(ContratoBean bean) {
        try {
            bean.setObjetoDetalle(new UnContratoDetalle());
            bean.getObjetoDetalle().setUnTecnologia(new UnTecnologia());
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void guardarDetalle(ContratoBean bean) {
        try {
            bean.getObjetoDetalle().setUnContrato(new UnContrato(bean.getObjeto().getId()));
            bean.auditoriaGuardar(bean.getObjetoDetalle());
            // Guardar Contrato
            bean.getObjetoDetalle().setId(getContratoRemoto().insertarDetalle(bean.getObjetoDetalle()));
            bean.addMensaje("Se creo un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void editarDetalle(ContratoBean bean) {
        try {
            bean.setObjetoDetalle(getContratoRemoto().consultarDetalle(bean.getObjetoDetalle().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void modificarDetalle(ContratoBean bean) {
        try {
            bean.auditoriaModificar(bean.getObjetoDetalle());
            getContratoRemoto().actualizarDetalle(bean.getObjetoDetalle());
            bean.addMensaje("Se actualizó el registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void borrarDetalle(ContratoBean bean) {
        try {
            bean.setObjetoDetalle(getContratoRemoto().eliminarDetalle(bean.getObjetoDetalle().getId()));
            bean.addMensaje("Se eliminó un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

}
