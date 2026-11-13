/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.gestion.bean;

import co.com.systemtech.unimos.dominio.administracion.GnMaestro;
import co.com.systemtech.unimos.dominio.administracion.Modulo;
import co.com.systemtech.unimos.dominio.contrato.UnContratoDetalle;
import co.com.systemtech.unimos.dominio.gestion.UnFidelizacion;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.dominio.gestion.UnFidelizacionTecnologia;
import co.com.systemtech.unimos.dominio.registro.UnPersona;
import co.com.systemtech.unimos.web.gestion.servicio.FidelizacionServicioIface;
import co.com.systemtech.unimos.web.utilidades.Url;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import co.com.systemtech.unimos.web.utilidades.CompatibilidadPF;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
@ManagedBean
@ViewScoped
public class FidelizacionBean extends Url {

    private FidelizacionServicioIface fidelizacionServicio;
    private UnFidelizacion objeto;
    private List<UnFidelizacion> registros;
    private LazyDataModel<UnFidelizacion> lazyRegistros;

//    private List<GnMaestro> listaMaeTiposDocumento;
    private List<GnMaestro> listaMaeTiposSuscripcion;
    private List<GnMaestro> listaMaePlanes;
    private List<GnMaestro> listaMaeServicioHabilitacion;

    public FidelizacionBean() {
        this.objeto = new UnFidelizacion();
        Modulo _mod = super.validarModulo(Modulo.ID_FIDELIZACIONES);
        super.getParamConsulta().setEmpresaId(super.getConexion().getEmpresa().getId());
        if (_mod == null) {
            super.redireccionar("/unimos/home.faces");
        } else {
            super.setModulo(_mod);

            lazyRegistros = new LazyDataModel<UnFidelizacion>() {

                private List<UnFidelizacion> lista;

                @Override
                public int count(Map<String, FilterMeta> filtros) {
                    return getParamConsulta().getCantidadRegistros();
                }

                @Override
                public List<UnFidelizacion> load(int primerRegistro, int registrosPagina, Map<String, SortMeta> listaOrdenes, Map<String, FilterMeta> filtros) {
                    getParamConsulta().setPrimerRegistro(primerRegistro);
                    getParamConsulta().setRegistrosPagina(registrosPagina);
                    getParamConsulta().setListaOrden(CompatibilidadPF.convertirFiltrosOrdenToHash(listaOrdenes));
                    getParamConsulta().setFiltros(CompatibilidadPF.ConvertirFiltroMetaToHash(filtros));
                    refrescar();
                    lista = getRegistros();
                    setRowCount(getParamConsulta().getCantidadRegistros());
                    return lista;
                }

                @Override
                public String getRowKey(UnFidelizacion objeto) {
                    return objeto.getId().toString();
                }

                @Override
                public UnFidelizacion getRowData(String objetoId) {
                    Integer id = Integer.valueOf(objetoId);
                    for (UnFidelizacion objeto : lista) {
                        if (id.equals(objeto.getId())) {
                            return objeto;
                        }
                    }
                    return null;
                }

            };

            lazyPersonas = new LazyDataModel<UnPersona>() {
                private List<UnPersona> personas;

                @Override
                public int count(Map<String, FilterMeta> filtros) {
                    return getParamConsultaPersonas().getCantidadRegistros();
                }

                @Override
                public List<UnPersona> load(int primerRegistro, int registrosPagina, Map<String, SortMeta> listaOrdenes, Map<String, FilterMeta> filtros) {
                    //usamos el mismo getParamConsulta pero validamos si debemos cambiarlo
                    getParamConsultaPersonas().setPrimerRegistro(primerRegistro);
                    getParamConsultaPersonas().setRegistrosPagina(registrosPagina);
                    getParamConsultaPersonas().setListaOrden(CompatibilidadPF.convertirFiltrosOrdenToHash(listaOrdenes));
                    getParamConsultaPersonas().setFiltros(CompatibilidadPF.ConvertirFiltroMetaToHash(filtros));
                    refrescarPersona();
                    personas = getListaPersonas();
                    setRowCount(getParamConsultaPersonas().getCantidadRegistros());
                    return personas;
                }

                @Override
                public String getRowKey(UnPersona perosna) {
                    return perosna.getId().toString();
                }

                @Override
                public UnPersona getRowData(String perosnaId) {
                    Integer id = Integer.valueOf(perosnaId);
                    for (UnPersona persona : personas) {
                        if (id.equals(persona.getId())) {
                            return persona;
                        }
                    }
                    return null;
                }
            };

            lazyContratoDetalles = new LazyDataModel<UnContratoDetalle>() {
                private List<UnContratoDetalle> contratoDetalles;

                @Override
                public int count(Map<String, FilterMeta> filtros) {
                    return getParamConsultaContratoDetalles().getCantidadRegistros();
                }

                @Override
                public List<UnContratoDetalle> load(int primerRegistro, int registrosPagina, Map<String, SortMeta> listaOrdenes, Map<String, FilterMeta> filtros) {
                    //usamos el mismo getParamConsulta pero validamos si debemos cambiarlo
                    getParamConsultaContratoDetalles().setPrimerRegistro(primerRegistro);
                    getParamConsultaContratoDetalles().setRegistrosPagina(registrosPagina);
                    getParamConsultaContratoDetalles().setListaOrden(CompatibilidadPF.convertirFiltrosOrdenToHash(listaOrdenes));
                    getParamConsultaContratoDetalles().setFiltros(CompatibilidadPF.ConvertirFiltroMetaToHash(filtros));
                    refrescarContratoDetalle();
                    contratoDetalles = getListaContratoDetalles();
                    setRowCount(getParamConsultaContratoDetalles().getCantidadRegistros());
                    return contratoDetalles;
                }

                @Override
                public String getRowKey(UnContratoDetalle contratoDetalle) {
                    return contratoDetalle.getId().toString();
                }

                @Override
                public UnContratoDetalle getRowData(String contratoDetalleId) {
                    Integer id = Integer.valueOf(contratoDetalleId);
                    for (UnContratoDetalle contratoDetalle : contratoDetalles) {
                        if (id.equals(contratoDetalle.getId())) {
                            return contratoDetalle;
                        }
                    }
                    return null;
                }
            };
        }
    }

