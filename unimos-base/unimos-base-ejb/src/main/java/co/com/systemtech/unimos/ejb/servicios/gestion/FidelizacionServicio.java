/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.gestion;

import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.dominio.contrato.UnContrato;
import co.com.systemtech.unimos.dominio.contrato.UnSuscripcion;
import co.com.systemtech.unimos.dominio.gestion.UnFidelizacion;
import co.com.systemtech.unimos.dominio.gestion.UnFidelizacionTecnologia;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.dominio.gestion.UnAgenda;
import co.com.systemtech.unimos.dominio.registro.UnEntidad;
import co.com.systemtech.unimos.dominio.registro.UnPersona;
import co.com.systemtech.unimos.dominio.registro.UnTecnologia;
import co.com.systemtech.unimos.ejb.entidades.UnAgendas;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import co.com.systemtech.unimos.ejb.entidades.UnContratos;
import co.com.systemtech.unimos.ejb.entidades.UnEntidades;
import co.com.systemtech.unimos.ejb.entidades.UnFidelizacionTecnologias;
import co.com.systemtech.unimos.ejb.entidades.UnPersonas;
import co.com.systemtech.unimos.ejb.entidades.UnFidelizaciones;
import co.com.systemtech.unimos.ejb.entidades.UnSuscripciones;
import co.com.systemtech.unimos.ejb.entidades.UnTecnologias;
import co.com.systemtech.unimos.negocio.gestion.FidelizacionRemoto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
@Stateless
@Remote(FidelizacionRemoto.class)
public class FidelizacionServicio extends GenericoServicio implements FidelizacionRemoto {

