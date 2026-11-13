/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.administracion;

import co.com.systemtech.unimos.dominio.administracion.GnDiaHabil;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.ejb.entidades.GnDiasHabiles;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import co.com.systemtech.unimos.negocio.administracion.CalendarioRemoto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

/**
 *
 * @author raul-palacios
 */
@Stateless
@Remote(CalendarioRemoto.class)
public class CalendarioServicio extends GenericoServicio implements CalendarioRemoto {

    @Override
    public List<GnDiaHabil> consultarTodos(ParamConsulta paramConsulta) throws Exception {

        List<GnDiaHabil> listResult = new ArrayList();
        String strQuery = "FROM GnDiasHabiles "
                + "WHERE fecha BETWEEN :fh_desde AND :fh_hasta";
        try {
            
            List<GnDiasHabiles> list = getEntityManager().createQuery(strQuery)
                    .setParameter("fh_desde", ((Date) paramConsulta.getParametroConsulta1()), TemporalType.TIMESTAMP)
                    .setParameter("fh_hasta", ((Date) paramConsulta.getParametroConsulta2()), TemporalType.TIMESTAMP)
                    .getResultList();
            for (GnDiasHabiles _obj : list) {
                listResult.add(castEntidadNegocio(_obj));
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
    public boolean actualizar(GnDiaHabil _obj) throws Exception {
        boolean crear = true;
        String strQuery = "FROM GnDiasHabiles WHERE fecha =:fecha";
        try {
            Query query = getEntityManager().createQuery(strQuery);
            query.setParameter("fecha", _obj.getFecha());
            List<GnDiasHabiles> list = query.getResultList();
            if (!list.isEmpty()) {
                getEntityManager().remove(list.get(0));
                crear = false;
            } else {
                _obj.setId(null);
                getEntityManager().merge(castNegocioEntidad(_obj)).getId();
                crear = true;
            }
        } catch (NoResultException e) {
            Exception(ACTUALIZAR, e);
        } catch (Exception e) {
            Exception(ACTUALIZAR, e);
        } finally {
            cerrarEntityManager();
        }
        return crear;
    }
    
    @Override
    public GnDiaHabil consultarDia(Date fecha) throws Exception {
        GnDiaHabil diaHabil = null;
        try {
            TypedQuery<GnDiasHabiles> query = em.createNamedQuery("GnDiasHabiles.findByFecha", GnDiasHabiles.class);
            query.setParameter("fecha",fecha);
            GnDiasHabiles dias = query.getSingleResult();
            if (dias!=null){
                diaHabil= castEntidadNegocio(dias);
            }
        } catch (NoResultException e) {
            diaHabil = null;
        } catch (Exception e) {
            Exception(CONSULTAR_TODOS, e);
        } finally {
            cerrarEntityManager();
        }
        return diaHabil;
    }

    public static GnDiaHabil castEntidadNegocio(GnDiasHabiles _per) {
        GnDiaHabil _obj = new GnDiaHabil();
        _obj.setId(_per.getId());
        _obj.setAgno(_per.getAgno());
        _obj.setFecha(_per.getFecha());
        _obj.setHabil(_per.getHabil());
        //Auditoria
        _obj.setUsuarioCrea(_per.getUsuarioCrea());
        _obj.setTerminalCrea(_per.getTerminalCrea());
        _obj.setFechaHoraCrea(_per.getFechaHoraCrea());
        return _obj;
    }

    public static GnDiasHabiles castNegocioEntidad(GnDiaHabil _obj) {
        GnDiasHabiles _per = new GnDiasHabiles();
        _per.setId(_obj.getId());
        _per.setAgno(_obj.getAgno());
        _per.setFecha(_obj.getFecha());
        _per.setHabil(_obj.isHabil());
        //Auditoria
        _per.setUsuarioCrea(_obj.getUsuarioCrea());
        _per.setTerminalCrea(_obj.getTerminalCrea());
        _per.setFechaHoraCrea(_obj.getFechaHoraCrea());
        return _per;
    }
}
