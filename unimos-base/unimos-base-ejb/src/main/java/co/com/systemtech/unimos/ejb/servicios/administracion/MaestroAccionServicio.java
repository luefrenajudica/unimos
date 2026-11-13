/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.administracion;


import co.com.systemtech.unimos.dominio.administracion.GnMaestroAccion;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroTipo;
import co.com.systemtech.unimos.ejb.entidades.GnMaestroAcciones;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import co.com.systemtech.unimos.negocio.administracion.MaestroAccionRemoto;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 *
 * @author raul-palacios
 */
@Stateless
@Remote(MaestroAccionRemoto.class)
@Local(MaestroAccionLocal.class)
public class MaestroAccionServicio extends GenericoServicio implements MaestroAccionLocal, MaestroAccionRemoto {

    @Override
    public GnMaestroAccion consultar(int id) throws Exception {
        GnMaestroAccion objRes = null;
        try {
            objRes = castEntidadNegocio((GnMaestroAcciones) getEntityManager().find(GnMaestroAcciones.class, id));
        } catch (NoResultException e) {
            objRes = null;
        } catch (Exception e) {
            Exception(CONSULTAR, e);
        } finally {
            cerrarEntityManager();
        }
        return objRes;
    }
    
    private GnMaestroAccion castEntidadNegocio( GnMaestroAcciones entidad ){
        GnMaestroAccion negocio = new GnMaestroAccion();
        negocio.setId(entidad.getId());
        negocio.setIdGrupo(entidad.getGrupoId());
        negocio.setMaestrosTipo(new GnMaestroTipo(entidad.getMaestrosTipo()));
        negocio.setNombre(entidad.getNombre());
        negocio.setDescripcion(entidad.getDescripcion());
        return negocio;
    }

}
