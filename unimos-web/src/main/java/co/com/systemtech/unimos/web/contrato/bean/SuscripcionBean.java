/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.contrato.bean;

import co.com.systemtech.unimos.dominio.administracion.GnMaestro;
import co.com.systemtech.unimos.dominio.administracion.Modulo;
import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.dominio.contrato.UnSuscripcion;
import co.com.systemtech.unimos.dominio.contrato.UnSuscripcionPago;
import co.com.systemtech.unimos.dominio.contrato.UnSuscripcionPersona;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.dominio.registro.Sede;
import co.com.systemtech.unimos.dominio.registro.UnComercial;
import co.com.systemtech.unimos.dominio.registro.UnPersona;
import co.com.systemtech.unimos.web.contrato.servicio.SuscripcionServicioIface;
import co.com.systemtech.unimos.web.utilidades.Url;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import co.com.systemtech.unimos.web.utilidades.CompatibilidadPF;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
@ManagedBean
@ViewScoped
public class SuscripcionBean extends Url {

    private SuscripcionServicioIface suscripcionServicio;
    private UnSuscripcion objeto;
    private List<UnSuscripcion> registros;
    private LazyDataModel<UnSuscripcion> lazyRegistros;

    private List<UnComercial> listaComerciales;
    private List<Sede> listaSedes;

    private List<GnMaestro> listaMaeTiposSuscripcion;
    private HashMap<Integer, GnMaestro> mapMaeTiposSuscripcion;
    private List<GnMaestro> listaMaePlanes;
    private HashMap<Integer, GnMaestro> mapMaePlanes;
    private HashMap<Integer, GnMaestro> mapMaeFormasPago;

    private List<Ubicacion> ubicaciones;
    private HashMap<Integer, Ubicacion> ubicacionesRecursiva;

    private byte[] pdfBytes;

