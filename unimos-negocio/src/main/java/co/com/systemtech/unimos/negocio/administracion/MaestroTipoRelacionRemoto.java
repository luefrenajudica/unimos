/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.negocio.administracion;

import co.com.systemtech.unimos.dominio.administracion.GnMaestroTipoRelacion;
import java.util.List;

/**
 *
 * @author sgiraldov
 */
public interface MaestroTipoRelacionRemoto {
    
    /**
     * Método para crear una nueva Relacion entre tablas tipomaestro
     *
     * @param obj (GnMaestroTipoRelaciones)
     * @return (int) id del registro generado
     * @throws java.lang.Exception
     */
    int insertar(GnMaestroTipoRelacion obj) throws Exception;
    
    /**
     * Método para consultar los registros activos
     * @param tipo
     * @return
     * @throws Exception
     */
    List<GnMaestroTipoRelacion> consultarPadresPorTipoHijo(String tipo)throws Exception;
    
    /**
     * Método para eliminar un GnMaestroTipoRelacion
     * @param id
     * @return (Empresa) objeto eliminado
     * @throws java.lang.Exception
     */
    GnMaestroTipoRelacion eliminar(int id)throws Exception;
    
}
