/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.utilidades;

import co.com.systemtech.unimos.negocio.administracion.ConfiguracionRemoto;
import java.util.HashMap;

/**
 *
 * @author raul-palacios
 */
public class PropApl {
    
    private ConfiguracionRemoto getConfiguracionRemoto() throws Exception {
        return (ConfiguracionRemoto) RemotoEJB.getEJBRemoto("ConfiguracionServicio", ConfiguracionRemoto.class.getName());
    }

    private static PropApl propAplInstance = null;
    private HashMap <Integer, String> hashConfiguraciones = new HashMap();
    
    public static final int GN_RUTA_MANUALES_AYUDA = 1;
    
    public static final int PC_RUTA_ADJUNTOS_CONTRATOS = 2;
    public static final int GP_ID_ESTADO_PERMITIDO_ACTUALIZAR = 3;
    
    public static final int GN_SMTP_BASE_AUTH_USER = 9;
    public static final int GN_SMTP_BASE_AUTH_PWD = 10;
    public static final int GN_SMTP_BASE_HOST_NAME = 11;
    public static final int GN_SMTP_BASE_HOST_PORT = 12;
    public static final int GN_SMTP_BASE_SECURITY = 13;
    
//    public static final int GN_SMTP_WEB_AUTH_USER = 14;
//    public static final int GN_SMTP_WEB_AUTH_PWD = 15;
//    public static final int GN_SMTP_WEB_HOST_NAME = 16;
//    public static final int GN_SMTP_WEB_HOST_PORT = 17;
//    public static final int GN_SMTP_WEB_SECURITY = 18;
    
    public static final int GN_SMS_REST_TIGO_URL = 19;
    public static final int GN_SMS_REST_TIGO_COUNTRY = 20;
    public static final int GN_SMS_REST_TIGO_USER = 21;
    public static final int GN_SMS_REST_TIGO_PWD = 22;
    public static final int GN_SMS_WS_TIGO_CONSUMO = 23;
    
    public static final int GP_RUTA_ADJUNTOS_GESTION = 35;

    public PropApl() {
        try {
            hashConfiguraciones = getConfiguracionRemoto().consultarHash();
        } catch (Exception ex) {
            hashConfiguraciones = new HashMap();
        }
    }

    public static PropApl getInstance() {
        if (propAplInstance == null) {
            propAplInstance = new PropApl();
        }
        return propAplInstance;
    }
    
    public static PropApl getInstanceLimpiar() {
        propAplInstance = new PropApl();
        return propAplInstance;
    }
    
    public String get(int id){
        return hashConfiguraciones.get(id);
    }

}
