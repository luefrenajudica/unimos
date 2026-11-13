/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.negocio.administracion;

import co.com.systemtech.unimos.dominio.administracion.GnMaestroAccionRelacion;
import java.util.List;

/**
 *
 * @author sgiraldov
 */
public interface MaestroAccionRelacionRemoto {
    
    /**
     * Inserta la relacion entre maestro y maestro accion
     * @param obj
     * @return
     * @throws Exception 
     */
    int insertar(GnMaestroAccionRelacion obj) throws Exception;
    
    /**
     * Lista las acciones que tiene un maestro
     * @param id
     * @return
     * @throws Exception 
     */
    List<GnMaestroAccionRelacion> consultarPorMaestroId(int id) throws Exception;
    
    /**
     * Elimina la relacion
     * @param id
     * @return
     * @throws Exception 
     */
    GnMaestroAccionRelacion eliminar(int id) throws Exception;
    
}
