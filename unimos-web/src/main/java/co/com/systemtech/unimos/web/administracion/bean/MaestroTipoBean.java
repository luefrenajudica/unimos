/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.administracion.bean;

import co.com.systemtech.unimos.dominio.administracion.Modulo;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroTipo;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroTipoRelacion;
import co.com.systemtech.unimos.dominio.administracion.Perfil;
import co.com.systemtech.unimos.web.administracion.servicio.MaestroTipoServicioIface;
import co.com.systemtech.unimos.web.utilidades.Url;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import co.com.systemtech.unimos.web.utilidades.CompatibilidadPF;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author rpalacic (SystemTech Integral)
 */
@ManagedBean
@ViewScoped
public class MaestroTipoBean extends Url {

    private MaestroTipoServicioIface maestroTipoServicio;
    private GnMaestroTipo objeto;
    private List<GnMaestroTipo> registros;
    private LazyDataModel<GnMaestroTipo> lazyMaestroTipo;

    private List<GnMaestroTipo> listaMaestroTiposPadre;
    private List<Perfil> listaPerfiles;

    private List<String> selectedMaestrosPadres = new ArrayList();
    private List<GnMaestroTipoRelacion> listaMaestroTipoRelacion;

    public MaestroTipoBean() {
        this.objeto = new GnMaestroTipo();
        Modulo _mod = super.validarModulo(Modulo.ID_MAESTRO_TIPOS);
        if (_mod == null) {
            super.redireccionar("/savia/home.faces");
        } else {
            super.setModulo(_mod);
            lazyMaestroTipo = new LazyDataModel<GnMaestroTipo>() {

                private List<GnMaestroTipo> lista;

                @Override
                public int count(Map<String, FilterMeta> filtros) {
                    return getParamConsulta().getCantidadRegistros();
                }

                @Override
                public List<GnMaestroTipo> load(int primerRegistro, int registrosPagina, Map<String, SortMeta> listaOrdenes, Map<String, FilterMeta> filtros) {
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
                public String getRowKey(GnMaestroTipo objeto) {
                    return objeto.getTipo();
                }

                @Override
                public GnMaestroTipo getRowData(String objetoTipo) {
                    for (GnMaestroTipo objeto : lista) {
                        if (objetoTipo.equals(objeto.getTipo())) {
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
        getMaestroTipoServicio().cargasInicial(this);
        listar();
    }

    public GnMaestroTipo getObjeto() {
        return objeto;
    }

    public void setObjeto(GnMaestroTipo objeto) {
        this.objeto = objeto;
    }

    public List<GnMaestroTipo> getRegistros() {
        return registros;
    }

    public void setRegistros(List<GnMaestroTipo> registros) {
        this.registros = registros;
    }

    public LazyDataModel<GnMaestroTipo> getLazyMaestroTipo() {
        return lazyMaestroTipo;
    }

    public void setLazyMaestro(LazyDataModel<GnMaestroTipo> lazyMaestroTipo) {
        this.lazyMaestroTipo = lazyMaestroTipo;
    }

    public List<GnMaestroTipo> getListaMaestroTiposPadre() {
        return listaMaestroTiposPadre;
    }

    public void setListaMaestroTiposPadre(List<GnMaestroTipo> listaMaestroTiposPadre) {
        this.listaMaestroTiposPadre = listaMaestroTiposPadre;
    }

    public List<Perfil> getListaPerfiles() {
        return listaPerfiles;
    }

    public void setListaPerfiles(List<Perfil> listaPerfiles) {
        this.listaPerfiles = listaPerfiles;
    }

    public MaestroTipoServicioIface getMaestroTipoServicio() {
        return maestroTipoServicio;
    }

    public void setMaestroTipoServicio(MaestroTipoServicioIface maestroTipoServicio) {
        this.maestroTipoServicio = maestroTipoServicio;
    }

    public List<String> getSelectedMaestrosPadres() {
        return selectedMaestrosPadres;
    }

    public void setSelectedMaestrosPadres(List<String> selectedMaestrosPadres) {
        this.selectedMaestrosPadres = selectedMaestrosPadres;
    }

    public List<GnMaestroTipoRelacion> getListaMaestroTipoRelacion() {
        return listaMaestroTipoRelacion;
    }

    public void setListaMaestroTipoRelacion(List<GnMaestroTipoRelacion> listaMaestroTipoRelacion) {
        this.listaMaestroTipoRelacion = listaMaestroTipoRelacion;
    }

    public void listar() {
        super.setAccion(ACCION_LISTAR);
        procesoFinal();
    }

    public void refrescar() {
        super.setAccion(Url.ACCION_LISTAR);
        getMaestroTipoServicio().Accion(this);
    }

    public void ver(String tipo) {
        getObjeto().setTipo(tipo);
        super.setAccion(ACCION_VER);
        getMaestroTipoServicio().Accion(this);        
        procesoFinal();
    }

    public void editar(String tipo) {
        getObjeto().setTipo(tipo);
        setSelectedMaestrosPadres(new ArrayList());
        super.setAccion(ACCION_EDITAR);
        getMaestroTipoServicio().Accion(this);
        PrimeFaces.current().resetInputs("frmEditar:panelEditar");
        PrimeFaces.current().ajax().update("frmEditar:panelEditar");
        PrimeFaces.current().executeScript("PF('dialogoEditar').show()");
        procesoFinal();
    }

    public void modificar() {
        super.setAccion(ACCION_MODIFICAR);
        getMaestroTipoServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoEditar').hide();");
        }
        procesoFinal();
    }

    private void procesoFinal() {
        if (!super.isError()) {
            switch (getAccion()) {
                case Url.ACCION_GUARDAR:
                case Url.ACCION_MODIFICAR:
                case Url.ACCION_BORRAR:
                case Url.ACCION_LISTAR:
                    PrimeFaces.current().ajax().update("frmMaestroTipos");
                case Url.ACCION_CREAR:
                case Url.ACCION_EDITAR:
                    crearLog(getObjeto().toString());
                    break;
                case Url.ACCION_VER:
                    PrimeFaces.current().ajax().update("frmVer:panelVer");
                    PrimeFaces.current().executeScript("PF('dialogoVer').show()");
                    crearLog(getObjeto().toString());
                    break;
            }
        }
        generarMensajes();
    }

}
