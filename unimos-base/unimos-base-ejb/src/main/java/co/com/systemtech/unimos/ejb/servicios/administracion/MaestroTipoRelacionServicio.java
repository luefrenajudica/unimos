/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.administracion;

import co.com.systemtech.unimos.dominio.administracion.GnMaestroTipo;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroTipoRelacion;
import co.com.systemtech.unimos.ejb.entidades.GnMaestroTipoRelaciones;
import co.com.systemtech.unimos.ejb.entidades.GnMaestroTipos;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import co.com.systemtech.unimos.negocio.administracion.MaestroTipoRelacionRemoto;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 *
 * @author sgiraldov
 */
@Stateless
@Remote(MaestroTipoRelacionRemoto.class)
public class MaestroTipoRelacionServicio extends GenericoServicio implements MaestroTipoRelacionRemoto {
 
    @Override
    public int insertar(GnMaestroTipoRelacion obj) throws Exception {
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
    public List<GnMaestroTipoRelacion> consultarPadresPorTipoHijo(String tipo) throws java.lang.Exception {
        List<GnMaestroTipoRelacion> listResult = new ArrayList();
        try {
            String strQuery = "FROM GnMaestroTipoRelaciones m "
                    + "WHERE m.gnMaestroTiposTipoHijo.tipo =  "+Integer.valueOf(tipo)
                    + " ORDER BY m.id";
            List< GnMaestroTipoRelaciones> list = getEntityManager().createQuery(strQuery)
                    .getResultList();
            for (GnMaestroTipoRelaciones per : list) {
                listResult.add(castEntidadNegocio(per));
            }
        } catch (NoResultException | NumberFormatException e) {
            listResult = new ArrayList();
        }
        //Exception(CONSULTAR_TODOS, e);
         finally {
            cerrarEntityManager();
        }
        return listResult;
    }

    @Override
    public GnMaestroTipoRelacion eliminar(int id) throws java.lang.Exception {
        GnMaestroTipoRelacion obj = null;
        try {
            GnMaestroTipoRelaciones ent = getEntityManager().find(GnMaestroTipoRelaciones.class, id);
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

    public static GnMaestroTipoRelacion castEntidadNegocio(GnMaestroTipoRelaciones per) {
        GnMaestroTipoRelacion obj = new GnMaestroTipoRelacion();
        obj.setId(per.getId());
        obj.setMaestroTipoPadre(new GnMaestroTipo(per.getGnMaestroTiposTipoPadre().getTipo()));
        obj.setMaestroTipoHijo(new GnMaestroTipo(per.getGnMaestroTiposTipoHijo().getTipo()));
        //Auditoría
        obj.setUsuarioCrea(per.getUsuarioCrea());
        obj.setTerminalCrea(per.getTerminalCrea());
        obj.setFechaHoraCrea(per.getFechaHoraCrea());
        return obj;
    }

    public static GnMaestroTipoRelaciones castNegocioEntidad(GnMaestroTipoRelacion obj) {
        GnMaestroTipoRelaciones per = new GnMaestroTipoRelaciones();
        
        per.setId(obj.getId());
        
        if (obj.getMaestroTipoPadre() != null && obj.getMaestroTipoPadre().getTipo() != null) {
            per.setGnMaestroTiposTipoPadre(new GnMaestroTipos(obj.getMaestroTipoPadre().getTipo()));
        }
        if (obj.getMaestroTipoHijo() != null && obj.getMaestroTipoHijo().getTipo() != null) {
            per.setGnMaestroTiposTipoHijo(new GnMaestroTipos(obj.getMaestroTipoHijo().getTipo()));
        }
        //Auditoría
        per.setUsuarioCrea(obj.getUsuarioCrea());
        per.setTerminalCrea(obj.getTerminalCrea());
        per.setFechaHoraCrea(obj.getFechaHoraCrea());
        
        return per;
    }
    
}
