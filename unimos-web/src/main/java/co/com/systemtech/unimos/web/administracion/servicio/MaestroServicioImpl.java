/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.administracion.servicio;

import co.com.systemtech.unimos.web.administracion.bean.MaestroBean;
import co.com.systemtech.unimos.dominio.administracion.GnMaestro;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroAccion;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroAccionRelacion;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroRelacion;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroTipo;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroTipoRelacion;
import co.com.systemtech.unimos.dominio.administracion.Perfil;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.negocio.administracion.MaestroAccionRelacionRemoto;
import co.com.systemtech.unimos.negocio.administracion.MaestroAccionRemoto;
import co.com.systemtech.unimos.negocio.administracion.MaestroRelacionRemoto;
import co.com.systemtech.unimos.negocio.administracion.MaestroRemoto;
import co.com.systemtech.unimos.negocio.administracion.MaestroTipoRelacionRemoto;
import co.com.systemtech.unimos.negocio.administracion.MaestroTipoRemoto;
import co.com.systemtech.unimos.negocio.administracion.PerfilRemoto;
import co.com.systemtech.unimos.web.utilidades.AccionesBO;
import co.com.systemtech.unimos.web.utilidades.RemotoEJB;
import co.com.systemtech.unimos.web.utilidades.Url;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author raul-palacios
 */
public class MaestroServicioImpl extends AccionesBO implements MaestroServicioIface{
    
    private GnMaestro per = new GnMaestro();

    private MaestroRemoto getMaestroRemoto() throws Exception {
        return (MaestroRemoto) RemotoEJB.getEJBRemoto("MaestroServicio", MaestroRemoto.class.getName());
    }
    
    private MaestroTipoRemoto getMaestroTipoRemoto() throws Exception {
        return (MaestroTipoRemoto) RemotoEJB.getEJBRemoto("MaestroTipoServicio", MaestroTipoRemoto.class.getName());
    }
    
    private PerfilRemoto getPerfilRemoto() throws Exception {
        return (PerfilRemoto) RemotoEJB.getEJBRemoto("PerfilServicio", PerfilRemoto.class.getName());
    }

    private MaestroTipoRelacionRemoto getMaestroTipoRelacionRemoto() throws Exception {
        return (MaestroTipoRelacionRemoto) RemotoEJB.getEJBRemoto("MaestroTipoRelacionServicio", MaestroTipoRelacionRemoto.class.getName());
    }

    private MaestroRelacionRemoto getMaestroRelacionRemoto() throws Exception {
        return (MaestroRelacionRemoto) RemotoEJB.getEJBRemoto("MaestroRelacionServicio", MaestroRelacionRemoto.class.getName());
    }
    
    private MaestroAccionRelacionRemoto getMaestroAccionRelacionRemoto() throws Exception {
        return (MaestroAccionRelacionRemoto) RemotoEJB.getEJBRemoto("MaestroAccionRelacionServicio", MaestroAccionRelacionRemoto.class.getName());
    }
    
    private MaestroAccionRemoto getMaestroAccionRemoto() throws Exception {
        return (MaestroAccionRemoto) RemotoEJB.getEJBRemoto("MaestroAccionServicio", MaestroAccionRemoto.class.getName());
    }