    public SuscripcionBean() {
        this.objeto = new UnSuscripcion();
        this.objetoPago = new UnSuscripcionPago();
        Modulo _mod = super.validarModulo(Modulo.ID_SUSCRIPCIONES);
        super.getParamConsulta().setEmpresaId(super.getConexion().getEmpresa().getId());
        if (_mod == null) {
            super.redireccionar("/unimos/home.faces");
        } else {
            super.setModulo(_mod);
            lazyRegistros = new LazyDataModel<UnSuscripcion>() {

                private List<UnSuscripcion> lista;

                @Override
                public int count(Map<String, FilterMeta> filtros) {
                    return getParamConsulta().getCantidadRegistros();
                }

                @Override
                public List<UnSuscripcion> load(int primerRegistro, int registrosPagina, Map<String, SortMeta> listaOrdenes, Map<String, FilterMeta> filtros) {
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
                public String getRowKey(UnSuscripcion objeto) {
                    return objeto.getId().toString();
                }

                @Override
                public UnSuscripcion getRowData(String objetoId) {
                    Integer id = Integer.valueOf(objetoId);
                    for (UnSuscripcion objeto : lista) {
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
        }
    }

    @PostConstruct
    public void postConstruct() {
        getSuscripcionServicio().cargasInicial(this);
        listar();
    }

    public UnSuscripcion getObjeto() {
        return objeto;
    }

    public void setObjeto(UnSuscripcion objeto) {
        this.objeto = objeto;
    }

    public List<UnSuscripcion> getRegistros() {
        return registros;
    }

    public void setRegistros(List<UnSuscripcion> registros) {
        this.registros = registros;
    }

    public LazyDataModel<UnSuscripcion> getLazyRegistros() {
        return lazyRegistros;
    }

    public void setLazyRegistros(LazyDataModel<UnSuscripcion> lazyRegistros) {
        this.lazyRegistros = lazyRegistros;
    }

    public List<UnComercial> getListaComerciales() {
        return listaComerciales;
    }

    public void setListaComerciales(List<UnComercial> listaComerciales) {
        this.listaComerciales = listaComerciales;
    }

    public List<Sede> getListaSedes() {
        return listaSedes;
    }

    public void setListaSedes(List<Sede> listaSedes) {
        this.listaSedes = listaSedes;
    }

    public List<GnMaestro> getListaMaeTiposSuscripcion() {
        return listaMaeTiposSuscripcion;
    }

    public void setListaMaeTiposSuscripcion(List<GnMaestro> listaMaeTiposSuscripcion) {
        this.listaMaeTiposSuscripcion = listaMaeTiposSuscripcion;
    }

    public HashMap<Integer, GnMaestro> getMapMaeTiposSuscripcion() {
        return mapMaeTiposSuscripcion;
    }

    public void setMapMaeTiposSuscripcion(HashMap<Integer, GnMaestro> mapMaeTiposSuscripcion) {
        this.mapMaeTiposSuscripcion = mapMaeTiposSuscripcion;
    }

    public List<GnMaestro> getListaMaePlanes() {
        return listaMaePlanes;
    }

    public void setListaMaePlanes(List<GnMaestro> listaMaePlanes) {
        this.listaMaePlanes = listaMaePlanes;
    }

    public HashMap<Integer, GnMaestro> getMapMaePlanes() {
        return mapMaePlanes;
    }

    public void setMapMaePlanes(HashMap<Integer, GnMaestro> mapMaePlanes) {
        this.mapMaePlanes = mapMaePlanes;
    }

    public HashMap<Integer, GnMaestro> getMapMaeFormasPago() {
        return mapMaeFormasPago;
    }

    public void setMapMaeFormasPago(HashMap<Integer, GnMaestro> mapMaeFormasPago) {
        this.mapMaeFormasPago = mapMaeFormasPago;
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

    public SuscripcionServicioIface getSuscripcionServicio() {
        return suscripcionServicio;
    }

    public void setSuscripcionServicio(SuscripcionServicioIface suscripcionServicio) {
        this.suscripcionServicio = suscripcionServicio;
    }

    public byte[] getPdfBytes() {
        return pdfBytes;
    }

    public void setPdfBytes(byte[] pdfBytes) {
        this.pdfBytes = pdfBytes;
    }

    public StreamedContent getFile() {
        if (pdfBytes == null || pdfBytes.length == 0) {
            return null;
        }

        String nombre = "Carnet_Unimos_"
                + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";

        return DefaultStreamedContent.builder()
                .name(nombre)
                .contentType("application/pdf")
                .stream(() -> new ByteArrayInputStream(pdfBytes)) // stream fresco en cada click
                .build();
    }

    public void listar() {
        super.setAccion(ACCION_LISTAR);
        procesoFinal();
    }

    public void refrescar() {
        super.setAccion(Url.ACCION_LISTAR);
        getSuscripcionServicio().Accion(this);
    }

    public void ver(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_VER);
        getSuscripcionServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmVer");
        PrimeFaces.current().executeScript("PF('dialogoVer').show()");
        procesoFinal();
    }

    public void crear() {
        super.setAccion(ACCION_CREAR);
        getSuscripcionServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmCrear");
        PrimeFaces.current().executeScript("PF('dialogoCrear').show()");
        procesoFinal();
    }

    public void guardar() {
        super.setAccion(ACCION_GUARDAR);
        getSuscripcionServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoCrear').hide();");
        }
        procesoFinal();
    }

    public void editar(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_EDITAR);
        getSuscripcionServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmEditar");
        PrimeFaces.current().executeScript("PF('dialogoEditar').show()");
        procesoFinal();
    }

    public void modificar() {
        super.setAccion(ACCION_MODIFICAR);
        getSuscripcionServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoEditar').hide();");
        }
        procesoFinal();
    }

    public void borrar(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_BORRAR);
        getSuscripcionServicio().Accion(this);
        procesoFinal();
    }

    public void verCartera(int _id) {
        getObjeto().setId(_id);
        super.setAccion(ACCION_ADICIONAL_2);
        super.setDoAccion(ACCION_LISTAR);
        getSuscripcionServicio().Accion(this);
//        PrimeFaces.current().ajax().update("frmCartera");
//        PrimeFaces.current().executeScript("PF('dialogoCartera').show()");
        procesoFinal();
    }

    public String getUbicacionRecursiva(int id) {
        String ubicacionStr = "";
        if (ubicacionesRecursiva != null) {
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
        }
        return ubicacionStr;
    }

    public void calcularFechaFin() {
        if (this.getObjeto().getFechaInicio() != null) {
            // Convertir Date a LocalDate
            LocalDate inicioLocal = this.getObjeto().getFechaInicio().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            // Suma 1 año y luego restar 1 día
            LocalDate resultado = inicioLocal.plusYears(1).minusDays(1);
            // Convertir LocalDate de vuelta a Date
            this.getObjeto().setFechaFin(Date.from(resultado.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant())
            );
        }
    }

