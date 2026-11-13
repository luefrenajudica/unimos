/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.negocio.administracion;

import co.com.systemtech.unimos.dominio.administracion.GnLog;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import java.util.List;

/**
 *
 * @author rpalacic
 */
public interface LogRemoto {
    
    /**
     * Consultar la cantidad de registros para la lista
     * @param paramConsulta
     * @return
     * @throws Exception 
     */
    int consultarCantidadTodos(ParamConsulta paramConsulta) throws Exception;
    
    /**
     * Método para consultar los perfiles con filtros
     * @param paramConsulta
     * @return 
     * @throws Exception 
     */
    List<GnLog> consultarTodos(ParamConsulta paramConsulta) throws Exception;
        
    /**
     * Método para consultar un perfil por ID
     * @param id
     * @return (Perfiles) cargado
     * @throws java.lang.Exception
     */
    GnLog consultar(int id) throws Exception;
    
    /**
     * Método para crear un nuev maestro
     * @param obj (Maestros)
     * @return (int) id del registro generado
     * @throws java.lang.Exception
     */
    int insertar(GnLog obj) throws Exception;
    
    /**
     * Método para eliminar un registro
     * @param id
     * @return
     * @throws Exception 
     */
    GnLog eliminar(int id) throws Exception;
    
    /**
     * Consultar ultimos registros
     * @param empresa (String) Empresa correspondiente
     * @param cant (int) número de registros a consultar
     * @return 
     */
    public List<GnLog> consultarUltimos(String empresa, int cant);
    
}
