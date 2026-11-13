/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.negocio.contrato;

import co.com.systemtech.unimos.dominio.contrato.UnContrato;
import co.com.systemtech.unimos.dominio.contrato.UnContratoDetalle;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import java.util.List;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public interface ContratoRemoto {
    
    /**
     * Consulta de cantidad de registros en una lista
     * @param paramConsulta
     * @return
     * @throws Exception 
     */
    int consultarCantidadLista(ParamConsulta paramConsulta) throws Exception;
    
    /**
     * Consultar lista de registros
     * @param paramConsulta
     * @return
     * @throws Exception 
     */
    List<UnContrato> consultarLista(ParamConsulta paramConsulta) throws Exception;
    
    /**
     * Método para consultar un registro por ID
     * @param id
     * @return (UnContrato) cargado
     * @throws java.lang.Exception
     */
    UnContrato consultar(int id) throws Exception;
    
    /**
     * Método para crear una nuevo registro
     * @param obj (UnContrato)
     * @return (int) id del registro generado
     * @throws java.lang.Exception
     */
    int insertar(UnContrato obj) throws Exception;
    
    /**
     * Método para actualizar la información de un registro
     * @param obj (UnContrato)
     * @throws java.lang.Exception
     */
    void actualizar(UnContrato obj) throws Exception;
    
    /**
     * Método para eliminar un registro
     * @param id
     * @return (UnContrato) La persistencia del objeto eliminado
     * @throws java.lang.Exception
     */
    UnContrato eliminar(int id) throws Exception;
    
    /**
     * Consultar todas las Zonas
     * @return
     * @throws Exception 
     */
    List<UnContrato> consultarTodas() throws Exception;
    
    /**
     * Consulta registros por persona
     * @param personaId
     * @return
     * @throws Exception 
     */
    public List<UnContrato> consultarTodasPorEntidad(int personaId) throws Exception;
    
    /**
     * Consulta de cantidad de registros en una lista
     * @param paramConsulta
     * @return
     * @throws Exception 
     */
    int consultarCantidadListaDetalles(ParamConsulta paramConsulta) throws Exception;
    
    /**
     * Consultar lista de registros
     * @param paramConsulta
     * @return
     * @throws Exception 
     */
    List<UnContratoDetalle> consultarListaDetalles(ParamConsulta paramConsulta) throws Exception;
    
    /**
     * Método para consultar un registro por ID
     * @param id
     * @return (UnContratoDetalle) cargado
     * @throws java.lang.Exception
     */
    UnContratoDetalle consultarDetalle(int id) throws Exception;
    
    /**
     * Método para crear una nuevo registro
     * @param obj (UnContratoDetalle)
     * @return (int) id del registro generado
     * @throws java.lang.Exception
     */
    int insertarDetalle(UnContratoDetalle obj) throws Exception;
    
    /**
     * Método para actualizar la información de un registro
     * @param obj (UnContratoDetalle)
     * @throws java.lang.Exception
     */
    void actualizarDetalle(UnContratoDetalle obj) throws Exception;
    
    /**
     * Método para eliminar un registro
     * @param id
     * @return (UnContratoDetalle) La persistencia del objeto eliminado
     * @throws java.lang.Exception
     */
    UnContratoDetalle eliminarDetalle(int id) throws Exception;
    
    /**
     * Consulta de cantidad de registros en una lista
     * @param paramConsulta
     * @return
     * @throws Exception 
     */
    int consultarCantidadListaDetallesContratados(ParamConsulta paramConsulta) throws Exception;
    
    /**
     * Consultar lista de registros
     * @param paramConsulta
     * @return
     * @throws Exception 
     */
    List<UnContratoDetalle> consultarListaDetallesContratados(ParamConsulta paramConsulta) throws Exception;
    
}
