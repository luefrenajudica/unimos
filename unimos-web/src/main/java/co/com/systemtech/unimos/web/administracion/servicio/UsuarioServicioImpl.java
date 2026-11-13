/*
 * To change this license header, choose License Headers in Project Proobjties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.administracion.servicio;

import co.com.systemtech.unimos.dominio.administracion.Rol;
import co.com.systemtech.unimos.web.administracion.bean.UsuarioBean;
import co.com.systemtech.unimos.dominio.administracion.Usuario;
import co.com.systemtech.unimos.dominio.administracion.UsuarioRol;
import co.com.systemtech.unimos.negocio.administracion.PerfilRemoto;
import co.com.systemtech.unimos.negocio.administracion.RolRemoto;
import co.com.systemtech.unimos.negocio.administracion.UsuarioRemoto;
import co.com.systemtech.unimos.web.utilidades.AccionesBO;
import co.com.systemtech.unimos.web.utilidades.Correo;
import co.com.systemtech.unimos.web.utilidades.GeneradorContrasena;
import co.com.systemtech.unimos.web.utilidades.RemotoEJB;
import co.com.systemtech.unimos.web.utilidades.Url;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author raul-palacios
 */
public class UsuarioServicioImpl extends AccionesBO implements UsuarioServicioIface {

    private UsuarioRemoto getUsuarioRemoto() throws Exception {
        return (UsuarioRemoto) RemotoEJB.getEJBRemoto("UsuarioServicio", UsuarioRemoto.class.getName());
    }

    private PerfilRemoto getPerfilRemoto() throws Exception {
        return (PerfilRemoto) RemotoEJB.getEJBRemoto("PerfilServicio", PerfilRemoto.class.getName());
    }

    private RolRemoto getRolRemoto() throws Exception {
        return (RolRemoto) RemotoEJB.getEJBRemoto("RolServicio", RolRemoto.class.getName());
    }

    @Override
    public void Accion(UsuarioBean bean) {
        if (super.ValidarSesion(bean)) {
            bean.getObjeto().setEmpresa(bean.getConexion().getEmpresa());
            switch (bean.getAccion()) {
                case Url.ACCION_LISTAR:
                    listar(bean);
                    break;
                case Url.ACCION_VER:
                    ver(bean);
                    break;
                case Url.ACCION_CREAR:
                    crear(bean);
                    break;
                case Url.ACCION_GUARDAR:
                    guardar(bean);
                    break;
                case Url.ACCION_EDITAR:
                    editar(bean);
                    break;
                case Url.ACCION_MODIFICAR:
                    modificar(bean);
                    break;
                case Url.ACCION_BORRAR:
                    borrar(bean);
                    break;
                case Url.ACCION_ADICIONAL_1:
                    restaurarContrasena(bean);
                    break;
                case Url.ACCION_ADICIONAL_2:
                    break;
                case Url.ACCION_ADICIONAL_3:
                    break;
                default:
                    break;
            }
            cargas(bean);
        } else {
            System.err.println("Sesion inactiva");
        }
    }

