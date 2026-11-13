/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.contrato;

import co.com.systemtech.unimos.dominio.contrato.UnContrato;
import co.com.systemtech.unimos.dominio.contrato.UnContratoDetalle;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.dominio.registro.UnEntidad;
import co.com.systemtech.unimos.dominio.registro.UnTecnologia;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import co.com.systemtech.unimos.ejb.entidades.UnContratoDetalles;
import co.com.systemtech.unimos.ejb.entidades.UnContratos;
import co.com.systemtech.unimos.ejb.entidades.UnEntidades;
import co.com.systemtech.unimos.ejb.entidades.UnTecnologias;
import co.com.systemtech.unimos.negocio.contrato.ContratoRemoto;
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
@Remote(ContratoRemoto.class)
public class ContratoServicio extends GenericoServicio implements ContratoRemoto {

    @Override
    public int consultarCantidadLista(ParamConsulta paramConsulta) throws Exception {
        int cant = 0;
        try {
            String strQuery = "SELECT COUNT(c) FROM UnContratos c "
                    + "INNER JOIN UnEntidades e ON c.unEntidadesId.id = e.id "
                    + "WHERE c.id > 0 ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "unEntidad.nit":
                            strQuery += "AND e.nit = '" + e.getValue() + "' ";
                            break;
                        case "unEntidad.razonSocial":
                            strQuery += "AND e.razonSocial LIKE '" + e.getValue() + "%' ";
                            break;
                        case "unEntidad.nombreComercial":
                            strQuery += "AND e.nombreComercial LIKE '" + e.getValue() + "%' ";
                            break;
                        case "contrato":
                            strQuery += "AND c.contrato LIKE '" + e.getValue() + "%' ";
                            break;
                        case "activo":
                            strQuery += "AND c.activo = " + e.getValue() + " ";
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
    public List<UnContrato> consultarLista(ParamConsulta paramConsulta) throws Exception {
        List<UnContrato> listResult = new ArrayList();
        try {
            String strQuery = "SELECT c FROM UnContratos c "
                    + "INNER JOIN UnEntidades e ON c.unEntidadesId.id = e.id "
                    + "WHERE c.id > 0 ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "unEntidad.nit":
                            strQuery += "AND e.nit = '" + e.getValue() + "' ";
                            break;
                        case "unEntidad.razonSocial":
                            strQuery += "AND e.razonSocial LIKE '" + e.getValue() + "%' ";
                            break;
                        case "unEntidad.nombreComercial":
                            strQuery += "AND e.nombreComercial LIKE '" + e.getValue() + "%' ";
                            break;
                        case "contrato":
                            strQuery += "AND c.contrato LIKE '" + e.getValue() + "%' ";
                            break;
                        case "activo":
                            strQuery += "AND c.activo = " + e.getValue() + " ";
                            break;
                    }
                }
            }
            strQuery += "ORDER BY ";
            if (paramConsulta.getOrden() != null) {
                if (paramConsulta.getOrden().contains("unEntidad")) {
                    strQuery += paramConsulta.getOrden().replace("unEntidad", "e") + " "
                            + (paramConsulta.isAscendente() ? "ASC" : "DESC");
                } else {
                    strQuery += "c." + paramConsulta.getOrden() + " "
                            + (paramConsulta.isAscendente() ? "ASC" : "DESC");
                }
            } else {
                strQuery += "e.id ASC";
            }
            List<UnContratos> list = getEntityManager().createQuery(strQuery)
                    .setFirstResult(paramConsulta.getPrimerRegistro())
                    .setMaxResults(paramConsulta.getRegistrosPagina())
                    .getResultList();
            for (UnContratos per : list) {
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
    public UnContrato consultar(int id) throws Exception {
        UnContrato objRes = null;
        try {
            objRes = castEntidadNegocio((UnContratos) getEntityManager().find(UnContratos.class, id));
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
    public int insertar(UnContrato obj) throws Exception {
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
    public void actualizar(UnContrato obj) throws Exception {
        try {
            getEntityManager().merge(castNegocioEntidad(obj));
        } catch (Exception e) {
            Exception(ACTUALIZAR, e);
        } finally {
            cerrarEntityManager();
        }
    }

    @Override
    public UnContrato eliminar(int id) throws Exception {
        UnContrato obj = null;
        try {
            UnContratos ent = getEntityManager().find(UnContratos.class, id);
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
    public List<UnContrato> consultarTodas() throws Exception {
        List<UnContrato> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnContratos e "
                    + "ORDER BY e.documentoNumero ASC";
            List<UnContratos> list = getEntityManager().createQuery(strQuery)
                    .getResultList();
            for (UnContratos per : list) {
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
    public List<UnContrato> consultarTodasPorEntidad(int entidadId) throws Exception {
        List<UnContrato> listResult = new ArrayList();
        try {
            String strQuery = "FROM UnContratos e "
                    + "WHERE e.unEntidadesId.id = :entidad_id "
                    + "ORDER BY e.fechaInicio ASC";
            List<UnContratos> list = getEntityManager().createQuery(strQuery)
                    .setParameter("entidad_id", entidadId)
                    .getResultList();
            for (UnContratos per : list) {
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

    public static UnContrato castEntidadNegocio(UnContratos per) {
        UnContrato obj = new UnContrato();
        obj.setId(per.getId());
        UnEntidad unEntidad = new UnEntidad(
                per.getUnEntidadesId().getId(),
                per.getUnEntidadesId().getMaeTipoTipo(),
                per.getUnEntidadesId().getMaeTipoId(),
                per.getUnEntidadesId().getMaeTipoCodigo(),
                per.getUnEntidadesId().getMaeTipoValor(),
                per.getUnEntidadesId().getNit(),
                per.getUnEntidadesId().getRazonSocial(),
                per.getUnEntidadesId().getNombreComercial()
        );
        obj.setUnEntidad(unEntidad);
        obj.setContrato(per.getContrato());
        obj.setFechaInicial(per.getFechaInicial());
        obj.setFechaFinal(per.getFechaFinal());
        obj.setActivo(per.getActivo());
        //Auditoría
        obj.setUsuarioCrea(per.getUsuarioCrea());
        obj.setTerminalCrea(per.getTerminalCrea());
        obj.setFechaHoraCrea(per.getFechaHoraCrea());
        obj.setUsuarioModifica(per.getUsuarioModifica());
        obj.setTerminalModifica(per.getTerminalModifica());
        obj.setFechaHoraModifica(per.getFechaHoraModifica());
        return obj;
    }

    public static UnContratos castNegocioEntidad(UnContrato obj) {
        UnContratos per = new UnContratos();
        per.setId(obj.getId());
        per.setUnEntidadesId(new UnEntidades(obj.getUnEntidad().getId()));
        per.setContrato(obj.getContrato());
        per.setFechaInicial(obj.getFechaInicial());
        per.setFechaFinal(obj.getFechaFinal());
        per.setActivo(obj.isActivo());
        //Auditoria
        per.setUsuarioCrea(obj.getUsuarioCrea());
        per.setTerminalCrea(obj.getTerminalCrea());
        per.setFechaHoraCrea(obj.getFechaHoraCrea());
        per.setUsuarioModifica(obj.getUsuarioModifica());
        per.setTerminalModifica(obj.getTerminalModifica());
        per.setFechaHoraModifica(obj.getFechaHoraModifica());
        return per;
    }

    /*
    * Contrato Detalle
     */
    @Override
    public int consultarCantidadListaDetalles(ParamConsulta paramConsulta) throws Exception {
        int cant = 0;
        try {
            String strQuery = "SELECT COUNT(d) FROM UnContratoDetalles d "
                    + "INNER JOIN UnTecnologias t ON d.unTecnologiasId.id = t.id "
                    + "WHERE d.unContratosId.id = :contrato_id ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "contrato":
                            strQuery += "AND d.activo = " + e.getValue() + " ";
                            break;
                        case "unTecnologia.codigo":
                            strQuery += "AND d.codigo = '" + e.getValue() + "' ";
                            break;
                        case "unTecnologia.codigoPropio":
                            strQuery += "AND d.codigoPropio = '" + e.getValue() + "' ";
                            break;
                        case "unTecnologia.nombre":
                            strQuery += "AND d.nombre LIKE '" + e.getValue() + "%' ";
                            break;
                    }
                }
            }
            cant = (int) (long) getEntityManager().createQuery(strQuery)
                    .setParameter("contrato_id", (int) paramConsulta.getParametroConsulta1())
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
    public List<UnContratoDetalle> consultarListaDetalles(ParamConsulta paramConsulta) throws Exception {
        List<UnContratoDetalle> listResult = new ArrayList();
        try {
            String strQuery = "SELECT d FROM UnContratoDetalles d "
                    + "INNER JOIN UnTecnologias t ON d.unTecnologiasId.id = t.id "
                    + "WHERE d.unContratosId.id = :contrato_id ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "contrato":
                            strQuery += "AND d.activo = " + e.getValue() + " ";
                            break;
                        case "unTecnologia.codigo":
                            strQuery += "AND d.codigo = '" + e.getValue() + "' ";
                            break;
                        case "unTecnologia.codigoPropio":
                            strQuery += "AND d.codigoPropio = '" + e.getValue() + "' ";
                            break;
                        case "unTecnologia.nombre":
                            strQuery += "AND d.nombre LIKE '" + e.getValue() + "%' ";
                            break;
                    }
                }
            }
            strQuery += "ORDER BY ";
            if (paramConsulta.getOrden() != null) {
                strQuery += "d." + paramConsulta.getOrden() + " "
                        + (paramConsulta.isAscendente() ? "ASC" : "DESC");
            } else {
                strQuery += "d.id ASC";
            }
            List<UnContratoDetalles> list = getEntityManager().createQuery(strQuery)
                    .setParameter("contrato_id", (int) paramConsulta.getParametroConsulta1())
                    .setFirstResult(paramConsulta.getPrimerRegistro())
                    .setMaxResults(paramConsulta.getRegistrosPagina())
                    .getResultList();
            for (UnContratoDetalles per : list) {
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
    public UnContratoDetalle consultarDetalle(int id) throws Exception {
        UnContratoDetalle objRes = null;
        try {
            objRes = castEntidadNegocio((UnContratoDetalles) getEntityManager().find(UnContratoDetalles.class, id));
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
    public int insertarDetalle(UnContratoDetalle obj) throws Exception {
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
    public void actualizarDetalle(UnContratoDetalle obj) throws Exception {
        try {
            getEntityManager().merge(castNegocioEntidad(obj));
        } catch (Exception e) {
            Exception(ACTUALIZAR, e);
        } finally {
            cerrarEntityManager();
        }
    }

    @Override
    public UnContratoDetalle eliminarDetalle(int id) throws Exception {
        UnContratoDetalle obj = null;
        try {
            UnContratoDetalles ent = getEntityManager().find(UnContratoDetalles.class, id);
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
    
    /*
    * Contrato Detalle
     */
    @Override
    public int consultarCantidadListaDetallesContratados(ParamConsulta paramConsulta) throws Exception {
        int cant = 0;
        try {
            String strQuery = "SELECT COUNT(d) FROM UnContratoDetalles d "
                    + "INNER JOIN UnTecnologias t ON d.unTecnologiasId.id = t.id "
                    + "INNER JOIN UnContratos c ON d.unContratosId.id = c.id "
                    + "INNER JOIN UnEntidades e ON c.unEntidadesId.id = e.id "
                    + "WHERE c.activo = true "
                    + "AND d.activo = true "
                    + "AND t.activa = true ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "codigo":
                            strQuery += "AND t.codigo = '" + e.getValue() + "' ";
                            break;
                        case "codigoPropio":
                            strQuery += "AND t.codigoPropio = '" + e.getValue() + "' ";
                            break;
                        case "nombre":
                            strQuery += "AND t.nombre LIKE '" + e.getValue() + "%' ";
                            break;
                    }
                }
            }
            cant = (int) (long) getEntityManager().createQuery(strQuery)
//                    .setParameter("contrato_id", (int) paramConsulta.getParametroConsulta1())
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
    public List<UnContratoDetalle> consultarListaDetallesContratados(ParamConsulta paramConsulta) throws Exception {
        List<UnContratoDetalle> listResult = new ArrayList();
        try {
            String strQuery = "SELECT d FROM UnContratoDetalles d "
                    + "INNER JOIN UnTecnologias t ON d.unTecnologiasId.id = t.id "
                    + "INNER JOIN UnContratos c ON d.unContratosId.id = c.id "
                    + "INNER JOIN UnEntidades e ON c.unEntidadesId.id = e.id "
                    + "WHERE c.activo = true "
                    + "AND d.activo = true "
                    + "AND t.activa = true ";
            if (paramConsulta.getFiltros() != null) {
                for (Map.Entry e : paramConsulta.getFiltros().entrySet()) {
                    switch ((String) e.getKey()) {
                        case "codigo":
                            strQuery += "AND t.codigo = '" + e.getValue() + "' ";
                            break;
                        case "codigoPropio":
                            strQuery += "AND t.codigoPropio = '" + e.getValue() + "' ";
                            break;
                        case "nombre":
                            strQuery += "AND t.nombre LIKE '" + e.getValue() + "%' ";
                            break;
                    }
                }
            }
            strQuery += "ORDER BY ";
            if (paramConsulta.getOrden() != null) {
                strQuery += "d." + paramConsulta.getOrden() + " "
                        + (paramConsulta.isAscendente() ? "ASC" : "DESC");
            } else {
                strQuery += "d.id ASC";
            }
            List<UnContratoDetalles> list = getEntityManager().createQuery(strQuery)
//                    .setParameter("contrato_id", (int) paramConsulta.getParametroConsulta1())
                    .setFirstResult(paramConsulta.getPrimerRegistro())
                    .setMaxResults(paramConsulta.getRegistrosPagina())
                    .getResultList();
            for (UnContratoDetalles per : list) {
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

    public static UnContratoDetalles castNegocioEntidad(UnContratoDetalle obj) {
        UnContratoDetalles per = new UnContratoDetalles();
        per.setId(obj.getId());
        per.setUnContratosId(new UnContratos(obj.getUnContrato().getId()));
        per.setUnTecnologiasId(new UnTecnologias(obj.getUnTecnologia().getId()));
        per.setFechaInicio(obj.getFechaInicio());
        per.setFechaFin(obj.getFechaFin());
        per.setValorSuscriptor(new BigDecimal(obj.getValorSuscriptor()));
        per.setValorConvenio(new BigDecimal(obj.getValorConvenio()));
        per.setActivo(obj.isActivo());
        //Auditoria
        per.setUsuarioCrea(obj.getUsuarioCrea());
        per.setTerminalCrea(obj.getTerminalCrea());
        per.setFechaHoraCrea(obj.getFechaHoraCrea());
        per.setUsuarioModifica(obj.getUsuarioModifica());
        per.setTerminalModifica(obj.getTerminalModifica());
        per.setFechaHoraModifica(obj.getFechaHoraModifica());
        return per;
    }

    public static UnContratoDetalle castEntidadNegocio(UnContratoDetalles per) {
        UnContratoDetalle obj = new UnContratoDetalle();
        obj.setId(per.getId());
        UnContrato objCont = new UnContrato(
                per.getUnContratosId().getId(),
                per.getUnContratosId().getContrato(),
                per.getUnContratosId().getFechaInicial(),
                per.getUnContratosId().getFechaFinal(),
                per.getUnContratosId().getActivo()
        );
        UnEntidad objEnt = new UnEntidad(
                per.getUnContratosId().getUnEntidadesId().getId(), 
                per.getUnContratosId().getUnEntidadesId().getMaeTipoTipo(),
                per.getUnContratosId().getUnEntidadesId().getMaeTipoId(),
                per.getUnContratosId().getUnEntidadesId().getMaeTipoCodigo(),
                per.getUnContratosId().getUnEntidadesId().getMaeTipoValor(),
                per.getUnContratosId().getUnEntidadesId().getNit(),
                per.getUnContratosId().getUnEntidadesId().getRazonSocial(),
                per.getUnContratosId().getUnEntidadesId().getNombreComercial()
        );
        objCont.setUnEntidad(objEnt);
        obj.setUnContrato(objCont);
        UnTecnologia objTec = new UnTecnologia(
                per.getUnTecnologiasId().getId(),
                per.getUnTecnologiasId().getTipo(),
                per.getUnTecnologiasId().getCodigo(),
                per.getUnTecnologiasId().getCodigoPropio(),
                per.getUnTecnologiasId().getNombre(),
                per.getUnTecnologiasId().getMaeServicioHabilitacionTipo(),
                per.getUnTecnologiasId().getMaeServicioHabilitacionId(),
                per.getUnTecnologiasId().getMaeServicioHabilitacionCodigo(),
                per.getUnTecnologiasId().getMaeServicioHabilitacionValor(),
                per.getUnTecnologiasId().getActiva()
        );
        obj.setUnTecnologia(objTec);
        obj.setFechaInicio(per.getFechaInicio());
        obj.setFechaFin(per.getFechaFin());
        obj.setValorSuscriptor(per.getValorSuscriptor().doubleValue());
        obj.setValorConvenio(per.getValorConvenio().doubleValue());
        obj.setActivo(per.getActivo());
        //Auditoria
        obj.setUsuarioCrea(per.getUsuarioCrea());
        obj.setTerminalCrea(per.getTerminalCrea());
        obj.setFechaHoraCrea(per.getFechaHoraCrea());
        obj.setUsuarioModifica(per.getUsuarioModifica());
        obj.setTerminalModifica(per.getTerminalModifica());
        obj.setFechaHoraModifica(per.getFechaHoraModifica());
        return obj;
    }

}
