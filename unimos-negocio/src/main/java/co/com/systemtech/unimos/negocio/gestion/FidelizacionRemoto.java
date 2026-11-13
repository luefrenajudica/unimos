/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.negocio.gestion;

import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.dominio.gestion.UnFidelizacion;
import java.util.List;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public interface FidelizacionRemoto {
    
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
    List<UnFidelizacion> consultarLista(ParamConsulta paramConsulta) throws Exception;
    
    /**
     * Método para consultar un registro por ID
     * @param id
     * @return (Zonas) cargado
     * @throws java.lang.Exception
     */
    UnFidelizacion consultar(int id) throws Exception;
    
    /**
     * Método para crear una nuevo registro
     * @param obj (Zonas)
     * @return (int) id del registro generado
     * @throws java.lang.Exception
     */
    int insertar(UnFidelizacion obj) throws Exception;
    
    /**
     * Método para actualizar la información de un registro
     * @param obj (Zonas)
     * @throws java.lang.Exception
     */
    void actualizar(UnFidelizacion obj) throws Exception;
    
    /**
     * Método para eliminar un registro
     * @param id
     * @return (Zonas) La persistencia del objeto eliminado
     * @throws java.lang.Exception
     */
    UnFidelizacion eliminar(int id) throws Exception;
    
    /**
     * Consultar todas las Zonas
     * @return
     * @throws Exception 
     */
    List<UnFidelizacion> consultarTodas() throws Exception;
    
    /**
     * Consulta registros por persona
     * @param personaId
     * @return
     * @throws Exception 
     */
    public List<UnFidelizacion> consultarTodasPorPersona(int personaId) throws Exception;
    
}
