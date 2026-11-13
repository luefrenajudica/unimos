/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.negocio.registro;

import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import co.com.systemtech.unimos.dominio.registro.UnEntidad;
import co.com.systemtech.unimos.dominio.registro.UnEntidadContacto;
import java.util.List;

/**
 *
 * @author rpalacic
 */
public interface EntidadRemoto {
    
//    /**
//     * Método para consultar todos los registros
//     * @return 
//     * @throws java.lang.Exception 
//     */
//    List<UnEntidad> consultarTodos() throws Exception;
    
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
    List<UnEntidad> consultarLista(ParamConsulta paramConsulta) throws Exception;
    
    /**
     * Método para consultar un registro por ID
     * @param id
     * @return (Zonas) cargado
     * @throws java.lang.Exception
     */
    UnEntidad consultar(int id) throws Exception;
    
    /**
     * Método para crear una nuevo registro
     * @param obj (Zonas)
     * @return (int) id del registro generado
     * @throws java.lang.Exception
     */
    int insertar(UnEntidad obj) throws Exception;
    
    /**
     * Método para actualizar la información de un registro
     * @param obj (Zonas)
     * @throws java.lang.Exception
     */
    void actualizar(UnEntidad obj) throws Exception;
    
    /**
     * Método para eliminar un registro
     * @param id
     * @return (Zonas) La persistencia del objeto eliminado
     * @throws java.lang.Exception
     */
    UnEntidad eliminar(int id) throws Exception;
    
    /**
     * Consultar todas las Zonas
     * @return
     * @throws Exception 
     */
    List<UnEntidad> consultarTodas() throws Exception;
    
    /**
     * Metodo para consultar lista de contactos de una entidad
     * @param idEntidad
     * @return
     * @throws Exception 
     */
    public List<UnEntidadContacto> consultarContactos(int idEntidad) throws Exception;
    
    /**
     * Metodo para consultar un contacto
     * @param id
     * @return
     * @throws Exception 
     */
    public UnEntidadContacto consultarContacto(int id) throws Exception;
    
    /**
     * Método pata insertar un contacto
     * @param obj
     * @return
     * @throws Exception 
     */
    public int insertarContacto(UnEntidadContacto obj) throws Exception;
    
    /**
     * Metodo para eliminar unhj contacto
     * @param id
     * @return
     * @throws Exception 
     */
    public UnEntidadContacto eliminarContacto(int id) throws Exception;
    
}
