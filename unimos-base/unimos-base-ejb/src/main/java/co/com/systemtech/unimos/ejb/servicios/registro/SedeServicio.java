/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.registro;

import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.dominio.registro.Sede;
import co.com.systemtech.unimos.ejb.entidades.GnSedes;
import co.com.systemtech.unimos.ejb.entidades.GnUbicaciones;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import co.com.systemtech.unimos.negocio.registro.SedeRemoto;
import java.util.ArrayList;
import java.util.HashMap;
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
@Remote(SedeRemoto.class)
public class SedeServicio extends GenericoServicio implements SedeRemoto {

    @Override
    public int consultarCantidadLista(ParamConsulta paramConsulta) throws Exception {
        int cant = 0;
        try {
            String strQuery = "SELECT COUNT(s) FROM GnSedes s ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "nombre":
                            strQuery += "AND s.nombre LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "ubicacion.id":
                            Ubicacion ubi = (Ubicacion) e.getValue();
                            strQuery += "AND s.gnUbicacionesId.id = " + ubi.getId() + " ";
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
    public List<Sede> consultarLista(ParamConsulta paramConsulta) throws Exception {
        List<Sede> listResult = new ArrayList();
        try {
            String strQuery = "FROM GnSedes s ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "nombre":
                            strQuery += "AND s.nombre LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "ubicacion.id":
                            Ubicacion ubi = (Ubicacion) e.getValue();
                            strQuery += "AND s.gnUbicacionesId.id = " + ubi.getId() + " ";
                            break;
                    }
                }
            }
            strQuery += "ORDER BY ";
            if (paramConsulta.getOrden() != null) {
                strQuery += "s." + paramConsulta.getOrden() + " "
                        + (paramConsulta.isAscendente() ? "ASC" : "DESC");
            } else {
                strQuery += "s.nombre ASC";
            }
            List<GnSedes> list = getEntityManager().createQuery(strQuery)
                    .setFirstResult(paramConsulta.getPrimerRegistro())
                    .setMaxResults(paramConsulta.getRegistrosPagina())
                    .getResultList();
            for (GnSedes per : list) {
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
    public Sede consultar(int id) throws Exception {
        Sede objRes = null;
        try {
            objRes = castEntidadNegocio((GnSedes) getEntityManager().find(GnSedes.class, id));
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
    public int insertar(Sede obj) throws Exception {
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
    public void actualizar(Sede obj) throws Exception {
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
    public Sede eliminar(int id) throws Exception {
        Sede obj = null;
        try {
            GnSedes ent = getEntityManager().find(GnSedes.class, id);
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
    public List<Sede> consultarTodas() throws Exception {
        List<Sede> listResult = new ArrayList();
        try {
            String strQuery = "FROM GnSedes s ";
            strQuery += "ORDER BY s.nombre ASC";
            List<GnSedes> list = getEntityManager().createQuery(strQuery)
                    .getResultList();
            for (GnSedes per : list) {
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
    public HashMap<Integer, Sede> consultarTodasHash(Integer empresaId) throws Exception {
        HashMap<Integer, Sede> hashResult = new HashMap();
        try {
            String strQuery = "FROM GnSedes s "
                    + "WHERE s.gnEmpresasId.id = :empresa_id ";
            strQuery += "ORDER BY s.nombre ASC";
            List<GnSedes> list = getEntityManager().createQuery(strQuery)
                    .setParameter("empresa_id", empresaId)
                    .getResultList();
            for (GnSedes per : list) {
                Sede obj = castEntidadNegocio(per);
                hashResult.put(obj.getId(), obj);
            }
        } catch (NoResultException e) {
            hashResult = new HashMap();
        } catch (Exception e) {
            Exception(CONSULTAR_TODOS, e);
        } finally {
            cerrarEntityManager();
        }
        return hashResult;
    }

    public static Sede castEntidadNegocio(GnSedes per) {
        Sede obj = new Sede();
        obj.setId(per.getId());
        obj.setNombre(per.getNombre());
        obj.setDescripcion(per.getDescripcion());
//        obj.setCodigoHabilitacion(per.getCodigoHabilitacion());
        obj.setDireccion(per.getDireccion());
        obj.setTelefono(per.getTelefono());
        obj.setUbicacion(new Ubicacion(per.getGnUbicacionesId().getId()));
        //Auditoría
        obj.setUsuarioCrea(per.getUsuarioCrea());
        obj.setTerminalCrea(per.getTerminalCrea());
        obj.setFechaHoraCrea(per.getFechaHoraCrea());
        obj.setUsuarioModifica(per.getUsuarioModifica());
        obj.setTerminalModifica(per.getTerminalModifica());
        obj.setFechaHoraModifica(per.getFechaHoraModifica());
        return obj;
    }

    public static GnSedes castNegocioEntidad(Sede obj) {
        GnSedes per = new GnSedes();
        per.setId(obj.getId());
        per.setNombre(obj.getNombre());
        per.setDescripcion(obj.getDescripcion());
//        per.setCodigoHabilitacion(obj.getCodigoHabilitacion());
        per.setDireccion(obj.getDireccion());
        per.setTelefono(obj.getTelefono());
        if (obj.getUbicacion() != null) {
            per.setGnUbicacionesId(new GnUbicaciones(obj.getUbicacion().getId()));
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

}
