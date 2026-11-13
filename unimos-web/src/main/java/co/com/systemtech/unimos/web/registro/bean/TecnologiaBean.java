/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.registro.bean;

import co.com.systemtech.unimos.dominio.administracion.GnMaestro;
import co.com.systemtech.unimos.dominio.registro.UnTecnologia;
import co.com.systemtech.unimos.dominio.administracion.Modulo;
import co.com.systemtech.unimos.web.utilidades.Url;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import co.com.systemtech.unimos.web.registro.servicio.TecnologiaServicioIface;
import co.com.systemtech.unimos.web.utilidades.CompatibilidadPF;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.primefaces.PrimeFaces;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
@ManagedBean
@ViewScoped
public class TecnologiaBean extends Url {

    private TecnologiaServicioIface tecnologiaServicio;
    private UnTecnologia objeto;
    private List<UnTecnologia> registros;
    private LazyDataModel<UnTecnologia> lazyTecnologia;
    
    private List<GnMaestro> listaMaeServicioHabilitacion;
    private HashMap<Integer, GnMaestro> mapMaeServicioHabilitacion;

    public TecnologiaBean() {
        this.objeto = new UnTecnologia();
        Modulo _mod = super.validarModulo(Modulo.ID_TECNOLOGIAS);
        super.getParamConsulta().setEmpresaId(super.getConexion().getEmpresa().getId());
        if (_mod == null) {
            super.redireccionar("/unimos/home.faces");
        } else {
            super.setModulo(_mod);
            lazyTecnologia = new LazyDataModel<UnTecnologia>() {

                private List<UnTecnologia> lista;
                
                @Override
                public int count(Map<String, FilterMeta> filtros) {
                    return getParamConsulta().getCantidadRegistros();
                }
                
                @Override
                public List<UnTecnologia> load(int primerRegistro, int registrosPagina, Map<String, SortMeta> listaOrdenes, Map<String, FilterMeta> filtros) {
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
                public String getRowKey(UnTecnologia objeto) {
                    return objeto.getId().toString();
                }

                @Override
                public UnTecnologia getRowData(String objetoId) {
                    Integer id = Integer.valueOf(objetoId);
                    for (UnTecnologia objeto : lista) {
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
        getTecnologiaServicio().cargasInicial(this);
        listar();
    }

    public UnTecnologia getObjeto() {
        return objeto;
    }

    public void setObjeto(UnTecnologia objeto) {
        this.objeto = objeto;
    }

    public List<UnTecnologia> getRegistros() {
        return registros;
    }

    public void setRegistros(List<UnTecnologia> registros) {
        this.registros = registros;
    }

    public LazyDataModel<UnTecnologia> getLazyTecnologia() {
        return lazyTecnologia;
    }

    public void setLazyTecnologia(LazyDataModel<UnTecnologia> lazyTecnologia) {
        this.lazyTecnologia = lazyTecnologia;
    }

    public List<GnMaestro> getListaMaeServicioHabilitacion() {
        return listaMaeServicioHabilitacion;
    }

    public void setListaMaeServicioHabilitacion(List<GnMaestro> listaMaeServicioHabilitacion) {
        this.listaMaeServicioHabilitacion = listaMaeServicioHabilitacion;
    }

    public HashMap<Integer, GnMaestro> getMapMaeServicioHabilitacion() {
        return mapMaeServicioHabilitacion;
    }

    public void setMapMaeServicioHabilitacion(HashMap<Integer, GnMaestro> mapMaeServicioHabilitacion) {
        this.mapMaeServicioHabilitacion = mapMaeServicioHabilitacion;
    }

    public TecnologiaServicioIface getTecnologiaServicio() {
        return tecnologiaServicio;
    }

    public void setTecnologiaServicio(TecnologiaServicioIface tecnologiaServicio) {
        this.tecnologiaServicio = tecnologiaServicio;
    }
    
    public void listar(){
        super.setAccion(ACCION_LISTAR);
        procesoFinal();
    }

    public void refrescar() {
        super.setAccion(Url.ACCION_LISTAR);
        getTecnologiaServicio().Accion(this);
    }

    public void ver(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_VER);
        getTecnologiaServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmVer");
        PrimeFaces.current().executeScript("PF('dialogoVer').show()");
        procesoFinal();
    }

    public void crear() {
        super.setAccion(ACCION_CREAR);
        getTecnologiaServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmCrear");
        PrimeFaces.current().executeScript("PF('dialogoCrear').show()");
        procesoFinal();
    }

    public void guardar() {
        super.setAccion(ACCION_GUARDAR);
        getTecnologiaServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoCrear').hide();");
        }
        procesoFinal();
    }

    public void editar(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_EDITAR);
        getTecnologiaServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmEditar");
        PrimeFaces.current().executeScript("PF('dialogoEditar').show()");
        procesoFinal();
    }

    public void modificar() {
        super.setAccion(ACCION_MODIFICAR);
        getTecnologiaServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoEditar').hide();");
        }
        procesoFinal();
    }

    public void borrar(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_BORRAR);
        getTecnologiaServicio().Accion(this);
        procesoFinal();
    }

    private void procesoFinal() {
        if (!super.isError()) {            
            switch (getAccion()) {
                case Url.ACCION_GUARDAR:
                case Url.ACCION_MODIFICAR:
                case Url.ACCION_BORRAR:
                case Url.ACCION_LISTAR:
                    PrimeFaces.current().ajax().update("frmTecnologias");
                case Url.ACCION_VER:
                case Url.ACCION_CREAR:
                case Url.ACCION_EDITAR:
                    crearLog(getObjeto().toString());
                    break;
            }
        }
        generarMensajes();        
    }
    
    public String fechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
    }
}
