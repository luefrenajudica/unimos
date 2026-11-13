/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.registro;

import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.dominio.registro.UnTecnologia;
import co.com.systemtech.unimos.ejb.entidades.UnContratoDetalles;
import co.com.systemtech.unimos.ejb.entidades.UnTecnologias;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import co.com.systemtech.unimos.negocio.registro.TecnologiaRemoto;
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
@Remote(TecnologiaRemoto.class)
public class TecnologiaServicio extends GenericoServicio implements TecnologiaRemoto {

    @Override
    public int consultarCantidadLista(ParamConsulta paramConsulta) throws Exception {
        int cant = 0;
        try {
            String strQuery = "SELECT COUNT(t) FROM UnTecnologias t "
                    + "WHERE t.id > 0 ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "tipo":
                            strQuery += "AND t.tipo = " + (String) e.getValue() + " ";
                            break;
                        case "codigo":
                            strQuery += "AND t.codigo = '" + (String) e.getValue() + "' ";
                            break;
                        case "codigoPropio":
                            strQuery += "AND t.codigoPropio = '" + (String) e.getValue() + "' ";
                            break;
                        case "maeServicioHabilitacionId":
                            strQuery += "AND t.maeServicioHabilitacionId = " + (String) e.getValue() + " ";
                            break;
                        case "nombre":
                            strQuery += "AND t.nombre LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "activa":
                            strQuery += "AND t.activa = " + e.getValue() + " ";
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
    public List<UnTecnologia> consultarLista(ParamConsulta paramConsulta) throws Exception {
        List<UnTecnologia> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnTecnologias t "
                    + "WHERE t.id > 0 ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "tipo":
                            strQuery += "AND t.tipo = " + (String) e.getValue() + " ";
                            break;
                        case "codigo":
                            strQuery += "AND t.codigo = '" + (String) e.getValue() + "' ";
                            break;
                        case "codigoPropio":
                            strQuery += "AND t.codigoPropio = '" + (String) e.getValue() + "' ";
                            break;
                        case "maeServicioHabilitacionId":
                            strQuery += "AND t.maeServicioHabilitacionId = " + (String) e.getValue() + " ";
                            break;
                        case "nombre":
                            strQuery += "AND t.nombre LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "activa":
                            strQuery += "AND t.activa = " + e.getValue() + " ";
                            break;
                    }
                }
            }
            strQuery += "ORDER BY ";
            if (paramConsulta.getOrden() != null) {
                strQuery += "s." + paramConsulta.getOrden() + " "
                        + (paramConsulta.isAscendente() ? "ASC" : "DESC");
            } else {
                strQuery += "t.nombre ASC";
            }
            List<UnTecnologias> list = getEntityManager().createQuery(strQuery)
                    .setFirstResult(paramConsulta.getPrimerRegistro())
                    .setMaxResults(paramConsulta.getRegistrosPagina())
                    .getResultList();
            for (UnTecnologias per : list) {
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
    public UnTecnologia consultar(int id) throws Exception {
        UnTecnologia objRes = null;
        try {
            objRes = castEntidadNegocio((UnTecnologias) getEntityManager().find(UnTecnologias.class, id));
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
    public int insertar(UnTecnologia obj) throws Exception {
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
    public void actualizar(UnTecnologia obj) throws Exception {
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
    public UnTecnologia eliminar(int id) throws Exception {
        UnTecnologia obj = null;
        try {
            UnTecnologias ent = getEntityManager().find(UnTecnologias.class, id);
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
    public List<UnTecnologia> consultarTodas() throws Exception {
        List<UnTecnologia> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnTecnologias t ";
            strQuery += "ORDER BY t.nombre ASC";
            List<UnTecnologias> list = getEntityManager().createQuery(strQuery)
                    .getResultList();
            for (UnTecnologias per : list) {
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
    public HashMap<Integer, UnTecnologia> consultarTodasHash(Integer empresaId) throws Exception {
        HashMap<Integer, UnTecnologia> hashResult = new HashMap();
        try {
            String strQuery = "FROM UnTecnologias t "
                    + "ORDER BY t.nombre ASC";
            List<UnTecnologias> list = getEntityManager().createQuery(strQuery)
                    .setParameter("empresa_id", empresaId)
                    .getResultList();
            for (UnTecnologias per : list) {
                UnTecnologia obj = castEntidadNegocio(per);
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

    public static UnTecnologia castEntidadNegocio(UnTecnologias per) {
        UnTecnologia obj = new UnTecnologia();
        obj.setId(per.getId());
        obj.setTipo(per.getTipo());
        obj.setCodigo(per.getCodigo());
        obj.setCodigoPropio(per.getCodigoPropio());
        obj.setNombre(per.getNombre());
        obj.setDescripcion(per.getDescripcion());
        obj.setActiva(per.getActiva());
        obj.setMaeServicioHabilitacionTipo(per.getMaeServicioHabilitacionTipo());
        obj.setMaeServicioHabilitacionId(per.getMaeServicioHabilitacionId());
        obj.setMaeServicioHabilitacionCodigo(per.getMaeServicioHabilitacionCodigo());
        obj.setMaeServicioHabilitacionValor(per.getMaeServicioHabilitacionValor());
        //Auditoría
        obj.setUsuarioCrea(per.getUsuarioCrea());
        obj.setTerminalCrea(per.getTerminalCrea());
        obj.setFechaHoraCrea(per.getFechaHoraCrea());
        obj.setUsuarioModifica(per.getUsuarioModifica());
        obj.setTerminalModifica(per.getTerminalModifica());
        obj.setFechaHoraModifica(per.getFechaHoraModifica());
        return obj;
    }

    public static UnTecnologias castNegocioEntidad(UnTecnologia obj) {
        UnTecnologias per = new UnTecnologias();
        per.setId(obj.getId());
        per.setTipo(obj.getTipo());
        per.setCodigo(obj.getCodigo());
        per.setCodigoPropio(obj.getCodigoPropio());
        per.setNombre(obj.getNombre());
        per.setDescripcion(obj.getDescripcion());
        per.setActiva(obj.isActiva());
        per.setMaeServicioHabilitacionTipo(obj.getMaeServicioHabilitacionTipo());
        per.setMaeServicioHabilitacionId(obj.getMaeServicioHabilitacionId());
        per.setMaeServicioHabilitacionCodigo(obj.getMaeServicioHabilitacionCodigo());
        per.setMaeServicioHabilitacionValor(obj.getMaeServicioHabilitacionValor());
        //Auditoria
        per.setUsuarioCrea(obj.getUsuarioCrea());
        per.setTerminalCrea(obj.getTerminalCrea());
        per.setFechaHoraCrea(obj.getFechaHoraCrea());
        per.setUsuarioModifica(obj.getUsuarioModifica());
        per.setTerminalModifica(obj.getTerminalModifica());
        per.setFechaHoraModifica(obj.getFechaHoraModifica());
        return per;
    }

    @Override
    public int consultarContratadaCantidadLista(ParamConsulta paramConsulta) throws Exception {
        int cant = 0;
        try {
            String strQuery = "SELECT COUNT(cd) FROM UnContratoDetalles cd "
                    + "INNER JOIN UnTecnologias t ON cd.unTecnologiasId.id = t.id "
                    + "INNER JOIN UnContratos c ON cd.unContratosId.id = c.id "
                    + "INNER JOIN UnEntidades e ON c.unEndidadesId.id = e.id "
                    + "WHERE cd.id > 0 ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {

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
    public List<UnTecnologia> consultarContratadaLista(ParamConsulta paramConsulta) throws Exception {
        List<UnTecnologia> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnContratoDetalles cd "
                    + "INNER JOIN UnTecnologias t ON cd.unTecnologiasId.id = t.id "
                    + "INNER JOIN UnContratos c ON cd.unContratosId.id = c.id "
                    + "INNER JOIN UnEntidades e ON c.unEndidadesId.id = e.id "
                    + "WHERE cd.id > 0 ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {

                    }
                }
            }
            strQuery += "ORDER BY ";
            if (paramConsulta.getOrden() != null) {
                strQuery += "t." + paramConsulta.getOrden() + " "
                        + (paramConsulta.isAscendente() ? "ASC" : "DESC");
            } else {
                strQuery += "t.nombre ASC";
            }
            List<UnTecnologias> list = getEntityManager().createQuery(strQuery)
                    .setFirstResult(paramConsulta.getPrimerRegistro())
                    .setMaxResults(paramConsulta.getRegistrosPagina())
                    .getResultList();
            for (UnTecnologias per : list) {
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
    
    public static UnTecnologia castEntidadNegocio(UnContratoDetalles per) {
        UnTecnologia obj = new UnTecnologia();
        //Tecnología
        obj.setId(per.getId());
        obj.setTipo(per.getUnTecnologiasId().getTipo());
        obj.setCodigo(per.getUnTecnologiasId().getCodigo());
        obj.setCodigoPropio(per.getUnTecnologiasId().getCodigoPropio());
        obj.setNombre(per.getUnTecnologiasId().getNombre());
        obj.setDescripcion(per.getUnTecnologiasId().getDescripcion());
        obj.setActiva(per.getUnTecnologiasId().getActiva());
        obj.setMaeServicioHabilitacionTipo(per.getUnTecnologiasId().getMaeServicioHabilitacionTipo());
        obj.setMaeServicioHabilitacionId(per.getUnTecnologiasId().getMaeServicioHabilitacionId());
        obj.setMaeServicioHabilitacionCodigo(per.getUnTecnologiasId().getMaeServicioHabilitacionCodigo());
        obj.setMaeServicioHabilitacionValor(per.getUnTecnologiasId().getMaeServicioHabilitacionValor());
        //Auditoría Tecnología
        obj.setUsuarioCrea(per.getUnTecnologiasId().getUsuarioCrea());
        obj.setTerminalCrea(per.getUnTecnologiasId().getTerminalCrea());
        obj.setFechaHoraCrea(per.getUnTecnologiasId().getFechaHoraCrea());
        obj.setUsuarioModifica(per.getUnTecnologiasId().getUsuarioModifica());
        obj.setTerminalModifica(per.getUnTecnologiasId().getTerminalModifica());
        obj.setFechaHoraModifica(per.getUnTecnologiasId().getFechaHoraModifica());
        //Contrato
        
        //Entidad
                
        return obj;
    }

}