    @PostConstruct
    public void postConstruct() {
        getFidelizacionServicio().cargasInicial(this);
        listar();
    }

    public UnFidelizacion getObjeto() {
        return objeto;
    }

    public void setObjeto(UnFidelizacion objeto) {
        this.objeto = objeto;
    }

    public List<UnFidelizacion> getRegistros() {
        return registros;
    }

    public void setRegistros(List<UnFidelizacion> registros) {
        this.registros = registros;
    }

    public LazyDataModel<UnFidelizacion> getLazyRegistros() {
        return lazyRegistros;
    }

    public void setLazyRegistros(LazyDataModel<UnFidelizacion> lazyRegistros) {
        this.lazyRegistros = lazyRegistros;
    }

//    public List<GnMaestro> getListaMaeTiposDocumento() {
//        return listaMaeTiposDocumento;
//    }
//
//    public void setListaMaeTiposDocumento(List<GnMaestro> listaMaeTiposDocumento) {
//        this.listaMaeTiposDocumento = listaMaeTiposDocumento;
//    }
    public List<GnMaestro> getListaMaeTiposSuscripcion() {
        return listaMaeTiposSuscripcion;
    }

    public void setListaMaeTiposSuscripcion(List<GnMaestro> listaMaeTiposSuscripcion) {
        this.listaMaeTiposSuscripcion = listaMaeTiposSuscripcion;
    }

    public List<GnMaestro> getListaMaePlanes() {
        return listaMaePlanes;
    }

    public void setListaMaePlanes(List<GnMaestro> listaMaePlanes) {
        this.listaMaePlanes = listaMaePlanes;
    }

    public List<GnMaestro> getListaMaeServicioHabilitacion() {
        return listaMaeServicioHabilitacion;
    }

    public void setListaMaeServicioHabilitacion(List<GnMaestro> listaMaeServicioHabilitacion) {
        this.listaMaeServicioHabilitacion = listaMaeServicioHabilitacion;
    }