    private void listar(UsuarioBean bean) {
        try {
            bean.getParamConsulta().setCantidadRegistros(getUsuarioRemoto().consultarCantidadLista(bean.getParamConsulta()));
            bean.setRegistros(getUsuarioRemoto().consultarLista(bean.getParamConsulta()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void ver(UsuarioBean bean) {
        try {
            bean.setObjeto(getUsuarioRemoto().consultar(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void crear(UsuarioBean bean) {
        try {
            bean.setObjeto(new Usuario());
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void editar(UsuarioBean bean) {
        try {
            bean.setObjeto(getUsuarioRemoto().consultar(bean.getObjeto().getId()));
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void guardar(UsuarioBean bean) {
        try {
            bean.getObjeto().setContrasena(GeneradorContrasena.getPassword());
            bean.getObjeto().setFechaInicio(new Date());
            bean.auditoriaGuardar(bean.getObjeto());
            //carga de lista de roles
            List<UsuarioRol> usuarioRoles = new ArrayList<>();
            for (Integer rolSel : bean.getSeleccionRoles()) {
                UsuarioRol perfil = new UsuarioRol(new Rol(rolSel));
                usuarioRoles.add(perfil);
            }
            bean.getObjeto().setListaRoles(usuarioRoles);
            //Insertar dato
            bean.getObjeto().setId(getUsuarioRemoto().insertar(bean.getObjeto()));
            
            bean.addMensaje("Se creo un registro de manera exitosa. "
                    + "En los póximos minutos el usuario recibirá un correo "
                    + "con los datos de acceso al sistema");
            //Enviar correo electrónico
            enviarCorreoInsert(bean);
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void modificar(UsuarioBean bean) {
        try {
            bean.auditoriaModificar(bean.getObjeto());
            //carga de lista de roles
            List<UsuarioRol> usuarioRoles = new ArrayList<>();
            for (Integer rolSel : bean.getSeleccionRoles()) {
                UsuarioRol perfil = new UsuarioRol(new Rol(rolSel));
                usuarioRoles.add(perfil);
            }
            bean.getObjeto().setListaRoles(usuarioRoles);
            //Actualizar dato
            getUsuarioRemoto().actualizar(bean.getObjeto());
            bean.addMensaje("Se actualizó un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void borrar(UsuarioBean bean) {
        try {
            bean.setObjeto(getUsuarioRemoto().eliminar(bean.getObjeto().getId()));
            bean.addMensaje("Se eliminó un registro de manera exitosa");
        } catch (Exception e) {
            bean.addError("Hubo un error eliminando el usuario, posiblemente tiene dependencia con otro modulo");
        }
    }

    private void restaurarContrasena(UsuarioBean bean) {
        try {
            Usuario obj = getUsuarioRemoto().consultar(bean.getObjeto().getId());
            obj.setContrasena(GeneradorContrasena.getPassword());
            bean.setObjeto(obj);
            getUsuarioRemoto().cambioContrasena(bean.getObjeto());
            enviarCorreoReset(bean);
            bean.addMensaje("Se restauro la contraseña de manera exitosa. "
                    + "En los próximos minutos, el usuario recibirá un correo "
                    + "con su nueva contraseña");
        } catch (Exception e) {
            bean.addError(e.getMessage());
        }
    }

    private void cargas(UsuarioBean bean) {
        try {
            switch (bean.getAccion()) {
                case Url.ACCION_LISTAR:
                    bean.setListaRoles(getRolRemoto().consultarTodos());
                    break;
                case Url.ACCION_VER:
                    break;
                case Url.ACCION_CREAR:
                case Url.ACCION_EDITAR:
                    bean.setListaRoles(getRolRemoto().consultarTodos());
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void cargasInicial(UsuarioBean bean) {
        try {
            bean.setListaRoles(getRolRemoto().consultarTodos());
        } catch (Exception e) {
            bean.addError("No fue posible cargar las listas de apoyo");
        }
    }

    private void enviarCorreoInsert(UsuarioBean bean) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String RequestURI = request.getRequestURI();
        String requiestURL = String.valueOf(request.getRequestURL());
        String ContextPath = request.getContextPath();
        String url = requiestURL.replace(RequestURI, "") + ContextPath + "/";
        String correo = bean.getObjeto().getCorreoElectronico();
        String encabezado = "SystemTech: Creación de nuevo Usuario";
        String mensaje = "Sr(a) " + bean.getObjeto().getNombre() + "."
                + " El sistema SystemTech ha detectado la creación de un nuevo"
                + " Usuario, el cual tiene asignado este correo electrónico."
                + " Sus datos son. Usuario: " + "'" + bean.getObjeto().getUsuario() + "'"
                + " y Contraseña: " + "'" + bean.getObjeto().getContrasena() + "'."
                + " Para acceder al sistema lo puede hacer por la siguiente dirección '" + url + "'";
        //Se arranca el hilo, para que comiencen su ejecución
        new Thread(new Correo(correo, encabezado, mensaje)).start();
    }

    private void generarCorreoReset(UsuarioBean bean) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String RequestURI = request.getRequestURI();
        String requiestURL = String.valueOf(request.getRequestURL());
        String ContextPath = request.getContextPath();
        String url = requiestURL.replace(RequestURI, "") + ContextPath + "/";
//        String correo = bean.getObjeto().getCorreoElectronico();
        String encabezado = "SystemTech: Restauración contraseña";
        String mensaje = "Sr(a) " + bean.getObjeto().getNombre() + ".'"
                + " El administrador del sistema SystemTech ha restaurado la contraseña"
                + " para el usuario " + "'" + bean.getObjeto().getUsuario() + "'. "
                + " Su nueva contraseña es " + "'" + bean.getObjeto().getContrasena() + "'" + "."
                + " Le recomendamos cambiar la contraseña en su próximo ingreso al sistema."
                + " Para acceder al sistema lo puede hacer por la dirección '" + url + "'";
        //Se arranca el hilo, para que comiencen su ejecución
//        new Thread(new Correo(correo, encabezado, mensaje)).start();
        bean.getObjeto().setEncabezado(encabezado);
        bean.getObjeto().setMensaje(mensaje);
    }

    private void enviarCorreoReset(UsuarioBean bean) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String RequestURI = request.getRequestURI();
        String requiestURL = String.valueOf(request.getRequestURL());
        String ContextPath = request.getContextPath();
        String url = requiestURL.replace(RequestURI, "") + ContextPath + "/";
        String correo = bean.getObjeto().getCorreoElectronico();
        String encabezado = "SystemTech: Restauración contraseña";
        String mensaje = "Sr(a) " + bean.getObjeto().getNombre() + ".'\n\n"
                + " El administrador del sistema SystemTech ha restaurado la contraseña"
                + " para el usuario " + "'" + bean.getObjeto().getUsuario() + "'.\n\n "
                + " La nueva contraseña para dicho usuario es " + "'" + bean.getObjeto().getContrasena() + "'" + ".\n\n"
                + " Le recomendamos cambiar la contraseña en su próximo ingreso al sistema.\n\n"
                + " Para acceder al sistema lo puede hacer por la dirección '" + url + "'";
        //Se arranca el hilo, para que comiencen su ejecución
        new Thread(new Correo(correo, encabezado, mensaje)).start();
    }

    public List<Usuario> consultarPorEmpresa(int empresaId) {
        List<Usuario> _lista = new ArrayList();
        try {
            _lista = getUsuarioRemoto().consultarPorEmpresa(empresaId);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return _lista;
    }

}
