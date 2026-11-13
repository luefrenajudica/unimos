/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.registro.bean;

import co.com.systemtech.unimos.dominio.administracion.GnMaestro;
import co.com.systemtech.unimos.dominio.registro.UnComercial;
import co.com.systemtech.unimos.dominio.administracion.Modulo;
import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.dominio.administracion.Usuario;
import co.com.systemtech.unimos.dominio.contrato.UnComercialComision;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.dominio.registro.Sede;
import co.com.systemtech.unimos.web.registro.servicio.ComercialServicioIface;
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
public class ComercialBean extends Url {

    private ComercialServicioIface comercialServicio;
    private UnComercial objeto;
    private List<UnComercial> registros;
    private LazyDataModel<UnComercial> lazyRegistros;

    private List<Ubicacion> ubicaciones;
    private HashMap<Integer, Ubicacion> ubicacionesRecursiva;

    private List<Sede> listaSedes;
    private List<Usuario> listaUsuarios;

    private List<GnMaestro> listaMaeTiposDocumento;
    private HashMap<Integer, GnMaestro> mapMaeTiposDocumento;

    public ComercialBean() {
        this.objeto = new UnComercial();
        Modulo _mod = super.validarModulo(Modulo.ID_COMERCIALES);
        super.getParamConsulta().setEmpresaId(super.getConexion().getEmpresa().getId());
        if (_mod == null) {
            super.redireccionar("/unimos/home.faces");
        } else {
            super.setModulo(_mod);
            lazyRegistros = new LazyDataModel<UnComercial>() {

                private List<UnComercial> lista;

                @Override
                public int count(Map<String, FilterMeta> filtros) {
                    return getParamConsulta().getCantidadRegistros();
                }

                @Override
                public List<UnComercial> load(int primerRegistro, int registrosPagina, Map<String, SortMeta> listaOrdenes, Map<String, FilterMeta> filtros) {
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
                public String getRowKey(UnComercial objeto) {
                    return objeto.getId().toString();
                }

                @Override
                public UnComercial getRowData(String objetoId) {
                    Integer id = Integer.valueOf(objetoId);
                    for (UnComercial objeto : lista) {
                        if (id.equals(objeto.getId())) {
                            return objeto;
                        }
                    }
                    return null;
                }

            };

            lazyComisiones = new LazyDataModel<UnComercialComision>() {
                private List<UnComercialComision> comisiones;

                @Override
                public int count(Map<String, FilterMeta> filtros) {
                    return getParamConsultaComisiones().getCantidadRegistros();
                }

                @Override
                public List<UnComercialComision> load(int primerRegistro, int registrosPagina, Map<String, SortMeta> listaOrdenes, Map<String, FilterMeta> filtros) {
                    //usamos el mismo getParamConsulta pero validamos si debemos cambiarlo
                    getParamConsultaComisiones().setPrimerRegistro(primerRegistro);
                    getParamConsultaComisiones().setRegistrosPagina(registrosPagina);
                    getParamConsultaComisiones().setListaOrden(CompatibilidadPF.convertirFiltrosOrdenToHash(listaOrdenes));
                    getParamConsultaComisiones().setFiltros(CompatibilidadPF.ConvertirFiltroMetaToHash(filtros));
                    refrescarComision();
                    comisiones = getListaComisiones();
                    setRowCount(getParamConsultaComisiones().getCantidadRegistros());
                    if(comisiones == null){
                        comisiones = new ArrayList();
                    }
                    return comisiones;
                }

                @Override
                public String getRowKey(UnComercialComision perosna) {
                    return perosna.getId().toString();
                }

                @Override
                public UnComercialComision getRowData(String perosnaId) {
                    Integer id = Integer.valueOf(perosnaId);
                    for (UnComercialComision comision : comisiones) {
                        if (id.equals(comision.getId())) {
                            return comision;
                        }
                    }
                    return null;
                }
            };
        }
    }

    @PostConstruct
    public void postConstruct() {
        getComercialServicio().cargasInicial(this);
        listar();
    }

    public UnComercial getObjeto() {
        return objeto;
    }

    public void setObjeto(UnComercial objeto) {
        this.objeto = objeto;
    }

    public List<UnComercial> getRegistros() {
        return registros;
    }

    public void setRegistros(List<UnComercial> registros) {
        this.registros = registros;
    }

    public LazyDataModel<UnComercial> getLazyRegistros() {
        return lazyRegistros;
    }

    public void setLazyRegistros(LazyDataModel<UnComercial> lazyRegistros) {
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

    public List<Sede> getListaSedes() {
        return listaSedes;
    }

    public void setListaSedes(List<Sede> listaSedes) {
        this.listaSedes = listaSedes;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
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

    public ComercialServicioIface getComercialServicio() {
        return comercialServicio;
    }

    public void setComercialServicio(ComercialServicioIface comercialServicio) {
        this.comercialServicio = comercialServicio;
    }

    public void listar() {
        super.setAccion(ACCION_LISTAR);
        procesoFinal();
    }

    public void refrescar() {
        super.setAccion(Url.ACCION_LISTAR);
        getComercialServicio().Accion(this);
    }

    public void ver(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_VER);
        getComercialServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmVer");
        PrimeFaces.current().executeScript("PF('dialogoVer').show()");
        procesoFinal();
    }

    public void crear() {
        super.setAccion(ACCION_CREAR);
        getComercialServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmCrear");
        PrimeFaces.current().executeScript("PF('dialogoCrear').show()");
        procesoFinal();
    }

    public void guardar() {
        super.setAccion(ACCION_GUARDAR);
        getComercialServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoCrear').hide();");
        }
        procesoFinal();
    }

    public void editar(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_EDITAR);
        getComercialServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmEditar");
        PrimeFaces.current().executeScript("PF('dialogoEditar').show()");
        procesoFinal();
    }