    /*
     * Procesos de Personas
     */
    public static final int ORIGEN_PERSONA_CREAR = 0;
    public static final int ORIGEN_PERSONA_EDITAR = 1;
    public static final int ORIGEN_PERSONA_BENEFICIARIO_CREAR = 2;
    public static final int ORIGEN_PERSONA_BENEFICIARIO_EDITAR = 3;

    private int origenPersona;
    private ParamConsulta paramConsultaPersonas;
    private LazyDataModel<UnPersona> lazyPersonas;
    private UnPersona persona;
    private List<UnPersona> listaPersonas;

    public int getOrigenPersona() {
        return origenPersona;
    }

    public void setOrigenPersona(int origenPersona) {
        this.origenPersona = origenPersona;
    }

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

    public UnPersona getPersona() {
        return persona;
    }

    public void setPersona(UnPersona persona) {
        this.persona = persona;
    }

    public List<UnPersona> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(List<UnPersona> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    public void refrescarPersona() {
        super.setAccion(ACCION_ADICIONAL_1);
        super.setDoAccion(ACCION_LISTAR);
        getSuscripcionServicio().Accion(this);
    }

    public void listarPersonas(int origen) {
        this.setOrigenPersona(origen);
        super.setAccion(Url.ACCION_ADICIONAL_1);
        super.setDoAccion(Url.ACCION_LISTAR);
        this.setParamConsultaPersonas(new ParamConsulta());
        this.getParamConsultaPersonas().setEmpresaId(super.getConexion().getEmpresa().getId());
        objeto.setPersonaAsignada(false);
        procesoFinal();
    }

    public void onRowPersonaSelect(SelectEvent event) {
        this.setPersona((UnPersona) event.getObject());
        super.setAccion(ACCION_ADICIONAL_1);
        super.setDoAccion(ACCION_ADICIONAL_1);
        getSuscripcionServicio().Accion(this);
        if (getErrores().size() >= 1) {
            generarMensajes();
            return;
        }
        this.paramConsultaPersonas.setFiltros(new HashMap());
        PrimeFaces.current().ajax().update("frmPersonaListar:tablaRegistros");
        PrimeFaces.current().executeScript("PF('dialogoPersonaListar').hide();");
        switch (getOrigenPersona()) {
            case SuscripcionBean.ORIGEN_PERSONA_CREAR:
                PrimeFaces.current().ajax().update("frmCrear:panelPersona");
            case SuscripcionBean.ORIGEN_PERSONA_EDITAR:
                PrimeFaces.current().ajax().update("frmEditar:panelPersona");
                break;
            case SuscripcionBean.ORIGEN_PERSONA_BENEFICIARIO_CREAR:
                PrimeFaces.current().ajax().update("frmCrear:tablaBeneficiarios");
            case SuscripcionBean.ORIGEN_PERSONA_BENEFICIARIO_EDITAR:
                PrimeFaces.current().ajax().update("frmEditar:tablaBeneficiarios");
                break;
        }
    }

    public void crearPersona() {
        super.setAccion(ACCION_ADICIONAL_1);
        super.setDoAccion(ACCION_CREAR);
        getSuscripcionServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmPersona");
        PrimeFaces.current().executeScript("PF('dialogoPersonaNuevo').show()");
        procesoFinal();
    }

    public void guardarPersona() {
        super.setAccion(ACCION_ADICIONAL_1);
        super.setDoAccion(ACCION_GUARDAR);
        getSuscripcionServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoPersonaNuevo').hide();");
        }
        procesoFinal();
    }

    /*
     * Procesos de Suscripcion Personas
     */
    private UnSuscripcionPersona objetoSuscrPer;
    private List<UnSuscripcionPersona> listaSuscripcionPersonas;

    public UnSuscripcionPersona getObjetoSuscrPer() {
        return objetoSuscrPer;
    }

    public void setObjetoSuscrPer(UnSuscripcionPersona objetoSuscrPer) {
        this.objetoSuscrPer = objetoSuscrPer;
    }

    public List<UnSuscripcionPersona> getListaSuscripcionPersonas() {
        return listaSuscripcionPersonas;
    }

    public void setListaSuscripcionPersonas(List<UnSuscripcionPersona> listaSuscripcionPersonas) {
        this.listaSuscripcionPersonas = listaSuscripcionPersonas;
    }

