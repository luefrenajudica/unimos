/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.utilidades;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author raul-palacios
 */
public class AccionesBO {
    
    public final static char ACCION_LISTAR = 'L';
    public final static char ACCION_VER = 'V';
    public final static char ACCION_CREAR = 'C';
    public final static char ACCION_EDITAR = 'E';
    public final static char ACCION_BORRAR = 'B';
    public final static char ACCION_GUARDAR = 'G';
    public final static char ACCION_MODIFICAR = 'M';
    
    public final static char ACCION_ADICIONAL_1 = 'X';
    public final static char ACCION_ADICIONAL_2 = 'Y';
    public final static char ACCION_ADICIONAL_3 = 'Z';
    
    /**
     * Metodo para realizar la validación de la sesion actual y los permisos del
     * usuario de la occion a ejecutar sobre la opcion seleccionada 
     * @param url
     * @return (boolena) indica si la validacion fue exitosa o no
     */
    protected boolean ValidarSesion(Url url){
        boolean validado = true;
        switch(url.getAccion()){
                case Url.ACCION_LISTAR:
                    if(!url.isAccionListar()){
                        validado = false;
                    }
                    break;
                case Url.ACCION_VER:
                    if(!url.isAccionVer()){
                        validado = false;
                    }
                    break;
                case Url.ACCION_CREAR:
                    if(!url.isAccionCrear()){
                        validado = false;
                    }
                    break;
                case Url.ACCION_GUARDAR:
                    if(!url.isAccionGuardar()){
                        validado = false;
                    }
                    break;
                case Url.ACCION_EDITAR:
                    if(!url.isAccionEditar()){
                        validado = false;
                    }
                    break;
                case Url.ACCION_MODIFICAR:
                    if(!url.isAccionModificar()){
                        validado = false;
                    }
                    break;
                case Url.ACCION_BORRAR:
                    if(!url.isAccionBorrar()){
                        validado = false;
                    }
                    break;
                case Url.ACCION_ADICIONAL_1:
                    if(!url.isAccionAdicional1()){
                        validado = false;
                    }
                    break;
                case Url.ACCION_ADICIONAL_2:
                    if(!url.isAccionAdicional2()){
                        validado = false;
                    } 
                    break;
                case Url.ACCION_ADICIONAL_3:
                    if(!url.isAccionAdicional3()){
                        validado = false;
                    } 
                    break;
                case Url.ACCION_ADICIONAL_4:
                    if(!url.isAccionAdicional4()){
                        validado = false;
                    } 
                    break;
                case Url.ACCION_ADICIONAL_5:
                    if(!url.isAccionAdicional5()){
                        validado = false;
                    } 
                    break;
                default:
                    break;
            }
        if(!validado){
            try{
                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "INTENTO DE VIOLACIÓN DE SEGURIDAD DEL SISTEMA", "Intento ejecutar una acción para la que no tiene permisos"));
                String _ruta = request.getContextPath();
                FacesContext.getCurrentInstance().getExternalContext().redirect(_ruta+"/");
            }catch(Exception ex){}
        }
        return validado;
    }
    
}
