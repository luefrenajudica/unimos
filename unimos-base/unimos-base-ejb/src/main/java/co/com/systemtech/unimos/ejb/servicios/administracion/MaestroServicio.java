/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.administracion;

import co.com.systemtech.unimos.dominio.administracion.GnMaestro;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroAccion;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroTipo;
import co.com.systemtech.unimos.dominio.administracion.Perfil;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.ejb.entidades.GnMaestroAccionRelaciones;
import co.com.systemtech.unimos.ejb.entidades.GnMaestroAcciones;
import co.com.systemtech.unimos.ejb.entidades.GnMaestroTipos;
import co.com.systemtech.unimos.ejb.entidades.GnMaestros;
import co.com.systemtech.unimos.negocio.administracion.MaestroRemoto;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import java.util.ArrayList;
import java.util.HashMap;
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
@Remote(MaestroRemoto.class)
public class MaestroServicio extends GenericoServicio implements MaestroRemoto {

    @Override
    public int consultarCantidadLista(ParamConsulta paramConsulta) throws Exception {
        int cant = 0;
        try {
            String strQuery = "SELECT COUNT(m) FROM GnMaestros m ";
            Object objeto = paramConsulta.getParametroConsulta1();
            if (objeto == null) {//Tiene permisos de verlos a todos
                strQuery += "WHERE 1 = 1 ";
            } else {//Solo puede ver los que tienen permisos asignados
                strQuery += "INNER JOIN GnMaestroTipos t ON t.tipo = m.tipo ";
                List<Perfil> listaPerfiles = (List<Perfil>) objeto;
                String sqlPerfiles = null;
                for (Perfil per : listaPerfiles) {
                    if (sqlPerfiles == null) {
                        sqlPerfiles = "t.gnPerfilesId.id = " + per.getId() + " ";
                    } else {
                        sqlPerfiles += "OR t.gnPerfilesId.id = " + per.getId() + " ";
                    }
                }
                if (sqlPerfiles != null) {
                    strQuery += "WHERE " + "(" + sqlPerfiles + ") ";
                } else {
                    strQuery += "WHERE 1 = 1 ";
                }
            }
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "maestroTipo.tipo":
                            strQuery += "AND m.tipo = '" + (String) e.getValue() + "' ";
                            break;
                        case "nombre":
                            strQuery += "AND m.nombre LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "valor":
                            strQuery += "AND m.valor LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "activo":
                            strQuery += "AND m.activo = " + (String) e.getValue() + " ";
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
    public List<GnMaestro> consultarLista(ParamConsulta paramConsulta) throws Exception {
        List<GnMaestro> listResult = new ArrayList();
        try {
            String strQuery = "SELECT m FROM GnMaestros m ";
            Object objeto = paramConsulta.getParametroConsulta1();
            if (objeto == null) {//Tiene permisos de verlos a todos
                strQuery += "WHERE 1 = 1 ";
            } else {//Solo puede ver los que tienen permisos asignados
                strQuery += "INNER JOIN GnMaestroTipos t ON t.tipo = m.tipo ";
                List<Perfil> listaPerfiles = (List<Perfil>) objeto;
                String sqlPerfiles = null;
                for (Perfil per : listaPerfiles) {
                    if (sqlPerfiles == null) {
                        sqlPerfiles = "t.gnPerfilesId.id = " + per.getId() + " ";
                    } else {
                        sqlPerfiles += "OR t.gnPerfilesId.id = " + per.getId() + " ";
                    }
                }
                if (sqlPerfiles != null) {
                    strQuery += "WHERE " + "(" + sqlPerfiles + ") ";
                } else {
                    strQuery += "WHERE 1 = 1 ";
                }
            }
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "maestroTipo.tipo":
                            strQuery += "AND m.tipo = '" + (String) e.getValue() + "' ";
                            break;
                        case "nombre":
                            strQuery += "AND m.nombre LIKE '%" + (String) e.getValue() + "%' ";
                            break;
                        case "valor":
                            strQuery += "AND m.valor LIKE '%" + (String) e.getValue() + "%' ";
                            break;
                        case "activo":
                            strQuery += "AND m.activo = " + (String) e.getValue() + " ";
                            break;
                    }
                }
            }
            strQuery += "ORDER BY ";
            if (paramConsulta.getOrden() != null) {
                strQuery += "m." + paramConsulta.getOrden() + " "
                        + (paramConsulta.isAscendente() ? "ASC" : "DESC");
            } else {
                strQuery += "m.nombre ASC ";
            }
            List<GnMaestros> list = getEntityManager().createQuery(strQuery)
                    .setFirstResult(paramConsulta.getPrimerRegistro())
                    .setMaxResults(paramConsulta.getRegistrosPagina())
                    .getResultList();
            for (GnMaestros per : list) {
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
    public GnMaestro consultar(int id) throws Exception {
        GnMaestro objRes = null;
        try {
            objRes = castEntidadNegocio((GnMaestros) getEntityManager().find(GnMaestros.class, id));
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
    public GnMaestro consultarPorValor(String valor) throws Exception {
        GnMaestro objRes = null;
        try {
            String strQuery = "FROM GnMaestros m "
                    + "WHERE m.valor ='" + valor + "'";
            objRes = castEntidadNegocio((GnMaestros) getEntityManager().createQuery(strQuery).getSingleResult());
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
    public GnMaestro consultarPorValorTipo(String valor, String tipo) throws Exception {
        GnMaestro objRes = null;
        try {
            String strQuery = "FROM GnMaestros m "
                    + "WHERE m.valor ='" + valor
                    + "' AND m.tipo='" + tipo + "'";
            objRes = castEntidadNegocio((GnMaestros) getEntityManager().createQuery(strQuery).getSingleResult());
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
    public int insertar(GnMaestro obj) throws Exception {
        int _id = 0;
        try {
            _id = (int) getEntityManager().merge(castNegocioEntidad(obj)).getId();
            obj.setId(_id);
        } catch (NoResultException e) {
            _id = 0;
        } catch (Exception e) {
            Exception(INSERTAR, e, "La combinación nombre, tipo, valor ya existe.");
        } finally {
            cerrarEntityManager();
        }
        return _id;
    }

    @Override
    public void actualizar(GnMaestro obj) throws Exception {
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
    public GnMaestro eliminar(int id) throws Exception {
        GnMaestro obj = null;
        try {
            GnMaestros ent = getEntityManager().find(GnMaestros.class, id);
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
    public List<GnMaestro> consultarPorTipo(String tipo) throws Exception {
        List<GnMaestro> listResult = new ArrayList();
        String strQuery = "FROM GnMaestros e "
                + "WHERE e.tipo = '" + tipo + "' "
                + "AND e.activo = 1 "
                + "ORDER BY e.nombre ";
        try {
            Query query = getEntityManager().createQuery(strQuery);
            List<GnMaestros> list = query.getResultList();
            for (GnMaestros obj : list) {
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
    public List<GnMaestro> consultarPorTipoHijo(String tipo) throws Exception {
        List<GnMaestro> listResult = new ArrayList();
        String strQuery = "SELECT m FROM GnMaestros m "
                + "INNER JOIN GnMaestroTipos mth ON m.tipo.tipo = mth.gnMaestroTiposTipo.tipo "
                + "WHERE mth.tipo = '" + tipo + "' "
                + "ORDER BY m.nombre ";
        try {
            Query query = getEntityManager().createQuery(strQuery);
            List<GnMaestros> list = query.getResultList();
            for (GnMaestros obj : list) {
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
    public HashMap<Integer, GnMaestro> consultarHashPorTipo(String tipo) throws Exception {
        HashMap<Integer, GnMaestro> hashResult = new HashMap();
        String strQuery = "FROM GnMaestros e "
                + "WHERE e.tipo = '" + tipo + "' "
                + "AND e.activo = 1";
        try {
            Query query = getEntityManager().createQuery(strQuery);
            List<GnMaestros> list = query.getResultList();
            for (GnMaestros per : list) {
                GnMaestro obj = castEntidadNegocio(per);
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

    @Override
    public HashMap<String, GnMaestro> consultarHashPorTipoValor(String tipo) throws Exception {
        HashMap<String, GnMaestro> hashResult = new HashMap();
        String strQuery = "FROM GnMaestros e "
                + "WHERE e.tipo = '" + tipo + "' "
                + "AND e.activo = 1";
        try {
            Query query = getEntityManager().createQuery(strQuery);
            List<GnMaestros> list = query.getResultList();
            for (GnMaestros per : list) {
                GnMaestro obj = castEntidadNegocio(per);
                hashResult.put(obj.getValor(), obj);
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

    @Override
    public HashMap<Integer, GnMaestro> consultarHash() throws Exception {
        HashMap<Integer, GnMaestro> hashResult = new HashMap();
        String strQuery = "FROM GnMaestros e ";
        try {
            Query query = getEntityManager().createQuery(strQuery);
            List<GnMaestros> list = query.getResultList();
            for (GnMaestros per : list) {
                GnMaestro obj = castEntidadNegocio(per);
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

    @Override
    public List<GnMaestro> consultarLista() throws Exception {
        List<GnMaestro> listResult = new ArrayList();
        String strQuery = "FROM GnMaestros e ";
        try {
            Query query = getEntityManager().createQuery(strQuery);
            List<GnMaestros> list = query.getResultList();
            for (GnMaestros obj : list) {
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
    public List<GnMaestro> consultarPorTipoYOrdenPorId(String tipo) throws Exception {
        List<GnMaestro> listResult = new ArrayList();
        String strQuery = "FROM GnMaestros m "
                + "WHERE m.tipo = '" + tipo + "' "
                + "AND m.activo = 1 "
                + "ORDER BY m.id ";
        try {
            Query query = getEntityManager().createQuery(strQuery);
            List<GnMaestros> list = query.getResultList();
            for (GnMaestros obj : list) {
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
    public List<GnMaestroAccion> consultarAccionesPorTipo(String tipo) throws Exception {
        List<GnMaestroAccion> listResult = new ArrayList();
        String strQuery = "FROM GnMaestroAcciones a "
                + "WHERE a.maestrosTipo = :tipo ";
        try {
            Query query = getEntityManager().createQuery(strQuery)
                    .setParameter("tipo", tipo);
            List<GnMaestroAcciones> list = query.getResultList();
            for (GnMaestroAcciones per : list) {
                GnMaestroAccion obj = new GnMaestroAccion();
                obj.setId(per.getId());
                obj.setMaestrosTipo(
                        new GnMaestroTipo(
                                per.getMaestrosTipo()
                                //per.getMaestrosTipo().getTipo()
                        )
                );
                obj.setNombre(per.getNombre());
                obj.setDescripcion(per.getDescripcion());
                listResult.add(obj);
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
    public GnMaestroAccion consultarAcciones(int id) throws Exception {
        GnMaestroAccion obj = null;
        String strQuery = "FROM GnMaestroAcciones a "
                + "WHERE a.id = " + id
                + "AND a.maestrosTipo.activo = 1 ";
        try {
            Query query = getEntityManager().createQuery(strQuery);
            GnMaestroAcciones per = (GnMaestroAcciones) query.getSingleResult();
            if (per != null) {
                obj = new GnMaestroAccion();
                obj.setId(per.getId());
                obj.setMaestrosTipo(new GnMaestroTipo(
                        per.getMaestrosTipo(),
                        per.getNombre(),
                        true
                        //per.getMaestrosTipo().getTipo(),
                        //per.getMaestrosTipo().getNombre(),
                        //per.getMaestrosTipo().getActivo()
                ));
                obj.setIdGrupo(per.getGrupoId());
                obj.setNombre(per.getNombre());
                obj.setDescripcion(per.getDescripcion());
            }
        } catch (NoResultException e) {
            obj = null;
        } catch (Exception e) {
            obj = null;
            Exception(CONSULTAR_TODOS, e);
        } finally {
            cerrarEntityManager();
        }
        return obj;
    }

    public static GnMaestro castEntidadNegocio(GnMaestros per) {
        GnMaestro obj = new GnMaestro();
        obj.setId(per.getId());
        if (per.getTipo() != null) {
            obj.setTipo(per.getTipo().getTipo());
            GnMaestroTipo tipo = new GnMaestroTipo(
                    per.getTipo().getTipo(),
                    per.getNombre(),
                    true
            );
            obj.setMaestroTipo(tipo);
        }
        obj.setNombre(per.getNombre());
        obj.setValor(per.getValor());
        obj.setDescripcion(per.getDescripcion());
        obj.setActivo(per.getActivo());
//        if (per.getGnMaestroAccionesId() != null) {
//            GnMaestroAccion ma = new GnMaestroAccion();
//            ma.setId(per.getGnMaestroAccionesId().getId());
//            ma.setGnMaestrosTipo(
//                    new GnMaestroTipo(
//                            per.getGnMaestroAccionesId().getMaestrosTipo().getTipo(),
//                            per.getGnMaestroAccionesId().getMaestrosTipo().getNombre(),
//                            per.getGnMaestroAccionesId().getMaestrosTipo().getActivo()
//                    )
//            );
//            ma.setNombre(per.getGnMaestroAccionesId().getNombre());
//            ma.setDescripcion(per.getGnMaestroAccionesId().getDescripcion());
//            obj.setGnMaestroAccion(ma);
//        }
        //2021-05-06 jyperez adicionamos la validación del objeto GnMaestro existente
//        //objetos
//        if (per.getGnMaestrosId() != null) {
//            obj.setGnMaestro(new GnMaestro(
//                    per.getGnMaestrosId().getId(),
//                    per.getGnMaestrosId().getNombre(),
//                    per.getGnMaestrosId().getTipo().getTipo(),
//                    per.getGnMaestrosId().getValor(),
//                    per.getGnMaestrosId().getDescripcion(),
//                    per.getGnMaestrosId().getActivo()
//            ));
//        }
        //Se recorre la lista de padres
        if (per.getGnMaestroAccionRelacionesList() != null) {
            obj.setMaestroAcciones(castListEntidadNegocioAcciones(per.getGnMaestroAccionRelacionesList()));
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

    public static GnMaestros castNegocioEntidad(GnMaestro obj) {
        GnMaestros per = new GnMaestros();
        per.setId(obj.getId());
        per.setNombre(obj.getNombre());
        per.setValor(obj.getValor());
        per.setDescripcion(obj.getDescripcion());
        per.setActivo(obj.isActivo());
        if (obj.getMaestroTipo() != null && obj.getMaestroTipo().getTipo() != null) {
            per.setTipo(new GnMaestroTipos(obj.getMaestroTipo().getTipo()));
        }
//        if (obj.getGnMaestro() != null && obj.getGnMaestro().getId() != null) {
//            per.setGnMaestrosId(new GnMaestros(obj.getGnMaestro().getId()));
//        }
//        if (obj.getGnMaestroAccion() != null && obj.getGnMaestroAccion().getId() != null) {
//            per.setGnMaestroAccionesId(new GnMaestroAcciones(obj.getGnMaestroAccion().getId()));
//        }
        //Auditoría
        per.setUsuarioCrea(obj.getUsuarioCrea());
        per.setTerminalCrea(obj.getTerminalCrea());
        per.setFechaHoraCrea(obj.getFechaHoraCrea());
        per.setUsuarioModifica(obj.getUsuarioModifica());
        per.setTerminalModifica(obj.getTerminalModifica());
        per.setFechaHoraModifica(obj.getFechaHoraModifica());
        return per;
    }
    
    @Override
    public HashMap<Integer, GnMaestro> consultarHashPorPadre(int idPadre) throws Exception {
        HashMap<Integer, GnMaestro> hashResult = new HashMap();
        String strQuery = "FROM GnMaestros m "
                + "WHERE m.gnMaestrosId.id = " + idPadre;
        try {
            Query query = getEntityManager().createQuery(strQuery);
            List<GnMaestros> list = query.getResultList();
            for (GnMaestros per : list) {
                GnMaestro obj = castEntidadNegocio(per);
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

    @Override
    public List<GnMaestro> consultarListaPorPadre(String tipo, int idPadre) throws Exception {
        List<GnMaestro> listResult = new ArrayList();
        String strQuery = "SELECT hijo "
                + "FROM GnMaestroRelaciones AS relacion "
                + "INNER JOIN relacion.gnMaestrosIdPadre AS padre "
                + "INNER JOIN relacion.gnMaestrosIdHijo AS hijo "
                + "WHERE hijo.tipo = '" + tipo + "' "
                + "AND padre.id = " + idPadre + " "
                + "AND hijo.activo = true";
        try {
            Query query = getEntityManager().createQuery(strQuery);
            List<GnMaestros> list = query.getResultList();
            for (GnMaestros obj : list) {
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
    public List<GnMaestro> consultarListaPorPadre(String tipo, int idPadre1, int idPadre2) throws Exception {
        List<GnMaestro> listResult = new ArrayList();
        String strQuery = "SELECT hijo "
                + "FROM GnMaestroRelaciones AS relacion "
                + "INNER JOIN relacion.gnMaestrosIdPadre AS padre "
                + "INNER JOIN relacion.gnMaestrosIdHijo AS hijo "
                + "WHERE hijo.tipo = '" + tipo + "' "
                + "AND hijo.activo = true "
                + "AND padre.id = " + idPadre1 + " "
                + "AND hijo.id IN ( SELECT gnMaestrosIdHijo.id FROM GnMaestroRelaciones AS relacion2 "
                + "WHERE relacion2.gnMaestrosIdPadre.id = " + idPadre2 + " ) ";
        try {
            Query query = getEntityManager().createQuery(strQuery);
            List<GnMaestros> list = query.getResultList();
            for (GnMaestros obj : list) {
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
    public List<GnMaestro> consultarListaPorPadre(String tipo, int idPadre1, int idPadre2, int idPadre3) throws Exception {
        List<GnMaestro> listResult = new ArrayList();
        String strQuery = "SELECT hijo "
                + "FROM GnMaestroRelaciones AS relacion "
                + "INNER JOIN relacion.gnMaestrosIdPadre AS padre "
                + "INNER JOIN relacion.gnMaestrosIdHijo AS hijo "
                + "WHERE hijo.tipo = '" + tipo + "' "
                + "AND hijo.activo = true "
                + "AND padre.id = " + idPadre1 + " "
                + "AND hijo.id IN ( SELECT gnMaestrosIdHijo.id FROM GnMaestroRelaciones AS relacion2 "
                + "WHERE relacion2.gnMaestrosIdPadre.id = " + idPadre2 + " ) "
                + "AND hijo.id IN ( SELECT gnMaestrosIdHijo.id FROM GnMaestroRelaciones AS relacion3 "
                + "WHERE relacion3.gnMaestrosIdPadre.id = " + idPadre3 + " ) ";
        try {
            Query query = getEntityManager().createQuery(strQuery);
            List<GnMaestros> list = query.getResultList();
            for (GnMaestros obj : list) {
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
    public List<GnMaestro> consultarListaPorPadre(String tipo, String valorPadre) throws Exception {
        List<GnMaestro> listResult = new ArrayList();
        String strQuery = "SELECT hijo "
                + "FROM GnMaestroRelaciones AS relacion "
                + "INNER JOIN relacion.gnMaestrosIdPadre AS padre "
                + "INNER JOIN relacion.gnMaestrosIdHijo AS hijo "
                + "WHERE hijo.tipo.tipo = '" + tipo + "' "
                + "AND padre.valor = '" + valorPadre + "' "
                + "AND hijo.activo = true";
        try {
            Query query = getEntityManager().createQuery(strQuery);
            List<GnMaestros> list = query.getResultList();
            for (GnMaestros obj : list) {
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
    public boolean isRelacionPadreHijo(int idHijo, int idPadre) throws Exception {
        boolean isRelacion;
        String strQuery = "SELECT hijo "
                + "FROM GnMaestroRelaciones AS relacion "
                + "INNER JOIN relacion.gnMaestrosIdPadre AS padre "
                + "INNER JOIN relacion.gnMaestrosIdHijo AS hijo "
                + "WHERE hijo.id = " + idHijo + " "
                + "AND padre.id = " + idPadre;
        try {
            Query query = getEntityManager().createQuery(strQuery);
            isRelacion = !query.getResultList().isEmpty();
        } catch (Exception e) {
            isRelacion = false;
            Exception(CONSULTAR_TODOS, e);
        } finally {
            cerrarEntityManager();
        }
        return isRelacion;
    }
    
    @Override
    public boolean isRelacionPadreHijo(int idHijo, int idPadre1, int idPadre2) throws Exception {
        boolean isRelacion;
        String strQuery = "SELECT hijo "
                + "FROM GnMaestroRelaciones AS relacion "
                + "INNER JOIN relacion.gnMaestrosIdPadre AS padre "
                + "INNER JOIN relacion.gnMaestrosIdHijo AS hijo "
                + "WHERE hijo.id = " + idHijo + " "
                + "AND padre.id = " + idPadre1 + " "
                + "AND hijo.id IN ( SELECT gnMaestrosIdHijo.id FROM GnMaestroRelaciones AS relacion2 "
                + "WHERE relacion2.gnMaestrosIdPadre.id = " + idPadre2 + " ) ";
        try {
            Query query = getEntityManager().createQuery(strQuery);
            List<GnMaestros> list = query.getResultList();
            isRelacion = !list.isEmpty();
        } catch (Exception e) {
            isRelacion = false;
            Exception(CONSULTAR_TODOS, e);
        } finally {
            cerrarEntityManager();
        }
        return isRelacion;
    }
    
    @Override
    public boolean isRelacionPadreHijo(int idHijo, int idPadre1, int idPadre2, int idPadre3) throws Exception {
        boolean isRelacion;
        String strQuery = "SELECT hijo "
                + "FROM GnMaestroRelaciones AS relacion "
                + "INNER JOIN relacion.gnMaestrosIdPadre AS padre "
                + "INNER JOIN relacion.gnMaestrosIdHijo AS hijo "
                + "WHERE hijo.id = " + idHijo + " "
                + "AND padre.id = " + idPadre1 + " "
                + "AND hijo.id IN ( SELECT gnMaestrosIdHijo.id FROM GnMaestroRelaciones AS relacion2 "
                + "WHERE relacion2.gnMaestrosIdPadre.id = " + idPadre2 + " ) "
                + "AND hijo.id IN ( SELECT gnMaestrosIdHijo.id FROM GnMaestroRelaciones AS relacion3 "
                + "WHERE relacion3.gnMaestrosIdPadre.id = " + idPadre3 + " ) ";
        try {
            Query query = getEntityManager().createQuery(strQuery);
            List<GnMaestros> list = query.getResultList();
            isRelacion = !list.isEmpty();
        } catch (Exception e) {
            isRelacion = false;
            Exception(CONSULTAR_TODOS, e);
        } finally {
            cerrarEntityManager();
        }
        return isRelacion;
    }
    
    public static List<GnMaestroAccion> castListEntidadNegocioAcciones(List<GnMaestroAccionRelaciones> relaciones) {
        List<GnMaestroAccion> negocioAcciones = new ArrayList<>();
        for(GnMaestroAccionRelaciones relacion : relaciones) {
            GnMaestroAccion accion = new GnMaestroAccion();
            accion.setId(relacion.getGnMaestroAccionesId().getId());
            accion.setIdGrupo(relacion.getGnMaestroAccionesId().getGrupoId());
            accion.setNombre(relacion.getGnMaestroAccionesId().getNombre());
            accion.setDescripcion(relacion.getGnMaestroAccionesId().getDescripcion());
            //accion.setGnMaestrosTipo(new GnMaestroTipo(relacion.getGnMaestroAccionesId().getMaestrosTipo().getTipo()));
            accion.setMaestrosTipo(new GnMaestroTipo(relacion.getGnMaestroAccionesId().getMaestrosTipo()));
            negocioAcciones.add(accion);
        }
        return negocioAcciones;
    }

}
