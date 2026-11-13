/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.negocio.administracion;

import co.com.systemtech.unimos.dominio.administracion.GnConfiguracion;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author rpalacic
 */
public interface ConfiguracionRemoto {
    
    /**
     * 
     * @param paramConsulta
     * @return
     * @throws Exception 
     */
    int consultarCantidadLista(ParamConsulta paramConsulta) throws Exception;
    
    /**
     * 
     * @param paramConsulta
     * @return
     * @throws Exception 
     */
    List<GnConfiguracion> consultarLista(ParamConsulta paramConsulta) throws Exception;
    
    /**
     * Método para consultar un maestro por ID
     * @param id
     * @return 
     * @throws java.lang.Exception 
     */
    GnConfiguracion consultar(int id) throws Exception;
    
    /**
     * Método para crear un nuev maestro
     * @param obj (GnConfiguracionObjs)
     * @return (int) id del registro generado
     * @throws java.lang.Exception
     */
    int insertar(GnConfiguracion obj) throws Exception;
    
    /**
     * Método para actualizar la información de un maestro
     * @param obj (GnConfiguracionObjs)
     * @throws java.lang.Exception
     */
    void actualizar(GnConfiguracion obj) throws Exception;
    
    /**
     * Metodo para consultar un nuevo Id para guardar
     * @return (int) id más alto de la tabla
     * @throws Exception 
     */
    int consultarIdNuevo() throws Exception;
//    
//    /**
//     * Método para eliminar un maestro
//     * @param id
//     * @return (GnConfiguracionObj) objeto eliminado
//     * @throws java.lang.Exception
//     */
//    GnConfiguracionObj eliminar(int id) throws Exception;
//    
//    /**
//     * Método para consultar los maestros activos por tipo
//     * @param _tipo
//     * @return
//     * @throws Exception 
//     */
//    List<GnConfiguracionObj> consultarPorTipo(String _tipo) throws Exception;
    
    /**
     * Método para consultar los maestros activos por tipo
     * @return (HashMap<Integer, String>) Hash con los maestros cargados 
     */
    HashMap<Integer, String> consultarHash();
    
//    /**
//     * 
//     * @param tipo
//     * @return
//     * @throws Exception 
//     */
//    HashMap<String, GnConfiguracionObj> consultarHashPorTipoValor(String tipo) throws Exception;
//    
//
//    
//    /**
//     * Consultar todos los GnConfiguracionObjs
//     * @return
//     * @throws Exception 
//     */
//    HashMap<Integer, GnConfiguracionObj> consultarHash() throws Exception;
//    
//    /**
//     * Consultar maestros para módulo de Anexos
//     * @return
//     * @throws Exception 
//     */
//    HashMap<Integer, GnConfiguracionObj> consultarAnexosHash() throws Exception;
//    
//    /**
//     * Consultar todos los maetsros
//     * @return
//     * @throws Exception 
//     */
//    List<GnConfiguracionObj> consultarLista() throws Exception;
    
}
