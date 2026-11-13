/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.contrato;

import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.dominio.contrato.UnSuscripcion;
import co.com.systemtech.unimos.dominio.contrato.UnSuscripcionPersona;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.dominio.registro.Sede;
import co.com.systemtech.unimos.dominio.registro.UnComercial;
import co.com.systemtech.unimos.dominio.registro.UnPersona;
import co.com.systemtech.unimos.ejb.entidades.GnSedes;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import co.com.systemtech.unimos.ejb.entidades.UnComerciales;
import co.com.systemtech.unimos.ejb.entidades.UnPersonas;
import co.com.systemtech.unimos.ejb.entidades.UnSuscripcionPersonas;
import co.com.systemtech.unimos.ejb.entidades.UnSuscripciones;
import co.com.systemtech.unimos.negocio.contrato.SuscripcionRemoto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
@Stateless
@Remote(SuscripcionRemoto.class)
public class SuscripcionServicio extends GenericoServicio implements SuscripcionRemoto {

    @Override
    public int consultarCantidadLista(ParamConsulta paramConsulta) throws Exception {
        int cant = 0;
        try {
            String strQuery = "SELECT COUNT(e) FROM UnSuscripciones e "
                    + " INNER JOIN UnPersonas p ON e.unPersonasId.id = p.id "
                    + "WHERE e.id > 0 ";
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
                            strQuery += "AND p.documentoNumero = '" + e.getValue() + "' ";
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
    public List<UnSuscripcion> consultarLista(ParamConsulta paramConsulta) throws Exception {
        List<UnSuscripcion> listResult = new ArrayList();
        try {
            String strQuery = "SELECT e FROM UnSuscripciones e "
                    + " INNER JOIN UnPersonas p ON e.unPersonasId.id = p.id "
                    + "WHERE e.id > 0 ";
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
                            strQuery += "AND p.documentoNumero = '" + e.getValue() + "' ";
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
            List<UnSuscripciones> list = getEntityManager().createQuery(strQuery)
                    .setFirstResult(paramConsulta.getPrimerRegistro())
                    .setMaxResults(paramConsulta.getRegistrosPagina())
                    .getResultList();
            for (UnSuscripciones per : list) {
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
    public UnSuscripcion consultar(int id) throws Exception {
        UnSuscripcion objRes = null;
        try {
            objRes = castEntidadNegocio((UnSuscripciones) getEntityManager().find(UnSuscripciones.class, id));
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
    public int insertar(UnSuscripcion obj) throws Exception {
        int id = 0;
        try {
            id = (int) getEntityManager().merge(castNegocioEntidad(obj)).getId();
            obj.setId(id);
            UnSuscripcionPersona objTitular = new UnSuscripcionPersona(
                    null,
                    obj.getFechaInicio(),
                    obj.getFechaFin(),
                    true,
                    true,
                    new UnSuscripcion(obj.getId()),
                    new UnPersona(obj.getUnPersona().getId())
            );
            objTitular.setUsuarioCrea(obj.getUsuarioCrea());
            objTitular.setTerminalCrea(obj.getTerminalCrea());
            objTitular.setFechaHoraCrea(obj.getFechaHoraCrea());
            getEntityManager().merge(castNegocioEntidad(objTitular)).getId();
            if (obj.getListaSuscripcionPersonas() != null && !obj.getListaSuscripcionPersonas().isEmpty()) {
                for (UnSuscripcionPersona objSus : obj.getListaSuscripcionPersonas()) {
                    objSus.setUnSuscripcion(new UnSuscripcion(id));
                    getEntityManager().merge(castNegocioEntidad(objSus)).getId();
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
    public void actualizar(UnSuscripcion obj) throws Exception {
        try {
            getEntityManager().merge(castNegocioEntidad(obj));
            if (obj.getListaSuscripcionPersonas() != null && !obj.getListaSuscripcionPersonas().isEmpty()) {
                for (UnSuscripcionPersona objSus : obj.getListaSuscripcionPersonas()) {
                    objSus.setUnSuscripcion(new UnSuscripcion(obj.getId()));
                    if (objSus.getEstadoRegistro() == UnSuscripcionPersona.ESTADO_REGISTRO_CREAR) {
                        getEntityManager().merge(castNegocioEntidad(objSus)).getId();
                    } else if (objSus.getEstadoRegistro() == UnSuscripcionPersona.ESTADO_REGISTRO_RETIRAR) {
                        getEntityManager().merge(castNegocioEntidad(objSus)).getId();
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
    public void actualizarPagos(UnSuscripcion obj) throws Exception {
        try {
            String hql = "UPDATE UnSuscripciones SET "
                    + "valorPagado = :valorPagado, "
                    + "valorPendiente = :valorPendiente "
                    + "WHERE id = :id ";
            Query query = getEntityManager().createQuery(hql);
            query.setParameter("valorPagado", BigDecimal.valueOf(obj.getValorPagado()));
            query.setParameter("valorPendiente", BigDecimal.valueOf(obj.getValorPendiente()));
            query.setParameter("id", obj.getId());
            query.executeUpdate();
        } catch (NoResultException e) {

        } catch (Exception e) {
            Exception(ACTUALIZAR, e);
        } finally {
            cerrarEntityManager();
        }
    }

    @Override
    public UnSuscripcion eliminar(int id) throws Exception {
        UnSuscripcion obj = null;
        try {
            UnSuscripciones ent = getEntityManager().find(UnSuscripciones.class, id);
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
    public List<UnSuscripcion> consultarTodas() throws Exception {
        List<UnSuscripcion> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnSuscripciones e "
                    + "ORDER BY e.documentoNumero ASC";
            List<UnSuscripciones> list = getEntityManager().createQuery(strQuery)
                    .getResultList();
            for (UnSuscripciones per : list) {
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
    public List<UnSuscripcion> consultarTodasPorPersona(int personaId) throws Exception {
        List<UnSuscripcion> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnSuscripciones e "
                    + "WHERE e.unPersonasId.id = :persona_id "
                    + "ORDER BY e.fechaInicio ASC";
            List<UnSuscripciones> list = getEntityManager().createQuery(strQuery)
                    .setParameter("persona_id", personaId)
                    .getResultList();
            for (UnSuscripciones per : list) {
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

    public static UnSuscripcion castEntidadNegocioCorto(UnSuscripciones per) {
        UnSuscripcion obj = new UnSuscripcion();
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
        obj.setSede(
                new Sede(
                        per.getGnSedesId().getId(),
                        per.getGnSedesId().getNombre(),
                        per.getGnSedesId().getDescripcion()
                )
        );
        obj.setMaeTipoTipo(per.getMaeTipoTipo());
        obj.setMaeTipoId(per.getMaeTipoId());
        obj.setMaeTipoCodigo(per.getMaeTipoCodigo());
        obj.setMaeTipoValor(per.getMaeTipoValor());
        obj.setMaePlanTipo(per.getMaePlanTipo());
        obj.setMaePlanId(per.getMaePlanId());
        obj.setMaePlanCodigo(per.getMaePlanCodigo());
        obj.setMaePlanValor(per.getMaePlanValor());
        obj.setUnComercial(new UnComercial(
                per.getUnComercialesId().getId(),
                per.getUnComercialesId().getMaeTipoDocumentoValor(),
                per.getUnComercialesId().getDocumentoNumero(),
                per.getUnComercialesId().getPrimerNombre(),
                per.getUnComercialesId().getSegundoNombre(),
                per.getUnComercialesId().getPrimerApellido(),
                per.getUnComercialesId().getSegundoApellido()
        ));
        obj.setFechaInicio(per.getFechaInicio());
        obj.setFechaFin(per.getFechaFin());
        obj.setValor(per.getValor().doubleValue());
        if (per.getValorPagado() != null) {
            obj.setValorPagado(per.getValorPagado().doubleValue());
        }
        if (per.getValorPendiente() != null) {
            obj.setValorPendiente(per.getValorPendiente().doubleValue());
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

    public static UnSuscripcion castEntidadNegocio(UnSuscripciones per) {
        UnSuscripcion obj = new UnSuscripcion();
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
        obj.setSede(
                new Sede(
                        per.getGnSedesId().getId(),
                        per.getGnSedesId().getNombre(),
                        per.getGnSedesId().getDescripcion()
                )
        );
        obj.setMaeTipoTipo(per.getMaeTipoTipo());
        obj.setMaeTipoId(per.getMaeTipoId());
        obj.setMaeTipoCodigo(per.getMaeTipoCodigo());
        obj.setMaeTipoValor(per.getMaeTipoValor());
        obj.setMaePlanTipo(per.getMaePlanTipo());
        obj.setMaePlanId(per.getMaePlanId());
        obj.setMaePlanCodigo(per.getMaePlanCodigo());
        obj.setMaePlanValor(per.getMaePlanValor());
        obj.setUnComercial(new UnComercial(
                per.getUnComercialesId().getId(),
                per.getUnComercialesId().getMaeTipoDocumentoValor(),
                per.getUnComercialesId().getDocumentoNumero(),
                per.getUnComercialesId().getPrimerNombre(),
                per.getUnComercialesId().getSegundoNombre(),
                per.getUnComercialesId().getPrimerApellido(),
                per.getUnComercialesId().getSegundoApellido()
        ));
        obj.setFechaInicio(per.getFechaInicio());
        obj.setFechaFin(per.getFechaFin());
        obj.setValor(per.getValor().doubleValue());
        if (per.getValorPagado() != null) {
            obj.setValorPagado(per.getValorPagado().doubleValue());
        }
        if (per.getValorPendiente() != null) {
            obj.setValorPendiente(per.getValorPendiente().doubleValue());
        }
        //Suscripciones
        if (per.getUnSuscripcionPersonasList() != null && !per.getUnSuscripcionPersonasList().isEmpty()) {
            List<UnSuscripcionPersona> listaObjSus = new ArrayList();
            for (UnSuscripcionPersonas perSus : per.getUnSuscripcionPersonasList()) {
                if (!perSus.getTitular()) {
                    UnPersona objPer = new UnPersona(
                            perSus.getUnPersonasId().getId(),
                            perSus.getUnPersonasId().getMaeTipoDocumentoTipo(),
                            perSus.getUnPersonasId().getMaeTipoDocumentoId(),
                            perSus.getUnPersonasId().getMaeTipoDocumentoCodigo(),
                            perSus.getUnPersonasId().getMaeTipoDocumentoValor(),
                            perSus.getUnPersonasId().getDocumentoNumero(),
                            perSus.getUnPersonasId().getPrimerNombre(),
                            perSus.getUnPersonasId().getSegundoNombre(),
                            perSus.getUnPersonasId().getPrimerApellido(),
                            perSus.getUnPersonasId().getSegundoApellido()
                    );
                    UnSuscripcionPersona objSus = new UnSuscripcionPersona(
                            perSus.getId(),
                            perSus.getFechaInicio(),
                            perSus.getFechaFin(),
                            perSus.getActivo(),
                            perSus.getTitular(),
                            new UnSuscripcion(per.getId()),
                            objPer
                    );
                    objSus.setEstadoRegistro(UnSuscripcionPersona.ESTADO_REGISTRO_CREADO);
                    objSus.setUsuarioCrea(perSus.getUsuarioCrea());
                    objSus.setTerminalCrea(perSus.getTerminalCrea());
                    objSus.setFechaHoraCrea(perSus.getFechaHoraCrea());
                    objSus.setUsuarioModifica(perSus.getUsuarioModifica());
                    objSus.setTerminalModifica(perSus.getTerminalModifica());
                    objSus.setFechaHoraModifica(perSus.getFechaHoraModifica());
                    listaObjSus.add(objSus);
                }
            }
            obj.setListaSuscripcionPersonas(listaObjSus);
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

    public static UnSuscripciones castNegocioEntidad(UnSuscripcion obj) {
        UnSuscripciones per = new UnSuscripciones();
        per.setId(obj.getId());
        per.setGnSedesId(new GnSedes(obj.getSede().getId()));
        per.setMaeTipoTipo(obj.getMaeTipoTipo());
        per.setMaeTipoId(obj.getMaeTipoId());
        per.setMaeTipoCodigo(obj.getMaeTipoCodigo());
        per.setMaeTipoValor(obj.getMaeTipoValor());
        per.setMaePlanTipo(obj.getMaePlanTipo());
        per.setMaePlanId(obj.getMaePlanId());
        per.setMaePlanCodigo(obj.getMaePlanCodigo());
        per.setMaePlanValor(obj.getMaePlanValor());
        per.setUnPersonasId(new UnPersonas(obj.getUnPersona().getId()));
        per.setUnComercialesId(new UnComerciales(obj.getUnComercial().getId()));
        per.setFechaInicio(obj.getFechaInicio());
        per.setFechaFin(obj.getFechaFin());
        if (obj.getValor() != null) {
            per.setValor(new BigDecimal(obj.getValor()));
        }
        if (obj.getValorPagado() != null) {
            per.setValorPagado(new BigDecimal(obj.getValorPagado()));
        }
        if (obj.getValorPendiente() != null) {
            per.setValorPendiente(new BigDecimal(obj.getValorPendiente()));
        }
        //Auditoria
        per.setUsuarioCrea(obj.getUsuarioCrea());
        per.setTerminalCrea(obj.getTerminalCrea());
        per.setFechaHoraCrea(obj.getFechaHoraCrea());
        per.setUsuarioModifica(obj.getUsuarioModifica());
        per.setTerminalModifica(obj.getTerminalModifica());
        per.setFechaHoraModifica(obj.getFechaHoraModifica());
        return per;
    }

    /*
    * Suscripcion Persona (Beneficiario)
     */
    public static UnSuscripcionPersonas castNegocioEntidad(UnSuscripcionPersona obj) {
        UnSuscripcionPersonas per = new UnSuscripcionPersonas();
        per.setId(obj.getId());
        per.setUnSuscripcionesId(new UnSuscripciones(obj.getUnSuscripcion().getId()));
        per.setUnPersonasId(new UnPersonas(obj.getUnPersona().getId()));
        per.setFechaInicio(obj.getFechaInicio());
        per.setFechaFin(obj.getFechaFin());
        per.setActivo(obj.isActivo());
        per.setTitular(obj.isTitular());
        //Auditoria
        per.setUsuarioCrea(obj.getUsuarioCrea());
        per.setTerminalCrea(obj.getTerminalCrea());
        per.setFechaHoraCrea(obj.getFechaHoraCrea());
        per.setUsuarioModifica(obj.getUsuarioModifica());
        per.setTerminalModifica(obj.getTerminalModifica());
        per.setFechaHoraModifica(obj.getFechaHoraModifica());
        return per;
    }

    public static UnSuscripcionPersona castEntidadNegocio(UnSuscripcionPersonas per) {
        UnSuscripcionPersona obj = new UnSuscripcionPersona();
        obj.setId(per.getId());
        obj.setUnSuscripcion(new UnSuscripcion(per.getUnSuscripcionesId().getId()));
        UnPersona objPer = new UnPersona(
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
        obj.setUnPersona(objPer);
        obj.setFechaInicio(per.getFechaInicio());
        obj.setFechaFin(per.getFechaFin());
        obj.setActivo(per.getActivo());
        obj.setTitular(per.getTitular());
        //Auditoria
        obj.setUsuarioCrea(per.getUsuarioCrea());
        obj.setTerminalCrea(per.getTerminalCrea());
        obj.setFechaHoraCrea(per.getFechaHoraCrea());
        obj.setUsuarioModifica(per.getUsuarioModifica());
        obj.setTerminalModifica(per.getTerminalModifica());
        obj.setFechaHoraModifica(per.getFechaHoraModifica());
        return obj;
    }

}
