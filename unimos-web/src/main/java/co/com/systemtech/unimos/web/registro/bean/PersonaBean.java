/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.registro.bean;

import co.com.systemtech.unimos.dominio.administracion.GnMaestro;
import co.com.systemtech.unimos.dominio.registro.UnPersona;
import co.com.systemtech.unimos.dominio.administracion.Modulo;
import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.dominio.registro.UnPersonaContacto;
import co.com.systemtech.unimos.web.registro.servicio.PersonaServicioIface;
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
public class PersonaBean extends Url {

    private PersonaServicioIface personaServicio;
    private UnPersona objeto;
    private List<UnPersona> registros;
    private LazyDataModel<UnPersona> lazyRegistros;

    private List<Ubicacion> ubicaciones;
    private HashMap<Integer, Ubicacion> ubicacionesRecursiva;
    
    private List<GnMaestro> listaMaeTiposDocumento;
    private HashMap<Integer, GnMaestro> mapMaeTiposDocumento;
    private List<GnMaestro> listaMaeSexo;
    private HashMap<Integer, GnMaestro> mapMaeSexo;
    private List<GnMaestro> listaMaeEstadoCivil;
    private HashMap<Integer, GnMaestro> mapMaeEstadoCivil;

    public PersonaBean() {
        this.objeto = new UnPersona();
        this.objetoContacto = new UnPersonaContacto();
        Modulo _mod = super.validarModulo(Modulo.ID_PERSONAS);
        super.getParamConsulta().setEmpresaId(super.getConexion().getEmpresa().getId());
        if (_mod == null) {
            super.redireccionar("/unimos/home.faces");
        } else {
            super.setModulo(_mod);
            lazyRegistros = new LazyDataModel<UnPersona>() {

                private List<UnPersona> lista;
                
                @Override
                public int count(Map<String, FilterMeta> filtros) {
                    return getParamConsulta().getCantidadRegistros();
                }
                
                @Override
                public List<UnPersona> load(int primerRegistro, int registrosPagina, Map<String, SortMeta> listaOrdenes, Map<String, FilterMeta> filtros) {
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
                public String getRowKey(UnPersona objeto) {
                    return objeto.getId().toString();
                }

                @Override
                public UnPersona getRowData(String objetoId) {
                    Integer id = Integer.valueOf(objetoId);
                    for (UnPersona objeto : lista) {
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
        getPersonaServicio().cargasInicial(this);
        listar();
    }

    public UnPersona getObjeto() {
        return objeto;
    }

    public void setObjeto(UnPersona objeto) {
        this.objeto = objeto;
    }

    public List<UnPersona> getRegistros() {
        return registros;
    }

    public void setRegistros(List<UnPersona> registros) {
        this.registros = registros;
    }

    public LazyDataModel<UnPersona> getLazyRegistros() {
        return lazyRegistros;
    }

    public void setLazyRegistros(LazyDataModel<UnPersona> lazyRegistros) {
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

    public List<GnMaestro> getListaMaeTiposDocumento() {
        return listaMaeTiposDocumento;
    }

    public void setListaMaeTiposDocumento(List<GnMaestro> listaMaeTiposDocumento) {
        this.listaMaeTiposDocumento = listaMaeTiposDocumento;
    }

    public HashMap<Integer, GnMaestro> getMapMaeTiposDocumento() {
        return mapMaeTiposDocumento;
    }

    public void setMapMaeTiposDocumento(HashMap<Integer, GnMaestro> mapMaeTiposDocumento) {
        this.mapMaeTiposDocumento = mapMaeTiposDocumento;
    }

    public List<GnMaestro> getListaMaeSexo() {
        return listaMaeSexo;
    }

    public void setListaMaeSexo(List<GnMaestro> listaMaeSexo) {
        this.listaMaeSexo = listaMaeSexo;
    }

    public HashMap<Integer, GnMaestro> getMapMaeSexo() {
        return mapMaeSexo;
    }

    public void setMapMaeSexo(HashMap<Integer, GnMaestro> mapMaeSexo) {
        this.mapMaeSexo = mapMaeSexo;
    }

    public List<GnMaestro> getListaMaeEstadoCivil() {
        return listaMaeEstadoCivil;
    }

    public void setListaMaeEstadoCivil(List<GnMaestro> listaMaeEstadoCivil) {
        this.listaMaeEstadoCivil = listaMaeEstadoCivil;
    }

    public HashMap<Integer, GnMaestro> getMapMaeEstadoCivil() {
        return mapMaeEstadoCivil;
    }

    public void setMapMaeEstadoCivil(HashMap<Integer, GnMaestro> mapMaeEstadoCivil) {
        this.mapMaeEstadoCivil = mapMaeEstadoCivil;
    }
    

    public PersonaServicioIface getPersonaServicio() {
        return personaServicio;
    }

    public void setPersonaServicio(PersonaServicioIface personaServicio) {
        this.personaServicio = personaServicio;
    }
    
    public void listar(){
        super.setAccion(ACCION_LISTAR);
        procesoFinal();
    }

    public void refrescar() {
        super.setAccion(Url.ACCION_LISTAR);
        getPersonaServicio().Accion(this);
    }

    public void ver(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_VER);
        getPersonaServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmVer");
        PrimeFaces.current().executeScript("PF('dialogoVer').show()");
        procesoFinal();
    }

    public void crear() {
        super.setAccion(ACCION_CREAR);
        getPersonaServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmCrear");
        PrimeFaces.current().executeScript("PF('dialogoCrear').show()");
        procesoFinal();
    }

    public void guardar() {
        super.setAccion(ACCION_GUARDAR);
        getPersonaServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoCrear').hide();");
        }
        procesoFinal();
    }

    public void editar(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_EDITAR);
        getPersonaServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmEditar");
        PrimeFaces.current().executeScript("PF('dialogoEditar').show()");
        procesoFinal();
    }

    public void modificar() {
        super.setAccion(ACCION_MODIFICAR);
        getPersonaServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoEditar').hide();");
        }
        procesoFinal();
    }

    public void borrar(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_BORRAR);
        getPersonaServicio().Accion(this);
        procesoFinal();
    }

    private void procesoFinal() {
        if (!super.isError()) {            
            switch (getAccion()) {
                case Url.ACCION_GUARDAR:
                case Url.ACCION_MODIFICAR:
                case Url.ACCION_BORRAR:
                case Url.ACCION_LISTAR:
                    PrimeFaces.current().ajax().update("frmPersonas");
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
            getObjeto().setGnDireccionUbicacion(ubicacionesFiltradas.get(0));
        }
        return ubicacionesFiltradas;
    }

//    public String fechaActual() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date();
//        return sdf.format(date);
//    }
    
    private UnPersonaContacto objetoContacto;
    private List<GnMaestro> listaMaeTiposContacto;
    private HashMap<Integer, GnMaestro> mapMaeTiposContacto;

    public UnPersonaContacto getObjetoContacto() {
        return objetoContacto;
    }

    public void setObjetoContacto(UnPersonaContacto objetoContacto) {
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
        this.setObjetoContacto(new UnPersonaContacto());
        PrimeFaces.current().ajax().update("frmCrearContacto");
        PrimeFaces.current().executeScript("PF('dialogoCrearContacto').show()");
    }
    
    public void contactoAgregar() {
        getObjetoContacto().setEstadoRegistro(UnPersonaContacto.ESTADO_REGISTRO_CREAR);
        GnMaestro maeCont = getMapMaeTiposContacto().get(getObjetoContacto().getMaeTipoId());
        getObjetoContacto().setMaeTipo(maeCont);
        this.getObjeto().addUnPersonaContactos(getObjetoContacto());
        PrimeFaces.current().executeScript("PF('dialogoCrearContacto').hide()");
        PrimeFaces.current().ajax().update("frmCrear:tablaContactos");
        PrimeFaces.current().ajax().update("frmEditar:tablaContactos");
    }
    
    public void contactoRetirar(UnPersonaContacto cont) {
        List<UnPersonaContacto> lista = new ArrayList();
        for (UnPersonaContacto obj : this.getObjeto().getListaUnPersonaContactos()) {
            if(!obj.equals(cont)){
                lista.add(obj);
            }
        }
        this.getObjeto().setListaUnPersonaContactos(lista);
        PrimeFaces.current().ajax().update("frmCrear:tablaContactos");
        PrimeFaces.current().ajax().update("frmEditar:tablaContactos");
    }
    
    public void contactoBorrar(int id) {
        List<UnPersonaContacto> lista = new ArrayList();
        for (UnPersonaContacto obj : this.getObjeto().getListaUnPersonaContactos()) {
            if(obj.getId() != null && obj.getId() == id){
                obj.setEstadoRegistro(UnPersonaContacto.ESTADO_REGISTRO_BORRAR);
            }
            lista.add(obj);
        }
        this.getObjeto().setListaUnPersonaContactos(lista);
        PrimeFaces.current().ajax().update("frmCrear:tablaContactos");
        PrimeFaces.current().ajax().update("frmEditar:tablaContactos");
    }
    
}
