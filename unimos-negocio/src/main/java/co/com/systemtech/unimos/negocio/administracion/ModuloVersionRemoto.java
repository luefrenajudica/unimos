/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.negocio.administracion;

import co.com.systemtech.unimos.dominio.administracion.ModuloVersion;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import java.util.List;

/**
 *
 * @author Stiven Giraldo
 */
public interface ModuloVersionRemoto {
    
    /**
     * Consulta la cantidad de lista
     * @param paramConsulta
     * @return
     * @throws Exception 
     */
    int consultarCantidadLista(ParamConsulta paramConsulta) throws Exception;
    
    /**
     * Consulta la lista
     * @param paramConsulta
     * @return
     * @throws Exception 
     */
    List<ModuloVersion> consultarLista(ParamConsulta paramConsulta) throws Exception;
    
    /**
     * Consultar lista de módulos Versión
     * @return
     * @throws Exception 
     */
    List<ModuloVersion> consultarTodos() throws Exception;
    
    /**
     * Método para consultar por ID
     * @param id
     * @return 
     * @throws java.lang.Exception 
     */
    ModuloVersion consultar(int id) throws Exception;
    
    /**
     * Método para consultar por modulo
     * @param id
     * @return
     * @throws Exception 
     */
    List<ModuloVersion> consultarByModulo(int id) throws Exception;
    
    
    /**
     * Método para crear un registro
     * @param objeto
     * @return
     * @throws Exception 
     */
    int insertar(ModuloVersion objeto) throws Exception;
    
    /**
     * Método para consultar la versión mas actual por modulo
     * @param id
     * @return
     * @throws Exception 
     */
    ModuloVersion consultarActualByModulo(int id) throws Exception;
    
}
