/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.administracion;

import co.com.systemtech.unimos.dominio.administracion.GnLog;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.ejb.entidades.GnLogs;
import co.com.systemtech.unimos.negocio.administracion.LogRemoto;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author raul-palacios
 */
@Stateless
@Remote(LogRemoto.class)
public class LogServicio extends GenericoServicio implements LogRemoto {
    
    @Override
    public int consultarCantidadTodos(ParamConsulta paramConsulta) throws Exception {
        int cant = 0;
        try {
            String strQuery = "SELECT COUNT(l) FROM GnLogs l "
                    + "WHERE fechaHora BETWEEN :fh_inicio AND :fh_fin ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "usuarioUsuario":
                            strQuery += "AND usuarioUsuario LIKE '%" + (String) e.getValue() + "%' ";
                            break;
                        case "empresaUsuario":
                            strQuery += "AND empresaUsuario LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "empresaEjecucion":
                            strQuery += "AND empresaEjecucion LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "modulo":
                            strQuery += "AND modulo LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "opcion":
                            strQuery += "AND opcion LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "accion":
                            strQuery += "AND accion LIKE '" + (String) e.getValue() + "%' ";
                            break;
                    }
                }
            }
            cant = (int) (long) getEntityManager().createQuery(strQuery)
                    .setParameter("fh_inicio", ((Date)paramConsulta.getParametroConsulta1()), TemporalType.TIMESTAMP)
                    .setParameter("fh_fin",((Date)paramConsulta.getParametroConsulta2()), TemporalType.TIMESTAMP)
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
    public List<GnLog> consultarTodos(ParamConsulta paramConsulta) throws Exception {
        List<GnLog> listResult = new ArrayList();
        try {
            //Armado de HQL
            String strQuery = "FROM GnLogs "
                    + "WHERE fechaHora BETWEEN :fh_inicio AND :fh_fin ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "usuarioUsuario":
                            strQuery += "AND usuarioUsuario LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "empresaUsuario":
                            strQuery += "AND empresaUsuario LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "empresaEjecucion":
                            strQuery += "AND empresaEjecucion LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "modulo":
                            strQuery += "AND modulo LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "opcion":
                            strQuery += "AND opcion LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "accion":
                            strQuery += "AND accion LIKE '" + (String) e.getValue() + "%' ";
                            break;
                    }
                }
            }
            if (paramConsulta.getOrden() != null) {
                strQuery += " ORDER BY " + paramConsulta.getOrden() + " "
                        + (paramConsulta.isAscendente() ? "ASC" : "DESC");
            } else {
                strQuery += "ORDER BY fechaHora DESC ";
            }
            List<GnLogs> list = getEntityManager().createQuery(strQuery)
                    .setFirstResult(paramConsulta.getPrimerRegistro())
                    .setMaxResults(paramConsulta.getRegistrosPagina())
                    .setParameter("fh_inicio", ((Date)paramConsulta.getParametroConsulta1()), TemporalType.TIMESTAMP)
                    .setParameter("fh_fin", ((Date)paramConsulta.getParametroConsulta2()), TemporalType.TIMESTAMP)
                    .getResultList();
            for (GnLogs obj : list) {
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

    @Override
    public GnLog consultar(int id) throws Exception {
        GnLog objRes = null;
        try {
            objRes = castEntidadNegocio((GnLogs) getEntityManager().find(GnLogs.class, id));
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
    public int insertar(GnLog obj) throws Exception {
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
    public GnLog eliminar(int id) throws Exception {
        GnLog obj = null;
        try {
            GnLogs per = getEntityManager().find(GnLogs.class, id);
            if (per != null) {
                obj = castEntidadNegocio(per);
                getEntityManager().remove(per);
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
    
    public static GnLog castEntidadNegocio(GnLogs _per) {
        GnLog _obj = new GnLog();
        _obj.setId(_per.getId());
        _obj.setModulo(_per.getModulo());
        _obj.setOpcion(_per.getOpcion());        
        _obj.setAccion(_per.getAccion());
        _obj.setDescripcion(_per.getDescripcion());
        _obj.setEmpresaEjecucion(_per.getEmpresaEjecucion());
        _obj.setEmpresaUsuario(_per.getEmpresaUsuario());
        _obj.setUsuarioUsuario(_per.getUsuarioUsuario());
        _obj.setUsuarioNombre(_per.getUsuarioNombre());
        _obj.setFechaHora(_per.getFechaHora());
        _obj.setIpPrivada(_per.getIpPrivada());
        _obj.setIpPublica(_per.getIpPublica());
        return _obj;
    }

    public static GnLogs castNegocioEntidad(GnLog _obj) {
        GnLogs _per = new GnLogs();
        _per.setId(_obj.getId());
        _per.setModulo(_obj.getModulo());
        _per.setOpcion(_obj.getOpcion());
        _per.setAccion(_obj.getAccion());
        _per.setDescripcion(_obj.getDescripcion());
        _per.setEmpresaEjecucion(_obj.getEmpresaEjecucion());
        _per.setEmpresaUsuario(_obj.getEmpresaUsuario());
        _per.setUsuarioUsuario(_obj.getUsuarioUsuario());
        _per.setUsuarioNombre(_obj.getUsuarioNombre());
        _per.setFechaHora(_obj.getFechaHora());
        _per.setIpPrivada(_obj.getIpPrivada());
        _per.setIpPublica(_obj.getIpPublica());
        return _per;
    }

    @Override
    public List<GnLog> consultarUltimos(String empresa, int cant) {
        List<GnLog> lista = new ArrayList();
        String strQuery = "FROM GnLogs "
                + "WHERE empresaEjecucion = :empresa_ejecucion "
                + "ORDER BY fechaHora DESC";
        try {
            Query query = getEntityManager().createQuery(strQuery);
            List<GnLogs> list = query
                    .setFirstResult(0)
                    .setMaxResults(cant)
                    .setParameter("empresa_ejecucion", empresa)
                    .getResultList();
            for (GnLogs obj : list) {
                lista.add(castEntidadNegocio(obj));
            }
        } catch (Exception e) {
            lista = new ArrayList();
        } finally {
            cerrarEntityManager();
        }
        return lista;
    }
    
}
