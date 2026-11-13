/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.gestion.servicio;

import co.com.systemtech.unimos.dominio.administracion.GnMaestroTipo;
import co.com.systemtech.unimos.dominio.gestion.UnFidelizacion;
import co.com.systemtech.unimos.dominio.gestion.UnFidelizacionTecnologia;
import co.com.systemtech.unimos.negocio.administracion.MaestroRemoto;
import co.com.systemtech.unimos.negocio.contrato.ContratoRemoto;
import co.com.systemtech.unimos.negocio.gestion.FidelizacionRemoto;
import co.com.systemtech.unimos.negocio.registro.PersonaRemoto;
import co.com.systemtech.unimos.web.gestion.bean.FidelizacionBean;
import co.com.systemtech.unimos.web.utilidades.AccionesBO;
import co.com.systemtech.unimos.web.utilidades.RemotoEJB;
import co.com.systemtech.unimos.web.utilidades.Url;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class FidelizacionServicioImpl extends AccionesBO implements FidelizacionServicioIface {

    private FidelizacionRemoto getFidelizacionRemoto() throws Exception {
        return (FidelizacionRemoto) RemotoEJB.getEJBRemoto("FidelizacionServicio", FidelizacionRemoto.class.getName());
    }

    private PersonaRemoto getPersonaRemoto() throws Exception {
        return (PersonaRemoto) RemotoEJB.getEJBRemoto("PersonaServicio", PersonaRemoto.class.getName());
    }

    private ContratoRemoto getContratoRemoto() throws Exception {
        return (ContratoRemoto) RemotoEJB.getEJBRemoto("ContratoServicio", ContratoRemoto.class.getName());
    }

    private MaestroRemoto getMaestroRemoto() throws Exception {
        return (MaestroRemoto) RemotoEJB.getEJBRemoto("MaestroServicio", MaestroRemoto.class.getName());
    }

//    private TecnologiaRemoto getTecnologiaRemoto() throws Exception {
//        return (TecnologiaRemoto) RemotoEJB.getEJBRemoto("TecnologiaServicio", TecnologiaRemoto.class.getName());
//    }
    @Override
    public void Accion(FidelizacionBean bean) {
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
                        case Url.ACCION_EDITAR:
                            gestionar(bean);
                            break;
                        case Url.ACCION_MODIFICAR:
                            aplicarGestion(bean);
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

    private void listar(FidelizacionBean bean) {
        try {
            bean.getParamConsulta().setCantidadRegistros(getFidelizacionRemoto().consultarCantidadLista(bean.getParamConsulta()));
            bean.setRegistros(getFidelizacionRemoto().consultarLista(bean.getParamConsulta()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void ver(FidelizacionBean bean) {
        try {
            bean.setObjeto(getFidelizacionRemoto().consultar(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void crear(FidelizacionBean bean) {
        try {
            bean.setObjeto(new UnFidelizacion());
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void guardar(FidelizacionBean bean) {
        try {
            //Llenar campos
            double sumaValorSuscriptor = 0;
            double sumaValorConvenio = 0;
            bean.auditoriaGuardar(bean.getObjeto());
            List<UnFidelizacionTecnologia> lista = new ArrayList();
            if (bean.getObjeto().getListaUnFidelizacionTecnologias() != null && !bean.getObjeto().getListaUnFidelizacionTecnologias().isEmpty()) {
                for (UnFidelizacionTecnologia obj : bean.getObjeto().getListaUnFidelizacionTecnologias()) {
                    sumaValorSuscriptor += obj.getValorSuscriptor();
                    sumaValorConvenio += obj.getValorConvenio();
                    bean.auditoriaGuardar(obj);
                    lista.add(obj);
                }
            }
            bean.getObjeto().setEstado(UnFidelizacion.ESTADO_PROGRAMADO);
            bean.getObjeto().setValorSuscriptor(sumaValorSuscriptor);
            bean.getObjeto().setValorConvenio(sumaValorConvenio);
            bean.getObjeto().setListaUnFidelizacionTecnologias(lista);
            // Guardar Suscripcion
            bean.getObjeto().setId(getFidelizacionRemoto().insertar(bean.getObjeto()));
            bean.addMensaje("Se creo un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void editar(FidelizacionBean bean) {
        try {
            bean.setObjeto(getFidelizacionRemoto().consultar(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void modificar(FidelizacionBean bean) {
        try {
            bean.auditoriaModificar(bean.getObjeto());
            List<UnFidelizacionTecnologia> lista = new ArrayList();
            for (UnFidelizacionTecnologia obj : bean.getObjeto().getListaUnFidelizacionTecnologias()) {
                if (obj.getEstadoRegistro() == UnFidelizacionTecnologia.ESTADO_REGISTRO_CREAR) {
                    bean.auditoriaGuardar(obj);
                } else if (obj.getEstadoRegistro() == UnFidelizacionTecnologia.ESTADO_REGISTRO_GESTIONADO) {
                    bean.auditoriaModificar(obj);
                }
                lista.add(obj);
            }
            bean.getObjeto().setListaUnFidelizacionTecnologias(lista);
            getFidelizacionRemoto().actualizar(bean.getObjeto());
            bean.addMensaje("Se actualizó el registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void gestionar(FidelizacionBean bean) {
        try {
            bean.setObjeto(getFidelizacionRemoto().consultar(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void aplicarGestion(FidelizacionBean bean) {
        try {
            List<UnFidelizacionTecnologia> lista = new ArrayList();
            int estado = UnFidelizacion.ESTADO_PROGRAMADO;
            int registros = bean.getObjeto().getListaUnFidelizacionTecnologias().size();
            int finalizados = 0;
            for (UnFidelizacionTecnologia obj : bean.getObjeto().getListaUnFidelizacionTecnologias()) {
                if (obj.getEstadoRegistro() == UnFidelizacionTecnologia.ESTADO_REGISTRO_GESTIONADO) {
                    obj.setUnFidelizacion(new UnFidelizacion(bean.getObjeto().getId()));
                    bean.auditoriaModificar(obj);
                }
                if (obj.getEstado() == UnFidelizacionTecnologia.ESTADO_PRESTADO || obj.getEstado() == UnFidelizacionTecnologia.ESTADO_CANCELADO) {
                    finalizados++;
                }
                lista.add(obj);
            }
            bean.getObjeto().setListaUnFidelizacionTecnologias(lista);
            if (registros == finalizados) {
                estado = UnFidelizacion.ESTADO_FINALIZADO;
            } else if (registros > 0) {
                estado = UnFidelizacion.ESTADO_EN_EJECUCION;
            }
            if (bean.getObjeto().getEstado() != estado) {
                bean.auditoriaModificar(bean.getObjeto());
                bean.getObjeto().setEstado(estado);
            }
            getFidelizacionRemoto().actualizar(bean.getObjeto());
            bean.addMensaje("Se gestiono la fidelización de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void borrar(FidelizacionBean bean) {
        try {
            bean.setObjeto(getFidelizacionRemoto().eliminar(bean.getObjeto().getId()));
            bean.addMensaje("Se eliminó un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void cargas(FidelizacionBean bean) {
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
    public void cargasInicial(FidelizacionBean bean) {
        try {
//            bean.setListaMaeTiposDocumento(getMaestroRemoto().consultarPorTipo(GnMaestroTipo.GN_TIPO_DOCUMENTO));
            bean.setListaMaeTiposSuscripcion(getMaestroRemoto().consultarPorTipo(GnMaestroTipo.SUS_TIPO_SUSCRIPCION));
            bean.setListaMaePlanes(getMaestroRemoto().consultarPorTipo(GnMaestroTipo.SUS_PLAN));
            bean.setListaMaeServicioHabilitacion(getMaestroRemoto().consultarPorTipo(GnMaestroTipo.REG_SERVICIO_HABILITACION));
        } catch (Exception e) {
            bean.addError("No fue posible cargar las listas de apoyo");
        }
    }

    @Override
    public void listarPersonas(FidelizacionBean bean) {
        try {
            bean.getParamConsultaPersonas().setCantidadRegistros(getPersonaRemoto().consultarSuscritosCantidadLista(bean.getParamConsultaPersonas()));
            bean.setListaPersonas(getPersonaRemoto().consultarSuscritosLista(bean.getParamConsultaPersonas()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

//    @Override
//    public void seleccionarPersona(FidelizacionBean bean) {
//        try {
//            bean.getObjeto().setUnPersona(getPersonaRemoto().consultar(bean.getPersona().getId()));
//        } catch (Exception e) {
//            bean.addError(e.getMessage());
//        }
//    }
    @Override
    public void listarContratoDetalles(FidelizacionBean bean) {
        try {
            bean.getParamConsultaContratoDetalles().setCantidadRegistros(getContratoRemoto().consultarCantidadListaDetallesContratados(bean.getParamConsultaContratoDetalles()));
            bean.setListaContratoDetalles(getContratoRemoto().consultarListaDetallesContratados(bean.getParamConsultaContratoDetalles()));
//            bean.getParamConsultaTecnologias().setCantidadRegistros(getTecnologiaRemoto().consultarContratadaCantidadLista(bean.getParamConsultaTecnologias()));
//            bean.setListaTecnologias(getTecnologiaRemoto().consultarContratadaLista(bean.getParamConsultaTecnologias()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    @Override
    public void seleccionarContratoDetalle(FidelizacionBean bean) {
        try {
//            bean.getObjetoFidelizacionContratoDetalle().setUnContratoDetalle(getTecnologiaRemoto().consultar(bean.getContratoDetalle().getId()));

        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

}