    @Override
    public int consultarCantidadLista(ParamConsulta paramConsulta) throws Exception {
        int cant = 0;
        try {
            String strQuery = "SELECT COUNT(e) FROM UnFidelizaciones e "
                    + " INNER JOIN UnPersonas p ON e.unPersonasId.id = p.id ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "id":
                            strQuery += "AND e.id = " + e.getValue() + " ";
                            break;
                        case "sede.id":
                            strQuery += "AND e.gnSedesId.id = " + e.getValue() + " ";
                            break;
                        case "maeTipoId":
                            strQuery += "AND e.maeTipoId = " + e.getValue() + " ";
                            break;
                        case "maePlanId":
                            strQuery += "AND e.maePlanId = " + e.getValue() + " ";
                            break;
                        case "unPersona.documentoNumero":
                            strQuery += "AND p.documentoNumero = " + e.getValue() + " ";
                            break;
                        case "unPersona.primerNombre":
                            strQuery += "AND p.primerNombre LIKE '" + e.getValue() + "%' ";
                            break;
                        case "unPersona.segundoNombre":
                            strQuery += "AND p.segundoNombre LIKE '" + e.getValue() + "%' ";
                            break;
                        case "unPersona.primerApellido":
                            strQuery += "AND p.primerApellido LIKE '" + e.getValue() + "%' ";
                            break;
                        case "unPersona.segundoApellido":
                            strQuery += "AND p.segundoApellido LIKE '" + e.getValue() + "%' ";
                            break;
                        case "estado":
                            strQuery += "AND e.estado = " + e.getValue() + " ";
                            break;
                    }
                }
            }
            cant = (int) (long) getEntityManager().createQuery(strQuery)
                    .getSingleResult();
        } catch (NoResultException e) {
            cant = 0;
        } catch (Exception e) {
            Exception(CONSULTAR_TODOS, e);
        } finally {
            cerrarEntityManager();
        }
        return cant;
    }

    @Override
    public List<UnFidelizacion> consultarLista(ParamConsulta paramConsulta) throws Exception {
        List<UnFidelizacion> listResult = new ArrayList();
        try {
            String strQuery = "SELECT e FROM UnFidelizaciones e "
                    + " INNER JOIN UnPersonas p ON e.unPersonasId.id = p.id ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "id":
                            strQuery += "AND e.id = " + e.getValue() + " ";
                            break;
                        case "sede.id":
                            strQuery += "AND e.gnSedesId.id = " + e.getValue() + " ";
                            break;
                        case "maeTipoId":
                            strQuery += "AND e.maeTipoId = " + e.getValue() + " ";
                            break;
                        case "maePlanId":
                            strQuery += "AND e.maePlanId = " + e.getValue() + " ";
                            break;
                        case "unPersona.documentoNumero":
                            strQuery += "AND p.documentoNumero = " + e.getValue() + " ";
                            break;
                        case "unPersona.primerNombre":
                            strQuery += "AND p.primerNombre LIKE '" + e.getValue() + "%' ";
                            break;
                        case "unPersona.segundoNombre":
                            strQuery += "AND p.segundoNombre LIKE '" + e.getValue() + "%' ";
                            break;
                        case "unPersona.primerApellido":
                            strQuery += "AND p.primerApellido LIKE '" + e.getValue() + "%' ";
                            break;
                        case "unPersona.segundoApellido":
                            strQuery += "AND p.segundoApellido LIKE '" + e.getValue() + "%' ";
                            break;
                        case "estado":
                            strQuery += "AND e.estado = " + e.getValue() + " ";
                            break;
                    }
                }
            }
            strQuery += "ORDER BY ";
            if (paramConsulta.getOrden() != null) {
                if (paramConsulta.getOrden().contains("unPersona")) {
                    strQuery += paramConsulta.getOrden().replace("unPersona", "p") + " "
                            + (paramConsulta.isAscendente() ? "ASC" : "DESC");
                } else {
                    strQuery += "e." + paramConsulta.getOrden() + " "
                            + (paramConsulta.isAscendente() ? "ASC" : "DESC");
                }
            } else {
                strQuery += "e.id ASC";
            }
            List<UnFidelizaciones> list = getEntityManager().createQuery(strQuery)
                    .setFirstResult(paramConsulta.getPrimerRegistro())
                    .setMaxResults(paramConsulta.getRegistrosPagina())
                    .getResultList();
            for (UnFidelizaciones per : list) {
                listResult.add(castEntidadNegocioCorto(per));
            }
        } catch (NoResultException e) {
            listResult = new ArrayList();
        } catch (Exception e) {
            Exception(CONSULTAR_TODOS, e);
        } finally {
            cerrarEntityManager();
        }
        return listResult;
    }

    @Override
    public UnFidelizacion consultar(int id) throws Exception {
        UnFidelizacion objRes = null;
        try {
            objRes = castEntidadNegocio((UnFidelizaciones) getEntityManager().find(UnFidelizaciones.class, id));
        } catch (NoResultException e) {
            objRes = null;
        } catch (Exception e) {
            Exception(CONSULTAR, e);
        } finally {
            cerrarEntityManager();
        }
        return objRes;
    }

    @Override
    public int insertar(UnFidelizacion obj) throws Exception {
        int id = 0;
        try {
            id = (int) getEntityManager().merge(castNegocioEntidad(obj)).getId();
            if (obj.getListaUnFidelizacionTecnologias() != null && !obj.getListaUnFidelizacionTecnologias().isEmpty()) {
                for (UnFidelizacionTecnologia objFidTec : obj.getListaUnFidelizacionTecnologias()) {
                    objFidTec.setUnFidelizacion(new UnFidelizacion(id));
                    getEntityManager().merge(castNegocioEntidad(objFidTec)).getId();
                }
            }
            obj.setId(id);
        } catch (Exception e) {
            Exception(INSERTAR, e);
        } finally {
            cerrarEntityManager();
        }
        return id;
    }

    @Override
    public void actualizar(UnFidelizacion obj) throws Exception {
        try {
            getEntityManager().merge(castNegocioEntidad(obj));
            if (obj.getListaUnFidelizacionTecnologias() != null && !obj.getListaUnFidelizacionTecnologias().isEmpty()) {
                for (UnFidelizacionTecnologia objFidTec : obj.getListaUnFidelizacionTecnologias()) {
                    if (objFidTec.getEstadoRegistro() == UnFidelizacionTecnologia.ESTADO_REGISTRO_GESTIONADO) {
//                        objFidTec.setUnFidelizacion(new UnFidelizacion(obj.getId()));
                        getEntityManager().merge(castNegocioEntidad(objFidTec)).getId();
                    }
                }
            }
        } catch (Exception e) {
            Exception(ACTUALIZAR, e);
        } finally {
            cerrarEntityManager();
        }
    }

    @Override
    public UnFidelizacion eliminar(int id) throws Exception {
        UnFidelizacion obj = null;
        try {
            UnFidelizaciones ent = getEntityManager().find(UnFidelizaciones.class, id);
            if (ent != null) {
                obj = castEntidadNegocioCorto(ent);
                getEntityManager().remove(ent);
            }
        } catch (NoResultException e) {
            obj = null;
        } catch (Exception e) {
            Exception(ELIMINAR, e);
        } finally {
            cerrarEntityManager();
        }
        return obj;
    }

    @Override
    public List<UnFidelizacion> consultarTodas() throws Exception {
        List<UnFidelizacion> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnFidelizaciones e "
                    + "ORDER BY e.fecha ASC, e.hora ASC";
            List<UnFidelizaciones> list = getEntityManager().createQuery(strQuery)
                    .getResultList();
            for (UnFidelizaciones per : list) {
                listResult.add(castEntidadNegocioCorto(per));
            }
        } catch (NoResultException e) {
            listResult = new ArrayList();
        } catch (Exception e) {
            Exception(CONSULTAR_TODOS, e);
        } finally {
            cerrarEntityManager();
        }
        return listResult;
    }

    @Override
    public List<UnFidelizacion> consultarTodasPorPersona(int personaId) throws Exception {
        List<UnFidelizacion> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnFidelizaciones e "
                    + "WHERE e.unPersonasId.id = :persona_id "
                    + "ORDER BY e.fechaInicio ASC";
            List<UnFidelizaciones> list = getEntityManager().createQuery(strQuery)
                    .setParameter("persona_id", personaId)
                    .getResultList();
            for (UnFidelizaciones per : list) {
                listResult.add(castEntidadNegocioCorto(per));
            }
        } catch (NoResultException e) {
            listResult = new ArrayList();
        } catch (Exception e) {
            Exception(CONSULTAR_TODOS, e);
        } finally {
            cerrarEntityManager();
        }
        return listResult;
    }

    public static UnFidelizacion castEntidadNegocioCorto(UnFidelizaciones per) {
        UnFidelizacion obj = new UnFidelizacion();
        obj.setId(per.getId());
        UnPersona usPersona = new UnPersona(
                per.getUnPersonasId().getId(),
                per.getUnPersonasId().getMaeTipoDocumentoTipo(),
                per.getUnPersonasId().getMaeTipoDocumentoId(),
                per.getUnPersonasId().getMaeTipoDocumentoCodigo(),
                per.getUnPersonasId().getMaeTipoDocumentoValor(),
                per.getUnPersonasId().getDocumentoNumero(),
                per.getUnPersonasId().getPrimerNombre(),
                per.getUnPersonasId().getSegundoNombre(),
                per.getUnPersonasId().getPrimerApellido(),
                per.getUnPersonasId().getSegundoApellido()
        );
        usPersona.setMaeSexoTipo(per.getUnPersonasId().getMaeSexoTipo());
        usPersona.setMaeSexoId(per.getUnPersonasId().getMaeSexoId());
        usPersona.setMaeSexoCodigo(per.getUnPersonasId().getMaeSexoCodigo());
        usPersona.setMaeSexoValor(per.getUnPersonasId().getMaeSexoValor());
        usPersona.setMaeEstadoCivilTipo(per.getUnPersonasId().getMaeEstadoCivilTipo());
        usPersona.setMaeEstadoCivilId(per.getUnPersonasId().getMaeEstadoCivilId());
        usPersona.setMaeEstadoCivilCodigo(per.getUnPersonasId().getMaeEstadoCivilCodigo());
        usPersona.setMaeEstadoCivilValor(per.getUnPersonasId().getMaeEstadoCivilValor());
        if (per.getUnPersonasId().getGnNacimientoUbicacionesId() != null) {
            usPersona.setGnNacimientoUbicacion(new Ubicacion(per.getUnPersonasId().getGnNacimientoUbicacionesId().getId()));
        }
        usPersona.setFechaNacimiento(per.getUnPersonasId().getFechaNacimiento());
        if (per.getUnPersonasId().getGnDireccionUbicacionesId() != null) {
            usPersona.setGnDireccionUbicacion(new Ubicacion(per.getUnPersonasId().getGnDireccionUbicacionesId().getId()));
        }
        if (per.getUnPersonasId().getGnNacionalidadUbicacionesId() != null) {
            usPersona.setGnNacionalidadUbicacion(new Ubicacion(per.getUnPersonasId().getGnNacionalidadUbicacionesId().getId()));
        }
        usPersona.setDireccion(per.getUnPersonasId().getDireccion());

        obj.setUnPersona(usPersona);
        obj.setValorSuscriptor(per.getValorSuscriptor().doubleValue());
        obj.setValorConvenio(per.getValorConvenio().doubleValue());
        obj.setEstado(per.getEstado());
        //Suscripciones
        if (per.getUnFidelizacionTecnologiasList() != null && !per.getUnFidelizacionTecnologiasList().isEmpty()) {
            List<UnFidelizacionTecnologia> listaObjFidTec = new ArrayList();
            for (UnFidelizacionTecnologias perTec : per.getUnFidelizacionTecnologiasList()) {
                UnTecnologia objTec = new UnTecnologia(
                        perTec.getUnTecnologiasId().getId(),
                        perTec.getUnTecnologiasId().getTipo(),
                        perTec.getUnTecnologiasId().getCodigo(),
                        perTec.getUnTecnologiasId().getCodigoPropio(),
                        perTec.getUnTecnologiasId().getNombre(),
                        perTec.getUnTecnologiasId().getMaeServicioHabilitacionTipo(),
                        perTec.getUnTecnologiasId().getMaeServicioHabilitacionId(),
                        perTec.getUnTecnologiasId().getMaeServicioHabilitacionCodigo(),
                        perTec.getUnTecnologiasId().getMaeServicioHabilitacionValor(),
                        perTec.getUnTecnologiasId().getActiva()
                );
                UnFidelizacionTecnologia objFidTec = new UnFidelizacionTecnologia(
                        perTec.getId(),
                        perTec.getFecha(),
                        perTec.getHora(),
                        perTec.getValorConvenio().doubleValue(),
                        perTec.getValorSuscriptor().doubleValue(),
                        perTec.getEstado(),
                        new UnFidelizacion(per.getId()),
                        new UnEntidad(perTec.getUnEntidadesId().getId()),
                        new UnContrato(perTec.getUnContratosId().getId()),
                        ((perTec.getUnAgendasId() == null) ? null : new UnAgenda(perTec.getUnAgendasId().getId())),
                        objTec
                );
                objFidTec.setUsuarioCrea(perTec.getUsuarioCrea());
                objFidTec.setTerminalCrea(perTec.getTerminalCrea());
                objFidTec.setFechaHoraCrea(perTec.getFechaHoraCrea());
                objFidTec.setUsuarioModifica(perTec.getUsuarioModifica());
                objFidTec.setTerminalModifica(perTec.getTerminalModifica());
                objFidTec.setFechaHoraModifica(perTec.getFechaHoraModifica());
                listaObjFidTec.add(objFidTec);
            }
            obj.setListaUnFidelizacionTecnologias(listaObjFidTec);
        }
        //Auditoría
        obj.setUsuarioCrea(per.getUsuarioCrea());
        obj.setTerminalCrea(per.getTerminalCrea());
        obj.setFechaHoraCrea(per.getFechaHoraCrea());
        obj.setUsuarioModifica(per.getUsuarioModifica());
        obj.setTerminalModifica(per.getTerminalModifica());
        obj.setFechaHoraModifica(per.getFechaHoraModifica());
        return obj;
    }

    public static UnFidelizacion castEntidadNegocio(UnFidelizaciones per) {
        UnFidelizacion obj = new UnFidelizacion();
        obj.setId(per.getId());
        //Persona
        UnPersona usPersona = new UnPersona(
                per.getUnPersonasId().getId(),
                per.getUnPersonasId().getMaeTipoDocumentoTipo(),
                per.getUnPersonasId().getMaeTipoDocumentoId(),
                per.getUnPersonasId().getMaeTipoDocumentoCodigo(),
                per.getUnPersonasId().getMaeTipoDocumentoValor(),
                per.getUnPersonasId().getDocumentoNumero(),
                per.getUnPersonasId().getPrimerNombre(),
                per.getUnPersonasId().getSegundoNombre(),
                per.getUnPersonasId().getPrimerApellido(),
                per.getUnPersonasId().getSegundoApellido()
        );
        usPersona.setMaeSexoTipo(per.getUnPersonasId().getMaeSexoTipo());
        usPersona.setMaeSexoId(per.getUnPersonasId().getMaeSexoId());
        usPersona.setMaeSexoCodigo(per.getUnPersonasId().getMaeSexoCodigo());
        usPersona.setMaeSexoValor(per.getUnPersonasId().getMaeSexoValor());
        usPersona.setMaeEstadoCivilTipo(per.getUnPersonasId().getMaeEstadoCivilTipo());
        usPersona.setMaeEstadoCivilId(per.getUnPersonasId().getMaeEstadoCivilId());
        usPersona.setMaeEstadoCivilCodigo(per.getUnPersonasId().getMaeEstadoCivilCodigo());
        usPersona.setMaeEstadoCivilValor(per.getUnPersonasId().getMaeEstadoCivilValor());
        if (per.getUnPersonasId().getGnNacimientoUbicacionesId() != null) {
            usPersona.setGnNacimientoUbicacion(new Ubicacion(per.getUnPersonasId().getGnNacimientoUbicacionesId().getId()));
        }
        usPersona.setFechaNacimiento(per.getUnPersonasId().getFechaNacimiento());
        if (per.getUnPersonasId().getGnDireccionUbicacionesId() != null) {
            usPersona.setGnDireccionUbicacion(new Ubicacion(per.getUnPersonasId().getGnDireccionUbicacionesId().getId()));
        }
        if (per.getUnPersonasId().getGnNacionalidadUbicacionesId() != null) {
            usPersona.setGnNacionalidadUbicacion(new Ubicacion(per.getUnPersonasId().getGnNacionalidadUbicacionesId().getId()));
        }
        usPersona.setDireccion(per.getUnPersonasId().getDireccion());
        obj.setUnPersona(usPersona);
        //Suscripcion
        UnSuscripcion usSuscripcion = new UnSuscripcion();
        usSuscripcion.setId(per.getUnSuscripcionesId().getId());
        usSuscripcion.setMaeTipoTipo(per.getUnSuscripcionesId().getMaeTipoTipo());
        usSuscripcion.setMaeTipoId(per.getUnSuscripcionesId().getMaeTipoId());
        usSuscripcion.setMaeTipoCodigo(per.getUnSuscripcionesId().getMaeTipoCodigo());
        usSuscripcion.setMaeTipoValor(per.getUnSuscripcionesId().getMaeTipoValor());
        usSuscripcion.setMaePlanTipo(per.getUnSuscripcionesId().getMaePlanTipo());
        usSuscripcion.setMaePlanId(per.getUnSuscripcionesId().getMaePlanId());
        usSuscripcion.setMaePlanCodigo(per.getUnSuscripcionesId().getMaePlanCodigo());
        usSuscripcion.setMaePlanValor(per.getUnSuscripcionesId().getMaePlanValor());
        usSuscripcion.setFechaInicio(per.getUnSuscripcionesId().getFechaInicio());
        usSuscripcion.setFechaFin(per.getUnSuscripcionesId().getFechaFin());
        usSuscripcion.setValor(per.getUnSuscripcionesId().getValor().doubleValue());
        if (per.getUnSuscripcionesId().getValorPagado() != null) {
            usSuscripcion.setValorPagado(per.getUnSuscripcionesId().getValorPagado().doubleValue());
        }
        if (per.getUnSuscripcionesId().getValorPendiente() != null) {
            usSuscripcion.setValorPendiente(per.getUnSuscripcionesId().getValorPendiente().doubleValue());
        }
        obj.setUnSuscripcion(usSuscripcion);
        obj.setValorSuscriptor(per.getValorSuscriptor().doubleValue());
        obj.setValorConvenio(per.getValorConvenio().doubleValue());
        obj.setEstado(per.getEstado());
        //Suscripciones
        if (per.getUnFidelizacionTecnologiasList() != null && !per.getUnFidelizacionTecnologiasList().isEmpty()) {
            List<UnFidelizacionTecnologia> listaObjFidTec = new ArrayList();
            for (UnFidelizacionTecnologias perTec : per.getUnFidelizacionTecnologiasList()) {
                UnTecnologia objTec = new UnTecnologia(
                        perTec.getUnTecnologiasId().getId(),
                        perTec.getUnTecnologiasId().getTipo(),
                        perTec.getUnTecnologiasId().getCodigo(),
                        perTec.getUnTecnologiasId().getCodigoPropio(),
                        perTec.getUnTecnologiasId().getNombre(),
                        perTec.getUnTecnologiasId().getMaeServicioHabilitacionTipo(),
                        perTec.getUnTecnologiasId().getMaeServicioHabilitacionId(),
                        perTec.getUnTecnologiasId().getMaeServicioHabilitacionCodigo(),
                        perTec.getUnTecnologiasId().getMaeServicioHabilitacionValor(),
                        perTec.getUnTecnologiasId().getActiva()
                );
                UnEntidad unEnt = new UnEntidad(
                        perTec.getUnEntidadesId().getId(),
                        perTec.getUnEntidadesId().getMaeTipoTipo(),
                        perTec.getUnEntidadesId().getMaeTipoId(),
                        perTec.getUnEntidadesId().getMaeTipoCodigo(),
                        perTec.getUnEntidadesId().getMaeTipoValor(),
                        perTec.getUnEntidadesId().getNit(),
                        perTec.getUnEntidadesId().getRazonSocial(),
                        perTec.getUnEntidadesId().getNombreComercial()
                );
                UnContrato unCont = new UnContrato(
                        perTec.getUnContratosId().getId(),
                        perTec.getUnContratosId().getContrato(),
                        perTec.getUnContratosId().getFechaInicial(),
                        perTec.getUnContratosId().getFechaFinal(),
                        perTec.getUnContratosId().getActivo()
                );
                UnAgenda unAgen = null;
                if (perTec.getUnAgendasId() != null) {
                    unAgen = new UnAgenda(perTec.getUnAgendasId().getId());
                }
                UnFidelizacionTecnologia objFidTec = new UnFidelizacionTecnologia(
                        perTec.getId(),
                        perTec.getFecha(),
                        perTec.getHora(),
                        perTec.getValorConvenio().doubleValue(),
                        perTec.getValorSuscriptor().doubleValue(),
                        perTec.getEstado(),
                        new UnFidelizacion(per.getId()),
                        unEnt,
                        unCont,
                        unAgen,
                        objTec
                );
                objFidTec.setUsuarioCrea(perTec.getUsuarioCrea());
                objFidTec.setTerminalCrea(perTec.getTerminalCrea());
                objFidTec.setFechaHoraCrea(perTec.getFechaHoraCrea());
                objFidTec.setUsuarioModifica(perTec.getUsuarioModifica());
                objFidTec.setTerminalModifica(perTec.getTerminalModifica());
                objFidTec.setFechaHoraModifica(perTec.getFechaHoraModifica());
                listaObjFidTec.add(objFidTec);
            }
            obj.setListaUnFidelizacionTecnologias(listaObjFidTec);
        }
        //Auditoría
        obj.setUsuarioCrea(per.getUsuarioCrea());
        obj.setTerminalCrea(per.getTerminalCrea());
        obj.setFechaHoraCrea(per.getFechaHoraCrea());
        obj.setUsuarioModifica(per.getUsuarioModifica());
        obj.setTerminalModifica(per.getTerminalModifica());
        obj.setFechaHoraModifica(per.getFechaHoraModifica());
        return obj;
    }

    public static UnFidelizaciones castNegocioEntidad(UnFidelizacion obj) {
        UnFidelizaciones per = new UnFidelizaciones();
        per.setId(obj.getId());
        per.setUnPersonasId(new UnPersonas(obj.getUnPersona().getId()));
        per.setUnSuscripcionesId(new UnSuscripciones(obj.getUnSuscripcion().getId()));
        per.setValorSuscriptor(new BigDecimal(obj.getValorSuscriptor()));
        per.setValorConvenio(new BigDecimal(obj.getValorConvenio()));
        per.setEstado(obj.getEstado());
        //Auditoria
        per.setUsuarioCrea(obj.getUsuarioCrea());
        per.setTerminalCrea(obj.getTerminalCrea());
        per.setFechaHoraCrea(obj.getFechaHoraCrea());
        per.setUsuarioModifica(obj.getUsuarioModifica());
        per.setTerminalModifica(obj.getTerminalModifica());
        per.setFechaHoraModifica(obj.getFechaHoraModifica());
        return per;
    }

    public static UnFidelizacionTecnologias castNegocioEntidad(UnFidelizacionTecnologia obj) {
        UnFidelizacionTecnologias per = new UnFidelizacionTecnologias();
        per.setId(obj.getId());
        per.setUnFidelizacionesId(new UnFidelizaciones(obj.getUnFidelizacion().getId()));
        per.setUnEntidadesId(new UnEntidades(obj.getUnEntidad().getId()));
        per.setUnContratosId(new UnContratos(obj.getUnContrato().getId()));
        if (obj.getUnAgenda() != null) {
            per.setUnAgendasId(new UnAgendas(obj.getUnAgenda().getId()));
        }
        per.setUnTecnologiasId(new UnTecnologias(obj.getUnTecnologia().getId()));
        per.setFecha(obj.getFecha());
        per.setHora(obj.getHora());
        per.setValorSuscriptor(new BigDecimal(obj.getValorSuscriptor()));
        per.setValorConvenio(new BigDecimal(obj.getValorConvenio()));
        per.setEstado(obj.getEstado());
        //Auditoria
        per.setUsuarioCrea(obj.getUsuarioCrea());
        per.setTerminalCrea(obj.getTerminalCrea());
        per.setFechaHoraCrea(obj.getFechaHoraCrea());
        per.setUsuarioModifica(obj.getUsuarioModifica());
        per.setTerminalModifica(obj.getTerminalModifica());
        per.setFechaHoraModifica(obj.getFechaHoraModifica());
        return per;
    }

}
