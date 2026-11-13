/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.registro;

import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.dominio.administracion.Usuario;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.dominio.registro.Sede;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import co.com.systemtech.unimos.dominio.registro.UnComercial;
import co.com.systemtech.unimos.ejb.entidades.GnSedes;
import co.com.systemtech.unimos.ejb.entidades.GnUbicaciones;
import co.com.systemtech.unimos.ejb.entidades.GnUsuarios;
import co.com.systemtech.unimos.ejb.entidades.UnComerciales;
import co.com.systemtech.unimos.negocio.registro.ComercialRemoto;
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
@Remote(ComercialRemoto.class)
public class ComercialServicio extends GenericoServicio implements ComercialRemoto {

    @Override
    public int consultarCantidadLista(ParamConsulta paramConsulta) throws Exception {
        int cant = 0;
        try {
            String strQuery = "SELECT COUNT(e) FROM UnComerciales e "
                    + "WHERE 1 = 1 ";
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
                        case "activo":
                            strQuery += "AND e.activo = " + e.getValue() + " ";
                            break;
                        case "sede.id":
                            strQuery += "AND e.gnSedesId.id = " + e.getValue() + " ";
                            break;
//                        case "direccionUbicacion.id":
//                            strQuery += "AND e.direccionUbicacionesId.id = " + e.getValue() + " ";
//                            break;
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
    public List<UnComercial> consultarLista(ParamConsulta paramConsulta) throws Exception {
        List<UnComercial> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnComerciales e "
                    + "WHERE 1 = 1 ";
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
                        case "activo":
                            strQuery += "AND e.activo = " + e.getValue() + " ";
                            break;
                        case "sede.id":
                            strQuery += "AND e.gnSedesId.id = " + e.getValue() + " ";
                            break;
//                        case "direccionUbicacion.id":
//                            strQuery += "AND e.direccionUbicacionesId.id = " + e.getValue() + " ";
//                            break;
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
            List<UnComerciales> list = getEntityManager().createQuery(strQuery)
                    .setFirstResult(paramConsulta.getPrimerRegistro())
                    .setMaxResults(paramConsulta.getRegistrosPagina())
                    .getResultList();
            for (UnComerciales per : list) {
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
    public UnComercial consultar(int id) throws Exception {
        UnComercial objRes = null;
        try {
            objRes = castEntidadNegocio((UnComerciales) getEntityManager().find(UnComerciales.class, id));
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
    public int insertar(UnComercial obj) throws Exception {
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
    public void actualizar(UnComercial obj) throws Exception {
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
    public UnComercial eliminar(int id) throws Exception {
        UnComercial obj = null;
        try {
            UnComerciales ent = getEntityManager().find(UnComerciales.class, id);
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
    public List<UnComercial> consultarTodosActivos() throws Exception {
        List<UnComercial> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnComerciales e "
                    + "WHERE e.activo = true "
                    + "ORDER BY e.primerNombre ASC, e.segundoNombre ASC, e.primerApellido ASC, e.segundoApellido ASC";
            List<UnComerciales> list = getEntityManager().createQuery(strQuery)
                    .getResultList();
            for (UnComerciales per : list) {
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

    public static UnComercial castEntidadNegocio(UnComerciales per) {
        UnComercial obj = new UnComercial();
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
        obj.setFechaNacimiento(per.getFechaNacimiento());
        if (per.getGnNacimientoUbicacionesId() != null) {
            obj.setNacimientoUbicacion(new Ubicacion(per.getGnNacimientoUbicacionesId().getId()));
        }
        obj.setFechaNacimiento(per.getFechaNacimiento());
        if (per.getGnDireccionUbicacionesId() != null) {
            obj.setDireccionUbicacion(new Ubicacion(per.getGnDireccionUbicacionesId().getId()));
        }
        if (per.getGnNacionalidadUbicacionesId() != null) {
            obj.setNacionalidadUbicacion(new Ubicacion(per.getGnNacionalidadUbicacionesId().getId()));
        }
        if (per.getGnSedesId()!= null) {
            obj.setSede(new Sede(per.getGnSedesId().getId(), per.getGnSedesId().getNombre(), per.getGnSedesId().getDescripcion()));
        }
        if (per.getGnUsuariosId()!= null) {
            obj.setUsuario(new Usuario(per.getGnUsuariosId().getId(), per.getGnUsuariosId().getUsuario(), per.getGnUsuariosId().getNombre()));
        }
        obj.setDireccion(per.getDireccion());
        obj.setComisiona(per.getComisiona());
        obj.setActivo(per.getActivo());
        //Auditoría
        obj.setUsuarioCrea(per.getUsuarioCrea());
        obj.setTerminalCrea(per.getTerminalCrea());
        obj.setFechaHoraCrea(per.getFechaHoraCrea());
        obj.setUsuarioModifica(per.getUsuarioModifica());
        obj.setTerminalModifica(per.getTerminalModifica());
        obj.setFechaHoraModifica(per.getFechaHoraModifica());
        return obj;
    }

    public static UnComerciales castNegocioEntidad(UnComercial obj) {
        UnComerciales per = new UnComerciales();
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
        per.setFechaNacimiento(obj.getFechaNacimiento());
        if (obj.getNacimientoUbicacion() != null) {
            per.setGnNacimientoUbicacionesId(new GnUbicaciones(obj.getNacimientoUbicacion().getId()));
        }
        per.setFechaNacimiento(obj.getFechaNacimiento());
        if (obj.getDireccionUbicacion() != null) {
            per.setGnDireccionUbicacionesId(new GnUbicaciones(obj.getDireccionUbicacion().getId()));
        }
        if (obj.getNacionalidadUbicacion() != null) {
            per.setGnNacionalidadUbicacionesId(new GnUbicaciones(obj.getNacionalidadUbicacion().getId()));
        }
        if (obj.getSede()!= null) {
            per.setGnSedesId(new GnSedes(obj.getSede().getId()));
        }
        if (obj.getUsuario()!= null) {
            per.setGnUsuariosId(new GnUsuarios(obj.getUsuario().getId()));
        }
        per.setDireccion(obj.getDireccion());
        per.setComisiona(obj.isComisiona());
        per.setActivo(obj.isActivo());
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
