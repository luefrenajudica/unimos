/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.contrato.bean;

import co.com.systemtech.unimos.dominio.administracion.Modulo;
import co.com.systemtech.unimos.dominio.contrato.UnContrato;
import co.com.systemtech.unimos.dominio.contrato.UnContratoDetalle;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.dominio.registro.UnEntidad;
import co.com.systemtech.unimos.dominio.registro.UnTecnologia;
import co.com.systemtech.unimos.web.contrato.servicio.ContratoServicioIface;
import co.com.systemtech.unimos.web.utilidades.Url;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import co.com.systemtech.unimos.web.utilidades.CompatibilidadPF;
import java.util.ArrayList;
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
public class ContratoBean extends Url {

    private ContratoServicioIface contratoServicio;
    private UnContrato objeto;
    private List<UnContrato> registros;
    private LazyDataModel<UnContrato> lazyRegistros;

    public ContratoBean() {
        this.objeto = new UnContrato();
        this.objeto.setUnEntidad(new UnEntidad());
        this.objetoDetalle = new UnContratoDetalle();
        this.objetoDetalle.setUnTecnologia(new UnTecnologia());
        Modulo _mod = super.validarModulo(Modulo.ID_CONTRATOS);
        super.getParamConsulta().setEmpresaId(super.getConexion().getEmpresa().getId());
        if (_mod == null) {
            super.redireccionar("/unimos/home.faces");
        } else {
            super.setModulo(_mod);
            lazyRegistros = new LazyDataModel<UnContrato>() {

                private List<UnContrato> lista;

                @Override
                public int count(Map<String, FilterMeta> filtros) {
                    return getParamConsulta().getCantidadRegistros();
                }

                @Override
                public List<UnContrato> load(int primerRegistro, int registrosPagina, Map<String, SortMeta> listaOrdenes, Map<String, FilterMeta> filtros) {
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
                public String getRowKey(UnContrato objeto) {
                    return objeto.getId().toString();
                }

                @Override
                public UnContrato getRowData(String objetoId) {
                    Integer id = Integer.valueOf(objetoId);
                    for (UnContrato objeto : lista) {
                        if (id.equals(objeto.getId())) {
                            return objeto;
                        }
                    }
                    return null;
                }

            };
            lazyDetalles = new LazyDataModel<UnContratoDetalle>() {
                private List<UnContratoDetalle> detalles;

                @Override
                public int count(Map<String, FilterMeta> filtros) {
                    return getParamConsultaDetalles().getCantidadRegistros();
                }

                @Override
                public List<UnContratoDetalle> load(int primerRegistro, int registrosPagina, Map<String, SortMeta> listaOrdenes, Map<String, FilterMeta> filtros) {
                    //usamos el mismo getParamConsulta pero validamos si debemos cambiarlo
                    getParamConsultaDetalles().setPrimerRegistro(primerRegistro);
                    getParamConsultaDetalles().setRegistrosPagina(registrosPagina);
                    getParamConsultaDetalles().setListaOrden(CompatibilidadPF.convertirFiltrosOrdenToHash(listaOrdenes));
                    getParamConsultaDetalles().setFiltros(CompatibilidadPF.ConvertirFiltroMetaToHash(filtros));
                    refrescarDetalles();
                    detalles = getListaDetalles();
                    setRowCount(getParamConsultaDetalles().getCantidadRegistros());
                    return detalles;
                }

                @Override
                public String getRowKey(UnContratoDetalle detalle) {
                    return detalle.getId().toString();
                }

                @Override
                public UnContratoDetalle getRowData(String perosnaId) {
                    Integer id = Integer.valueOf(perosnaId);
                    for (UnContratoDetalle detalle : detalles) {
                        if (id.equals(detalle.getId())) {
                            return detalle;
                        }
                    }
                    return null;
                }
            };
        }
    }

    @PostConstruct
    public void postConstruct() {
        getContratoServicio().cargasInicial(this);
        listar();
    }

    public UnContrato getObjeto() {
        return objeto;
    }

    public void setObjeto(UnContrato objeto) {
        this.objeto = objeto;
    }

    public List<UnContrato> getRegistros() {
        return registros;
    }

    public void setRegistros(List<UnContrato> registros) {
        this.registros = registros;
    }

    public LazyDataModel<UnContrato> getLazyRegistros() {
        return lazyRegistros;
    }

    public void setLazyRegistros(LazyDataModel<UnContrato> lazyRegistros) {
        this.lazyRegistros = lazyRegistros;
    }

    public ContratoServicioIface getContratoServicio() {
        return contratoServicio;
    }

    public void setContratoServicio(ContratoServicioIface contratoServicio) {
        this.contratoServicio = contratoServicio;
    }

    public void listar() {
        super.setAccion(ACCION_LISTAR);
        procesoFinal();
    }

    public void refrescar() {
        super.setAccion(Url.ACCION_LISTAR);
        getContratoServicio().Accion(this);
    }

    public void ver(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_VER);
        getContratoServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmVer");
        PrimeFaces.current().executeScript("PF('dialogoVer').show()");
        procesoFinal();
    }

    public void crear() {
        super.setAccion(ACCION_CREAR);
        getContratoServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmCrear");
        PrimeFaces.current().executeScript("PF('dialogoCrear').show()");
        procesoFinal();
    }

