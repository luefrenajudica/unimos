/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.negocio.administracion;

import co.com.systemtech.unimos.dominio.administracion.GnMaestro;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroAccion;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author rpalacic
 */
public interface MaestroRemoto {

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
    List<GnMaestro> consultarLista(ParamConsulta paramConsulta) throws Exception;

    /**
     * Método para consultar un maestro por ID
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    GnMaestro consultar(int id) throws Exception;

    /**
     * Método para crear un nuev maestro
     *
     * @param obj (GnMaestros)
     * @return (int) id del registro generado
     * @throws java.lang.Exception
     */
    int insertar(GnMaestro obj) throws Exception;

    /**
     * Método para actualizar la información de un maestro
     *
     * @param obj (GnMaestros)
     * @throws java.lang.Exception
     */
    void actualizar(GnMaestro obj) throws Exception;

    /**
     * Método para eliminar un maestro
     *
     * @param id
     * @return (GnMaestro) objeto eliminado
     * @throws java.lang.Exception
     */
    GnMaestro eliminar(int id) throws Exception;

    /**
     * Método para consultar los maestros activos por tipo
     *
     * @param _tipo
     * @return
     * @throws Exception
     */
    List<GnMaestro> consultarPorTipo(String _tipo) throws Exception;
    
    /**
     * Consulta lista de maestros segun el tipo de su Hijo
     * @param tipo
     * @return
     * @throws Exception 
     */
    List<GnMaestro> consultarPorTipoHijo(String tipo) throws Exception;

    /**
     * Método para consultar los maestros activos por tipo
     * @param _tipo
     * @return (HashMap<Integer, GnMaestro>) Hash con los maestros cargados
     * @throws Exception
     */
    HashMap<Integer, GnMaestro> consultarHashPorTipo(String _tipo) throws Exception;

    /**
     *
     * @param tipo
     * @return
     * @throws Exception
     */
    HashMap<String, GnMaestro> consultarHashPorTipoValor(String tipo) throws Exception;

    /**
     * Consultar todos los GnMaestros
     * @return (HashMap<Integer, GnMaestro>) Hash con los maestros cargados
     * @throws Exception
     */
    HashMap<Integer, GnMaestro> consultarHash() throws Exception;

    /**
     * Consultar todos los maetsros
     *
     * @return
     * @throws Exception
     */
    List<GnMaestro> consultarLista() throws Exception;
    
    /**
     * Consultar maestro por valor
     *
     * @param valor
     * @return
     * @throws Exception
     */
    public GnMaestro consultarPorValor(String valor) throws Exception;

    /**
     * Consultar de maestro por valor y tipo
     *
     * @param valor
     * @param tipo 
     * @return
     * @throws Exception
     */
    public GnMaestro consultarPorValorTipo(String valor, String tipo) throws Exception;
   
    /**
     * Método para consultar los maestros activos por tipo, ordenandolos por su id.
     *
     * @param _tipo
     * @return
     * @throws Exception
     */
    List<GnMaestro> consultarPorTipoYOrdenPorId(String _tipo) throws Exception;

    /**
     * Consultar lista de Acciones de GnMaestros por Tipo de maestro
     * @param tipo
     * @return
     * @throws Exception
     */
    List<GnMaestroAccion> consultarAccionesPorTipo(String tipo) throws Exception;
    
    /**
     * Consultar accion por ID
     * @param id
     * @return
     * @throws Exception 
     */
    GnMaestroAccion consultarAcciones(int id) throws Exception;
    
    /**
     * Consultar todos los GnMaestros
     * @param idPadre
     * @return (HashMap<Integer, GnMaestro>) Hash con los maestros cargados
     * @throws Exception
     */
    HashMap<Integer, GnMaestro> consultarHashPorPadre(int idPadre) throws Exception;

    /**
     * Consultar todos los maetros partiendo de un padre
     *
     * @param tipo tipo de maestro hijo
     * @param idPadre
     * @return
     * @throws Exception
     */
    List<GnMaestro> consultarListaPorPadre(String tipo, int idPadre) throws Exception;
    
    /**
     * Consultar todos los maetros partiendo de dos padres
     * 
     * @param tipo tipo de maestro hijo
     * @param idPadre1
     * @param idPadre2
     * @return 
     * @throws java.lang.Exception 
     */
    List<GnMaestro> consultarListaPorPadre(String tipo, int idPadre1, int idPadre2) throws Exception;
    
    /**
     * Consultar todos los maetros partiendo de tres padres
     * 
     * @param tipo tipo de maestro hijo
     * @param idPadre1
     * @param idPadre2
     * @param idPadre3
     * @return 
     * @throws java.lang.Exception 
     */
    List<GnMaestro> consultarListaPorPadre(String tipo, int idPadre1, int idPadre2, int idPadre3) throws Exception;

    /**
     * Consultar todos los maetros partiendo del valor de un padre
     * @param tipo
     * @param valorPadre
     * @return 
     * @throws java.lang.Exception 
     */
    List<GnMaestro> consultarListaPorPadre(String tipo, String valorPadre) throws Exception;
    
    /**
     * Validar si existe relación entre padres e hijos
     * 
     * @param idHijo
     * @param idPadre
     * @return
     * @throws Exception 
     */
    boolean isRelacionPadreHijo(int idHijo, int idPadre) throws Exception;

    /**
     * Validar si existe relación entre padres e hijos
     * 
     * @param idHijo
     * @param idPadre1
     * @param idPadre2
     * @return
     * @throws Exception 
     */
    boolean isRelacionPadreHijo(int idHijo, int idPadre1, int idPadre2) throws Exception;

    /**
     * Validar si existe relación entre padres e hijos
     * 
     * @param idHijo
     * @param idPadre1
     * @param idPadre2
     * @param idPadre3
     * @return
     * @throws Exception 
     */
    boolean isRelacionPadreHijo(int idHijo, int idPadre1, int idPadre2, int idPadre3) throws Exception;
    
}
