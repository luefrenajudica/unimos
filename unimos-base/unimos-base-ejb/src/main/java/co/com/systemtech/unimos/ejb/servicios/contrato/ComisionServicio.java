/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.contrato;

import co.com.systemtech.unimos.dominio.contrato.UnSuscripcion;
import co.com.systemtech.unimos.dominio.contrato.UnComercialComision;
import co.com.systemtech.unimos.dominio.contrato.UnSuscripcionCartera;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.dominio.registro.UnComercial;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import co.com.systemtech.unimos.ejb.entidades.UnComercialComisiones;
import co.com.systemtech.unimos.ejb.entidades.UnComerciales;
import co.com.systemtech.unimos.ejb.entidades.UnSuscripcionCarteras;
import co.com.systemtech.unimos.ejb.entidades.UnSuscripciones;
import co.com.systemtech.unimos.negocio.contrato.ComisionRemoto;
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
@Remote(ComisionRemoto.class)
public class ComisionServicio extends GenericoServicio implements ComisionRemoto {

    @Override
    public int consultarCantidadLista(ParamConsulta paramConsulta) throws Exception {
        int cant = 0;
        try {
            String strQuery = "SELECT COUNT(1) FROM UnComercialComisiones c "
                    + "WHERE c.unComercialesId.id = :comercial_id ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "unSuscripcion.id":
                            strQuery += "AND c.unSuscripcionesId.id = " + e.getValue() + " ";
                            break;
                    }
                }
            }
            cant = (int) (long) getEntityManager().createQuery(strQuery)
                    .setParameter("comercial_id", (int) paramConsulta.getParametroConsulta1())
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
    public List<UnComercialComision> consultarLista(ParamConsulta paramConsulta) throws Exception {
        List<UnComercialComision> listResult = new ArrayList();
        try {
            String strQuery = "SELECT c FROM UnComercialComisiones c "
                    + "WHERE c.unComercialesId.id = :comercial_id ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "unSuscripcion.id":
                            strQuery += "AND c.unSuscripcionesId.id = " + e.getValue() + " ";
                            break;
                    }
                }
            }
            strQuery += "ORDER BY ";
            if (paramConsulta.getOrden() != null) {
                strQuery += "c." + paramConsulta.getOrden() + " "
                        + (paramConsulta.isAscendente() ? "ASC" : "DESC");
            } else {
                strQuery += "c.id DESC";
            }
            List<UnComercialComisiones> list = getEntityManager().createQuery(strQuery)
                    .setParameter("comercial_id", (int) paramConsulta.getParametroConsulta1())
                    .setFirstResult(paramConsulta.getPrimerRegistro())
                    .setMaxResults(paramConsulta.getRegistrosPagina())
                    .getResultList();
            for (UnComercialComisiones per : list) {
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
    public List<UnComercialComision> consultarPorSuscripcion(int suscripcionId) throws Exception {
        List<UnComercialComision> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnComercialComisiones p "
                    + "WHERE p.unSuscripcionesId.id = :suscripcion_id "
                    + "ORDER BY p.fecha ASC, p.id ASC";
            List<UnComercialComisiones> list = getEntityManager().createQuery(strQuery)
                    .setParameter("suscripcion_id", suscripcionId)
                    .getResultList();
            for (UnComercialComisiones per : list) {
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
    public UnComercialComision consultar(int id) throws Exception {
        UnComercialComision objRes = null;
        try {
            objRes = castEntidadNegocio((UnComercialComisiones) getEntityManager().find(UnComercialComisiones.class, id));
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
    public int insertar(UnComercialComision obj) throws Exception {
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
    public void actualizar(UnComercialComision obj) throws Exception {
        try {
            getEntityManager().merge(castNegocioEntidad(obj));
        } catch (Exception e) {
            Exception(ACTUALIZAR, e);
        } finally {
            cerrarEntityManager();
        }
    }

    public static UnComercialComision castEntidadNegocio(UnComercialComisiones per) {
        UnComercialComision obj = new UnComercialComision();
        obj.setId(per.getId());
        obj.setUnComercial(new UnComercial(per.getUnComercialesId().getId()));
        UnSuscripcion suscripcion = new UnSuscripcion(per.getUnSuscripcionesId().getId());
        suscripcion.setMaeTipoValor(per.getUnSuscripcionesId().getMaeTipoValor());
        suscripcion.setFechaInicio(per.getUnSuscripcionesId().getFechaInicio());
        obj.setUnSuscripcion(suscripcion);
        UnSuscripcionCartera cartera = new UnSuscripcionCartera(per.getUnSuscripcionCarterasId().getId());
        cartera.setValorAPagar(per.getUnSuscripcionCarterasId().getValorAPagar().doubleValue());
        cartera.setFechaPago(per.getUnSuscripcionCarterasId().getFechaPago());
        obj.setUnSuscripcionCartera(cartera);
        obj.setFecha(per.getFecha());
        obj.setPorcentaje(per.getPorcentaje().doubleValue());
        obj.setValor(per.getValor().doubleValue());
        //Auditoría
        obj.setUsuarioCrea(per.getUsuarioCrea());
        obj.setTerminalCrea(per.getTerminalCrea());
        obj.setFechaHoraCrea(per.getFechaHoraCrea());
        return obj;
    }

    public static UnComercialComisiones castNegocioEntidad(UnComercialComision obj) {
        UnComercialComisiones per = new UnComercialComisiones();
        per.setId(obj.getId());
        per.setUnComercialesId(new UnComerciales(obj.getUnComercial().getId()));
        per.setUnSuscripcionesId(new UnSuscripciones(obj.getUnSuscripcion().getId()));
        per.setUnSuscripcionCarterasId(new UnSuscripcionCarteras(obj.getUnSuscripcionCartera().getId()));
        if (obj.getPorcentaje() != null) {
            per.setPorcentaje(BigDecimal.valueOf(obj.getPorcentaje()));
        }
        per.setValor(BigDecimal.valueOf(obj.getValor()));
        per.setFecha(obj.getFecha());
        //Auditoria
        per.setUsuarioCrea(obj.getUsuarioCrea());
        per.setTerminalCrea(obj.getTerminalCrea());
        per.setFechaHoraCrea(obj.getFechaHoraCrea());
        return per;
    }

}
