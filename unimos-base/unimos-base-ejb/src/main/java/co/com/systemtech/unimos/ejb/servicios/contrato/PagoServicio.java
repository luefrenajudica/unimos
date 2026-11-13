/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.contrato;

import co.com.systemtech.unimos.dominio.contrato.UnSuscripcion;
import co.com.systemtech.unimos.dominio.contrato.UnSuscripcionPago;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import co.com.systemtech.unimos.ejb.entidades.UnSuscripcionPagos;
import co.com.systemtech.unimos.ejb.entidades.UnSuscripciones;
import co.com.systemtech.unimos.negocio.contrato.PagoRemoto;
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
@Remote(PagoRemoto.class)
public class PagoServicio extends GenericoServicio implements PagoRemoto {

    @Override
    public int consultarCantidadLista(ParamConsulta paramConsulta) throws Exception {
        int cant = 0;
        try {
            String strQuery = "SELECT COUNT(e) FROM UnSuscripcionPagos p ";
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
    public List<UnSuscripcionPago> consultarLista(ParamConsulta paramConsulta) throws Exception {
        List<UnSuscripcionPago> listResult = new ArrayList();
        try {
            String strQuery = "SELECT e FROM UnSuscripcionPagos p ";
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
                strQuery += "p.id ASC";
            }
            List<UnSuscripcionPagos> list = getEntityManager().createQuery(strQuery)
                    .setFirstResult(paramConsulta.getPrimerRegistro())
                    .setMaxResults(paramConsulta.getRegistrosPagina())
                    .getResultList();
            for (UnSuscripcionPagos per : list) {
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
    public List<UnSuscripcionPago> consultarPorSuscripcion(int suscripcionId) throws Exception {
        List<UnSuscripcionPago> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnSuscripcionPagos p "
                    + "WHERE p.unSuscripcionesId.id = :suscripcion_id "
                    + "ORDER BY p.fecha ASC, p.id ASC";
            List<UnSuscripcionPagos> list = getEntityManager().createQuery(strQuery)
                    .setParameter("suscripcion_id", suscripcionId)
                    .getResultList();
            for (UnSuscripcionPagos per : list) {
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
    public UnSuscripcionPago consultar(int id) throws Exception {
        UnSuscripcionPago objRes = null;
        try {
            objRes = castEntidadNegocio((UnSuscripcionPagos) getEntityManager().find(UnSuscripcionPagos.class, id));
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
    public int insertar(UnSuscripcionPago obj) throws Exception {
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
    public void actualizar(UnSuscripcionPago obj) throws Exception {
        try {
            getEntityManager().merge(castNegocioEntidad(obj));
        } catch (Exception e) {
            Exception(ACTUALIZAR, e);
        } finally {
            cerrarEntityManager();
        }
    }

    @Override
    public UnSuscripcionPago eliminar(int id) throws Exception {
        UnSuscripcionPago obj = null;
        try {
            UnSuscripcionPagos ent = getEntityManager().find(UnSuscripcionPagos.class, id);
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

    public static UnSuscripcionPago castEntidadNegocio(UnSuscripcionPagos per) {
        UnSuscripcionPago obj = new UnSuscripcionPago();
        obj.setId(per.getId());
        obj.setUnSuscripcion(new UnSuscripcion(per.getUnSuscripcionesId().getId()));
        obj.setMaeFormaPagoTipo(per.getMaeFormaPagoTipo());
        obj.setMaeFormaPagoId(per.getMaeFormaPagoId());
        obj.setMaeFormaPagoCodigo(per.getMaeFormaPagoCodigo());
        obj.setMaeFormaPagoValor(per.getMaeFormaPagoValor());
        obj.setValor(per.getValor().doubleValue());
        obj.setFecha(per.getFecha());
        //Auditoría
        obj.setUsuarioCrea(per.getUsuarioCrea());
        obj.setTerminalCrea(per.getTerminalCrea());
        obj.setFechaHoraCrea(per.getFechaHoraCrea());
        obj.setUsuarioModifica(per.getUsuarioModifica());
        obj.setTerminalModifica(per.getTerminalModifica());
        obj.setFechaHoraModifica(per.getFechaHoraModifica());
        return obj;
    }

    public static UnSuscripcionPagos castNegocioEntidad(UnSuscripcionPago obj) {
        UnSuscripcionPagos per = new UnSuscripcionPagos();
        per.setId(obj.getId());
        per.setUnSuscripcionesId(new UnSuscripciones(obj.getUnSuscripcion().getId()));
        per.setMaeFormaPagoTipo(obj.getMaeFormaPagoTipo());
        per.setMaeFormaPagoId(obj.getMaeFormaPagoId());
        per.setMaeFormaPagoCodigo(obj.getMaeFormaPagoCodigo());
        per.setMaeFormaPagoValor(obj.getMaeFormaPagoValor());
        per.setValor(new BigDecimal(obj.getValor()));
        per.setFecha(obj.getFecha());
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
