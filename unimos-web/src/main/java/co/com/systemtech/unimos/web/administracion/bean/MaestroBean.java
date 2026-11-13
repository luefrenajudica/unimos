/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.administracion.bean;

import co.com.systemtech.unimos.dominio.administracion.GnMaestroAccion;
import co.com.systemtech.unimos.dominio.administracion.Modulo;
import co.com.systemtech.unimos.dominio.administracion.GnMaestro;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroRelacion;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroTipo;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroTipoRelacion;
import co.com.systemtech.unimos.web.utilidades.Url;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import co.com.systemtech.unimos.web.administracion.servicio.MaestroServicioIface;
import co.com.systemtech.unimos.web.utilidades.CompatibilidadPF;
import java.util.ArrayList;
import java.util.HashMap;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rpalacic (SystemTech Integral)
 */
@ManagedBean
@ViewScoped
public class MaestroBean extends Url {

    @Autowired
    private MaestroServicioIface maestroServicio;
    private List<GnMaestroAccion> listaAcciones;
    private GnMaestro objeto;
    private List<GnMaestro> registros;
    private LazyDataModel<GnMaestro> lazyMaestro;

    private List<GnMaestroTipo> listaTipoMaestros;
    private List<GnMaestro> listaDependencias;
    private HashMap<Integer, GnMaestro> hashListaDependencias;

    private List<GnMaestroAccion> listaTipoAcciones;
    private HashMap<Integer, GnMaestroAccion> hashListaTipoAcciones;
    private List<Integer> selectedMaestrosHijos = new ArrayList();
    private List<String> selectedMaestrosPadres = new ArrayList();
    private List<GnMaestroTipoRelacion> listaMaestroTipoRelacion;
    private List<GnMaestro> listaMaestrosConsultados;
    private List<GnMaestroRelacion> selectedMaestros;
    private List<GnMaestroTipo> listaMaestroTiposPadre;
    private List<Integer> selectMaestroAcciones;

    public MaestroBean() {
        this.objeto = new GnMaestro();
        listaMaestrosConsultados = new ArrayList();
        Modulo _mod = super.validarModulo(Modulo.ID_MAESTROS);
        if (_mod == null) {
            super.redireccionar("/unimos/home.faces");
        } else {
            super.setModulo(_mod);
            lazyMaestro = new LazyDataModel<GnMaestro>() {

                private List<GnMaestro> lista;

                @Override
                public int count(Map<String, FilterMeta> filtros) {
                    return getParamConsulta().getCantidadRegistros();
                }

                @Override
                public List<GnMaestro> load(int primerRegistro, int registrosPagina, Map<String, SortMeta> listaOrdenes, Map<String, FilterMeta> filtros) {
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
                public String getRowKey(GnMaestro objeto) {
                    return objeto.getId().toString();
                }

                @Override
                public GnMaestro getRowData(String objetoId) {
                    Integer id = Integer.valueOf(objetoId);
                    for (GnMaestro objeto : lista) {
                        if (id.equals(objeto.getId())) {
                            return objeto;
                        }
                    }
                    return null;
                }

            };
        }
    }

    @PostConstruct
    public void postConstruct() {
        getMaestroServicio().cargasInicial(this);
        listar();
    }

    public GnMaestro getObjeto() {
        return objeto;
    }

    public void setObjeto(GnMaestro objeto) {
        this.objeto = objeto;
    }

    public List<GnMaestro> getRegistros() {
        return registros;
    }

    public void setRegistros(List<GnMaestro> registros) {
        this.registros = registros;
    }

    public LazyDataModel<GnMaestro> getLazyMaestro() {
        return lazyMaestro;
    }

    public void setLazyMaestro(LazyDataModel<GnMaestro> lazyMaestro) {
        this.lazyMaestro = lazyMaestro;
    }

    public List<GnMaestroTipo> getListaTipoMaestros() {
        return listaTipoMaestros;
    }

    public void setListaTipoMaestros(List<GnMaestroTipo> listaTipoMaestros) {
        this.listaTipoMaestros = listaTipoMaestros;
    }

    public List<GnMaestro> getListaDependencias() {
        return listaDependencias;
    }

    public void setListaDependencias(List<GnMaestro> listaDependencias) {
        this.listaDependencias = listaDependencias;
    }

    public HashMap<Integer, GnMaestro> getHashListaDependencias() {
        return hashListaDependencias;
    }

    public void setHashListaDependencias(HashMap<Integer, GnMaestro> hashListaDependencias) {
        this.hashListaDependencias = hashListaDependencias;
    }

    public List<GnMaestroAccion> getListaTipoAcciones() {
        return listaTipoAcciones;
    }

    public void setListaTipoAcciones(List<GnMaestroAccion> listaTipoAcciones) {
        this.listaTipoAcciones = listaTipoAcciones;
    }

    public HashMap<Integer, GnMaestroAccion> getHashListaTipoAcciones() {
        return hashListaTipoAcciones;
    }

    public void setHashListaTipoAcciones(HashMap<Integer, GnMaestroAccion> hashListaTipoAcciones) {
        this.hashListaTipoAcciones = hashListaTipoAcciones;
    }

    public List<GnMaestroAccion> getListaAcciones() {
        return listaAcciones;
    }

    public void setListaAcciones(List<GnMaestroAccion> listaAcciones) {
        this.listaAcciones = listaAcciones;
    }

    public List<Integer> getSelectedMaestrosHijos() {
        return selectedMaestrosHijos;
    }

    public void setSelectedMaestrosHijos(List<Integer> selectedMaestrosHijos) {
        this.selectedMaestrosHijos = selectedMaestrosHijos;
    }

    public List<GnMaestroTipoRelacion> getListaMaestroTipoRelacion() {
        return listaMaestroTipoRelacion;
    }

    public void setListaMaestroTipoRelacion(List<GnMaestroTipoRelacion> listaMaestroTipoRelacion) {
        this.listaMaestroTipoRelacion = listaMaestroTipoRelacion;
    }

    public List<GnMaestro> getListaMaestrosConsultados() {
        return listaMaestrosConsultados;
    }

    public void setListaMaestrosConsultados(List<GnMaestro> listaMaestrosConsultados) {
        this.listaMaestrosConsultados = listaMaestrosConsultados;
    }

    public List<GnMaestroRelacion> getSelectedMaestros() {
        return selectedMaestros;
    }

    public void setSelectedMaestros(List<GnMaestroRelacion> selectedMaestros) {
        this.selectedMaestros = selectedMaestros;
    }

    public List<GnMaestroTipo> getListaMaestroTiposPadre() {
        return listaMaestroTiposPadre;
    }

    public void setListaMaestroTiposPadre(List<GnMaestroTipo> listaMaestroTiposPadre) {
        this.listaMaestroTiposPadre = listaMaestroTiposPadre;
    }

    public List<String> getSelectedMaestrosPadres() {
        return selectedMaestrosPadres;
    }

    public void setSelectedMaestrosPadres(List<String> selectedMaestrosPadres) {
        this.selectedMaestrosPadres = selectedMaestrosPadres;
    }

    public List<Integer> getSelectMaestroAcciones() {
        return selectMaestroAcciones;
    }

    public void setSelectMaestroAcciones(List<Integer> selectMaestroAcciones) {
        this.selectMaestroAcciones = selectMaestroAcciones;
    }

    public void cargarAcciones() {
    }

    public MaestroServicioIface getMaestroServicio() {
        return maestroServicio;
    }

    public void setMaestroServicio(MaestroServicioIface maestroServicio) {
        this.maestroServicio = maestroServicio;
    }

    public void listar() {
        super.setAccion(ACCION_LISTAR);
        setSelectedMaestrosHijos(new ArrayList());
        procesoFinal();
    }

    public void refrescar() {
        super.setAccion(Url.ACCION_LISTAR);
        getMaestroServicio().Accion(this);
    }

    public void ver(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_VER);
        getMaestroServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmVer:panelVer");
        PrimeFaces.current().executeScript("PF('dialogoVer').show()");
        procesoFinal();
    }

