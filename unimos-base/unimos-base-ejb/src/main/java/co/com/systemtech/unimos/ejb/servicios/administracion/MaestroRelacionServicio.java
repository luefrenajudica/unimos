/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.administracion;

import co.com.systemtech.unimos.dominio.administracion.GnMaestro;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroRelacion;
import co.com.systemtech.unimos.ejb.entidades.GnMaestroRelaciones;
import co.com.systemtech.unimos.ejb.entidades.GnMaestros;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import co.com.systemtech.unimos.negocio.administracion.MaestroRelacionRemoto;
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
@Remote(MaestroRelacionRemoto.class)
public class MaestroRelacionServicio extends GenericoServicio implements MaestroRelacionRemoto {
    
    @Override
    public int insertar(GnMaestroRelacion obj) throws Exception {
        int _id = 0;
        try {
            _id = (int) getEntityManager().merge(castNegocioEntidad(obj)).getId();
            obj.setId(_id);
        } catch (NoResultException e) {
            _id = 0;
        } catch (Exception e) {
            Exception(INSERTAR, e, "La combinación  maestrostipo ya existe.");
        } finally {
            cerrarEntityManager();
        }
        return _id;
    }

    @Override
    public List<GnMaestroRelacion> consultarPadresPorIdMaestroHijo(int id) throws java.lang.Exception {
    List<GnMaestroRelacion> listResult = new ArrayList();
        try {
            String strQuery = "FROM GnMaestroRelaciones m "
                    + "WHERE m.gnMaestrosIdHijo.id =  "+id
                    + " ORDER BY m.id";
            List< GnMaestroRelaciones> list = getEntityManager().createQuery(strQuery)
                    .getResultList();
            for (GnMaestroRelaciones per : list) {
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
    public GnMaestroRelacion eliminar(int id) throws java.lang.Exception {
        GnMaestroRelacion obj = null;
        try {
            GnMaestroRelaciones ent = getEntityManager().find(GnMaestroRelaciones.class, id);
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

    public static GnMaestroRelacion castEntidadNegocio(GnMaestroRelaciones per) {
        GnMaestroRelacion obj = new GnMaestroRelacion();
        obj.setId(per.getId());
        obj.setMaestroPadre(new GnMaestro(per.getGnMaestrosIdPadre().getId()));
        obj.setMaestroHijo(new GnMaestro(per.getGnMaestrosIdHijo().getId()));
        //Auditoría
        obj.setUsuarioCrea(per.getUsuarioCrea());
        obj.setTerminalCrea(per.getTerminalCrea());
        obj.setFechaHoraCrea(per.getFechaHoraCrea());
        return obj;
    }

    public static GnMaestroRelaciones castNegocioEntidad(GnMaestroRelacion obj) {
        GnMaestroRelaciones per = new GnMaestroRelaciones();
        
        per.setId(obj.getId());
        
        if (obj.getMaestroPadre() != null && obj.getMaestroPadre().getTipo() != null) {
            per.setGnMaestrosIdPadre(new GnMaestros(obj.getMaestroPadre().getId()));
        }
        if (obj.getMaestroHijo() != null && obj.getMaestroHijo().getTipo() != null) {
            per.setGnMaestrosIdHijo(new GnMaestros(obj.getMaestroHijo().getId()));
        }
        //Auditoría
        per.setUsuarioCrea(obj.getUsuarioCrea());
        per.setTerminalCrea(obj.getTerminalCrea());
        per.setFechaHoraCrea(obj.getFechaHoraCrea());
        
        return per;
    }
    
}
