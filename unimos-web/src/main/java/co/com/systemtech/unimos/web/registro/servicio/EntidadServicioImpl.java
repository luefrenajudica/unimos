/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.registro.servicio;

import co.com.systemtech.unimos.dominio.administracion.GnMaestro;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroTipo;
import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.web.registro.bean.EntidadBean;
import co.com.systemtech.unimos.dominio.registro.UnEntidad;
import co.com.systemtech.unimos.dominio.registro.UnEntidadContacto;
import co.com.systemtech.unimos.negocio.administracion.MaestroRemoto;
import co.com.systemtech.unimos.negocio.administracion.UbicacionRemoto;
import co.com.systemtech.unimos.negocio.registro.EntidadRemoto;
import co.com.systemtech.unimos.web.utilidades.AccionesBO;
import co.com.systemtech.unimos.web.utilidades.RemotoEJB;
import co.com.systemtech.unimos.web.utilidades.Url;
import java.util.ArrayList;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class EntidadServicioImpl extends AccionesBO implements EntidadServicioIface {

    private EntidadRemoto getEntidadRemoto() throws Exception {
        return (EntidadRemoto) RemotoEJB.getEJBRemoto("EntidadServicio", EntidadRemoto.class.getName());
    }

    private MaestroRemoto getMaestroRemoto() throws Exception {
        return (MaestroRemoto) RemotoEJB.getEJBRemoto("MaestroServicio", MaestroRemoto.class.getName());
    }

    private UbicacionRemoto getUbicacionRemoto() throws Exception {
        return (UbicacionRemoto) RemotoEJB.getEJBRemoto("UbicacionServicio", UbicacionRemoto.class.getName());
    }

    @Override
    public void Accion(EntidadBean bean) {
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

    private void listar(EntidadBean bean) {
        try {
            bean.getParamConsulta().setCantidadRegistros(getEntidadRemoto().consultarCantidadLista(bean.getParamConsulta()));
            bean.setRegistros(getEntidadRemoto().consultarLista(bean.getParamConsulta()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void ver(EntidadBean bean) {
        try {
            bean.setObjeto(getEntidadRemoto().consultar(bean.getObjeto().getId()));
            bean.getObjeto().setListaUnEntidadContactos(getEntidadRemoto().consultarContactos(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void crear(EntidadBean bean) {
        try {
            bean.setObjeto(new UnEntidad());
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void editar(EntidadBean bean) {
        try {
            bean.setObjeto(getEntidadRemoto().consultar(bean.getObjeto().getId()));
            bean.getObjeto().setListaUnEntidadContactos(getEntidadRemoto().consultarContactos(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void guardar(EntidadBean bean) {
        try {
            mapearMaestros(bean.getObjeto(), bean);
            bean.auditoriaGuardar(bean.getObjeto());
            bean.getObjeto().setId(getEntidadRemoto().insertar(bean.getObjeto()));
            //Crear contactos
            if (bean.getObjeto().getListaUnEntidadContactos() != null) {
                for (UnEntidadContacto cont : bean.getObjeto().getListaUnEntidadContactos()) {
                    cont.setUnEntidad(new UnEntidad(bean.getObjeto().getId()));
                    bean.auditoriaGuardar(cont);
                    getEntidadRemoto().insertarContacto(cont);
                }
            }
            bean.addMensaje("Se creo un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void modificar(EntidadBean bean) {
        try {
            mapearMaestros(bean.getObjeto(), bean);
            bean.auditoriaModificar(bean.getObjeto());
            getEntidadRemoto().actualizar(bean.getObjeto());
            //Actualizar contactos
            if (bean.getObjeto().getListaUnEntidadContactos() != null) {
                for (UnEntidadContacto cont : bean.getObjeto().getListaUnEntidadContactos()) {
                    if (cont.getEstadoRegistro() == UnEntidadContacto.ESTADO_REGISTRO_CREAR) {
                        cont.setUnEntidad(new UnEntidad(bean.getObjeto().getId()));
                        bean.auditoriaGuardar(cont);
                        getEntidadRemoto().insertarContacto(cont);
                    } else if (cont.getEstadoRegistro() == UnEntidadContacto.ESTADO_REGISTRO_BORRAR) {
                        getEntidadRemoto().eliminarContacto(cont.getId());
                    }
                }
            }
            bean.addMensaje("Se actualizó el registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void borrar(EntidadBean bean) {
        try {
            bean.setObjeto(getEntidadRemoto().eliminar(bean.getObjeto().getId()));
            bean.addMensaje("Se eliminó un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void cargas(EntidadBean bean) {
        try {
            switch (bean.getAccion()) {
                case Url.ACCION_LISTAR:
//                    bean.setUbicacionesRecursiva(getUbicacionRemoto().consultarMunicipios());
//                    bean.setUbicaciones(getUbicacionRemoto().consultarPorTipo(Ubicacion.TIPO_MUNICIPIO));
                    break;
                case Url.ACCION_VER:
                    break;
                case Url.ACCION_CREAR:
                case Url.ACCION_EDITAR:
//                    bean.setUbicacionesRecursiva(getUbicacionRemoto().consultarMunicipios());
//                    bean.setUbicaciones(getUbicacionRemoto().consultarPorTipo(Ubicacion.TIPO_MUNICIPIO));
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void cargasInicial(EntidadBean bean) {
        try {
            //Tipo Entidad
            bean.setMapMaeTiposEntidad(getMaestroRemoto().consultarHashPorTipo(GnMaestroTipo.REG_TIPO_ENTIDAD));
            bean.setListaMaeTiposEntidad(new ArrayList<>(bean.getMapMaeTiposEntidad().values()));
            //Tipo Contacto
            bean.setMapMaeTiposContacto(getMaestroRemoto().consultarHashPorTipo(GnMaestroTipo.GN_TIPO_CONTACTO));
            bean.setListaMaeTiposContacto(new ArrayList<>(bean.getMapMaeTiposContacto().values()));
            //Ubicaciones
            bean.setUbicacionesRecursiva(getUbicacionRemoto().consultarMunicipios());
            bean.setUbicaciones(getUbicacionRemoto().consultarPorTipo(Ubicacion.TIPO_MUNICIPIO));
        } catch (Exception e) {
            bean.addError("No fue posible cargar las listas de apoyo");
        }
    }

    private void mapearMaestros(UnEntidad obj, EntidadBean bean) {
        // Tipo Entidad
        if (bean.getMapMaeTiposEntidad() != null) {
            GnMaestro tipoDoc = bean.getMapMaeTiposEntidad().get(obj.getMaeTipoId());
            obj.setMaeTipoTipo(tipoDoc.getTipo());
            obj.setMaeTipoCodigo(tipoDoc.getValor());
            obj.setMaeTipoValor(tipoDoc.getDescripcion());
        }
        // Tipo Contacto
        if (obj.getListaUnEntidadContactos() != null) {
            for (UnEntidadContacto cont : obj.getListaUnEntidadContactos()) {
                if (bean.getMapMaeTiposContacto() != null) {
                    GnMaestro tipoCont = bean.getMapMaeTiposContacto().get(cont.getMaeTipoId());
                    cont.setMaeTipoTipo(tipoCont.getTipo());
                    cont.setMaeTipoCodigo(tipoCont.getValor());
                    cont.setMaeTipoValor(tipoCont.getDescripcion());
                }
            }
        }
    }

}
