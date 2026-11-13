/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.contrato.servicio;

import co.com.systemtech.unimos.dominio.administracion.GnMaestro;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroTipo;
import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.dominio.contrato.UnComercialComision;
import co.com.systemtech.unimos.dominio.contrato.UnSuscripcion;
import co.com.systemtech.unimos.dominio.contrato.UnSuscripcionCartera;
import co.com.systemtech.unimos.dominio.contrato.UnSuscripcionPago;
import co.com.systemtech.unimos.dominio.contrato.UnSuscripcionPersona;
import co.com.systemtech.unimos.dominio.registro.UnPersona;
import co.com.systemtech.unimos.negocio.administracion.MaestroRemoto;
import co.com.systemtech.unimos.negocio.administracion.UbicacionRemoto;
import co.com.systemtech.unimos.negocio.contrato.CarteraRemoto;
import co.com.systemtech.unimos.negocio.contrato.ComisionRemoto;
import co.com.systemtech.unimos.negocio.contrato.PagoRemoto;
import co.com.systemtech.unimos.negocio.contrato.SuscripcionRemoto;
import co.com.systemtech.unimos.negocio.registro.ComercialRemoto;
import co.com.systemtech.unimos.negocio.registro.PersonaRemoto;
import co.com.systemtech.unimos.negocio.registro.SedeRemoto;
import co.com.systemtech.unimos.web.contrato.bean.SuscripcionBean;
import co.com.systemtech.unimos.web.utilidades.AccionesBO;
import co.com.systemtech.unimos.web.utilidades.RemotoEJB;
import co.com.systemtech.unimos.web.utilidades.Url;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class SuscripcionServicioImpl extends AccionesBO implements SuscripcionServicioIface {

    private SuscripcionRemoto getSuscripcionRemoto() throws Exception {
        return (SuscripcionRemoto) RemotoEJB.getEJBRemoto("SuscripcionServicio", SuscripcionRemoto.class.getName());
    }

    private CarteraRemoto getCarteraRemoto() throws Exception {
        return (CarteraRemoto) RemotoEJB.getEJBRemoto("CarteraServicio", CarteraRemoto.class.getName());
    }

    private PagoRemoto getPagoRemoto() throws Exception {
        return (PagoRemoto) RemotoEJB.getEJBRemoto("PagoServicio", PagoRemoto.class.getName());
    }

    private MaestroRemoto getMaestroRemoto() throws Exception {
        return (MaestroRemoto) RemotoEJB.getEJBRemoto("MaestroServicio", MaestroRemoto.class.getName());
    }

    private PersonaRemoto getPersonaRemoto() throws Exception {
        return (PersonaRemoto) RemotoEJB.getEJBRemoto("PersonaServicio", PersonaRemoto.class.getName());
    }

    private SedeRemoto getSedeRemoto() throws Exception {
        return (SedeRemoto) RemotoEJB.getEJBRemoto("SedeServicio", SedeRemoto.class.getName());
    }

    private ComercialRemoto getComercialRemoto() throws Exception {
        return (ComercialRemoto) RemotoEJB.getEJBRemoto("ComercialServicio", ComercialRemoto.class.getName());
    }

    private ComisionRemoto getComisionRemoto() throws Exception {
        return (ComisionRemoto) RemotoEJB.getEJBRemoto("ComisionServicio", ComisionRemoto.class.getName());
    }

    private UbicacionRemoto getUbicacionRemoto() throws Exception {
        return (UbicacionRemoto) RemotoEJB.getEJBRemoto("UbicacionServicio", UbicacionRemoto.class.getName());
    }

    @Override
    public void Accion(SuscripcionBean bean) {
        if (super.ValidarSesion(bean)) {
            switch (bean.getAccion()) {
                case Url.ACCION_LISTAR:
                    listar(bean);
                    break;
                case Url.ACCION_VER:
                    ver(bean);
                    break;
                case Url.ACCION_CREAR:
                    crear(bean);
                    break;
                case Url.ACCION_GUARDAR:
                    guardar(bean);
                    break;
                case Url.ACCION_EDITAR:
                    editar(bean);
                    break;
                case Url.ACCION_MODIFICAR:
                    modificar(bean);
                    break;
                case Url.ACCION_BORRAR:
                    borrar(bean);
                    break;
                case Url.ACCION_ADICIONAL_1:
                    switch (bean.getDoAccion()) {
                        case Url.ACCION_LISTAR:
                            listarPersonas(bean);
                            break;
                        case Url.ACCION_CREAR:

                            break;
                        case Url.ACCION_GUARDAR:

                            break;
                        case Url.ACCION_ADICIONAL_1:
                            seleccionarPersona(bean);
                            break;
                    }
                    break;
                case Url.ACCION_ADICIONAL_2:
                    switch (bean.getDoAccion()) {
                        case Url.ACCION_LISTAR:
                            listarCartera(bean);
                            break;
                        case Url.ACCION_CREAR:
                            crearPago(bean);
                            break;
                        case Url.ACCION_GUARDAR:
                            guardarPago(bean);
                            break;
                    }
                    break;
                default:
                    break;
            }
            cargas(bean);
        } else {
            System.err.println("Sesion inactiva");
        }
    }

    private void listar(SuscripcionBean bean) {
        try {
            bean.getParamConsulta().setCantidadRegistros(getSuscripcionRemoto().consultarCantidadLista(bean.getParamConsulta()));
            bean.setRegistros(getSuscripcionRemoto().consultarLista(bean.getParamConsulta()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void ver(SuscripcionBean bean) {
        try {
            bean.setObjeto(getSuscripcionRemoto().consultar(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void crear(SuscripcionBean bean) {
        try {
            bean.setObjeto(new UnSuscripcion());
//            //Registrar fecha inicio
//            bean.getObjeto().setFechaInicio(bean.getObjeto().getFechaInicioMinima());
//            //Agregar 1 año y restar 1 dia
//            Date fecha = (Date) bean.getObjeto().getFechaInicio().clone();
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(fecha);
//            cal.add(Calendar.YEAR, 1);
//            cal.add(Calendar.DAY_OF_MONTH, -1);
//            bean.getObjeto().setFechaFin(cal.getTime());
            //Valor
            bean.getObjeto().setValor(Double.valueOf(220000));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void guardar(SuscripcionBean bean) {
        try {
            //Llenar campos
            mapearMaestros(bean.getObjeto(), bean);
            bean.getObjeto().setValorPagado(Double.valueOf(0));
            bean.getObjeto().setValorPendiente(bean.getObjeto().getValor());
            bean.auditoriaGuardar(bean.getObjeto());
            List<UnSuscripcionPersona> lista = new ArrayList();
            if (bean.getObjeto().getListaSuscripcionPersonas() != null && !bean.getObjeto().getListaSuscripcionPersonas().isEmpty()) {
                for (UnSuscripcionPersona obj : bean.getObjeto().getListaSuscripcionPersonas()) {
                    obj.setFechaInicio(bean.getObjeto().getFechaInicio());
                    obj.setFechaFin(bean.getObjeto().getFechaFin());
                    bean.auditoriaGuardar(obj);
                    lista.add(obj);
                }
            }
            bean.getObjeto().setListaSuscripcionPersonas(lista);
            // Guardar Suscripcion
            bean.getObjeto().setId(getSuscripcionRemoto().insertar(bean.getObjeto()));
            // Guardar Cartera
            if (bean.getObjeto().getMaePlanCodigo().equals("TOT")) {
                UnSuscripcionCartera cartera = new UnSuscripcionCartera(
                        bean.getObjeto().getId(),
                        UnSuscripcionCartera.ESTADO_PENDIENTE,
                        new Date(),
                        new Date(),
                        Double.valueOf(220000),
                        Double.valueOf(0),
                        Double.valueOf(220000));
                bean.auditoriaGuardar(cartera);
                getCarteraRemoto().insertar(cartera);
            } else if (bean.getObjeto().getMaePlanCodigo().equals("FIN")) {
                Date fechaMaxima = new Date();
                UnSuscripcionCartera cartera = new UnSuscripcionCartera(
                        bean.getObjeto().getId(),
                        UnSuscripcionCartera.ESTADO_PENDIENTE,
                        new Date(),
                        fechaMaxima,
                        Double.valueOf(80000),
                        Double.valueOf(0),
                        Double.valueOf(80000));
                bean.auditoriaGuardar(cartera);
                //Cartera 1
                getCarteraRemoto().insertar(cartera);
                //Cartera 2
                LocalDate localDate = fechaMaxima.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate nuevaFecha = localDate.plusMonths(1);
                fechaMaxima = Date.from(nuevaFecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
                cartera.setValorAPagar(Double.valueOf(70000));
                cartera.setFechaMaxima(fechaMaxima);
                getCarteraRemoto().insertar(cartera);
                //Cartera 3
                localDate = fechaMaxima.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                nuevaFecha = localDate.plusMonths(1);
                fechaMaxima = Date.from(nuevaFecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
                cartera.setValorAPagar(Double.valueOf(70000));
                cartera.setFechaMaxima(fechaMaxima);
                getCarteraRemoto().insertar(cartera);
            }
            bean.addMensaje("Se creo un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void editar(SuscripcionBean bean) {
        try {
            bean.setObjeto(getSuscripcionRemoto().consultar(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void modificar(SuscripcionBean bean) {
        try {
            mapearMaestros(bean.getObjeto(), bean);
            bean.auditoriaModificar(bean.getObjeto());
            List<UnSuscripcionPersona> lista = new ArrayList();
            for (UnSuscripcionPersona obj : bean.getObjeto().getListaSuscripcionPersonas()) {
                if (obj.getEstadoRegistro() == UnSuscripcionPersona.ESTADO_REGISTRO_CREAR) {
                    obj.setFechaInicio(bean.getObjeto().getFechaInicio());
                    obj.setFechaFin(bean.getObjeto().getFechaFin());
                    bean.auditoriaGuardar(obj);
                } else if (obj.getEstadoRegistro() == UnSuscripcionPersona.ESTADO_REGISTRO_RETIRAR) {
                    bean.auditoriaModificar(obj);
                }
                lista.add(obj);
            }
            bean.getObjeto().setListaSuscripcionPersonas(lista);
            getSuscripcionRemoto().actualizar(bean.getObjeto());
            bean.addMensaje("Se actualizó el registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void borrar(SuscripcionBean bean) {
        try {
            bean.setObjeto(getSuscripcionRemoto().eliminar(bean.getObjeto().getId()));
            bean.addMensaje("Se eliminó un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void cargas(SuscripcionBean bean) {
        try {
            switch (bean.getAccion()) {
                case Url.ACCION_LISTAR:
//                    bean.setUbicacionesRecursiva(getUbicacionRemoto().consultarMunicipios());
//                    bean.setUbicaciones(getUbicacionRemoto().consultarPorTipo(Ubicacion.TIPO_MUNICIPIO));
                    break;
                case Url.ACCION_VER:
                    break;
                case Url.ACCION_CREAR:
                case Url.ACCION_EDITAR:
//                    bean.setUbicacionesRecursiva(getUbicacionRemoto().consultarMunicipios());
//                    bean.setUbicaciones(getUbicacionRemoto().consultarPorTipo(Ubicacion.TIPO_MUNICIPIO));
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void cargasInicial(SuscripcionBean bean) {
        try {
            //Personas

            //Comerciales
            //Sedes
            //Tipos Suscripcion
            bean.setMapMaeTiposSuscripcion(getMaestroRemoto().consultarHashPorTipo(GnMaestroTipo.SUS_TIPO_SUSCRIPCION));
            bean.setListaMaeTiposSuscripcion(new ArrayList<>(bean.getMapMaeTiposSuscripcion().values()));
            //Planes
            bean.setMapMaePlanes(getMaestroRemoto().consultarHashPorTipo(GnMaestroTipo.SUS_PLAN));
            bean.setListaMaePlanes(new ArrayList<>(bean.getMapMaePlanes().values()));
            //Formas pago
            bean.setMapMaeFormasPago(getMaestroRemoto().consultarHashPorTipo(GnMaestroTipo.SUS_FORMA_PAGO));
            bean.setListaMaeFormasPago(new ArrayList<>(bean.getMapMaeFormasPago().values()));
            //Comerciales
            bean.setListaComerciales(getComercialRemoto().consultarTodosActivos());
            //Sedes
            bean.setListaSedes(getSedeRemoto().consultarTodas());
//            //Personas
//            bean.setListaPersonas(getPersonaRemoto().consultarTodas());
            //Ubicaciones
            bean.setUbicacionesRecursiva(getUbicacionRemoto().consultarMunicipios());
            bean.setUbicaciones(getUbicacionRemoto().consultarPorTipo(Ubicacion.TIPO_MUNICIPIO));
        } catch (Exception e) {
            bean.addError("No fue posible cargar las listas de apoyo");
        }
    }

    private void mapearMaestros(UnSuscripcion obj, SuscripcionBean bean) {
        // Tipo Suscripcion
        if (bean.getMapMaeTiposSuscripcion() != null) {
            GnMaestro tipoSuscr = bean.getMapMaeTiposSuscripcion().get(obj.getMaeTipoId());
            obj.setMaeTipoTipo(tipoSuscr.getTipo());
            obj.setMaeTipoCodigo(tipoSuscr.getValor());
            obj.setMaeTipoValor(tipoSuscr.getDescripcion());
        }
        // Plan
        if (bean.getMapMaePlanes() != null) {
            GnMaestro plan = bean.getMapMaePlanes().get(obj.getMaePlanId());
            obj.setMaePlanTipo(plan.getTipo());
            obj.setMaePlanCodigo(plan.getValor());
            obj.setMaePlanValor(plan.getDescripcion());
        }
//        // Forma de pago
//        if (bean.getMapMaeFormasPago() != null) {
//            GnMaestro estadoCiv = bean.getMapMaeFormasPago().get(obj.getMaeFormaPagoId());
//            obj.setMaeFormaPagoTipo(estadoCiv.getTipo());
//            obj.setMaeFormaPagoCodigo(estadoCiv.getValor());
//            obj.setMaeFormaPagoValor(estadoCiv.getDescripcion());
//        }
    }

    /**
     * Procesos de gestion de Personas
     */
    private void listarPersonas(SuscripcionBean bean) {
        try {
            bean.getParamConsultaPersonas().setCantidadRegistros(getPersonaRemoto().consultarCantidadLista(bean.getParamConsultaPersonas()));
            bean.setListaPersonas(getPersonaRemoto().consultarLista(bean.getParamConsultaPersonas()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void seleccionarPersona(SuscripcionBean bean) {
        try {
            switch (bean.getOrigenPersona()) {
                case SuscripcionBean.ORIGEN_PERSONA_CREAR:
                case SuscripcionBean.ORIGEN_PERSONA_EDITAR:
                    bean.getObjeto().setUnPersona(getPersonaRemoto().consultar(bean.getPersona().getId()));
                    break;
                case SuscripcionBean.ORIGEN_PERSONA_BENEFICIARIO_CREAR:
                case SuscripcionBean.ORIGEN_PERSONA_BENEFICIARIO_EDITAR:
                    UnPersona pers = getPersonaRemoto().consultar(bean.getPersona().getId());
                    UnSuscripcionPersona susc = new UnSuscripcionPersona(
                            null,
                            bean.getObjeto().getFechaInicio(),
                            bean.getObjeto().getFechaFin(),
                            true,
                            false,
                            new UnSuscripcion(bean.getObjeto().getId()),
                            pers
                    );
                    susc.setEstadoRegistro(UnSuscripcionPersona.ESTADO_REGISTRO_CREAR);
                    bean.getObjeto().addSuscripcionPersona(susc);
                    break;
            }
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    /*
    * Procesos de cartera
     */
    private void listarCartera(SuscripcionBean bean) {
        try {
            bean.setObjeto(getSuscripcionRemoto().consultar(bean.getObjeto().getId()));
            bean.getObjeto().setListaSuscripcionCarteras(getCarteraRemoto().consultarPorSuscripcion(bean.getObjeto().getId()));
            bean.getObjeto().setListaSuscripcionPagos(getPagoRemoto().consultarPorSuscripcion(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void crearPago(SuscripcionBean bean) {
        try {
            bean.setObjetoPago(new UnSuscripcionPago());
            bean.getObjetoPago().setUnSuscripcion(new UnSuscripcion(bean.getObjeto().getId()));
            bean.getObjetoPago().setFecha(new Date());
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void guardarPago(SuscripcionBean bean) {
        try {
            //Llenar campos
            mapearMaestrosPago(bean.getObjetoPago(), bean);
            bean.auditoriaGuardar(bean.getObjetoPago());
            // Guardar Suscripcion
            bean.getObjetoPago().setId(getPagoRemoto().insertar(bean.getObjetoPago()));
            //Listar registros de nuevo
            listarCartera(bean);
            //Ajustar Pago en Cartera
            double valorPago = 0;
            for (UnSuscripcionPago pago : bean.getObjeto().getListaSuscripcionPagos()) {
                valorPago += pago.getValor();
            }
            if (valorPago > bean.getObjeto().getValor()) {
                throw new Exception("El valor de los pagos no puede superar el valor de la suscripción");
            }
            //valores pagados por Suscripcion            
            bean.getObjeto().setValorPagado(valorPago);
            bean.getObjeto().setValorPendiente(bean.getObjeto().getValor() - valorPago);
            getSuscripcionRemoto().actualizarPagos(bean.getObjeto());
            //Valores pagados por cartera
            if (valorPago > 0) {
                for (UnSuscripcionCartera cartera : bean.getObjeto().getListaSuscripcionCarteras()) {
                    boolean comision = false;
                    UnSuscripcionCartera carteraIni = (UnSuscripcionCartera) cartera.clone();
                    if (valorPago > 0) {
                        if (valorPago >= cartera.getValorAPagar()) {
                            cartera.setValorPagado(cartera.getValorAPagar());
                            cartera.setValorPendiente(Double.valueOf(0));
                            cartera.setEstado(UnSuscripcionCartera.ESTADO_PAGO_TOTAL);
                            if (cartera.getFechaPago() == null) {
                                cartera.setFechaPago(bean.getObjetoPago().getFecha());
                            }
                            valorPago = valorPago - cartera.getValorAPagar();
                            if (!carteraIni.equals(cartera)) {
                                comision = true;
                            }
                        } else {
                            cartera.setValorPagado(valorPago);
                            cartera.setValorPendiente(cartera.getValorAPagar() - cartera.getValorPagado());
                            cartera.setEstado(UnSuscripcionCartera.ESTADO_PAGO_PARCIAL);
                            cartera.setFechaPago(null);
                            valorPago = 0;
                        }
                        getCarteraRemoto().actualizarPagos(cartera);
                        if (comision && bean.getObjeto().getUnComercial().isComisiona()) {
                            //Generar comisión
                            UnComercialComision objComision = new UnComercialComision(
                                    null,
                                    bean.getObjeto().getUnComercial().getId(),
                                    bean.getObjeto().getId(),
                                    cartera.getId(),
                                    cartera.getFechaPago(),
                                    UnComercialComision.COMISION,
                                    cartera.getValorPagado() * (UnComercialComision.COMISION / 100));
                            bean.auditoriaGuardar(objComision);
                            getComisionRemoto().insertar(objComision);
                        }
                    } else {
                        cartera.setValorPagado(Double.valueOf(0));
                        cartera.setValorPendiente(cartera.getValorAPagar());
                        cartera.setEstado(UnSuscripcionCartera.ESTADO_PENDIENTE);
                        cartera.setFechaPago(null);
                    }
                }
            }
            bean.addMensaje("Se creo un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void mapearMaestrosPago(UnSuscripcionPago obj, SuscripcionBean bean) {
        // Forma de pago
        if (bean.getMapMaeFormasPago() != null) {
            GnMaestro estadoCiv = bean.getMapMaeFormasPago().get(obj.getMaeFormaPagoId());
            obj.setMaeFormaPagoTipo(estadoCiv.getTipo());
            obj.setMaeFormaPagoCodigo(estadoCiv.getValor());
            obj.setMaeFormaPagoValor(estadoCiv.getDescripcion());
        }
    }

}
