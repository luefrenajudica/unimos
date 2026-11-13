/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.administracion;

import co.com.systemtech.unimos.dominio.administracion.GnEmpresa;
import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.negocio.administracion.EmpresaRemoto;
import co.com.systemtech.unimos.ejb.entidades.GnEmpresas;
import co.com.systemtech.unimos.ejb.entidades.GnUbicaciones;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author raul-palacios
 */
@Stateless
@Remote(EmpresaRemoto.class)
public class EmpresaServicio extends GenericoServicio implements EmpresaRemoto {

    @Override
    public int consultarCantidadLista(ParamConsulta paramConsulta) throws Exception {
        int cant = 0;
        try {
            String strQuery = "SELECT COUNT(e) FROM GnEmpresas e "
                    + "WHERE 1 = 1 ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "nit":
                            strQuery += "AND e.nit LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "razonSocial":
                            strQuery += "AND e.razonSocial LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "nombreComercial":
                            strQuery += "AND e.nombreComercial LIKE '%" + (String) e.getValue() + "%' ";
                            break;
                        case "ciudad.id":
                            strQuery += "AND e.gnUbicacionesId.id = " + e.getValue() + " ";
                            break;
                        case "activa":
                            strQuery += "AND e.activa = " + (String) e.getValue() + " ";
                            break;
                        case "administradora":
                            strQuery += "AND e.administradora = " + (String) e.getValue() + " ";
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
    public List<GnEmpresa> consultarLista(ParamConsulta paramConsulta) throws Exception {
        List<GnEmpresa> listResult = new ArrayList();
        try {
            String strQuery = "FROM GnEmpresas e "
                    + "WHERE 1 = 1 ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "nit":
                            strQuery += "AND e.nit LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "razonSocial":
                            strQuery += "AND e.razonSocial LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "nombreComercial":
                            strQuery += "AND e.nombreComercial LIKE '%" + (String) e.getValue() + "%' ";
                            break;
                        case "ciudad.id":
                            strQuery += "AND e.gnUbicacionesId.id = " + e.getValue() + " ";
                            break;
                        case "activa":
                            strQuery += "AND e.activa = " + (String) e.getValue() + " ";
                            break;
                        case "administradora":
                            strQuery += "AND e.administradora = " + (String) e.getValue() + " ";
                            break;
                    }
                }
            }
            strQuery += "ORDER BY ";
            if (paramConsulta.getOrden() != null) {
                strQuery += "e." + paramConsulta.getOrden() + " "
                        + (paramConsulta.isAscendente() ? "ASC" : "DESC");
            } else {
                strQuery += "e.nit ASC ";
            }
            List<GnEmpresas> list = getEntityManager().createQuery(strQuery)
                    .setFirstResult(paramConsulta.getPrimerRegistro())
                    .setMaxResults(paramConsulta.getRegistrosPagina())
                    .getResultList();
            for (GnEmpresas per : list) {
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
    public GnEmpresa consultar(int id) throws Exception {
        GnEmpresa objRes = null;
        try {
            objRes = castEntidadNegocio((GnEmpresas) getEntityManager().find(GnEmpresas.class, id));
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
    public int insertar(GnEmpresa obj) throws Exception {
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
    public void actualizar(GnEmpresa obj) throws Exception {
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
    public GnEmpresa eliminar(int id) throws Exception {
        GnEmpresa obj = null;
        try {
            GnEmpresas ent = getEntityManager().find(GnEmpresas.class, id);
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
    public List<GnEmpresa> consultarActivas() throws Exception {
        List<GnEmpresa> listResult = new ArrayList();
        String strQuery = "FROM GnEmpresas WHERE activa = 1";
        try {
            Query query = getEntityManager().createQuery(strQuery);
            List<GnEmpresas> list = query.getResultList();
            for (GnEmpresas obj : list) {
                listResult.add(castEntidadNegocio(obj));
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

    public static GnEmpresa castEntidadNegocio(GnEmpresas per) {
        GnEmpresa obj = new GnEmpresa();
        obj.setId(per.getId());
        obj.setNit(per.getNit());
        obj.setRazonSocial(per.getRazonSocial());
        obj.setNombreComercial(per.getNombreComercial());
        obj.setActiva(per.getActiva());
        obj.setDescripcion(per.getDescripcion());
        obj.setAdministradora(per.getAdministradora());
        obj.setCiudad(new Ubicacion(per.getGnUbicacionesId().getId()));
        obj.setReceptorUsuario(per.getReceptorUsuario());
        obj.setReceptorContrasena(per.getReceptorContrasena());
        obj.setCodigoHabilitacion(per.getCodigoHabilitacion());
        //Auditoría
        obj.setUsuarioCrea(per.getUsuarioCrea());
        obj.setTerminalCrea(per.getTerminalCrea());
        obj.setFechaHoraCrea(per.getFechaHoraCrea());
        obj.setUsuarioModifica(per.getUsuarioModifica());
        obj.setTerminalModifica(per.getTerminalModifica());
        obj.setFechaHoraModifica(per.getFechaHoraModifica());
        return obj;
    }

    public static GnEmpresas castNegocioEntidad(GnEmpresa obj) {
        GnEmpresas per = new GnEmpresas();
        per.setId(obj.getId());
        per.setNit(obj.getNit());
        per.setRazonSocial(obj.getRazonSocial());
        per.setNombreComercial(obj.getNombreComercial());
        per.setDescripcion(obj.getDescripcion());
        per.setActiva(obj.isActiva());
        per.setAdministradora(obj.isAdministradora());
        per.setReceptorUsuario(obj.getReceptorUsuario());
        per.setReceptorContrasena(obj.getReceptorContrasena());
        per.setCodigoHabilitacion(obj.getCodigoHabilitacion());
        //Ubicación
        per.setGnUbicacionesId(new GnUbicaciones(obj.getCiudad().getId()));
        //Auditoría
        per.setUsuarioCrea(obj.getUsuarioCrea());
        per.setTerminalCrea(obj.getTerminalCrea());
        per.setFechaHoraCrea(obj.getFechaHoraCrea());
        per.setUsuarioModifica(obj.getUsuarioModifica());
        per.setTerminalModifica(obj.getTerminalModifica());
        per.setFechaHoraModifica(obj.getFechaHoraModifica());
        return per;
    }

}