    public void beneficiarioRetirar(int id) {
        List<UnSuscripcionPersona> lista = new ArrayList();
        for (UnSuscripcionPersona obj : this.getObjeto().getListaSuscripcionPersonas()) {
            if (obj.getEstadoRegistro() == UnSuscripcionPersona.ESTADO_REGISTRO_CREADO && obj.getId() == id) {
                obj.setActivo(false);
                obj.setFechaFin(new Date());
                obj.setEstadoRegistro(UnSuscripcionPersona.ESTADO_REGISTRO_RETIRAR);
            }
            lista.add(obj);
        }
        this.getObjeto().setListaSuscripcionPersonas(lista);
        PrimeFaces.current().ajax().update("frmCrear:tablaBeneficiarios");
        PrimeFaces.current().ajax().update("frmEditar:tablaBeneficiarios");
    }

    public void beneficiarioBorrar(String documento) {
        List<UnSuscripcionPersona> lista = new ArrayList();
        for (UnSuscripcionPersona obj : this.getObjeto().getListaSuscripcionPersonas()) {
            if (obj.getEstadoRegistro() == UnSuscripcionPersona.ESTADO_REGISTRO_CREAR && obj.getUnPersona().getDocumentoNumero().equals(documento)) {

            } else {
                lista.add(obj);
            }
        }
        this.getObjeto().setListaSuscripcionPersonas(lista);
        PrimeFaces.current().ajax().update("frmCrear:tablaBeneficiarios");
        PrimeFaces.current().ajax().update("frmEditar:tablaBeneficiarios");
    }

    /*
    * Procesos Cartera y pagos
     */
    private UnSuscripcionPago objetoPago;
    private List<GnMaestro> listaMaeFormasPago;

    public UnSuscripcionPago getObjetoPago() {
        return objetoPago;
    }

    public void setObjetoPago(UnSuscripcionPago objetoPago) {
        this.objetoPago = objetoPago;
    }

    public List<GnMaestro> getListaMaeFormasPago() {
        return listaMaeFormasPago;
    }

    public void setListaMaeFormasPago(List<GnMaestro> listaMaeFormasPago) {
        this.listaMaeFormasPago = listaMaeFormasPago;
    }

    public void crearPago() {
        super.setAccion(ACCION_ADICIONAL_2);
        super.setDoAccion(ACCION_CREAR);
        getSuscripcionServicio().Accion(this);
        PrimeFaces.current().ajax().update("frmCrearPago");
        PrimeFaces.current().executeScript("PF('dialogoCrearPago').show()");
        procesoFinal();
    }

    public void guardarPago() {
        super.setAccion(ACCION_ADICIONAL_2);
        super.setDoAccion(ACCION_GUARDAR);
        getSuscripcionServicio().Accion(this);
        if (!super.isError()) {
            PrimeFaces.current().executeScript("PF('dialogoCrearPago').hide();");
        }
        procesoFinal();
    }

    /*
    * Generar PDF certificado
     */
    public StreamedContent generarCarnetUn(int _id) {
        try {
            getObjeto().setId(_id);
            super.setAccion(ACCION_ADICIONAL_3);
            super.setDoAccion(ACCION_ADICIONAL_1);
            getSuscripcionServicio().Accion(this);  // aquí dentro se llenará pdfBytes
            procesoFinal();
            return getFile();                       // <-- DEVUELVE el archivo
        } catch (Exception e) {
            addError("Error inesperado al generar el certificado. " + e.getMessage());
            procesoFinal();
            return null;
        }
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
                    PrimeFaces.current().ajax().update("frmSuscripciones");
                case Url.ACCION_VER:
                case Url.ACCION_CREAR:
                case Url.ACCION_EDITAR:
                    crearLog(getObjeto().toString());
                    break;
                case Url.ACCION_ADICIONAL_1:
                    if (getDoAccion() == Url.ACCION_LISTAR) {
                        PrimeFaces.current().ajax().update("frmPersonaListar");
                        PrimeFaces.current().executeScript("PF('dialogoPersonaListar').show()");
                    }
                    break;
                case Url.ACCION_ADICIONAL_2:
                    switch (getDoAccion()) {
                        case Url.ACCION_LISTAR:
                            PrimeFaces.current().ajax().update("frmCartera");
                            PrimeFaces.current().executeScript("PF('dialogoCartera').show()");
                            break;
                        case Url.ACCION_CREAR:
                            break;
                        case Url.ACCION_GUARDAR:
                            PrimeFaces.current().ajax().update("frmCartera");
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
