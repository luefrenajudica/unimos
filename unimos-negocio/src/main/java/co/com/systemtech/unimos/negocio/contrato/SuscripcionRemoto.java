/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.negocio.contrato;

import co.com.systemtech.unimos.dominio.contrato.UnSuscripcion;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import java.util.List;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public interface SuscripcionRemoto {
    
//    /**
//     * Método para consultar todos los registros
//     * @return 
//     * @throws java.lang.Exception 
//     */
//    List<UnSuscripcion> consultarTodos() throws Exception;
    
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
    List<UnSuscripcion> consultarLista(ParamConsulta paramConsulta) throws Exception;
    
    /**
     * Método para consultar un registro por ID
     * @param id
     * @return (Zonas) cargado
     * @throws java.lang.Exception
     */
    UnSuscripcion consultar(int id) throws Exception;
    
    /**
     * Método para crear una nuevo registro
     * @param obj (Zonas)
     * @return (int) id del registro generado
     * @throws java.lang.Exception
     */
    int insertar(UnSuscripcion obj) throws Exception;
    
    /**
     * Método para actualizar la información de un registro
     * @param obj (Zonas)
     * @throws java.lang.Exception
     */
    void actualizar(UnSuscripcion obj) throws Exception;
    
    /**
     * Método para actualizar solo la suscripción sin personas
     * @param obj
     * @throws Exception 
     */
    void actualizarPagos(UnSuscripcion obj) throws Exception;
    
    /**
     * Método para eliminar un registro
     * @param id
     * @return (Zonas) La persistencia del objeto eliminado
     * @throws java.lang.Exception
     */
    UnSuscripcion eliminar(int id) throws Exception;
    
    /**
     * Consultar todas las Zonas
     * @return
     * @throws Exception 
     */
    List<UnSuscripcion> consultarTodas() throws Exception;
    
    /**
     * Consulta registros por persona
     * @param personaId
     * @return
     * @throws Exception 
     */
    public List<UnSuscripcion> consultarTodasPorPersona(int personaId) throws Exception;
    
}
