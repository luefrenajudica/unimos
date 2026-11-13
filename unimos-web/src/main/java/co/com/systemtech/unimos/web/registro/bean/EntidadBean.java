/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.registro.bean;

import co.com.systemtech.unimos.dominio.administracion.GnMaestro;
import co.com.systemtech.unimos.dominio.registro.UnEntidad;
import co.com.systemtech.unimos.dominio.administracion.Modulo;
import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.dominio.registro.UnEntidadContacto;
import co.com.systemtech.unimos.web.registro.servicio.EntidadServicioIface;
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
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
@ManagedBean
@ViewScoped
public class EntidadBean extends Url {

    private EntidadServicioIface entidadServicio;
    private UnEntidad objeto;
    private List<UnEntidad> registros;
    private LazyDataModel<UnEntidad> lazyRegistros;

    private List<Ubicacion> ubicaciones;
    private HashMap<Integer, Ubicacion> ubicacionesRecursiva;
    
    private List<GnMaestro> listaMaeTiposEntidad;
    private HashMap<Integer, GnMaestro> mapMaeTiposEntidad;

    public EntidadBean() {
        this.objeto = new UnEntidad();
        this.objetoContacto = new UnEntidadContacto();
        Modulo _mod = super.validarModulo(Modulo.ID_ENTIDADES);
        super.getParamConsulta().setEmpresaId(super.getConexion().getEmpresa().getId());
        if (_mod == null) {
            super.redireccionar("/unimos/home.faces");
        } else {
            super.setModulo(_mod);
            lazyRegistros = new LazyDataModel<UnEntidad>() {

                private List<UnEntidad> lista;
                
                @Override
                public int count(Map<String, FilterMeta> filtros) {
                    return getParamConsulta().getCantidadRegistros();
                }
                
                @Override
                public List<UnEntidad> load(int primerRegistro, int registrosPagina, Map<String, SortMeta> listaOrdenes, Map<String, FilterMeta> filtros) {
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
                public String getRowKey(UnEntidad objeto) {
                    return objeto.getId().toString();
                }

                @Override
                public UnEntidad getRowData(String objetoId) {
                    Integer id = Integer.valueOf(objetoId);
                    for (UnEntidad objeto : lista) {
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
        getEntidadServicio().cargasInicial(this);
        listar();
    }

    public UnEntidad getObjeto() {
        return objeto;
    }

    public void setObjeto(UnEntidad objeto) {
        this.objeto = objeto;
    }

    public List<UnEntidad> getRegistros() {
        return registros;
    }

    public void setRegistros(List<UnEntidad> registros) {
        this.registros = registros;
    }

    public LazyDataModel<UnEntidad> getLazyRegistros() {
        return lazyRegistros;
    }

    public void setLazyRegistros(LazyDataModel<UnEntidad> lazyRegistros) {
        this.lazyRegistros = lazyRegistros;
    }

    public List<Ubicacion> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<Ubicacion> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public HashMap<Integer, Ubicacion> getUbicacionesRecursiva() {
        return ubicacionesRecursiva;
    }

    public void setUbicacionesRecursiva(HashMap<Integer, Ubicacion> ubicacionesRecursiva) {
        this.ubicacionesRecursiva = ubicacionesRecursiva;
    }

    public List<GnMaestro> getListaMaeTiposEntidad() {
        return listaMaeTiposEntidad;
    }

    public void setListaMaeTiposEntidad(List<GnMaestro> listaMaeTiposEntidad) {
        this.listaMaeTiposEntidad = listaMaeTiposEntidad;
    }

    public HashMap<Integer, GnMaestro> getMapMaeTiposEntidad() {
        return mapMaeTiposEntidad;
    }

    public void setMapMaeTiposEntidad(HashMap<Integer, GnMaestro> mapMaeTiposEntidad) {
        this.mapMaeTiposEntidad = mapMaeTiposEntidad;
    }

    public EntidadServicioIface getEntidadServicio() {
        return entidadServicio;
    }

    public void setEntidadServicio(EntidadServicioIface entidadServicio) {
        this.entidadServicio = entidadServicio;
    }
    
    public void listar(){
        super.setAccion(ACCION_LISTAR);
        procesoFinal();
    }

    public void refrescar() {
        super.setAccion(Url.ACCION_LISTAR);
        getEntidadServicio().Accion(this);
    }

    public void ver(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_VER);
        getEntidadServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmVer");
        PrimeFaces.current().executeScript("PF('dialogoVer').show()");
        procesoFinal();
    }

    public void crear() {
        super.setAccion(ACCION_CREAR);
        getEntidadServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmCrear");
        PrimeFaces.current().executeScript("PF('dialogoCrear').show()");
        procesoFinal();
    }

    public void guardar() {
        super.setAccion(ACCION_GUARDAR);
        getEntidadServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoCrear').hide();");
        }
        procesoFinal();
    }

    public void editar(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_EDITAR);
        getEntidadServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmEditar");
        PrimeFaces.current().executeScript("PF('dialogoEditar').show()");
        procesoFinal();
    }

    public void modificar() {
        super.setAccion(ACCION_MODIFICAR);
        getEntidadServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoEditar').hide();");
        }
        procesoFinal();
    }

    public void borrar(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_BORRAR);
        getEntidadServicio().Accion(this);
        procesoFinal();
    }
    
