/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.dominio.generico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;


/**
 *
 * @author ripalacios
 */
public class Auditoria implements Serializable {

    private String usuarioCrea;
    private String terminalCrea;
    private Date fechaHoraCrea;

    private String usuarioModifica;
    private String terminalModifica;
    private Date fechaHoraModifica;

    private List<String> messages = new ArrayList();
    private List<String> errors = new ArrayList();

    public String getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(String usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    public String getTerminalCrea() {
        return terminalCrea;
    }

    public void setTerminalCrea(String terminalCrea) {
        this.terminalCrea = terminalCrea;
    }

    public Date getFechaHoraCrea() {
        return fechaHoraCrea;
    }

    public void setFechaHoraCrea(Date fechaHoraCrea) {
        this.fechaHoraCrea = fechaHoraCrea;
    }

    public String getUsuarioModifica() {
        return usuarioModifica;
    }

    public void setUsuarioModifica(String usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }

    public String getTerminalModifica() {
        return terminalModifica;
    }

    public void setTerminalModifica(String terminalModifica) {
        this.terminalModifica = terminalModifica;
    }

    public Date getFechaHoraModifica() {
        return fechaHoraModifica;
    }

    public void setFechaHoraModifica(Date fechaHoraModifica) {
        this.fechaHoraModifica = fechaHoraModifica;
    }

    public List<String> getMenssages() {
        return messages;
    }

    public void addMenssge(String mensaje) {
        this.messages.add(mensaje);
    }

    public List<String> getErrors() {
        return errors;
    }

    public void addError(String error) {
        this.errors.add(error);
    }

    public boolean isError() {
        return (!this.errors.isEmpty());
    }

    //Verificar si aplica salto de linea pendiente******
    public String getSaltoLinea(String palabra) {
        String strCss = " ";
        if (palabra != null) {
            if (!(palabra.contains(" "))) {
                strCss = "word-break:break-all";
            }
        }
        return strCss;
    }

    public String getAuditoriaCrea() {
        return "USUARIO: " + usuarioCrea + " - FECHA HORA: " + fechaHoraCrea;
    }

    public String getAuditoriaModifica() {
        if (!"".equals(this.getUsuarioModifica()) && this.getUsuarioModifica() != null) {
            return "USUARIO: " + usuarioModifica + " - FECHA HORA: " + fechaHoraModifica;
        }
        return "";
    }

    public String getAuditoriaStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String auditoria = "";
        if (fechaHoraCrea != null) {
            auditoria += "CREACIÓN: Fecha Hora: " + sdf.format(fechaHoraCrea) + " \n Usuario: " + usuarioCrea;
        }
        if (fechaHoraModifica != null) {
            auditoria += "\n";
            auditoria += "ÚLTIMA EDICIÓN: Fecha Hora: " + sdf.format(fechaHoraModifica) + " \n Usuario: " + usuarioModifica;
        }
        return auditoria;
    }

    public String getAuditoriaStrHTML() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String auditoria = "";
        if (fechaHoraCrea != null) {
            auditoria += sdf.format(fechaHoraCrea) + "<br>" + usuarioCrea;
        }
        if (fechaHoraModifica != null) {
            auditoria += "&&";
            auditoria += sdf.format(fechaHoraModifica) + "<br>" + usuarioModifica;
        }
        return auditoria;
    }

}
