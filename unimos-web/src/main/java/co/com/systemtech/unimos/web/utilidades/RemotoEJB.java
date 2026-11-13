/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.utilidades;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author rpalacic (SystemTech Integral)
 */
public class RemotoEJB {

    public static Object getEJBRemoto(String nameEjb, String iface) throws Exception {
        Object object = null;
        try {
            Properties props = new Properties();
            props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");            
//            props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
//            props.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:7070");
//            props.put(Context.SECURITY_PRINCIPAL, "unimos");
//            props.put(Context.SECURITY_CREDENTIALS, "unimos");
//            props.put("jboss.naming.client.ejb.context", true);
            Context context = new InitialContext(props);
            String preContext = "ejb:unimos-base-ear/unimos-base-ejb-1.0-SNAPSHOT//";
            String nombre = preContext + nameEjb + "!" + iface;
            object = context.lookup(nombre);
        } catch (NamingException ex) {
            throw new Exception("It was not possible to connect to the remote EJB " + nameEjb);        
        } catch (Exception ex) {
            throw new Exception("It was not possible to connect to the remote EJB " + nameEjb);
        }
        return object;
    }
    
}