    @Override
    public void Accion(MaestroBean bean) {
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
                    break;
                case Url.ACCION_ADICIONAL_2:
                    break;
                case Url.ACCION_ADICIONAL_3:
                    break;
                default:
                    break;
            }
            cargas(bean);
        } else {
            System.err.println("Sesion inactiva");
        }
    }

    private void listar(MaestroBean bean) {
        try {
            ParamConsulta parametros = bean.getParamConsulta();
            if(bean.isAccionAdicional1()){//Ver todos los maestros
                parametros.setParametroConsulta1(null);
            }else{//Solo por perfil
                List<Perfil> listaPerfiles = getPerfilRemoto().consultarListaPorUsuario(bean.getConexion().getUsuario().getId());
                parametros.setParametroConsulta1(listaPerfiles);
            }
            bean.getParamConsulta().setCantidadRegistros(getMaestroRemoto().consultarCantidadLista(parametros));
            bean.setRegistros(getMaestroRemoto().consultarLista(parametros));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void ver(MaestroBean bean) {
        try {
            bean.setObjeto(getMaestroRemoto().consultar(bean.getObjeto().getId()));
            cargarRelaciones(bean);
            for (GnMaestroTipoRelacion maestroTipoRelacion : bean.getListaMaestroTipoRelacion()) {
                maestroTipoRelacion.setMaestros(new ArrayList());
                if (maestroTipoRelacion.getSeleccion() != null) {
                    for (int maestrosId : maestroTipoRelacion.getSeleccion()) {
                        GnMaestro maestro = getMaestroRemoto().consultar(maestrosId);
                        if (maestroTipoRelacion.getMaestroTipoPadre().getTipo().equals(maestro.getTipo())) {
                            maestroTipoRelacion.getMaestros().add(maestro);
                        }
                    }
                }
            }
            List<GnMaestroAccionRelacion> accionRelaciones = getMaestroAccionRelacionRemoto().consultarPorMaestroId(bean.getObjeto().getId());
            List<GnMaestroAccion> acciones = new ArrayList();
            for(GnMaestroAccionRelacion relacion : accionRelaciones) {
                GnMaestroAccion accion = getMaestroAccionRemoto().consultar(relacion.getMaestroAccionId().getId());
                acciones.add(accion);
            }
            bean.getObjeto().setMaestroAcciones(acciones);
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void crear(MaestroBean bean) {
        try {
            bean.setObjeto(new GnMaestro());
            bean.setSelectMaestroAcciones(new ArrayList());
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void editar(MaestroBean bean) {
        try {
            per.setId(bean.getObjeto().getId());
            bean.setObjeto(getMaestroRemoto().consultar(per.getId()));
            bean.setListaMaestrosConsultados(new ArrayList());
            bean.setSelectedMaestros(new ArrayList());
            bean.setListaAcciones(getMaestroRemoto().consultarAccionesPorTipo(bean.getObjeto().getTipo()));
            cargarRelaciones(bean);
            if(!bean.getListaMaestroTipoRelacion().isEmpty()){
                bean.getListaMaestroTipoRelacion().forEach(hijosRelacion -> {
                    List<GnMaestro> listHijo = new ArrayList();
                    bean.getListaMaestrosConsultados().stream().filter(maestros -> 
                            (hijosRelacion.getMaestroTipoPadre().getTipo()
                                    .equals(maestros.getTipo()))).forEachOrdered(maestrosHijos -> {
                        listHijo.add(maestrosHijos);
                    });
                    hijosRelacion.setMaestros(listHijo);
                });          
            }
            List<GnMaestroAccionRelacion> accionRelaciones = getMaestroAccionRelacionRemoto().consultarPorMaestroId(bean.getObjeto().getId());
            bean.setSelectMaestroAcciones(new ArrayList());
            for(GnMaestroAccionRelacion relacion : accionRelaciones) {
                GnMaestroAccion accion = getMaestroAccionRemoto().consultar(relacion.getMaestroAccionId().getId());
                bean.getSelectMaestroAcciones().add(accion.getId());
            }
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }
    
    private void cargarRelaciones(MaestroBean bean){
        try {
            //se listan los masestrosTipoRelacion grabados
            bean.setListaMaestroTipoRelacion(getMaestroTipoRelacionRemoto().consultarPadresPorTipoHijo(bean.getObjeto().getMaestroTipo().getTipo()));
            if (!bean.getListaMaestroTipoRelacion().isEmpty()) {
                bean.getListaMaestroTipoRelacion().forEach(maestroTipoRelacion -> {
                    try {
                        GnMaestroTipo maestroConsulta = getMaestroTipoRemoto().consultar((maestroTipoRelacion.getMaestroTipoPadre().getTipo()));
                        maestroTipoRelacion.getMaestroTipoPadre().setNombre(maestroConsulta.getNombre());
                        //se lista por registro los maestros relacionados con el MaestroTipo
                        List<GnMaestro> listaMaestroHijos = getMaestroRemoto().consultarPorTipo(maestroTipoRelacion.getMaestroTipoPadre().getTipo());
                        //se agregan a la lista principal de maestros a seleccionar
                        listaMaestroHijos.forEach(maestro -> {
                            bean.getListaMaestrosConsultados().add(maestro);
                        });
                    } catch (Exception ex) {
                        Logger.getLogger(MaestroServicioImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                bean.setSelectedMaestros(getMaestroRelacionRemoto().consultarPadresPorIdMaestroHijo(bean.getObjeto().getId()));
                if (!bean.getSelectedMaestros().isEmpty()) {
                    for (GnMaestroTipoRelacion mtr : bean.getListaMaestroTipoRelacion()) {
                        bean.getSelectedMaestros().forEach(maestroRelacion -> {
                            mtr.getSeleccion().add(maestroRelacion.getMaestroPadre().getId());
                        });
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MaestroServicioImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void guardar(MaestroBean bean) {
        try {
            bean.auditoriaGuardar(bean.getObjeto());
            
            bean.setListaMaestroTipoRelacion(getMaestroTipoRelacionRemoto().consultarPadresPorTipoHijo(bean.getObjeto().getMaestroTipo().getTipo()));
            
            bean.getObjeto().setId(getMaestroRemoto().insertar(bean.getObjeto()));
            
            //recorremos la lista de elementos ya guardados
            bean.getListaMaestroTipoRelacion().forEach(maestroTipoRelacion -> {
                //recorre la lista consultada para quitar los elementos seleccionados que ya estan registrados
                bean.getSelectedMaestrosPadres().stream().filter(padreTipoMaestro -> 
                        (maestroTipoRelacion.getMaestroTipoPadre().getTipo()
                                .equals(padreTipoMaestro))).forEachOrdered(padreTipoMaestro -> {
                    bean.getSelectedMaestrosPadres().remove(padreTipoMaestro);
                });
            });

            for (var padreTipoMaestro : bean.getSelectedMaestrosPadres()) {
                //recorre la lista de elementos que se selecciono registrando los que no estan todavia
                GnMaestroTipoRelacion maestroTipoRelaciones = new GnMaestroTipoRelacion();
                for (GnMaestroTipo maestroTipo : bean.getListaMaestroTiposPadre()) {
                    if (padreTipoMaestro.equals(maestroTipo.getTipo())) {
                        maestroTipoRelaciones.setMaestroTipoPadre(maestroTipo);
                        maestroTipoRelaciones.setMaestroTipoHijo(bean.getObjeto().getMaestroTipo());
                        bean.auditoriaGuardar(maestroTipoRelaciones);
                        getMaestroTipoRelacionRemoto().insertar(maestroTipoRelaciones);
                    }
                }
            }
            
            for (Integer accionId : bean.getSelectMaestroAcciones()) {
                GnMaestroAccionRelacion relacion = new GnMaestroAccionRelacion();
                relacion.setMaestroId(bean.getObjeto());
                relacion.setMaestroAccionId(new GnMaestroAccion(accionId));
                bean.auditoriaGuardar(relacion);
                getMaestroAccionRelacionRemoto().insertar(relacion);
            }
            
            bean.setSelectMaestroAcciones(new ArrayList());
            bean.setSelectedMaestrosPadres(new ArrayList());
            bean.addMensaje("Se creo un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void modificar(MaestroBean bean) {
        try {
            bean.auditoriaModificar(bean.getObjeto());
//            busca los elementos que ya no estan para eliminarlos de la base de datos
            if (!bean.getSelectedMaestros().isEmpty()) {
                for (GnMaestroRelacion mr : bean.getSelectedMaestros()) {
                        getMaestroRelacionRemoto().eliminar(mr.getId());
                }
            }
            for (GnMaestroTipoRelacion mestroTipoR : bean.getListaMaestroTipoRelacion()) {
                if (!mestroTipoR.getSeleccion().isEmpty()) {
                    for (int idMaestro : mestroTipoR.getSeleccion()) {
                        GnMaestroRelacion maestroRelaciones = new GnMaestroRelacion();
                        GnMaestro maestro = new GnMaestro();
                        for(GnMaestro maestroFind: mestroTipoR.getMaestros()){
                            if(maestroFind.getId() == idMaestro){
                                maestro = maestroFind;
                            }
                        } 
                        maestroRelaciones.setMaestroPadre(maestro);
                        maestroRelaciones.setMaestroHijo(bean.getObjeto());
                        bean.auditoriaGuardar(maestroRelaciones);
                        getMaestroRelacionRemoto().insertar(maestroRelaciones);
                    }
                }
            }
            //Eliminamos las acciones que se deseleccionan
            List<GnMaestroAccionRelacion> accionRelaciones = getMaestroAccionRelacionRemoto().consultarPorMaestroId(bean.getObjeto().getId());
            List<GnMaestroAccionRelacion> nuevaLista = new ArrayList();
            for(GnMaestroAccionRelacion relacion : accionRelaciones) {
                boolean existe = false;
                for (Integer accionId : bean.getSelectMaestroAcciones()) {
                    if (accionId.equals(relacion.getMaestroAccionId().getId())) {
                        existe = true;
                    }
                }
                if (!existe) {
                    getMaestroAccionRelacionRemoto().eliminar(relacion.getId());
                } else {
                    nuevaLista.add(relacion);
                }                
            }
            for(Integer accionId : bean.getSelectMaestroAcciones()) {
                boolean existe = false;
                for(GnMaestroAccionRelacion relacion : nuevaLista) {
                    if(accionId.equals(relacion.getMaestroAccionId().getId())) {
                        existe = true;
                    }
                }
                if (!existe) {
                    GnMaestroAccionRelacion relacion = new GnMaestroAccionRelacion();
                    relacion.setMaestroId(bean.getObjeto());
                    relacion.setMaestroAccionId(new GnMaestroAccion(accionId));
                    bean.auditoriaGuardar(relacion);
                    getMaestroAccionRelacionRemoto().insertar(relacion);
                }
            }
            
            getMaestroRemoto().actualizar(bean.getObjeto());
            bean.addMensaje("Se actualizó un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void borrar(MaestroBean bean) {
        try {
            per = new GnMaestro();
            per.setId(bean.getObjeto().getId());
            bean.setObjeto(getMaestroRemoto().eliminar(per.getId()));
            bean.addMensaje("Se eliminó un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }
    
    private void cargas(MaestroBean bean) {
        try {
            switch (bean.getAccion()) {
                case Url.ACCION_LISTAR:                    
                    break;
                case Url.ACCION_VER:
                    break;
                case Url.ACCION_CREAR:
                    bean.setListaDependencias(new ArrayList());
                    bean.setListaTipoAcciones(new ArrayList());
                    break;
                case Url.ACCION_EDITAR:
                    bean.setListaDependencias(listaDependencias(bean.getObjeto().getMaestroTipo().getTipo()));
                    bean.setListaTipoAcciones(getMaestroRemoto().consultarAccionesPorTipo(bean.getObjeto().getMaestroTipo().getTipo()));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
        }
    }
    
    @Override
    public void cargasInicial(MaestroBean bean) {
        try {
            List<Perfil> listaPerfiles = null;
            if(!bean.isAccionAdicional1()){//Ver solo los asignados a los perfiles
                listaPerfiles = getPerfilRemoto().consultarListaPorUsuario(bean.getConexion().getUsuario().getId());
            }
            
            List<GnMaestroTipo> listaMaestroTipos = new ArrayList();
            for (GnMaestroTipo obj : getMaestroTipoRemoto().consultarTodos()) {
                    listaMaestroTipos.add(obj);
            }
            bean.setListaTipoMaestros(getMaestroTipoRemoto().consultarTodos());
        } catch (Exception e) {
            bean.addError("No fue posible cargar las listas de apoyo");
        }
    }

    @Override
    public List<GnMaestroTipo> consultarDependenciaPadre(String tipo) {
        List<GnMaestroTipo> padre= new ArrayList();
        try {

            //padre = getMaestroTipoRemoto().consultarPadrePorTipo(tipo);
            List<GnMaestroTipoRelacion> relaciones = getMaestroTipoRelacionRemoto()
                    .consultarPadresPorTipoHijo(tipo);
            if (!relaciones.isEmpty()) {
                for(GnMaestroTipoRelacion padres: relaciones){
                    padre.add(padres.getMaestroTipoPadre());
                }
            }
        } catch (Exception e) {
            padre = null;
        }
        return padre;
    }
    
    @Override
    public List<GnMaestro> listaDependencias(String tipo) {
        List<GnMaestro> lista;
        try {
            lista = getMaestroRemoto().consultarPorTipoHijo(tipo);
        } catch (Exception e) {
            lista = new ArrayList();
        }
        return lista;
    }
    @Override
    public List<GnMaestroAccion> listaAcciones(String tipo) {
        List<GnMaestroAccion> lista;
        try {
            lista = getMaestroRemoto().consultarAccionesPorTipo(tipo);
        } catch (Exception e) {
            lista = new ArrayList();
        }
        return lista;
    }
    
}
