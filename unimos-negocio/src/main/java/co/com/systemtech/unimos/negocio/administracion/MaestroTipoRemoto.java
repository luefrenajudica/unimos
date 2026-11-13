/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.negocio.administracion;

import co.com.systemtech.unimos.dominio.administracion.GnMaestroTipo;
import co.com.systemtech.unimos.dominio.administracion.Perfil;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import java.util.List;

/**
 *
 * @author sgiraldov
 */
public interface MaestroTipoRemoto {
    
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
    List<GnMaestroTipo> consultarLista(ParamConsulta paramConsulta) throws Exception;

    /**
     * Método para consultar un maestro por ID
     *
     * @param tipo
     * @return
     * @throws java.lang.Exception
     */
    GnMaestroTipo consultar(String tipo) throws Exception;

    /**
     * Método para actualizar la información de un Tipo GnMaestro (Perfil)
     *
     * @param obj (GnMaestros)
     * @throws java.lang.Exception
     */
    void actualizar(GnMaestroTipo obj) throws Exception;

    /**
     * Consultar MestroTipo padre por tipo hijo
     * @param tipo
     * @return
     * @throws Exception 
     */
    GnMaestroTipo consultarPadrePorTipo(String tipo) throws Exception;
            
    /**
     * Método para consultar todos los registros
     * @return
     * @throws Exception
     */
    List<GnMaestroTipo> consultarTodos()throws Exception;
    
    /**
     * Método para consultar todos los registros activos
     * @return
     * @throws Exception
     */
    List<GnMaestroTipo> consultarActivos()throws Exception;
    
    /**
     * Consultar listado de activos por perfil
     * @param listaPerfiles
     * @return
     * @throws Exception 
     */
    List<GnMaestroTipo> consultarActivosPorPerfil(List<Perfil> listaPerfiles) throws Exception;
    
}