    public void modificar() {
        super.setAccion(ACCION_MODIFICAR);
        getComercialServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoEditar').hide();");
        }
        procesoFinal();
    }

    public void borrar(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_BORRAR);
        getComercialServicio().Accion(this);
        procesoFinal();
    }

    private void procesoFinal() {
        if (!super.isError()) {
            switch (getAccion()) {
                case Url.ACCION_GUARDAR:
                case Url.ACCION_MODIFICAR:
                case Url.ACCION_BORRAR:
                case Url.ACCION_LISTAR:
                    PrimeFaces.current().ajax().update("frmComerciales");
                case Url.ACCION_VER:
                case Url.ACCION_CREAR:
                case Url.ACCION_EDITAR:
                    crearLog(getObjeto().toString());
                    break;
                case Url.ACCION_ADICIONAL_1:
                    PrimeFaces.current().ajax().update("frmComisiones");
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
            getObjeto().setDireccionUbicacion(ubicacionesFiltradas.get(0));
        }
        return ubicacionesFiltradas;
    }

//    public String fechaActual() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date();
//        return sdf.format(date);
//    }
    private LazyDataModel<UnComercialComision> lazyComisiones;
    private ParamConsulta paramConsultaComisiones;
    private UnComercialComision objetoComision;
    private List<UnComercialComision> listaComisiones;

    public LazyDataModel<UnComercialComision> getLazyComisiones() {
        return lazyComisiones;
    }

    public void setLazyComisiones(LazyDataModel<UnComercialComision> lazyComisiones) {
        this.lazyComisiones = lazyComisiones;
    }

    public ParamConsulta getParamConsultaComisiones() {
        if (paramConsultaComisiones == null) {
            paramConsultaComisiones = new ParamConsulta();
        }
        return paramConsultaComisiones;
    }

    public void setParamConsultaComisiones(ParamConsulta paramConsultaComisiones) {
        this.paramConsultaComisiones = paramConsultaComisiones;
    }

    public UnComercialComision getObjetoComision() {
        return objetoComision;
    }

    public void setObjetoComision(UnComercialComision objetoComision) {
        this.objetoComision = objetoComision;
    }

    public List<UnComercialComision> getListaComisiones() {
        return listaComisiones;
    }

    public void setListaComisiones(List<UnComercialComision> listaComisiones) {
        this.listaComisiones = listaComisiones;
    }

    public void refrescarComision() {
        if (getParamConsultaComisiones().getParametroConsulta1() != null) {
            super.setAccion(ACCION_ADICIONAL_1);
            super.setDoAccion(ACCION_LISTAR);
            getComercialServicio().Accion(this);
        }
    }

    public void listarComisiones(int id) {
//        getObjeto().setId(id);
        getParamConsultaComisiones().setParametroConsulta1(id);
        super.setAccion(Url.ACCION_ADICIONAL_1);
        super.setDoAccion(Url.ACCION_LISTAR);
        PrimeFaces.current().ajax().update("frmComisiones");
        PrimeFaces.current().executeScript("PF('dialogoComisiones').show()");
        procesoFinal();
    }

}