    public void crear() {
        super.setAccion(ACCION_CREAR);
        getMaestroServicio().Accion(this);
        PrimeFaces.current().resetInputs("frmCrear:panelCrear");
        PrimeFaces.current().ajax().update("frmCrear:panelCrear");
        PrimeFaces.current().executeScript("PF('dialogoCrear').show()");
        procesoFinal();
    }

    public void guardar() {
        super.setAccion(ACCION_GUARDAR);
        getMaestroServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoCrear').hide();");
        }
        procesoFinal();
    }

    public void editar(int _id) {
        getObjeto().setId(_id);
        setSelectedMaestrosHijos(new ArrayList());
        super.setAccion(ACCION_EDITAR);
        getMaestroServicio().Accion(this);
        PrimeFaces.current().resetInputs("frmEditar:panelEditar");
        PrimeFaces.current().ajax().update("frmEditar:panelEditar");
        PrimeFaces.current().executeScript("PF('dialogoEditar').show()");
        procesoFinal();
    }

    public void modificar() {
        super.setAccion(ACCION_MODIFICAR);
        getMaestroServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoEditar').hide();");
        }
        procesoFinal();
    }

    public void borrar(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_BORRAR);
        getMaestroServicio().Accion(this);
        procesoFinal();
    }

    public void listarDependencias() {
        getObjeto().getMaestroTipo().setMaestroTipo(getMaestroServicio().consultarDependenciaPadre(getObjeto().getMaestroTipo().getTipo()));
        if (getObjeto().getMaestroTipo().getTipo() != null) {
            setListaDependencias(getMaestroServicio().listaDependencias(getObjeto().getMaestroTipo().getTipo()));
            setListaAcciones(getMaestroServicio().listaAcciones(getObjeto().getMaestroTipo().getTipo()));
        } else {
            setListaDependencias(new ArrayList());
            setListaAcciones(new ArrayList());
        }
    }

    public List<GnMaestro> maestroPorTipo(String maestroTipo) {
        List<GnMaestro> filtro = listaMaestrosConsultados;
        for (GnMaestro maestroFiltro : filtro) {
            if (!maestroFiltro.getTipo().equals(maestroTipo)) {
                filtro.remove(maestroFiltro);
            }
        }
        return filtro;
    }

    public void modificarRelacionHijos(RowEditEvent event) {
        setObjeto((GnMaestro) event.getObject());
        super.setAccion(ACCION_ADICIONAL_1);
        super.setDoAccion(ACCION_MODIFICAR);
        getMaestroServicio().Accion(this);
        generarMensajes();
    }

    private void procesoFinal() {
        if (!super.isError()) {
            switch (getAccion()) {
                case Url.ACCION_GUARDAR:
                case Url.ACCION_MODIFICAR:
                case Url.ACCION_BORRAR:
                case Url.ACCION_LISTAR:
                    PrimeFaces.current().ajax().update("frmMaestros");
                case Url.ACCION_CREAR:
                case Url.ACCION_EDITAR:
                    crearLog(getObjeto().toString());
                    break;
            }
        }
        generarMensajes();
    }

    public String getNombreTipo(String tipo) {
        return listaTipoMaestros.stream()
                .filter(m -> m.getTipo().equals(tipo))
                .map(m -> m.getNombre()) 
                .findFirst()
                .orElse("Tipo Desconocido");

    }

}
