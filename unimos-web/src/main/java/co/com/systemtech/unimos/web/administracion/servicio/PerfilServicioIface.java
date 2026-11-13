/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.administracion.servicio;

import co.com.systemtech.unimos.web.administracion.bean.PerfilBean;
import co.com.systemtech.unimos.dominio.administracion.Perfil;
import java.util.List;

/**
 *
 * @author raul-palacios
 */
public interface PerfilServicioIface {

    void Accion(PerfilBean bean);

    List<Perfil> consultarLista();

    void consultarListaPermisos(PerfilBean bean);
}
