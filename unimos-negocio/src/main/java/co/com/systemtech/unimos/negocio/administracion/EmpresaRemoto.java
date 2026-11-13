/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.negocio.administracion;

import co.com.systemtech.unimos.dominio.administracion.GnEmpresa;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author rpalacic
 */
@Remote
public interface EmpresaRemoto {
    
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
    List<GnEmpresa> consultarLista(ParamConsulta paramConsulta) throws Exception;
    
    /**
     * Método para consultar una empresa por ID
     * @param id
     * @return (Empresas) objeto consultado
     * @throws java.lang.Exception 
     */
    GnEmpresa consultar(int id)throws Exception;
    
    /**
     * Método para crear una nueva Empresa
     * @param obj (Empresas)
     * @return (int) id del registro generado
     * @throws java.lang.Exception
     */
    int insertar(GnEmpresa obj)throws Exception;
    
    /**
     * Método para actualizar la información de una Empresa
     * @param obj (Empresas) Objeto actualizado
     * @throws java.lang.Exception
     */
    void actualizar(GnEmpresa obj)throws Exception;
    
    /**
     * Método para eliminar una Empresa
     * @param id
     * @return (Empresa) objeto eliminado
     * @throws java.lang.Exception
     */
    GnEmpresa eliminar(int id)throws Exception;
    
    /**
     * Método para consultar todas las Empresas activas.
     * @return (List) objetos consultados
     * @throws java.lang.Exception 
     */
    List<GnEmpresa> consultarActivas()throws Exception;
        
}
