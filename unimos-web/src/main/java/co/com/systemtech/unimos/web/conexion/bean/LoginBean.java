/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.conexion.bean;

import co.com.systemtech.unimos.dominio.administracion.GnEmpresa;
import co.com.systemtech.unimos.dominio.administracion.Login;
import co.com.systemtech.unimos.dominio.administracion.Modulo;
import co.com.systemtech.unimos.dominio.administracion.ModuloManual;
import co.com.systemtech.unimos.dominio.administracion.ModuloVersion;
import co.com.systemtech.unimos.web.utilidades.Conexion;
import co.com.systemtech.unimos.web.utilidades.Url;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;
import co.com.systemtech.unimos.web.administracion.servicio.ConexionServicioIface;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author raul-palacios
 */
@ManagedBean
@RequestScoped
public class LoginBean extends Url {

    private Login objeto = new Login();
    private ModuloVersion moduloVersion;
    private List<GnEmpresa> empresas = new ArrayList();
    boolean mostrarIcono;
    
    private ConexionServicioIface conexionServicio;
    
    @PostConstruct
    public void init() {
        try {
            mostrarIcono = true;
            moduloVersion = getConexionServicio().consultarVersion();
            ModuloManual moduloManual = getConexionServicio().consultarManual(1, ModuloManual.TIPO_MANUAL_INTERNO);
            if (moduloManual.getArchivo() == null) {
                mostrarIcono = false;
            }
        } catch (Exception e) {
            moduloVersion = null;
        }
    }
    
    public ModuloVersion getModuloVersion() {
        return moduloVersion;
    }
    
    public void setModuloVersion(ModuloVersion moduloVersion) {
        this.moduloVersion = moduloVersion;
    }
    
    public ConexionServicioIface getConexionServicio() {
        return conexionServicio;
    }
    
    public void setConexionServicio(ConexionServicioIface conexionServicio) {
        this.conexionServicio = conexionServicio;
    }
    
    public Login getObjeto() {
        return objeto;
    }
    
    public void setObjeto(Login objeto) {
        this.objeto = objeto;
    }
    
    public boolean isMostrarIcono() {
        return mostrarIcono;
    }
    
    public void setMostrarIcono(boolean mostrarIcono) {
        this.mostrarIcono = mostrarIcono;
    }
    
    public List<GnEmpresa> getEmpresasActivas() {
        List<GnEmpresa> list = new ArrayList();
        try {
            list = getConexionServicio().consultarEmpresasActivas();
        } catch (Exception ex) {
            addError("No fue posible cargar la lista de lista de Empresdas (" + ex.getMessage() + ")");
        }
        generarMensajes();
        return list;
    }
    
    public List<GnEmpresa> getEmpresas() {
        return empresas;
    }
    
    public void setEmpresas(List<GnEmpresa> empresas) {
        this.empresas = empresas;
    }
    
