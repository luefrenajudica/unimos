/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.negocio.administracion;

import co.com.systemtech.unimos.dominio.administracion.GnDiaHabil;
import co.com.systemtech.unimos.dominio.generico.ParamConsulta;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rpalacic
 */
public interface CalendarioRemoto {
    
    /**
     * Método para consultar todos los registros
     * @param paramConsulta
     * @return 
     * @throws java.lang.Exception 
     */
    public List<GnDiaHabil> consultarTodos(ParamConsulta paramConsulta) throws Exception;
    public GnDiaHabil consultarDia(Date fecha) throws Exception;
    
    /**
     * Método para crear una nuevo registro
     * @param obj (DiasHabiles)
     * @return (int) id del registro generado
     * @throws java.lang.Exception
     */
    public boolean actualizar(GnDiaHabil obj) throws Exception;
}