    public void guardar() {
        super.setAccion(ACCION_GUARDAR);
        getContratoServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoCrear').hide();");
        }
        procesoFinal();
    }

    public void editar(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_EDITAR);
        getContratoServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmEditar");
        PrimeFaces.current().executeScript("PF('dialogoEditar').show()");
        procesoFinal();
    }

    public void modificar() {
        super.setAccion(ACCION_MODIFICAR);
        getContratoServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoEditar').hide();");
        }
        procesoFinal();
    }

    public void borrar(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_BORRAR);
        getContratoServicio().Accion(this);
        procesoFinal();
    }

    /*
     * Procesos de Detalles
     */
    private ParamConsulta paramConsultaDetalles;
    private List<UnContratoDetalle> listaDetalles;
    private LazyDataModel<UnContratoDetalle> lazyDetalles;
    private UnContratoDetalle objetoDetalle;
    private List<UnTecnologia> listaTecnologias;
    private List<UnEntidad> listaEntidades;

    public ParamConsulta getParamConsultaDetalles() {
        if (paramConsultaDetalles == null) {
            paramConsultaDetalles = new ParamConsulta();
        }
        return paramConsultaDetalles;
    }

    public void setParamConsultaDetalles(ParamConsulta paramConsultaDetalles) {
        this.paramConsultaDetalles = paramConsultaDetalles;
    }

    public LazyDataModel<UnContratoDetalle> getLazyDetalles() {
        return lazyDetalles;
    }

    public void setLazyDetalles(LazyDataModel<UnContratoDetalle> lazyDetalles) {
        this.lazyDetalles = lazyDetalles;
    }

    public UnContratoDetalle getObjetoDetalle() {
        return objetoDetalle;
    }

    public void setObjetoDetalle(UnContratoDetalle objetoDetalle) {
        this.objetoDetalle = objetoDetalle;
    }

    public List<UnContratoDetalle> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<UnContratoDetalle> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public List<UnTecnologia> getListaTecnologias() {
        return listaTecnologias;
    }

    public void setListaTecnologias(List<UnTecnologia> listaTecnologias) {
        this.listaTecnologias = listaTecnologias;
    }

    public List<UnEntidad> getListaEntidades() {
        return listaEntidades;
    }

    public void setListaEntidades(List<UnEntidad> listaEntidades) {
        this.listaEntidades = listaEntidades;
    }

    public void listarDetalles(int idContrato) {
        super.setAccion(Url.ACCION_ADICIONAL_1);
        super.setDoAccion(Url.ACCION_LISTAR);
        this.setParamConsultaDetalles(new ParamConsulta());
        this.getParamConsultaDetalles().setParametroConsulta1(idContrato);
        procesoFinal();
    }

    public void refrescarDetalles() {
        if (this.getParamConsultaDetalles().getParametroConsulta1() != null) {
            super.setAccion(ACCION_ADICIONAL_1);
            super.setDoAccion(ACCION_LISTAR);
            getContratoServicio().Accion(this);
        } else {
            setListaDetalles(new ArrayList());
        }
    }

    public void verDetalle(int _id) {
        getObjetoDetalle().setId(_id);
        super.setAccion(ACCION_ADICIONAL_1);
        super.setDoAccion(ACCION_VER);
        getContratoServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmVerDetalle");
        PrimeFaces.current().executeScript("PF('dialogoVerDetalle').show()");
        procesoFinal();
    }

    public void crearDetalle() {
        super.setAccion(ACCION_ADICIONAL_1);
        super.setDoAccion(ACCION_CREAR);
        getContratoServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmCrearDetalle");
        PrimeFaces.current().executeScript("PF('dialogoCrearDetalle').show()");
        procesoFinal();
    }

    public void guardarDetalle() {
        super.setAccion(ACCION_ADICIONAL_1);
        super.setDoAccion(ACCION_GUARDAR);
        getContratoServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoCrearDetalle').hide();");
        }
        procesoFinal();
    }

    public void editarDetalle(int _id) {
        getObjetoDetalle().setId(_id);
        super.setAccion(ACCION_ADICIONAL_1);
        super.setDoAccion(ACCION_EDITAR);
        getContratoServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmEditarDetalle");
        PrimeFaces.current().executeScript("PF('dialogoEditarDetalle').show()");
        procesoFinal();
    }

    public void modificarDetalle() {
        super.setAccion(ACCION_ADICIONAL_1);
        super.setDoAccion(ACCION_MODIFICAR);
        getContratoServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoEditarDetalle').hide();");
        }
        procesoFinal();
    }

    public void borrarDetalle(int _id) {
        getObjetoDetalle().setId(_id);
        super.setAccion(ACCION_ADICIONAL_1);
        super.setDoAccion(ACCION_BORRAR);
        getContratoServicio().Accion(this);
        procesoFinal();
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
                    PrimeFaces.current().ajax().update("frmContratos");
                case Url.ACCION_VER:
                case Url.ACCION_CREAR:
                case Url.ACCION_EDITAR:
                    crearLog(getObjeto().toString());
                    break;
                case Url.ACCION_ADICIONAL_1:
                    switch (getDoAccion()) {
                        case Url.ACCION_LISTAR:
                            PrimeFaces.current().executeScript("PF('dialogoListarDetalles').show()");
                        case Url.ACCION_GUARDAR:
                        case Url.ACCION_MODIFICAR:
                        case Url.ACCION_BORRAR:
                            PrimeFaces.current().ajax().update("frmDetalles");
                        case Url.ACCION_VER:
                        case Url.ACCION_CREAR:
                        case Url.ACCION_EDITAR:
                            
                            break;
                        default:
                            break;
                    }
                    break;
            }
        }
        generarMensajes();
    }

}
