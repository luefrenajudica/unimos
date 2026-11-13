/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.administracion.bean;

import co.com.systemtech.unimos.dominio.administracion.Modulo;
import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.web.administracion.servicio.UbicacionServicioImpl;
import co.com.systemtech.unimos.web.utilidades.CompatibilidadPF;
import co.com.systemtech.unimos.web.utilidades.Url;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
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
public class UbicacionBean extends Url {

    private UbicacionServicioImpl ubicacionServicio;
    private Ubicacion objeto;
    private List<Ubicacion> registros;
    private LazyDataModel<Ubicacion> lazyUbicacion;
    private List<Ubicacion> padres;
    
    private final static List<String> tiposFiltro;
    private final static Map<String, String> tipos;

    public UbicacionBean() {
        this.objeto = new Ubicacion();
        Modulo _mod = super.validarModulo(Modulo.ID_UBICACIONES);
        if (_mod == null) {
            super.redireccionar("/unimos/home.faces");
        }else{
            super.setModulo(_mod);
            lazyUbicacion = new LazyDataModel<Ubicacion>() {

                private List<Ubicacion> lista;
                
                @Override
                public int count(Map<String, FilterMeta> filtros) {
                    return getParamConsulta().getCantidadRegistros();
                }
                
                
                @Override
                public List<Ubicacion> load(int primerRegistro, int registrosPagina, Map<String, SortMeta> listaOrdenes, Map<String, FilterMeta> filtros) {
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
                public String getRowKey(Ubicacion objeto) {
                    return objeto.getId().toString();
                }

                @Override
                public Ubicacion getRowData(String objetoId) {
                    Integer id = Integer.valueOf(objetoId);
                    for (Ubicacion objeto : lista) {
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
        listar();
    }

    static {
        tipos = new HashMap<>();
        tipos.put(Ubicacion.getTipoStr(Ubicacion.TIPO_PAIS), String.valueOf(Ubicacion.TIPO_PAIS));
        tipos.put(Ubicacion.getTipoStr(Ubicacion.TIPO_DEPARTAMENTO), String.valueOf(Ubicacion.TIPO_DEPARTAMENTO));
        tipos.put(Ubicacion.getTipoStr(Ubicacion.TIPO_MUNICIPIO), String.valueOf(Ubicacion.TIPO_MUNICIPIO));
        
        tiposFiltro = new ArrayList<>();
        tiposFiltro.add(Ubicacion.getTipoStr(Ubicacion.TIPO_PAIS));
        tiposFiltro.add(Ubicacion.getTipoStr(Ubicacion.TIPO_DEPARTAMENTO));
        tiposFiltro.add(Ubicacion.getTipoStr(Ubicacion.TIPO_MUNICIPIO));
    }

    public Ubicacion getObjeto() {
        return objeto;
    }

    public void setObjeto(Ubicacion objeto) {
        this.objeto = objeto;
    }

    public List<Ubicacion> getRegistros() {
        return registros;
    }

    public void setRegistros(List<Ubicacion> registros) {
        this.registros = registros;
    }

    public LazyDataModel<Ubicacion> getLazyUbicacion() {
        return lazyUbicacion;
    }

    public void setLazyUbicacion(LazyDataModel<Ubicacion> lazyUbicacion) {
        this.lazyUbicacion = lazyUbicacion;
    }

    public List<Ubicacion> getPadres() {
        return padres;
    }

    public void setPadres(List<Ubicacion> padres) {
        this.padres = padres;
    }

    public UbicacionServicioImpl getUbicacionServicio() {
        if (ubicacionServicio == null) {
            ubicacionServicio = new UbicacionServicioImpl();
        }
        return ubicacionServicio;
    }

    public void setUbicacionServicio(UbicacionServicioImpl ubicacionServicio) {
        this.ubicacionServicio = ubicacionServicio;
    }
    
    public void listar(){
        super.setAccion(ACCION_LISTAR);
        procesoFinal();
    }    

    public void ver(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_VER);
        getUbicacionServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmVer:panelVer");
        PrimeFaces.current().executeScript("PF('dialogoVer').show()");
        procesoFinal();
    }

    public void crear() {
        super.setAccion(ACCION_CREAR);
        getUbicacionServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmCrear:panelCrear");
        PrimeFaces.current().executeScript("PF('dialogoCrear').show()");
        procesoFinal();
    }

    public void guardar() {
        super.setAccion(ACCION_GUARDAR);
        getUbicacionServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoCrear').hide();");
        }
        procesoFinal();
    }

    public void editar(Ubicacion obj) {
        setObjeto(obj);
        PrimeFaces.current().ajax().update("frmEditar:panelEditar");
        PrimeFaces.current().executeScript("PF('dialogoEditar').show()");
        procesoFinal();
    }

    public void modificar() {
        super.setAccion(ACCION_MODIFICAR);
        getUbicacionServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoEditar').hide();");
        }
        procesoFinal();
    }

    public void borrar(Ubicacion obj) {
        setObjeto(obj);
        super.setAccion(ACCION_BORRAR);
        getUbicacionServicio().Accion(this);
        procesoFinal();
    }
    
    public void refrescar() {
        super.setAccion(Url.ACCION_LISTAR);
        getUbicacionServicio().Accion(this);
    }
    
    private void procesoFinal() {
        if (!super.isError()) {            
            switch (getAccion()) {
                case Url.ACCION_GUARDAR:
                case Url.ACCION_MODIFICAR:
                case Url.ACCION_BORRAR:
                case Url.ACCION_LISTAR:
                case Url.ACCION_CREAR:
                case Url.ACCION_EDITAR:
                    crearLog(getObjeto().toString());
                    PrimeFaces.current().ajax().update("frmUbicaciones");
                    break;
            }
            refrescar();
        }
        generarMensajes();        
    }

    public Map<String, String> getTipos() {
        return tipos;
    }

    public List<String> getTiposFiltro() {
        return tiposFiltro;
    }

    public List<Ubicacion> listarPorTipo(int tipo, int profundidad) {
        return getUbicacionServicio().consultarPorTipo(tipo, profundidad);
    }

    public void cargaPadresTipo() {
        getUbicacionServicio().cargarPadreTipo(this);
    }

    public List<Ubicacion> completarPadre(String query) {
        List<Ubicacion> ubicacionesFiltradas = new ArrayList<>();
        for (int i = 0; i < this.getPadres().size(); i++) {
            Ubicacion ubicacion = this.getPadres().get(i);
            if (ubicacion.getNombre().toLowerCase().contains(query.toLowerCase())) {
                ubicacionesFiltradas.add(ubicacion);
            }
        }
        return ubicacionesFiltradas;
    }

}
