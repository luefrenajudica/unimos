/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.ejb.servicios.administracion;

import java.util.List;
import co.com.systemtech.unimos.dominio.administracion.Usuario;
import co.com.systemtech.unimos.dominio.administracion.Login;
import co.com.systemtech.unimos.dominio.administracion.UsuarioRol;
import co.com.systemtech.unimos.ejb.entidades.GnEmpresas;
import co.com.systemtech.unimos.ejb.entidades.GnRolesUsuario;
import co.com.systemtech.unimos.ejb.entidades.GnUsuarios;
import co.com.systemtech.unimos.negocio.administracion.ConexionRemoto;
import co.com.systemtech.unimos.ejb.utilidades.Encrypt;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import co.com.systemtech.unimos.ejb.utilidades.GenericoServicio;
import java.util.ArrayList;

/**
 *
 * @author ripalacios
 */
@Stateless
@Remote(ConexionRemoto.class)
public class ConexionServicio extends GenericoServicio implements ConexionRemoto {

    @Override
    public boolean conectar(Login bean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean desconectar(Login bean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario validarConexion(Login obj) throws Exception {//(int empresa_id, String usuario, String contrasena) throws Exception {
        Usuario usu = null;
        try {
            String sql = "FROM GnUsuarios AS us "
                    + "INNER JOIN us.gnEmpresasId emp "
                    + "WHERE us.usuario = :usuario "
                    + "AND us.contrasena = :contrasena "
                    + "AND us.activo = :usuario_activo "
                    + "AND emp.activa = :empresa_activa ";
            Query query = getEntityManager().createQuery(sql)
                    .setParameter("usuario", obj.getUsuario().getUsuario())
                    .setParameter("contrasena", Encrypt.sha512(obj.getUsuario().getContrasena()))
                    .setParameter("usuario_activo", true)
                    .setParameter("empresa_activa", true);
            List<Object> list = query.getResultList();
            if (list != null && list.size() > 0) {
                GnUsuarios entidad = (GnUsuarios) ((Object[]) list.get(0))[0];
                usu = UsuarioServicio.castEntidadNegocio(entidad);                
                String sqlRoles = "FROM GnRolesUsuario ru "
                + "WHERE ru.gnUsuariosId.id = " + usu.getId();
                Query queryRoles = getEntityManager().createQuery(sqlRoles);
                List<UsuarioRol> listaRoles = new ArrayList();
                for(Object object : queryRoles.getResultList()){
                    GnRolesUsuario rolPer = (GnRolesUsuario)object;
                    UsuarioRol rolObj = new UsuarioRol();
                    rolObj.setId(rolPer.getId());
                    rolObj.setRol(RolServicio.castEntidadNegocio(rolPer.getGnRolesId()));
                    listaRoles.add(rolObj);
                }
                usu.setListaRoles(listaRoles);
                usu.setEmpresa(EmpresaServicio.castEntidadNegocio((GnEmpresas) ((Object[]) list.get(0))[1]));             
            } else {
                usu = null;
            }
        } catch (NoResultException e) {
            usu = null;
        } catch (Exception e) {
            Exception("Fallo al intentar el login del usuario", e);
        } finally {
            cerrarEntityManager();
        }
        return usu;
    }

}