    public FidelizacionServicioIface getFidelizacionServicio() {
        return fidelizacionServicio;
    }

    public void setFidelizacionServicio(FidelizacionServicioIface fidelizacionServicio) {
        this.fidelizacionServicio = fidelizacionServicio;
    }

    public void listar() {
        super.setAccion(ACCION_LISTAR);
        procesoFinal();
    }

    public void refrescar() {
        super.setAccion(Url.ACCION_LISTAR);
        getFidelizacionServicio().Accion(this);
    }

    public void ver(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_VER);
        getFidelizacionServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmVer");
        PrimeFaces.current().executeScript("PF('dialogoVer').show()");
        procesoFinal();
    }

    public void crear() {
        super.setAccion(ACCION_CREAR);
        getFidelizacionServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmCrear");
        PrimeFaces.current().executeScript("PF('dialogoCrear').show()");
        procesoFinal();
    }

    public void guardar() {
        super.setAccion(ACCION_GUARDAR);
        getFidelizacionServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoCrear').hide();");
        }
        procesoFinal();
    }

    public void editar(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_EDITAR);
        getFidelizacionServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmEditar");
        PrimeFaces.current().executeScript("PF('dialogoEditar').show()");
        procesoFinal();
    }

    public void modificar() {
        super.setAccion(ACCION_MODIFICAR);
        getFidelizacionServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoEditar').hide();");
        }
        procesoFinal();
    }

    public void gestionar(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_ADICIONAL_1);
        super.setDoAccion(ACCION_EDITAR);
        getFidelizacionServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmGestionar");
        PrimeFaces.current().executeScript("PF('dialogoGestionar').show()");
        procesoFinal();
    }

    public void aplicarGestion() {
        super.setAccion(ACCION_ADICIONAL_1);
        super.setDoAccion(ACCION_MODIFICAR);
        getFidelizacionServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoGestionar').hide();");
        }
        procesoFinal();
    }

    public void borrar(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_BORRAR);
        getFidelizacionServicio().Accion(this);
        procesoFinal();
    }

    /*
     * Procesos de Fidelizacion Tecnologias
     */
    private UnFidelizacionTecnologia objetoFidelizacionTecnologia;

    public UnFidelizacionTecnologia getObjetoFidelizacionTecnologia() {
        return objetoFidelizacionTecnologia;
    }

    public void setObjetoFidelizacionTecnologia(UnFidelizacionTecnologia objetoFidelizacionTecnologia) {
        this.objetoFidelizacionTecnologia = objetoFidelizacionTecnologia;
    }

    public void fidelizacionTecnologiaCrear() {
        this.setObjetoFidelizacionTecnologia(new UnFidelizacionTecnologia());
        PrimeFaces.current().ajax().update("frmCrearFidelizacionTecnologia");
        PrimeFaces.current().executeScript("PF('dialogoCrearFidelizacionTecnologia').show()");
    }

    public void fidelizacionTecnologiaAgregar() {
        this.getObjeto().addUnFidelizacionTecnologia(this.getObjetoFidelizacionTecnologia());
        PrimeFaces.current().ajax().update("frmCrear:tablaFidelizacionTecnologias");
//        PrimeFaces.current().ajax().update("frmEditar:tablaFidelizacionTecnologias");
        PrimeFaces.current().executeScript("PF('dialogoCrearFidelizacionTecnologia').hide()");
    }

    public void fidelizacionTecnologiaRetirar(int id) {
        List<UnFidelizacionTecnologia> lista = new ArrayList();
        for (UnFidelizacionTecnologia obj : this.getObjeto().getListaUnFidelizacionTecnologias()) {
            if (obj.getEstadoRegistro() == UnFidelizacionTecnologia.ESTADO_REGISTRO_CREADO && obj.getId() == id) {
                obj.setEstadoRegistro(UnFidelizacionTecnologia.ESTADO_REGISTRO_RETIRAR);
            }
            lista.add(obj);
        }
        this.getObjeto().setListaUnFidelizacionTecnologias(lista);
        PrimeFaces.current().ajax().update("frmCrear:tablaFidelizacionTecnologias");
        PrimeFaces.current().ajax().update("frmEditar:tablaFidelizacionTecnologias");
    }

    public void fidelizacionTecnologiaBorrar(String codigo) {
        List<UnFidelizacionTecnologia> lista = new ArrayList();
        for (UnFidelizacionTecnologia obj : this.getObjeto().getListaUnFidelizacionTecnologias()) {
            if (obj.getEstadoRegistro() == UnFidelizacionTecnologia.ESTADO_REGISTRO_CREAR && obj.getUnTecnologia().getCodigo().equals(codigo)) {

            } else {
                lista.add(obj);
            }
        }
        this.getObjeto().setListaUnFidelizacionTecnologias(lista);
        PrimeFaces.current().ajax().update("frmCrear:tablaFidelizacionTecnologias");
        PrimeFaces.current().ajax().update("frmEditar:tablaFidelizacionTecnologias");
    }

    public void fidelizacionTecnologiaVer(UnFidelizacionTecnologia obj) {
        this.setObjetoFidelizacionTecnologia(obj);
        PrimeFaces.current().ajax().update("frmVerFidelizacionTecnologia");
        PrimeFaces.current().executeScript("PF('dialogoVerFidelizacionTecnologia').show()");
    }

    public void fidelizacionTecnologiaGestionar(int id, int estado) {
        List<UnFidelizacionTecnologia> lista = new ArrayList();
        for (UnFidelizacionTecnologia obj : this.getObjeto().getListaUnFidelizacionTecnologias()) {
            if (obj.getId() == id) {
                obj.setEstadoRegistro(UnFidelizacionTecnologia.ESTADO_REGISTRO_GESTIONADO);
                obj.setEstado(estado);
            }
            lista.add(obj);
        }
        this.getObjeto().setListaUnFidelizacionTecnologias(lista);
        PrimeFaces.current().ajax().update("frmGestionar:tablaFidelizacionTecnologias");
        PrimeFaces.current().ajax().update("frmGestionar:tablaFidelizacionTecnologias");
    }

    /*
     * Procesos de Personas
     */
    private ParamConsulta paramConsultaPersonas;
    private LazyDataModel<UnPersona> lazyPersonas;