    public String validarConexion() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        if (getObjeto().getUsuario().getUsuario().equals("") || getObjeto().getUsuario().getContrasena().equals("")) {
            addError("Los campos Usuario y Contraseña son obligatorios");
            return "/login";
        } else {
            Login obj;
            try {
                obj = getConexionServicio().validaConexion(this.getObjeto());
                if (obj.isConectado()) {
                    Map<String, Object> sessionMap = externalContext.getSessionMap();
                    Conexion conexion = new Conexion();
                    //Carga de Menu Modelo
                    conexion.setMenuModel(construirArbol(getConexionServicio().consultarArbolModuloPorUsuario(obj.getUsuario())));
                    //Carga de lista de modulos
                    conexion.setModulos(getConexionServicio().consultarModulosPermiso(obj.getUsuario()));
                    //Carga de lista de empresas
                    if (obj.getUsuario().getEmpresa().isAdministradora()) {
                        conexion.setEmpresas(getConexionServicio().consultarEmpresasActivas());
                    } else {
                        List<GnEmpresa> list = new ArrayList();
                        list.add(obj.getUsuario().getEmpresa());
                        conexion.setEmpresas(list);
                    }
                    //Limpiar usuario y cargar
                    obj.getUsuario().setListaRoles(null);
                    conexion.setUsuario(obj.getUsuario());
                    obj.getEmpresa().setCiudad(null);
                    conexion.setEmpresa(obj.getEmpresa());
                    crearLogInicioSesion(conexion, "Inicio de Sesion");
                    sessionMap.put("conexion", conexion);
                    sessionMap.put("version", moduloVersion);
                    return "/home?faces-redirect=true";
                } else {
                    addError("Usuario y/o Contraseña inválido");
                    generarMensajes();
                    return null;
                }
            } catch (Exception ex) {
                addError(ex.getMessage());
                return null;
            }
        }
    }
    
    public StreamedContent generarManual() {
        StreamedContent streamedContent = null;
        try {
            ModuloManual moduloManual = getConexionServicio().consultarManual(1, ModuloManual.TIPO_MANUAL_INTERNO);
            File manual = new File(moduloManual.getRuta().concat(moduloManual.getArchivo()));
            InputStream stream = new FileInputStream(manual);
            stream.mark(0); //remember to this position!
            streamedContent = DefaultStreamedContent.builder().stream(()-> stream).contentType("application/pdf").name(moduloManual.getArchivo()).build();            
        } catch (Exception ex) {
            streamedContent = null;
            this.addError("Error al descargar el manual: " + ex.toString() + ex.getMessage());
            generarMensajes();
        }
        return streamedContent;
    }
    
    public void restaurarEditar() {
        PrimeFaces.current().ajax().update("frmEditar:panelEditar");
        PrimeFaces.current().executeScript("PF('dialogoEditar').show()");
    }

    /**
     * Metodo para enviar email
     */
    public void restaurarModificar() {
        String ip = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
        try {
            getConexionServicio().restaurarContrasena(getObjeto());
            addMensaje("La contraseña del usuario ha sido enviado a su correo electronico");
            PrimeFaces.current().executeScript("PF('dialogoEditar').hide();");
            crearLog("Restauración de Contraseña", "El usuario '" + getObjeto().getUsuario() + "' reseteo la contraseña de manera exitosa desde la IP '" + ip + "'.");
        } catch (Exception ex) {
            addError("Error intentando restaurar contraseña: " + ex.getMessage());
            crearLog("Restauración de Contraseña", "El usuario '" + getObjeto().getUsuario() + "' intento resetear la contraseña de manera infructuosa desde la IP '" + ip + "'.");
        }
        super.generarMensajes();
    }

    /**
     * Método para realizar la construcción de un argol de menú tipo MenuModel
     *
     * @param _modPadre (Modulo) objeto cargado con todas sus ramificaciones
     * @return (MenuModel) con el arbol cargado
     */
    private MenuModel construirArbol(Modulo _modPadre) {
        MenuModel modelMenu = new DefaultMenuModel();
        try {
            for (Modulo modObj : _modPadre.getModulosHijos()) {
                switch (modObj.getTipo()) {
                    case Modulo.TIPO_APLICACION:
                        modelMenu.getElements().add(subMenu(modObj));
                        break;
                    case Modulo.TIPO_MODULO:
                        modelMenu.getElements().add(subMenu(modObj));
                        break;
                    case Modulo.TIPO_OPCION:
                        modelMenu.getElements().add(item(modObj));
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception ex) {
            addError(ex.getMessage());
        }
        return modelMenu;
    }

    /**
     * Método auxiliar de arbol para generar un sub menú y sus items
     *
     * @param _modPadre
     * @return
     */
    private DefaultSubMenu subMenu(Modulo _modPadre) {
        DefaultSubMenu subMenu = new DefaultSubMenu(); // Cria o submenu
        subMenu.setId("submenu_" + String.valueOf(_modPadre.getId()));
        subMenu.setLabel(_modPadre.getNombre());
        List<MenuElement> listElements = new ArrayList<>();
        for (Modulo modObj : _modPadre.getModulosHijos()) {
            if (modObj.getTipo() == Modulo.TIPO_MODULO) {
                listElements.add(subMenu(modObj));
            } else if (modObj.getTipo() == Modulo.TIPO_OPCION) {
                listElements.add(item(modObj));
            }
        }
        if (!listElements.isEmpty()) {
            subMenu.setElements(listElements);
        }
        return subMenu;
    }

    /**
     * Método auxiliar de arbol para generar in item
     *
     * @param _mod
     * @return
     */
 private DefaultMenuItem item(Modulo _mod) {
        DefaultMenuItem item = new DefaultMenuItem();
        item.setId("item_" + String.valueOf(_mod.getId()));
        item.setValue(_mod.getNombre());
        item.setTitle(_mod.getDescripcion());
        item.setOnclick("sessionStorage.setItem('Modulo','" + _mod.getId() + "');");
        if (_mod.getIcono() != null && !_mod.getIcono().equals("")) {
            item.setIcon(_mod.getIcono());
        }
        item.setAjax(false);
        if (_mod.getUrl() != null && !_mod.getUrl().equals("")) {
            item.setHref("/unimos" + _mod.getUrl());
        }
        return item;
    }

}
