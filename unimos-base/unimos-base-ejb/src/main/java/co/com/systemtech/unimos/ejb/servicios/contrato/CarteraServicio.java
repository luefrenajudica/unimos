/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.contrato;

import co.com.systemtech.unimos.dominio.contrato.UnSuscripcion;
import co.com.systemtech.unimos.dominio.contrato.UnSuscripcionCartera;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import co.com.systemtech.unimos.ejb.entidades.UnSuscripcionCarteras;
import co.com.systemtech.unimos.ejb.entidades.UnSuscripciones;
import co.com.systemtech.unimos.negocio.contrato.CarteraRemoto;
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
@Remote(CarteraRemoto.class)
public class CarteraServicio extends GenericoServicio implements CarteraRemoto {

    @Override
    public int consultarCantidadLista(ParamConsulta paramConsulta) throws Exception {
        int cant = 0;
        try {
            String strQuery = "SELECT COUNT(e) FROM UnSuscripcionCarteras c ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
//                        case "sede.id":
//                            strQuery += "AND e.gnSedesId.id = " + e.getValue() + " ";
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
    public List<UnSuscripcionCartera> consultarLista(ParamConsulta paramConsulta) throws Exception {
        List<UnSuscripcionCartera> listResult = new ArrayList();
        try {
            String strQuery = "SELECT e FROM UnSuscripcionCarteras c ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
//                        case "sede.id":
//                            strQuery += "AND e.gnSedesId.id = " + e.getValue() + " ";
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
            List<UnSuscripcionCarteras> list = getEntityManager().createQuery(strQuery)
                    .setFirstResult(paramConsulta.getPrimerRegistro())
                    .setMaxResults(paramConsulta.getRegistrosPagina())
                    .getResultList();
            for (UnSuscripcionCarteras per : list) {
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
    public List<UnSuscripcionCartera> consultarPorSuscripcion(int suscripcionId) throws Exception {
        List<UnSuscripcionCartera> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnSuscripcionCarteras c "
                    + "WHERE c.unSuscripcionesId.id = :suscripcion_id "
                    + "ORDER BY c.fechaGeneracion ASC, c.id ASC";
            List<UnSuscripcionCarteras> list = getEntityManager().createQuery(strQuery)
                    .setParameter("suscripcion_id", suscripcionId)
                    .getResultList();
            for (UnSuscripcionCarteras per : list) {
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
    public UnSuscripcionCartera consultar(int id) throws Exception {
        UnSuscripcionCartera objRes = null;
        try {
            objRes = castEntidadNegocio((UnSuscripcionCarteras) getEntityManager().find(UnSuscripcionCarteras.class, id));
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
    public int insertar(UnSuscripcionCartera obj) throws Exception {
        int id = 0;
        try {
            id = (int) getEntityManager().merge(castNegocioEntidad(obj)).getId();
            obj.setId(id);
        } catch (Exception e) {
            Exception(INSERTAR, e);
        } finally {
            cerrarEntityManager();
        }
        return id;
    }

    @Override
    public void actualizar(UnSuscripcionCartera obj) throws Exception {
        try {
            getEntityManager().merge(castNegocioEntidad(obj));
        } catch (Exception e) {
            Exception(ACTUALIZAR, e);
        } finally {
            cerrarEntityManager();
        }
    }
    
    @Override
    public void actualizarPagos(UnSuscripcionCartera obj) throws Exception {
        try {
            String hql = "UPDATE UnSuscripcionCarteras SET "
                    + "estado = :estado, "
                    + "fechaPago = :fechaPago, "
                    + "valorPagado = :valorPagado, "
                    + "valorPendiente = :valorPendiente "
                    + "WHERE id = :id ";
            Query query = getEntityManager().createQuery(hql);
            query.setParameter("estado", obj.getEstado());
            query.setParameter("fechaPago", obj.getFechaPago());
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
    public UnSuscripcionCartera eliminar(int id) throws Exception {
        UnSuscripcionCartera obj = null;
        try {
            UnSuscripcionCarteras ent = getEntityManager().find(UnSuscripcionCarteras.class, id);
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

    public static UnSuscripcionCartera castEntidadNegocio(UnSuscripcionCarteras per) {
        UnSuscripcionCartera obj = new UnSuscripcionCartera();
        obj.setId(per.getId());
        obj.setUnSuscripcion(new UnSuscripcion(per.getUnSuscripcionesId().getId()));
        obj.setEstado(per.getEstado());
        obj.setFechaGeneracion(per.getFechaGeneracion());
        obj.setFechaMaxima(per.getFechaMaxima());
        obj.setFechaPago(per.getFechaPago());
        obj.setValorAPagar(per.getValorAPagar().doubleValue());
        if (per.getValorPagado() != null) {
            obj.setValorPagado(per.getValorPagado().doubleValue());
        }
        if (per.getValorPendiente()!= null) {
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

    public static UnSuscripcionCarteras castNegocioEntidad(UnSuscripcionCartera obj) {
        UnSuscripcionCarteras per = new UnSuscripcionCarteras();
        per.setId(obj.getId());
        per.setUnSuscripcionesId(new UnSuscripciones(obj.getUnSuscripcion().getId()));
        per.setEstado(obj.getEstado());
        per.setFechaGeneracion(obj.getFechaGeneracion());
        per.setFechaMaxima(obj.getFechaMaxima());
        per.setFechaPago(obj.getFechaPago());
        per.setValorAPagar(BigDecimal.valueOf(obj.getValorAPagar()));
        if (obj.getValorPagado() != null) {
            per.setValorPagado(BigDecimal.valueOf(obj.getValorPagado()));
        }
        if (obj.getValorPendiente()!= null) {
            per.setValorPendiente(BigDecimal.valueOf(obj.getValorPendiente()));
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
