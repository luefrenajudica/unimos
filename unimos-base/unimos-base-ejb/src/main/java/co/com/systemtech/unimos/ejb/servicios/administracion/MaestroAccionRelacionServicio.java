/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.administracion;

import co.com.systemtech.unimos.dominio.administracion.GnMaestro;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroAccion;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroAccionRelacion;
import co.com.systemtech.unimos.ejb.entidades.GnMaestroAccionRelaciones;
import co.com.systemtech.unimos.ejb.entidades.GnMaestroAcciones;
import co.com.systemtech.unimos.ejb.entidades.GnMaestros;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import co.com.systemtech.unimos.negocio.administracion.MaestroAccionRelacionRemoto;
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
@Remote(MaestroAccionRelacionRemoto.class)
public class MaestroAccionRelacionServicio extends GenericoServicio implements MaestroAccionRelacionRemoto {
    
    @Override
    public int insertar(GnMaestroAccionRelacion obj) throws Exception {
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
    public List<GnMaestroAccionRelacion> consultarPorMaestroId(int id) throws Exception {
        List<GnMaestroAccionRelacion> listResult = new ArrayList<>();
        try {
            String strQuery = "FROM GnMaestroAccionRelaciones m "
                    + "WHERE m.gnMaestrosId.id =  "+id
                    + " ORDER BY m.id";
            List<GnMaestroAccionRelaciones> list = getEntityManager().createQuery(strQuery)
                    .getResultList();
            for (GnMaestroAccionRelaciones per : list) {
                listResult.add(castEntidadNegocio(per));
            }
        } catch (NoResultException e) {
            listResult = new ArrayList();
        } catch (Exception e) {
            listResult = new ArrayList();
        } finally {
            cerrarEntityManager();
        }
        return listResult;
    }

    @Override
    public GnMaestroAccionRelacion eliminar(int id) throws Exception {
         GnMaestroAccionRelacion obj = null;
        try {
            GnMaestroAccionRelaciones ent = getEntityManager().find(GnMaestroAccionRelaciones.class, id);
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
    
    private GnMaestroAccionRelaciones castNegocioEntidad(GnMaestroAccionRelacion negocio){
        GnMaestroAccionRelaciones entidad = new GnMaestroAccionRelaciones();
        entidad.setId(negocio.getId());
        entidad.setGnMaestrosId(new GnMaestros(negocio.getMaestroId().getId()));
        entidad.setGnMaestroAccionesId(new GnMaestroAcciones(negocio.getMaestroAccionId().getId()));
        //Auditoria
        entidad.setFechaHoraCrea(negocio.getFechaHoraCrea());
        entidad.setTerminalCrea(negocio.getTerminalCrea());
        entidad.setUsuarioCrea(negocio.getUsuarioCrea());
        return entidad;
    }
    
    private GnMaestroAccionRelacion castEntidadNegocio(GnMaestroAccionRelaciones entidad){
        GnMaestroAccionRelacion negocio = new GnMaestroAccionRelacion();
        negocio.setId(entidad.getId());
        negocio.setMaestroId(new GnMaestro(entidad.getGnMaestrosId().getId()));
        negocio.setMaestroAccionId(new GnMaestroAccion(entidad.getGnMaestroAccionesId().getId()));
        //Auditoria
        negocio.setFechaHoraCrea(entidad.getFechaHoraCrea());
        negocio.setTerminalCrea(entidad.getTerminalCrea());
        negocio.setUsuarioCrea(entidad.getUsuarioCrea());
        return negocio;
    }
}