//    private UnPersona persona;
    private List<UnPersona> listaPersonas;

    public ParamConsulta getParamConsultaPersonas() {
        return paramConsultaPersonas;
    }

    public void setParamConsultaPersonas(ParamConsulta paramConsultaPersonas) {
        this.paramConsultaPersonas = paramConsultaPersonas;
    }

    public LazyDataModel<UnPersona> getLazyPersonas() {
        return lazyPersonas;
    }

    public void setLazyPersonas(LazyDataModel<UnPersona> lazyPersonas) {
        this.lazyPersonas = lazyPersonas;
    }

//    public UnPersona getPersona() {
//        return persona;
//    }
//
//    public void setPersona(UnPersona persona) {
//        this.persona = persona;
//    }
    public List<UnPersona> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(List<UnPersona> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    public void refrescarPersona() {
        getFidelizacionServicio().listarPersonas(this);
    }

    public void listarPersonas() {
        this.setParamConsultaPersonas(new ParamConsulta());
//        this.getParamConsultaPersonas().setEmpresaId(super.getConexion().getEmpresa().getId());
        objeto.setPersonaAsignada(false);
        PrimeFaces.current().ajax().update("frmPersonaListar:tablaRegistros");
        PrimeFaces.current().executeScript("PF('dialogoPersonaListar').show()");
    }

    public void onRowPersonaSelect(SelectEvent event) {
//        this.setPersona((UnPersona) event.getObject());
        UnPersona unPer = (UnPersona) event.getObject();
        this.getObjeto().setUnPersona(unPer);
        this.getObjeto().setUnSuscripcion(unPer.getUnSuscripcion());
        this.paramConsultaPersonas.setFiltros(new HashMap());
//        PrimeFaces.current().ajax().update("frmPersonaListar:tablaRegistros");
        PrimeFaces.current().executeScript("PF('dialogoPersonaListar').hide();");
        PrimeFaces.current().ajax().update("frmCrear:panelPersona");
    }

    /*
     * Procesos de Tecnologías
     */
    private ParamConsulta paramConsultaContratoDetalles;
    private LazyDataModel<UnContratoDetalle> lazyContratoDetalles;
//    private UnContratoDetalle contratoDetalle;
    private List<UnContratoDetalle> listaContratoDetalles;

    public ParamConsulta getParamConsultaContratoDetalles() {
        return paramConsultaContratoDetalles;
    }

    public void setParamConsultaContratoDetalles(ParamConsulta paramConsultaContratoDetalles) {
        this.paramConsultaContratoDetalles = paramConsultaContratoDetalles;
    }

    public LazyDataModel<UnContratoDetalle> getLazyContratoDetalles() {
        return lazyContratoDetalles;
    }

    public void setLazyContratoDetalles(LazyDataModel<UnContratoDetalle> lazyContratoDetalles) {
        this.lazyContratoDetalles = lazyContratoDetalles;
    }

//    public UnContratoDetalle getContratoDetalle() {
//        return contratoDetalle;
//    }
//
//    public void setContratoDetalle(UnContratoDetalle contratoDetalle) {
//        this.contratoDetalle = contratoDetalle;
//    }
    public List<UnContratoDetalle> getListaContratoDetalles() {
        return listaContratoDetalles;
    }

    public void setListaContratoDetalles(List<UnContratoDetalle> listaContratoDetalles) {
        this.listaContratoDetalles = listaContratoDetalles;
    }

    public void refrescarContratoDetalle() {
        getFidelizacionServicio().listarContratoDetalles(this);
    }

    public void listarContratoDetalle() {
        this.setParamConsultaContratoDetalles(new ParamConsulta());
//        PrimeFaces.current().ajax().update("frmContratoDetallesListar");
        PrimeFaces.current().ajax().update("frmContratoDetallesListar:tablaRegistros");
        PrimeFaces.current().executeScript("PF('dialogoContratoDetallesListar').show()");
    }

    public void onRowContratoDetalleSelect(SelectEvent event) {
//        this.setContratoDetalle((UnContratoDetalle) event.getObject());
        UnContratoDetalle conDet = (UnContratoDetalle) event.getObject();
        this.getObjetoFidelizacionTecnologia().setUnContrato(conDet.getUnContrato());
        this.getObjetoFidelizacionTecnologia().setUnTecnologia(conDet.getUnTecnologia());
        this.getObjetoFidelizacionTecnologia().setUnEntidad(conDet.getUnContrato().getUnEntidad());
        this.getObjetoFidelizacionTecnologia().setValorSuscriptor(conDet.getValorSuscriptor());
        this.getObjetoFidelizacionTecnologia().setValorConvenio(conDet.getValorConvenio());
        this.getObjetoFidelizacionTecnologia().setEstado(UnFidelizacionTecnologia.ESTADO_AGENDADO);
//        PrimeFaces.current().ajax().update("frmContratoDetallesListar:tablaRegistros");
        PrimeFaces.current().executeScript("PF('dialogoContratoDetallesListar').hide();");
        PrimeFaces.current().ajax().update("frmCrearFidelizacionTecnologia:panelFidelizacionContratoDetalle");
    }

    /*
    * Proceos final
     */
    private void procesoFinal() {
        if (!super.isError()) {
            switch (getAccion()) {
                case Url.ACCION_GUARDAR:
                case Url.ACCION_MODIFICAR:
                case Url.ACCION_BORRAR:
                case Url.ACCION_LISTAR:
                    PrimeFaces.current().ajax().update("frmFidelizaciones");
                case Url.ACCION_VER:
                case Url.ACCION_CREAR:
                case Url.ACCION_EDITAR:
                    crearLog(getObjeto().toString());
                    break;
                case Url.ACCION_ADICIONAL_1:
                    switch (getDoAccion()) {
                        case Url.ACCION_MODIFICAR:
                            PrimeFaces.current().ajax().update("frmFidelizaciones");
                            break;
                    }
                    break;
            }
        }
        generarMensajes();
    }

}
