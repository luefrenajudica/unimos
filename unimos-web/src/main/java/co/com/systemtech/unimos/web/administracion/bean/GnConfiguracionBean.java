
package co.com.systemtech.unimos.web.administracion.bean;

import co.com.systemtech.unimos.dominio.administracion.GnConfiguracion;
import co.com.systemtech.unimos.dominio.administracion.Modulo;
import co.com.systemtech.unimos.web.administracion.servicio.GnConfiguracionServicioIface;
import co.com.systemtech.unimos.web.utilidades.CompatibilidadPF;
import co.com.systemtech.unimos.web.utilidades.PropApl;
import co.com.systemtech.unimos.web.utilidades.Url;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;


/**
 *
 * @author Raúl Ignacio Palacios Correa
 */
@ManagedBean
@ViewScoped
public class GnConfiguracionBean extends Url {

    private GnConfiguracionServicioIface gnConfiguracionServicio;
    private GnConfiguracion objeto;
    private List<GnConfiguracion> registros;
    private LazyDataModel<GnConfiguracion> lazyGnConfiguracion;

    public GnConfiguracionBean() {
        this.objeto = new GnConfiguracion();
        Modulo mod = super.validarModulo(Modulo.ID_GN_CONFIGURACION);
        if (mod == null) {
            super.redireccionar("/unimos/home.faces");
        } else {
            super.setModulo(mod);

            lazyGnConfiguracion = new LazyDataModel<GnConfiguracion>() {
                
                private List<GnConfiguracion> gnConfiguraciones;
                
                @Override
                public int count(Map<String, FilterMeta> filtros) {
                    return getParamConsulta().getCantidadRegistros();
                }

                @Override
                public List<GnConfiguracion> load(int primerRegistro, int registrosPagina, Map<String, SortMeta> listaOrdenes, Map<String, FilterMeta> filtros) {
                    getParamConsulta().setPrimerRegistro(primerRegistro);
                    getParamConsulta().setRegistrosPagina(registrosPagina);
                    getParamConsulta().setListaOrden(CompatibilidadPF.convertirFiltrosOrdenToHash(listaOrdenes));
                    getParamConsulta().setFiltros(CompatibilidadPF.ConvertirFiltroMetaToHash(filtros));
                    refrescar();
                    gnConfiguraciones = getRegistros();
                    setRowCount(getParamConsulta().getCantidadRegistros());
                    return gnConfiguraciones;
                }

                @Override
                public String getRowKey(GnConfiguracion configuracion) {
                    return configuracion.getId().toString();
                }

                @Override
                public GnConfiguracion getRowData(String portabilidadId) {
                    Integer id = Integer.valueOf(portabilidadId);
                    for (GnConfiguracion portabilidad : gnConfiguraciones) {
                        if (id.equals(portabilidad.getId())) {
                            return portabilidad;
                        }
                    }
                    return null;
                }
                
            };
        }
    }
    
    @PostConstruct
    public void postConstruct() {
//        getGnConfiguracionServicio().cargasInicial(this);
        listar();
    }

    public GnConfiguracionServicioIface getGnConfiguracionServicio() {
        return gnConfiguracionServicio;
    }

    public void setGnConfiguracionServicio(GnConfiguracionServicioIface gnConfiguracionServicio) {
        this.gnConfiguracionServicio = gnConfiguracionServicio;
    }

    public GnConfiguracion getObjeto() {
        return objeto;
    }

    public void setObjeto(GnConfiguracion objeto) {
        this.objeto = objeto;
    }

    public LazyDataModel<GnConfiguracion> getLazyGnConfiguracion() {
        return lazyGnConfiguracion;
    }

    public void setLazyGnConfiguracion(LazyDataModel<GnConfiguracion> lazyGnConfiguracion) {
        this.lazyGnConfiguracion = lazyGnConfiguracion;
    }

    public List<GnConfiguracion> getRegistros() {
        return registros;
    }

    public void setRegistros(List<GnConfiguracion> registros) {
        this.registros = registros;
    }

    public void listar(){
        super.setAccion(ACCION_LISTAR);
        procesoFinal();
    }

    public void refrescar() {
        super.setAccion(Url.ACCION_LISTAR);
        getGnConfiguracionServicio().Accion(this);
    }

    public void modificarConfiguracion(RowEditEvent event) {
        setObjeto((GnConfiguracion) event.getObject());
        super.setAccion(ACCION_EDITAR);
        getGnConfiguracionServicio().Accion(this);
        procesoFinal();
    }
    
    public void refrescarConfiguracion() {
        try {
            PropApl.getInstanceLimpiar();            
            addMensaje("Se actualizo la configuración del sistema correctamente.");
            generarMensajes();
        } catch (Exception ex) {
            addError(ex.toString());
            generarMensajes();
        } 
    }    
    
    private void procesoFinal() {
        switch (getAccion()) {            
            case Url.ACCION_LISTAR:
            case Url.ACCION_EDITAR:
//                PrimeFaces.current().ajax().update("frmConfiguracion");
                crearLog(getObjeto().toString());
                break;
        }
        generarMensajes();
    }

}
