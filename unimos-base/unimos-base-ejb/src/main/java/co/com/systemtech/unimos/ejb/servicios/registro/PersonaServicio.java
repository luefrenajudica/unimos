/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.registro;

import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.dominio.contrato.UnSuscripcion;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import co.com.systemtech.unimos.dominio.registro.UnPersona;
import co.com.systemtech.unimos.dominio.registro.UnPersonaContacto;
import co.com.systemtech.unimos.ejb.entidades.GnUbicaciones;
import co.com.systemtech.unimos.ejb.entidades.UnPersonaContactos;
import co.com.systemtech.unimos.ejb.entidades.UnPersonas;
import co.com.systemtech.unimos.ejb.entidades.UnSuscripcionPersonas;
import co.com.systemtech.unimos.negocio.registro.PersonaRemoto;
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
@Remote(PersonaRemoto.class)
public class PersonaServicio extends GenericoServicio implements PersonaRemoto {

    @Override
    public int consultarCantidadLista(ParamConsulta paramConsulta) throws Exception {
        int cant = 0;
        try {
            String strQuery = "SELECT COUNT(e) FROM UnPersonas e "
                    + "WHERE e.id > 0 ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "maeTipoDocumentoId":
                            strQuery += "AND e.maeTipoDocumentoId = " + e.getValue() + " ";
                            break;
                        case "documentoNumero":
                            strQuery += "AND e.documentoNumero = '" + e.getValue() + "' ";
                            break;
                        case "primerNombre":
                            strQuery += "AND e.primerNombre LIKE '" + e.getValue() + "%' ";
                            break;
                        case "segundoNombre":
                            strQuery += "AND e.segundoNombre LIKE '" + e.getValue() + "%' ";
                            break;
                        case "primerApellido":
                            strQuery += "AND e.primerApellido LIKE '" + e.getValue() + "%' ";
                            break;
                        case "segundoApellido":
                            strQuery += "AND e.segundoApellido LIKE '" + e.getValue() + "%' ";
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
    public List<UnPersona> consultarLista(ParamConsulta paramConsulta) throws Exception {
        List<UnPersona> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnPersonas e "
                    + "WHERE e.id > 0 ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "maeTipoDocumentoId":
                            strQuery += "AND e.maeTipoDocumentoId = " + e.getValue() + " ";
                            break;
                        case "documentoNumero":
                            strQuery += "AND e.documentoNumero = '" + e.getValue() + "' ";
                            break;
                        case "primerNombre":
                            strQuery += "AND e.primerNombre LIKE '" + e.getValue() + "%' ";
                            break;
                        case "segundoNombre":
                            strQuery += "AND e.segundoNombre LIKE '" + e.getValue() + "%' ";
                            break;
                        case "primerApellido":
                            strQuery += "AND e.primerApellido LIKE '" + e.getValue() + "%' ";
                            break;
                        case "segundoApellido":
                            strQuery += "AND e.segundoApellido LIKE '" + e.getValue() + "%' ";
                            break;
                    }
                }
            }
            strQuery += "ORDER BY ";
            if (paramConsulta.getOrden() != null) {
                strQuery += "e." + paramConsulta.getOrden() + " "
                        + (paramConsulta.isAscendente() ? "ASC" : "DESC");
            } else {
                strQuery += "e.id ASC";
            }
            List<UnPersonas> list = getEntityManager().createQuery(strQuery)
                    .setFirstResult(paramConsulta.getPrimerRegistro())
                    .setMaxResults(paramConsulta.getRegistrosPagina())
                    .getResultList();
            for (UnPersonas per : list) {
                listResult.add(castEntidadNegocio(per));
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
    public UnPersona consultar(int id) throws Exception {
        UnPersona objRes = null;
        try {
            objRes = castEntidadNegocio((UnPersonas) getEntityManager().find(UnPersonas.class, id));
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
    public int insertar(UnPersona obj) throws Exception {
        int _id = 0;
        try {
            _id = (int) getEntityManager().merge(castNegocioEntidad(obj)).getId();
            obj.setId(_id);
        } catch (NoResultException e) {
            _id = 0;
        } catch (Exception e) {
            Exception(INSERTAR, e);
        } finally {
            cerrarEntityManager();
        }
        return _id;
    }

    @Override
    public void actualizar(UnPersona obj) throws Exception {
        try {
            getEntityManager().merge(castNegocioEntidad(obj));
        } catch (NoResultException e) {

        } catch (Exception e) {
            Exception(ACTUALIZAR, e);
        } finally {
            cerrarEntityManager();
        }
    }

    @Override
    public UnPersona eliminar(int id) throws Exception {
        UnPersona obj = null;
        try {
            UnPersonas ent = getEntityManager().find(UnPersonas.class, id);
            if (ent != null) {
                obj = castEntidadNegocio(ent);
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
    public List<UnPersona> consultarTodas() throws Exception {
        List<UnPersona> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnPersonas e "
                    + "ORDER BY e.primerNombre ASC, e.segundoNombre ASC, e.primerApellido ASC, e.segundoApellido ASC";
            List<UnPersonas> list = getEntityManager().createQuery(strQuery)
                    .getResultList();
            for (UnPersonas per : list) {
                listResult.add(castEntidadNegocio(per));
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
    public List<UnPersonaContacto> consultarContactos(int personaId) throws Exception {
        List<UnPersonaContacto> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnPersonaContactos c "
                    + "WHERE c.unPersonasId.id = :persona_id "
                    + "ORDER BY c.fechaHoraCrea ASC";
            List<UnPersonaContactos> list = getEntityManager().createQuery(strQuery)
                    .setParameter("persona_id", personaId)
                    .getResultList();
            for (UnPersonaContactos per : list) {
                listResult.add(castEntidadNegocio(per));
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
    public UnPersonaContacto consultarContacto(int id) throws Exception {
        UnPersonaContacto objRes = null;
        try {
            objRes = castEntidadNegocio((UnPersonaContactos) getEntityManager().find(UnPersonaContactos.class, id));
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
    public int insertarContacto(UnPersonaContacto obj) throws Exception {
        int _id = 0;
        try {
            _id = (int) getEntityManager().merge(castNegocioEntidad(obj)).getId();
            obj.setId(_id);
        } catch (NoResultException e) {
            _id = 0;
        } catch (Exception e) {
            Exception(INSERTAR, e);
        } finally {
            cerrarEntityManager();
        }
        return _id;
    }

    @Override
    public UnPersonaContacto eliminarContacto(int id) throws Exception {
        UnPersonaContacto obj = null;
        try {
            UnPersonaContactos ent = getEntityManager().find(UnPersonaContactos.class, id);
            if (ent != null) {
                obj = castEntidadNegocio(ent);
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

    public static UnPersona castEntidadNegocio(UnPersonas per) {
        UnPersona obj = new UnPersona();
        obj.setId(per.getId());
        obj.setMaeTipoDocumentoTipo(per.getMaeTipoDocumentoTipo());
        obj.setMaeTipoDocumentoId(per.getMaeTipoDocumentoId());
        obj.setMaeTipoDocumentoCodigo(per.getMaeTipoDocumentoCodigo());
        obj.setMaeTipoDocumentoValor(per.getMaeTipoDocumentoValor());
        obj.setDocumentoNumero(per.getDocumentoNumero());
        obj.setFechaDocumento(per.getFechaDocumento());
        obj.setPrimerNombre(per.getPrimerNombre());
        obj.setSegundoNombre(per.getSegundoNombre());
        obj.setPrimerApellido(per.getPrimerApellido());
        obj.setSegundoApellido(per.getSegundoApellido());
        obj.setMaeSexoTipo(per.getMaeSexoTipo());
        obj.setMaeSexoId(per.getMaeSexoId());
        obj.setMaeSexoCodigo(per.getMaeSexoCodigo());
        obj.setMaeSexoValor(per.getMaeSexoValor());
        obj.setMaeEstadoCivilTipo(per.getMaeEstadoCivilTipo());
        obj.setMaeEstadoCivilId(per.getMaeEstadoCivilId());
        obj.setMaeEstadoCivilCodigo(per.getMaeEstadoCivilCodigo());
        obj.setMaeEstadoCivilValor(per.getMaeEstadoCivilValor());
        if (per.getGnNacimientoUbicacionesId() != null) {
            obj.setGnNacimientoUbicacion(new Ubicacion(per.getGnNacimientoUbicacionesId().getId()));
        }
        obj.setFechaNacimiento(per.getFechaNacimiento());
        if (per.getGnDireccionUbicacionesId() != null) {
            obj.setGnDireccionUbicacion(new Ubicacion(per.getGnDireccionUbicacionesId().getId()));
        }
        if (per.getGnNacionalidadUbicacionesId() != null) {
            obj.setGnNacionalidadUbicacion(new Ubicacion(per.getGnNacionalidadUbicacionesId().getId()));
        }
        obj.setDireccion(per.getDireccion());
        //Auditoría
        obj.setUsuarioCrea(per.getUsuarioCrea());
        obj.setTerminalCrea(per.getTerminalCrea());
        obj.setFechaHoraCrea(per.getFechaHoraCrea());
        obj.setUsuarioModifica(per.getUsuarioModifica());
        obj.setTerminalModifica(per.getTerminalModifica());
        obj.setFechaHoraModifica(per.getFechaHoraModifica());
        return obj;
    }

    public static UnPersonas castNegocioEntidad(UnPersona obj) {
        UnPersonas per = new UnPersonas();
        per.setId(obj.getId());
        per.setMaeTipoDocumentoTipo(obj.getMaeTipoDocumentoTipo());
        per.setMaeTipoDocumentoId(obj.getMaeTipoDocumentoId());
        per.setMaeTipoDocumentoCodigo(obj.getMaeTipoDocumentoCodigo());
        per.setMaeTipoDocumentoValor(obj.getMaeTipoDocumentoValor());
        per.setDocumentoNumero(obj.getDocumentoNumero());
        per.setFechaDocumento(obj.getFechaDocumento());
        per.setPrimerNombre(obj.getPrimerNombre());
        per.setSegundoNombre(obj.getSegundoNombre());
        per.setPrimerApellido(obj.getPrimerApellido());
        per.setSegundoApellido(obj.getSegundoApellido());
        per.setMaeSexoTipo(obj.getMaeSexoTipo());
        per.setMaeSexoId(obj.getMaeSexoId());
        per.setMaeSexoCodigo(obj.getMaeSexoCodigo());
        per.setMaeSexoValor(obj.getMaeSexoValor());
        per.setMaeEstadoCivilTipo(obj.getMaeEstadoCivilTipo());
        per.setMaeEstadoCivilId(obj.getMaeEstadoCivilId());
        per.setMaeEstadoCivilCodigo(obj.getMaeEstadoCivilCodigo());
        per.setMaeEstadoCivilValor(obj.getMaeEstadoCivilValor());
        if (obj.getGnNacimientoUbicacion() != null) {
            per.setGnNacimientoUbicacionesId(new GnUbicaciones(obj.getGnNacimientoUbicacion().getId()));
        }
        per.setFechaNacimiento(obj.getFechaNacimiento());
        if (obj.getGnDireccionUbicacion() != null) {
            per.setGnDireccionUbicacionesId(new GnUbicaciones(obj.getGnDireccionUbicacion().getId()));
        }
        if (obj.getGnNacionalidadUbicacion() != null) {
            per.setGnNacionalidadUbicacionesId(new GnUbicaciones(obj.getGnNacionalidadUbicacion().getId()));
        }
        per.setDireccion(obj.getDireccion());
        //Auditoria
        per.setUsuarioCrea(obj.getUsuarioCrea());
        per.setTerminalCrea(obj.getTerminalCrea());
        per.setFechaHoraCrea(obj.getFechaHoraCrea());
        per.setUsuarioModifica(obj.getUsuarioModifica());
        per.setTerminalModifica(obj.getTerminalModifica());
        per.setFechaHoraModifica(obj.getFechaHoraModifica());
        return per;
    }

    public static UnPersonaContacto castEntidadNegocio(UnPersonaContactos per) {
        UnPersonaContacto obj = new UnPersonaContacto();
        obj.setId(per.getId());
        obj.setUnPersona(new UnPersona(per.getUnPersonasId().getId()));
        obj.setMaeTipoTipo(per.getMaeTipoTipo());
        obj.setMaeTipoId(per.getMaeTipoId());
        obj.setMaeTipoCodigo(per.getMaeTipoCodigo());
        obj.setMaeTipoValor(per.getMaeTipoValor());
        obj.setNombre(per.getNombre());
        obj.setContacto(per.getContacto());
        obj.setEstadoRegistro(UnPersonaContacto.ESTADO_REGISTRO_CREADO);
        //Auditoría
        obj.setUsuarioCrea(per.getUsuarioCrea());
        obj.setTerminalCrea(per.getTerminalCrea());
        obj.setFechaHoraCrea(per.getFechaHoraCrea());
        obj.setUsuarioModifica(per.getUsuarioModifica());
        obj.setTerminalModifica(per.getTerminalModifica());
        obj.setFechaHoraModifica(per.getFechaHoraModifica());
        return obj;
    }

    public static UnPersonaContactos castNegocioEntidad(UnPersonaContacto obj) {
        UnPersonaContactos per = new UnPersonaContactos();
        per.setId(obj.getId());
        per.setUnPersonasId(new UnPersonas(obj.getUnPersona().getId()));
        per.setMaeTipoTipo(obj.getMaeTipoTipo());
        per.setMaeTipoId(obj.getMaeTipoId());
        per.setMaeTipoCodigo(obj.getMaeTipoCodigo());
        per.setMaeTipoValor(obj.getMaeTipoValor());
        per.setNombre(obj.getNombre());
        per.setContacto(obj.getContacto());
        //Auditoria
        per.setUsuarioCrea(obj.getUsuarioCrea());
        per.setTerminalCrea(obj.getTerminalCrea());
        per.setFechaHoraCrea(obj.getFechaHoraCrea());
        per.setUsuarioModifica(obj.getUsuarioModifica());
        per.setTerminalModifica(obj.getTerminalModifica());
        per.setFechaHoraModifica(obj.getFechaHoraModifica());
        return per;
    }

    /**
     * Consulata de personas y suscripciones
     */
    @Override
    public int consultarSuscritosCantidadLista(ParamConsulta paramConsulta) throws Exception {
        int cant = 0;
        try {
            String strQuery = "SELECT COUNT(s) FROM UnSuscripcionPersonas s "
                    + " INNER JOIN UnPersonas p ON s.unPersonasId.id = p.id "
                    + " INNER JOIN UnSuscripciones j ON s.unSuscripcionesId.id = j.id ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "unSuscripcion.id":
                            strQuery += "AND s.unSuscripcionesId.id = " + e.getValue() + " ";
                            break;
                        case "documentoNumero":
                            strQuery += "AND p.documentoNumero LIKE '" + e.getValue() + "%' ";
                            break;
                        case "primerNombre":
                            strQuery += "AND p.primerNombre LIKE '" + e.getValue() + "%' ";
                            break;
                        case "segundoNombre":
                            strQuery += "AND p.segundoNombre LIKE '" + e.getValue() + "%' ";
                            break;
                        case "primerApellido":
                            strQuery += "AND p.primerApellido LIKE '" + e.getValue() + "%' ";
                            break;
                        case "segundoApellido":
                            strQuery += "AND p.segundoApellido LIKE '" + e.getValue() + "%' ";
                            break;
                        case "unSuscripcion.maeTipoValor":
                            strQuery += "AND j.maeTipoId = " + e.getValue() + " ";
                            break;
                        case "unSuscripcion.maePlanValor":
                            strQuery += "AND j.maePlanId = " + e.getValue() + " ";
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
    public List<UnPersona> consultarSuscritosLista(ParamConsulta paramConsulta) throws Exception {
        List<UnPersona> listResult = new ArrayList();
        try {
            String strQuery = "SELECT s FROM UnSuscripcionPersonas s "
                    + " INNER JOIN UnPersonas p ON s.unPersonasId.id = p.id "
                    + " INNER JOIN UnSuscripciones j ON s.unSuscripcionesId.id = j.id ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "unSuscripcion.id":
                            strQuery += "AND s.unSuscripcionesId.id = " + e.getValue() + " ";
                            break;
                        case "documentoNumero":
                            strQuery += "AND p.documentoNumero LIKE '" + e.getValue() + "%' ";
                            break;
                        case "primerNombre":
                            strQuery += "AND p.primerNombre LIKE '" + e.getValue() + "%' ";
                            break;
                        case "segundoNombre":
                            strQuery += "AND p.segundoNombre LIKE '" + e.getValue() + "%' ";
                            break;
                        case "primerApellido":
                            strQuery += "AND p.primerApellido LIKE '" + e.getValue() + "%' ";
                            break;
                        case "segundoApellido":
                            strQuery += "AND p.segundoApellido LIKE '" + e.getValue() + "%' ";
                            break;
                        case "unSuscripcion.maeTipoValor":
                            strQuery += "AND j.maeTipoId = " + e.getValue() + " ";
                            break;
                        case "unSuscripcion.maePlanValor":
                            strQuery += "AND j.maePlanId = " + e.getValue() + " ";
                            break;

                    }
                }
            }
            strQuery += "ORDER BY ";
            if (paramConsulta.getOrden() != null) {
                strQuery += "s." + paramConsulta.getOrden() + " "
                        + (paramConsulta.isAscendente() ? "ASC" : "DESC");
            } else {
                strQuery += "s.id ASC";
            }
            List<UnSuscripcionPersonas> list = getEntityManager().createQuery(strQuery)
                    .setFirstResult(paramConsulta.getPrimerRegistro())
                    .setMaxResults(paramConsulta.getRegistrosPagina())
                    .getResultList();
            for (UnSuscripcionPersonas per : list) {
                listResult.add(castEntidadNegocio(per));
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

    public static UnPersona castEntidadNegocio(UnSuscripcionPersonas per) {
        //Persona
        UnPersona obj = new UnPersona();
        obj.setId(per.getUnPersonasId().getId());
        obj.setMaeTipoDocumentoTipo(per.getUnPersonasId().getMaeTipoDocumentoTipo());
        obj.setMaeTipoDocumentoId(per.getUnPersonasId().getMaeTipoDocumentoId());
        obj.setMaeTipoDocumentoCodigo(per.getUnPersonasId().getMaeTipoDocumentoCodigo());
        obj.setMaeTipoDocumentoValor(per.getUnPersonasId().getMaeTipoDocumentoValor());
        obj.setDocumentoNumero(per.getUnPersonasId().getDocumentoNumero());
        obj.setPrimerNombre(per.getUnPersonasId().getPrimerNombre());
        obj.setSegundoNombre(per.getUnPersonasId().getSegundoNombre());
        obj.setPrimerApellido(per.getUnPersonasId().getPrimerApellido());
        obj.setSegundoApellido(per.getUnPersonasId().getSegundoApellido());
        obj.setMaeSexoTipo(per.getUnPersonasId().getMaeSexoTipo());
        obj.setMaeSexoId(per.getUnPersonasId().getMaeSexoId());
        obj.setMaeSexoCodigo(per.getUnPersonasId().getMaeSexoCodigo());
        obj.setMaeSexoValor(per.getUnPersonasId().getMaeSexoValor());
        obj.setMaeEstadoCivilTipo(per.getUnPersonasId().getMaeEstadoCivilTipo());
        obj.setMaeEstadoCivilId(per.getUnPersonasId().getMaeEstadoCivilId());
        obj.setMaeEstadoCivilCodigo(per.getUnPersonasId().getMaeEstadoCivilCodigo());
        obj.setMaeEstadoCivilValor(per.getUnPersonasId().getMaeEstadoCivilValor());
        if (per.getUnPersonasId().getGnNacimientoUbicacionesId() != null) {
            obj.setGnNacimientoUbicacion(new Ubicacion(per.getUnPersonasId().getGnNacimientoUbicacionesId().getId()));
        }
        obj.setFechaNacimiento(per.getUnPersonasId().getFechaNacimiento());
        if (per.getUnPersonasId().getGnDireccionUbicacionesId() != null) {
            obj.setGnDireccionUbicacion(new Ubicacion(per.getUnPersonasId().getGnDireccionUbicacionesId().getId()));
        }
        if (per.getUnPersonasId().getGnNacionalidadUbicacionesId() != null) {
            obj.setGnNacionalidadUbicacion(new Ubicacion(per.getUnPersonasId().getGnNacionalidadUbicacionesId().getId()));
        }
        obj.setDireccion(per.getUnPersonasId().getDireccion());
        //Suscriptor
        UnSuscripcion objSuscrip = new UnSuscripcion();
        objSuscrip.setId(per.getUnSuscripcionesId().getId());
        objSuscrip.setFechaInicio(per.getFechaInicio());
        objSuscrip.setFechaFin(per.getFechaFin());
        objSuscrip.setTitular(per.getTitular());
        objSuscrip.setMaeTipoTipo(per.getUnSuscripcionesId().getMaeTipoTipo());
        objSuscrip.setMaeTipoId(per.getUnSuscripcionesId().getMaeTipoId());
        objSuscrip.setMaeTipoCodigo(per.getUnSuscripcionesId().getMaeTipoCodigo());
        objSuscrip.setMaeTipoValor(per.getUnSuscripcionesId().getMaeTipoValor());
        objSuscrip.setMaePlanTipo(per.getUnSuscripcionesId().getMaePlanTipo());
        objSuscrip.setMaePlanId(per.getUnSuscripcionesId().getMaePlanId());
        objSuscrip.setMaePlanCodigo(per.getUnSuscripcionesId().getMaePlanCodigo());
        objSuscrip.setMaePlanValor(per.getUnSuscripcionesId().getMaePlanValor());
        obj.setUnSuscripcion(objSuscrip);
        //Auditoría
        obj.setUsuarioCrea(per.getUsuarioCrea());
        obj.setTerminalCrea(per.getTerminalCrea());
        obj.setFechaHoraCrea(per.getFechaHoraCrea());
        obj.setUsuarioModifica(per.getUsuarioModifica());
        obj.setTerminalModifica(per.getTerminalModifica());
        obj.setFechaHoraModifica(per.getFechaHoraModifica());
        return obj;
    }

}