    private UnEntidadContacto objetoContacto;
    private List<GnMaestro> listaMaeTiposContacto;
    private HashMap<Integer, GnMaestro> mapMaeTiposContacto;

    public UnEntidadContacto getObjetoContacto() {
        return objetoContacto;
    }

    public void setObjetoContacto(UnEntidadContacto objetoContacto) {
        this.objetoContacto = objetoContacto;
    }
    
    public List<GnMaestro> getListaMaeTiposContacto() {
        return listaMaeTiposContacto;
    }

    public void setListaMaeTiposContacto(List<GnMaestro> listaMaeTiposContacto) {
        this.listaMaeTiposContacto = listaMaeTiposContacto;
    }

    public HashMap<Integer, GnMaestro> getMapMaeTiposContacto() {
        return mapMaeTiposContacto;
    }

    public void setMapMaeTiposContacto(HashMap<Integer, GnMaestro> mapMaeTiposContacto) {
        this.mapMaeTiposContacto = mapMaeTiposContacto;
    }
    
    public void contactoCrear() {
        this.setObjetoContacto(new UnEntidadContacto());
        PrimeFaces.current().ajax().update("frmCrearContacto");
        PrimeFaces.current().executeScript("PF('dialogoCrearContacto').show()");
    }
    
    public void contactoAgregar() {
        getObjetoContacto().setEstadoRegistro(UnEntidadContacto.ESTADO_REGISTRO_CREAR);
        GnMaestro maeCont = getMapMaeTiposContacto().get(getObjetoContacto().getMaeTipoId());
        getObjetoContacto().setMaeTipo(maeCont);
        this.getObjeto().addUnEntidadContactos(getObjetoContacto());
        PrimeFaces.current().executeScript("PF('dialogoCrearContacto').hide()");
        PrimeFaces.current().ajax().update("frmCrear:tablaContactos");
        PrimeFaces.current().ajax().update("frmEditar:tablaContactos");
    }
    
    public void contactoRetirar(UnEntidadContacto cont) {
        List<UnEntidadContacto> lista = new ArrayList();
        for (UnEntidadContacto obj : this.getObjeto().getListaUnEntidadContactos()) {
            if(!obj.equals(cont)){
                lista.add(obj);
            }
        }
        this.getObjeto().setListaUnEntidadContactos(lista);
        PrimeFaces.current().ajax().update("frmCrear:tablaContactos");
        PrimeFaces.current().ajax().update("frmEditar:tablaContactos");
    }
    
    public void contactoBorrar(int id) {
        List<UnEntidadContacto> lista = new ArrayList();
        for (UnEntidadContacto obj : this.getObjeto().getListaUnEntidadContactos()) {
            if(obj.getId() != null && obj.getId() == id){
                obj.setEstadoRegistro(UnEntidadContacto.ESTADO_REGISTRO_BORRAR);
            }
            lista.add(obj);
        }
        this.getObjeto().setListaUnEntidadContactos(lista);
        PrimeFaces.current().ajax().update("frmCrear:tablaContactos");
        PrimeFaces.current().ajax().update("frmEditar:tablaContactos");
    }

    private void procesoFinal() {
        if (!super.isError()) {            
            switch (getAccion()) {
                case Url.ACCION_GUARDAR:
                case Url.ACCION_MODIFICAR:
                case Url.ACCION_BORRAR:
                case Url.ACCION_LISTAR:
                    PrimeFaces.current().ajax().update("frmEntidades");
                case Url.ACCION_VER:
                case Url.ACCION_CREAR:
                case Url.ACCION_EDITAR:
                    crearLog(getObjeto().toString());
                    break;
            }
        }
        generarMensajes();        
    }

    public String getUbicacionRecursiva(int id) {
        String ubicacionStr = "";
        Ubicacion _municipio = ubicacionesRecursiva.get(id);
        if (_municipio != null && _municipio.getUbicacionPadre() != null) {
            Ubicacion _departamento = _municipio.getUbicacionPadre();
            if (_departamento.getUbicacionPadre() != null) {
                Ubicacion _pais = _departamento.getUbicacionPadre();
                ubicacionStr = _pais.getNombre();
            }
            ubicacionStr = _departamento.getNombre() + " - " + ubicacionStr;
            ubicacionStr = _municipio.getNombre() + " - " + ubicacionStr;
        }
        return ubicacionStr;
    }
    
    public List<Ubicacion> completarUbicacion(String query) {
        List<Ubicacion> ubicacionesFiltradas = new ArrayList<>();
        for (Ubicacion ubicacion : this.getUbicaciones()) {
            if (ubicacion.getNombre().toLowerCase().contains(query.toLowerCase())) {
                ubicacionesFiltradas.add(ubicacion);
            }
        }
        if (ubicacionesFiltradas.size() == 1) {
            getObjeto().setGnUbicacion(ubicacionesFiltradas.get(0));
        }
        return ubicacionesFiltradas;
    }
    
}
