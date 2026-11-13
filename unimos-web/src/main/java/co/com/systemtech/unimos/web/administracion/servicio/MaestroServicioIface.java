/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.administracion.servicio;

import co.com.systemtech.unimos.dominio.administracion.GnMaestro;
import co.com.systemtech.unimos.web.administracion.bean.MaestroBean;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroAccion;
import co.com.systemtech.unimos.dominio.administracion.GnMaestroTipo;
import java.util.List;

/**
 *
 * @author rpalacic (SystemTech Integral)
 */
public interface MaestroServicioIface {

    void Accion(MaestroBean bean);
    
    /**
     * Carga inicial de la opción
     * @param bean 
     */
    void cargasInicial(MaestroBean bean);
    
    /**
     * Consultar MaestroTipo padre por tipo hijo
     * @param tipo
     * @return 
     */
    List<GnMaestroTipo> consultarDependenciaPadre(String tipo);
    
    /**
     * Consulta lista de depéndencias
     * @param tipo
     * @return 
     */
    List<GnMaestro> listaDependencias(String tipo);
    
    /**
     * Consulta de liosta de Acciones
     * @param tipo
     * @return 
     */
    List<GnMaestroAccion> listaAcciones(String tipo);

//    /**
//     * Método para consultar acciones por tipo de maestro
//     * @param tipo
//     * @return 
//     */
//    List<MaestroAccion> consultarAcciones(String tipo);    
//    
//    /**
//     * Método para consultar lista de dependencias por maestro 
//     * @param tipoDependencia
//     * @return  
//     */
//    List<Maestro> ListarDependencias(String tipoDependencia);
    
}
