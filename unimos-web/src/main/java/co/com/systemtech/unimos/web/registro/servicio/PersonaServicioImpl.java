/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.registro.servicio;

import co.com.systemtech.unimos.dominio.administracion.GnMaestro;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroTipo;
import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.web.registro.bean.PersonaBean;
import co.com.systemtech.unimos.dominio.registro.UnPersona;
import co.com.systemtech.unimos.dominio.registro.UnPersonaContacto;
import co.com.systemtech.unimos.negocio.administracion.MaestroRemoto;
import co.com.systemtech.unimos.negocio.administracion.UbicacionRemoto;
import co.com.systemtech.unimos.negocio.registro.PersonaRemoto;
import co.com.systemtech.unimos.web.utilidades.AccionesBO;
import co.com.systemtech.unimos.web.utilidades.RemotoEJB;
import co.com.systemtech.unimos.web.utilidades.Url;
import java.util.ArrayList;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class PersonaServicioImpl extends AccionesBO implements PersonaServicioIface {

    private PersonaRemoto getPersonaRemoto() throws Exception {
        return (PersonaRemoto) RemotoEJB.getEJBRemoto("PersonaServicio", PersonaRemoto.class.getName());
    }

    private MaestroRemoto getMaestroRemoto() throws Exception {
        return (MaestroRemoto) RemotoEJB.getEJBRemoto("MaestroServicio", MaestroRemoto.class.getName());
    }

    private UbicacionRemoto getUbicacionRemoto() throws Exception {
        return (UbicacionRemoto) RemotoEJB.getEJBRemoto("UbicacionServicio", UbicacionRemoto.class.getName());
    }

    @Override
    public void Accion(PersonaBean bean) {
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

    private void listar(PersonaBean bean) {
        try {
            bean.getParamConsulta().setCantidadRegistros(getPersonaRemoto().consultarCantidadLista(bean.getParamConsulta()));
            bean.setRegistros(getPersonaRemoto().consultarLista(bean.getParamConsulta()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void ver(PersonaBean bean) {
        try {
            bean.setObjeto(getPersonaRemoto().consultar(bean.getObjeto().getId()));
            bean.getObjeto().setListaUnPersonaContactos(getPersonaRemoto().consultarContactos(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void crear(PersonaBean bean) {
        try {
            bean.setObjeto(new UnPersona());
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void editar(PersonaBean bean) {
        try {
            bean.setObjeto(getPersonaRemoto().consultar(bean.getObjeto().getId()));
            bean.getObjeto().setListaUnPersonaContactos(getPersonaRemoto().consultarContactos(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void guardar(PersonaBean bean) {
        try {
            mapearMaestros(bean.getObjeto(), bean);
            bean.auditoriaGuardar(bean.getObjeto());
            bean.getObjeto().setId(getPersonaRemoto().insertar(bean.getObjeto()));
            //Crear contactos
            if (bean.getObjeto().getListaUnPersonaContactos() != null) {
                for (UnPersonaContacto cont : bean.getObjeto().getListaUnPersonaContactos()) {
                    cont.setUnPersona(new UnPersona(bean.getObjeto().getId()));
                    bean.auditoriaGuardar(cont);
                    getPersonaRemoto().insertarContacto(cont);
                }
            }
            bean.addMensaje("Se creo un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void modificar(PersonaBean bean) {
        try {
            mapearMaestros(bean.getObjeto(), bean);
            bean.auditoriaModificar(bean.getObjeto());
            getPersonaRemoto().actualizar(bean.getObjeto());
            //Actualizar contactos
            if (bean.getObjeto().getListaUnPersonaContactos() != null) {
                for (UnPersonaContacto cont : bean.getObjeto().getListaUnPersonaContactos()) {
                    if (cont.getEstadoRegistro() == UnPersonaContacto.ESTADO_REGISTRO_CREAR) {
                        cont.setUnPersona(new UnPersona(bean.getObjeto().getId()));
                        bean.auditoriaGuardar(cont);
                        getPersonaRemoto().insertarContacto(cont);
                    } else if (cont.getEstadoRegistro() == UnPersonaContacto.ESTADO_REGISTRO_BORRAR) {
                        getPersonaRemoto().eliminarContacto(cont.getId());
                    }
                }
            }
            bean.addMensaje("Se actualizó el registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void borrar(PersonaBean bean) {
        try {
            bean.setObjeto(getPersonaRemoto().eliminar(bean.getObjeto().getId()));
            bean.addMensaje("Se eliminó un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void cargas(PersonaBean bean) {
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
    public void cargasInicial(PersonaBean bean) {
        try {
            //Tipo Documento
            bean.setMapMaeTiposDocumento(getMaestroRemoto().consultarHashPorTipo(GnMaestroTipo.GN_TIPO_DOCUMENTO));
            bean.setListaMaeTiposDocumento(new ArrayList<>(bean.getMapMaeTiposDocumento().values()));
            //Sexo
            bean.setMapMaeSexo(getMaestroRemoto().consultarHashPorTipo(GnMaestroTipo.GN_SEXO));
            bean.setListaMaeSexo(new ArrayList<>(bean.getMapMaeSexo().values()));
            //Estado Civil
            bean.setMapMaeEstadoCivil(getMaestroRemoto().consultarHashPorTipo(GnMaestroTipo.GN_ESTADO_CIVIL));
            bean.setListaMaeEstadoCivil(new ArrayList<>(bean.getMapMaeEstadoCivil().values()));
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

    private void mapearMaestros(UnPersona obj, PersonaBean bean) {
        // Tipo Documento
        if (bean.getMapMaeTiposDocumento() != null) {
            GnMaestro tipoDoc = bean.getMapMaeTiposDocumento().get(obj.getMaeTipoDocumentoId());
            obj.setMaeTipoDocumentoTipo(tipoDoc.getTipo());
            obj.setMaeTipoDocumentoCodigo(tipoDoc.getValor());
            obj.setMaeTipoDocumentoValor(tipoDoc.getDescripcion());
        }
        // Sexo
        if (bean.getMapMaeSexo() != null) {
            GnMaestro sexo = bean.getMapMaeSexo().get(obj.getMaeSexoId());
            obj.setMaeSexoTipo(sexo.getTipo());
            obj.setMaeSexoCodigo(sexo.getValor());
            obj.setMaeSexoValor(sexo.getDescripcion());
        }
        // Sexo
        if (bean.getMapMaeEstadoCivil() != null) {
            GnMaestro estadoCiv = bean.getMapMaeEstadoCivil().get(obj.getMaeEstadoCivilId());
            obj.setMaeEstadoCivilTipo(estadoCiv.getTipo());
            obj.setMaeEstadoCivilCodigo(estadoCiv.getValor());
            obj.setMaeEstadoCivilValor(estadoCiv.getDescripcion());
        }
    }

}
