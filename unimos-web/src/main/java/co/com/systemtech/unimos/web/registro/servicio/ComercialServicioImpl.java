/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.registro.servicio;

import co.com.systemtech.unimos.dominio.administracion.GnMaestro;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroTipo;
import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.web.registro.bean.ComercialBean;
import co.com.systemtech.unimos.dominio.registro.UnComercial;
import co.com.systemtech.unimos.negocio.administracion.MaestroRemoto;
import co.com.systemtech.unimos.negocio.administracion.UbicacionRemoto;
import co.com.systemtech.unimos.negocio.administracion.UsuarioRemoto;
import co.com.systemtech.unimos.negocio.contrato.ComisionRemoto;
import co.com.systemtech.unimos.negocio.registro.ComercialRemoto;
import co.com.systemtech.unimos.negocio.registro.SedeRemoto;
import co.com.systemtech.unimos.web.utilidades.AccionesBO;
import co.com.systemtech.unimos.web.utilidades.RemotoEJB;
import co.com.systemtech.unimos.web.utilidades.Url;
import java.util.ArrayList;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class ComercialServicioImpl extends AccionesBO implements ComercialServicioIface {

    private ComercialRemoto getComercialRemoto() throws Exception {
        return (ComercialRemoto) RemotoEJB.getEJBRemoto("ComercialServicio", ComercialRemoto.class.getName());
    }

    private MaestroRemoto getMaestroRemoto() throws Exception {
        return (MaestroRemoto) RemotoEJB.getEJBRemoto("MaestroServicio", MaestroRemoto.class.getName());
    }
    
    private SedeRemoto getSedeRemoto() throws Exception {
        return (SedeRemoto) RemotoEJB.getEJBRemoto("SedeServicio", SedeRemoto.class.getName());
    }
    
    private UsuarioRemoto getUsuarioRemoto() throws Exception {
        return (UsuarioRemoto) RemotoEJB.getEJBRemoto("UsuarioServicio", UsuarioRemoto.class.getName());
    }

    private UbicacionRemoto getUbicacionRemoto() throws Exception {
        return (UbicacionRemoto) RemotoEJB.getEJBRemoto("UbicacionServicio", UbicacionRemoto.class.getName());
    }

    private ComisionRemoto getComisionRemoto() throws Exception {
        return (ComisionRemoto) RemotoEJB.getEJBRemoto("ComisionServicio", ComisionRemoto.class.getName());
    }

    @Override
    public void Accion(ComercialBean bean) {
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
                            listarComisiones(bean);
                            break;
                        case Url.ACCION_CREAR:
                            break;
                        case Url.ACCION_GUARDAR:
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

    private void listar(ComercialBean bean) {
        try {
            bean.getParamConsulta().setCantidadRegistros(getComercialRemoto().consultarCantidadLista(bean.getParamConsulta()));
            bean.setRegistros(getComercialRemoto().consultarLista(bean.getParamConsulta()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void ver(ComercialBean bean) {
        try {
            bean.setObjeto(getComercialRemoto().consultar(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void crear(ComercialBean bean) {
        try {
            bean.setObjeto(new UnComercial());
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void editar(ComercialBean bean) {
        try {
            bean.setObjeto(getComercialRemoto().consultar(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void guardar(ComercialBean bean) {
        try {
            mapearMaestros(bean.getObjeto(), bean);
            bean.auditoriaGuardar(bean.getObjeto());
            bean.getObjeto().setId(getComercialRemoto().insertar(bean.getObjeto()));
            bean.addMensaje("Se creo un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void modificar(ComercialBean bean) {
        try {
            mapearMaestros(bean.getObjeto(), bean);
            bean.auditoriaModificar(bean.getObjeto());
            getComercialRemoto().actualizar(bean.getObjeto());
            bean.addMensaje("Se actualizó el registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void borrar(ComercialBean bean) {
        try {
            bean.setObjeto(getComercialRemoto().eliminar(bean.getObjeto().getId()));
            bean.addMensaje("Se eliminó un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }
    
    private void listarComisiones(ComercialBean bean) {
        try {
            bean.getParamConsultaComisiones().setCantidadRegistros(getComisionRemoto().consultarCantidadLista(bean.getParamConsultaComisiones()));
            bean.setListaComisiones(getComisionRemoto().consultarLista(bean.getParamConsultaComisiones()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void cargas(ComercialBean bean) {
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
    public void cargasInicial(ComercialBean bean) {
        try {
            //Tipo Documento
            bean.setMapMaeTiposDocumento(getMaestroRemoto().consultarHashPorTipo(GnMaestroTipo.GN_TIPO_DOCUMENTO));
            bean.setListaMaeTiposDocumento(new ArrayList<>(bean.getMapMaeTiposDocumento().values()));
            //Sedes
            bean.setListaSedes(getSedeRemoto().consultarTodas());
            //Usuarios
            bean.setListaUsuarios(getUsuarioRemoto().consultarPorEmpresa(bean.getConexion().getEmpresa().getId()));
            //Ubicaciones
            bean.setUbicacionesRecursiva(getUbicacionRemoto().consultarMunicipios());
            bean.setUbicaciones(getUbicacionRemoto().consultarPorTipo(Ubicacion.TIPO_MUNICIPIO));
        } catch (Exception e) {
            bean.addError("No fue posible cargar las listas de apoyo");
        }
    }

    private void mapearMaestros(UnComercial obj, ComercialBean bean) {
        // Tipo Documento
        if (bean.getMapMaeTiposDocumento() != null) {
            GnMaestro tipoDoc = bean.getMapMaeTiposDocumento().get(obj.getMaeTipoDocumentoId());
            obj.setMaeTipoDocumentoTipo(tipoDoc.getTipo());
            obj.setMaeTipoDocumentoCodigo(tipoDoc.getValor());
            obj.setMaeTipoDocumentoValor(tipoDoc.getDescripcion());
        }
    }

}
