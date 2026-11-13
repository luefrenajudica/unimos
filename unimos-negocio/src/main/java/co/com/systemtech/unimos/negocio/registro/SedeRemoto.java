/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.negocio.registro;

import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.dominio.registro.Sede;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author rpalacic
 */
public interface SedeRemoto {
    
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
    List<Sede> consultarLista(ParamConsulta paramConsulta) throws Exception;
    
    /**
     * Método para consultar un registro por ID
     * @param id
     * @return (Sedes) cargado
     * @throws java.lang.Exception
     */
    Sede consultar(int id) throws Exception;
    
    /**
     * Método para crear una nuevo registro
     * @param obj (Sedes)
     * @return (int) id del registro generado
     * @throws java.lang.Exception
     */
    int insertar(Sede obj) throws Exception;
    
    /**
     * Método para actualizar la información de un registro
     * @param obj (Sedes)
     * @throws java.lang.Exception
     */
    void actualizar(Sede obj) throws Exception;
    
    /**
     * Método para eliminar un registro
     * @param id
     * @return (Sedes) La persistencia del objeto eliminado
     * @throws java.lang.Exception
     */
    Sede eliminar(int id) throws Exception;
    
    /**
     * Consultar todas las Sedes de una empresa y retornarlas en List
     * @return
     * @throws Exception 
     */
    List<Sede> consultarTodas() throws Exception;
    
    /**
     * Consultar todas las Sedes de una empresa y retornarlas en Hash
     * @param empresaId
     * @return
     * @throws Exception 
     */
    HashMap<Integer, Sede> consultarTodasHash(Integer empresaId) throws Exception;
    
}
