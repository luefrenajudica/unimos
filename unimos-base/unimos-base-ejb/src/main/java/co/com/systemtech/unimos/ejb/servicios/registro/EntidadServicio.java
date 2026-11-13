/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.registro;

import co.com.systemtech.unimos.dominio.administracion.Ubicacion;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import co.com.systemtech.unimos.dominio.registro.UnEntidad;
import co.com.systemtech.unimos.dominio.registro.UnEntidadContacto;
import co.com.systemtech.unimos.ejb.entidades.GnUbicaciones;
import co.com.systemtech.unimos.ejb.entidades.UnEntidadContactos;
import co.com.systemtech.unimos.ejb.entidades.UnEntidades;
import co.com.systemtech.unimos.negocio.registro.EntidadRemoto;
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
@Remote(EntidadRemoto.class)
public class EntidadServicio extends GenericoServicio implements EntidadRemoto {
    
    @Override
    public int consultarCantidadLista(ParamConsulta paramConsulta) throws Exception {
        int cant = 0;
        try {
            String strQuery = "SELECT COUNT(e) FROM UnEntidades e ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "nit":
                            strQuery += "AND e.nit = '" + e.getValue() + "' ";
                            break;
                        case "razon_social":
                            strQuery += "AND e.razonSocial LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "nombreComercial":
                            strQuery += "AND e.nombreComercial LIKE '" + (String) e.getValue() + "%' ";
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
    public List<UnEntidad> consultarLista(ParamConsulta paramConsulta) throws Exception {
        List<UnEntidad> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnEntidades e ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "nit":
                            strQuery += "AND e.nit = '" + e.getValue() + "' ";
                            break;
                        case "razonSocial":
                            strQuery += "AND e.razonSocial LIKE '" + (String) e.getValue() + "%' ";
                            break;
                        case "nombreComercial":
                            strQuery += "AND e.nombreComercial LIKE '" + (String) e.getValue() + "%' ";
                            break;
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
            List<UnEntidades> list = getEntityManager().createQuery(strQuery)
                    .setFirstResult(paramConsulta.getPrimerRegistro())
                    .setMaxResults(paramConsulta.getRegistrosPagina())
                    .getResultList();
            for (UnEntidades per : list) {
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
    public UnEntidad consultar(int id) throws Exception {
        UnEntidad objRes = null;
        try {
            objRes = castEntidadNegocio((UnEntidades) getEntityManager().find(UnEntidades.class, id));
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
    public int insertar(UnEntidad obj) throws Exception {
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
    public void actualizar(UnEntidad obj) throws Exception{
        try{
            getEntityManager().merge(castNegocioEntidad(obj));
        } catch (NoResultException e) {
            
        } catch (Exception e) {
            Exception(ACTUALIZAR, e);
        } finally {
            cerrarEntityManager();
        }
    }

    @Override
    public UnEntidad eliminar(int id) throws Exception {
        UnEntidad obj = null;
        try {
            UnEntidades ent = getEntityManager().find(UnEntidades.class, id);
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
    public List<UnEntidad> consultarTodas() throws Exception {
        List<UnEntidad> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnEntidades e "
                    + "ORDER BY e.razonSocial ASC";
            List<UnEntidades> list = getEntityManager().createQuery(strQuery)
                    .getResultList();
            for (UnEntidades per : list) {
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
    public List<UnEntidadContacto> consultarContactos(int entidadId) throws Exception {
        List<UnEntidadContacto> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnEntidadContactos c "
                    + "WHERE c.unEntidadesId.id = :entidad_id "
                    + "ORDER BY c.fechaHoraCrea ASC";
            List<UnEntidadContactos> list = getEntityManager().createQuery(strQuery)
                    .setParameter("entidad_id", entidadId)
                    .getResultList();
            for (UnEntidadContactos per : list) {
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
    public UnEntidadContacto consultarContacto(int id) throws Exception {
        UnEntidadContacto objRes = null;
        try {
            objRes = castEntidadNegocio((UnEntidadContactos) getEntityManager().find(UnEntidadContactos.class, id));
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
    public int insertarContacto(UnEntidadContacto obj) throws Exception {
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
    public UnEntidadContacto eliminarContacto(int id) throws Exception {
        UnEntidadContacto obj = null;
        try {
            UnEntidadContactos ent = getEntityManager().find(UnEntidadContactos.class, id);
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

    public static UnEntidad castEntidadNegocio(UnEntidades per){
        UnEntidad obj = new UnEntidad();
        obj.setId(per.getId());
        obj.setMaeTipoTipo(per.getMaeTipoTipo());
        obj.setMaeTipoId(per.getMaeTipoId());
        obj.setMaeTipoCodigo(per.getMaeTipoCodigo());
        obj.setMaeTipoValor(per.getMaeTipoValor());
        obj.setNit(per.getNit());
        obj.setNombreComercial(per.getNombreComercial());
        obj.setRazonSocial(per.getRazonSocial());
        obj.setGnUbicacion(new Ubicacion(per.getGnUbicacionesId().getId()));
        obj.setDireccion(per.getDireccion());
        //Auditoría
        obj.setUsuarioCrea(per.getUsuarioCrea());
        obj.setTerminalCrea(per.getTerminalCrea());
        obj.setFechaHoraCrea(per.getFechaHoraCrea());
        obj.setUsuarioModifica(per.getUsuarioModifica());
        obj.setTerminalModifica(per.getTerminalModifica());
        obj.setFechaHoraModifica(per.getFechaHoraModifica());
        return obj;
    }
    
    public static UnEntidades castNegocioEntidad(UnEntidad obj){
        UnEntidades per = new UnEntidades();
        per.setId(obj.getId());
        per.setMaeTipoTipo(obj.getMaeTipoTipo());
        per.setMaeTipoId(obj.getMaeTipoId());
        per.setMaeTipoCodigo(obj.getMaeTipoCodigo());
        per.setMaeTipoValor(obj.getMaeTipoValor());
        per.setNit(obj.getNit());
        per.setNombreComercial(obj.getNombreComercial());
        per.setRazonSocial(obj.getRazonSocial());
        per.setGnUbicacionesId(new GnUbicaciones(obj.getGnUbicacion().getId()));
        per.setDireccion(obj.getDireccion());
        //Auditoria
        per.setUsuarioCrea(obj.getUsuarioCrea());
        per.setTerminalCrea(obj.getTerminalCrea());
        per.setFechaHoraCrea(obj.getFechaHoraCrea());        
        per.setUsuarioModifica(obj.getUsuarioModifica());
        per.setTerminalModifica(obj.getTerminalModifica());
        per.setFechaHoraModifica(obj.getFechaHoraModifica());
        return per;
    }
    
    public static UnEntidadContacto castEntidadNegocio(UnEntidadContactos per){
        UnEntidadContacto obj = new UnEntidadContacto();
        obj.setId(per.getId());
        obj.setUnEntidad(new UnEntidad(per.getUnEntidadesId().getId()));
        obj.setMaeTipoTipo(per.getMaeTipoTipo());
        obj.setMaeTipoId(per.getMaeTipoId());
        obj.setMaeTipoCodigo(per.getMaeTipoCodigo());
        obj.setMaeTipoValor(per.getMaeTipoValor());
        obj.setNombre(per.getNombre());
        obj.setContacto(per.getContacto());
        obj.setEstadoRegistro(UnEntidadContacto.ESTADO_REGISTRO_CREADO);
        //Auditoría
        obj.setUsuarioCrea(per.getUsuarioCrea());
        obj.setTerminalCrea(per.getTerminalCrea());
        obj.setFechaHoraCrea(per.getFechaHoraCrea());
        obj.setUsuarioModifica(per.getUsuarioModifica());
        obj.setTerminalModifica(per.getTerminalModifica());
        obj.setFechaHoraModifica(per.getFechaHoraModifica());
        return obj;
    }
    
    public static UnEntidadContactos castNegocioEntidad(UnEntidadContacto obj){
        UnEntidadContactos per = new UnEntidadContactos();
        per.setId(obj.getId());
        per.setUnEntidadesId(new UnEntidades(obj.getUnEntidad().getId()));
        per.setMaeTipoTipo(obj.getMaeTipoTipo());
        per.setMaeTipoId(obj.getMaeTipoId());
        per.setMaeTipoCodigo(obj.getMaeTipoCodigo());
        per.setMaeTipoValor(obj.getMaeTipoValor());
        per.setNombre(obj.getNombre());
        per.setContacto(obj.getContacto());
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
