/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.administracion.servicio;

import co.com.systemtech.unimos.dominio.administracion.GnEmpresa;
import co.com.systemtech.unimos.dominio.administracion.Login;
import co.com.systemtech.unimos.dominio.administracion.Modulo;
import co.com.systemtech.unimos.dominio.administracion.ModuloManual;
import co.com.systemtech.unimos.dominio.administracion.ModuloVersion;
import co.com.systemtech.unimos.dominio.administracion.Usuario;
import co.com.systemtech.unimos.web.conexion.bean.ContrasenaBean;
import java.util.List;

/**
 *
 * @author raul-palacios
 */
public interface ConexionServicioIface {
    
    /**
     * 
     * @param obj
     * @return
     * @throws Exception 
     */
    Login validaConexion(Login obj)throws Exception;
    
    /**
     * 
     * @return 
     * @throws java.lang.Exception 
     */
    List<GnEmpresa> consultarEmpresasActivas()throws Exception;
    
    /**
     * 
     * @param obj
     * @return 
     */
    List<Modulo> consultarModulosPermiso(Usuario obj);
    
    /**
     * 
     * @param obj
     * @return 
     */
    Modulo consultarArbolModuloPorUsuario(Usuario obj);
    
    /**
     * 
     * @param empresa
     * @return 
     */
    GnEmpresa cambiarEmpresa(GnEmpresa empresa);
    
    /**
     * 
     * @param bean 
     */
    void cambiarContrasena(ContrasenaBean bean);
    
    /**
     * 
     * @param obj
     * @throws Exception 
     */
    void restaurarContrasena(Login obj) throws Exception;
    
    /**
     * Método para consultar la versión actual del sistema
     * @return
     * @throws Exception 
     */
    ModuloVersion consultarVersion() throws Exception;
    
    /**
     * Método para consultar el manual del sistema
     * @param id
     * @param tipo
     * @return
     * @throws Exception 
     */
    ModuloManual consultarManual(int id, int tipo) throws Exception;
    
}
