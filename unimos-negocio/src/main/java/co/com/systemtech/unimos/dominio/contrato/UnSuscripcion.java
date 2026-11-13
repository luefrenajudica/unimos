/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.dominio.contrato;

import co.com.systemtech.unimos.dominio.generico.Auditoria;
import co.com.systemtech.unimos.dominio.registro.Sede;
import co.com.systemtech.unimos.dominio.registro.UnComercial;
import co.com.systemtech.unimos.dominio.registro.UnPersona;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class UnSuscripcion extends Auditoria {

    private Integer id;
    private Sede sede;
    private String maeTipoTipo;
    private int maeTipoId;
    private String maeTipoCodigo;
    private String maeTipoValor;
    private String maePlanTipo;
    private int maePlanId;
    private String maePlanCodigo;
    private String maePlanValor;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaInicioMinima;
    private Double valor;
    private Double valorPagado;
    private Double valorPendiente;
    private UnPersona unPersona;
    private UnComercial unComercial;
    private boolean personaAsignada;
    private List<UnSuscripcionPersona> listaSuscripcionPersonas;
    private List<UnSuscripcionCartera> listaSuscripcionCarteras;
    private List<UnSuscripcionPago> listaSuscripcionPagos;

    private boolean titular;

    public UnSuscripcion() {
    }

    public UnSuscripcion(Integer id) {
        this.id = id;
    }

    public UnSuscripcion(Integer id, String maeTipoTipo, int maeTipoId, String maeTipoCodigo, String maeTipoValor, String maePlanTipo, int maePlanId, String maePlanCodigo, String maePlanValor, Date fechaInicio, Date fechaFin, Double valor) {
        this.id = id;
        this.maeTipoTipo = maeTipoTipo;
        this.maeTipoId = maeTipoId;
        this.maeTipoCodigo = maeTipoCodigo;
        this.maeTipoValor = maeTipoValor;
        this.maePlanTipo = maePlanTipo;
        this.maePlanId = maePlanId;
        this.maePlanCodigo = maePlanCodigo;
        this.maePlanValor = maePlanValor;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public String getMaeTipoTipo() {
        return maeTipoTipo;
    }

    public void setMaeTipoTipo(String maeTipoTipo) {
        this.maeTipoTipo = maeTipoTipo;
    }

    public int getMaeTipoId() {
        return maeTipoId;
    }

    public void setMaeTipoId(int maeTipoId) {
        this.maeTipoId = maeTipoId;
    }

    public String getMaeTipoCodigo() {
        return maeTipoCodigo;
    }

    public void setMaeTipoCodigo(String maeTipoCodigo) {
        this.maeTipoCodigo = maeTipoCodigo;
    }

    public String getMaeTipoValor() {
        return maeTipoValor;
    }

    public void setMaeTipoValor(String maeTipoValor) {
        this.maeTipoValor = maeTipoValor;
    }

    public String getMaePlanTipo() {
        return maePlanTipo;
    }

    public void setMaePlanTipo(String maePlanTipo) {
        this.maePlanTipo = maePlanTipo;
    }

    public int getMaePlanId() {
        return maePlanId;
    }

    public void setMaePlanId(int maePlanId) {
        this.maePlanId = maePlanId;
    }

    public String getMaePlanCodigo() {
        return maePlanCodigo;
    }

    public void setMaePlanCodigo(String maePlanCodigo) {
        this.maePlanCodigo = maePlanCodigo;
    }

    public String getMaePlanValor() {
        return maePlanValor;
    }

    public void setMaePlanValor(String maePlanValor) {
        this.maePlanValor = maePlanValor;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaInicioMinima() {
//        if(fechaInicioMinima == null){
//            LocalDate manana = LocalDate.now().plusDays(1);
//        LocalDate manana = LocalDate.now().plusMonths(-6);
        LocalDate manana = LocalDate.now().plusMonths(-60);
        fechaInicioMinima = Date.from(manana.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        }
        return fechaInicioMinima;
    }

    public void setFechaInicioMinima(Date fechaInicioMinima) {

        this.fechaInicioMinima = fechaInicioMinima;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(Double valorPagado) {
        this.valorPagado = valorPagado;
    }

    public Double getValorPendiente() {
        return valorPendiente;
    }

    public void setValorPendiente(Double valorPendiente) {
        this.valorPendiente = valorPendiente;
    }

    public UnPersona getUnPersona() {
        return unPersona;
    }

    public void setUnPersona(UnPersona unPersona) {
        this.unPersona = unPersona;
    }

    public UnComercial getUnComercial() {
        return unComercial;
    }

    public void setUnComercial(UnComercial unComercial) {
        this.unComercial = unComercial;
    }

    public boolean isPersonaAsignada() {
        return personaAsignada;
    }

    public void setPersonaAsignada(boolean personaAsignada) {
        this.personaAsignada = personaAsignada;
    }

    public List<UnSuscripcionPersona> getListaSuscripcionPersonas() {
        return listaSuscripcionPersonas;
    }

    public void setListaSuscripcionPersonas(List<UnSuscripcionPersona> listaSuscripcionPersonas) {
        this.listaSuscripcionPersonas = listaSuscripcionPersonas;
    }

    public UnSuscripcionPersona getSuscripcionPersona(int pos) {
        if (listaSuscripcionPersonas != null && !listaSuscripcionPersonas.isEmpty()) {
            return listaSuscripcionPersonas.get(pos);
        } else {
            return null;
        }
    }

    public void addSuscripcionPersona(UnSuscripcionPersona obj) {
        if (listaSuscripcionPersonas == null) {
            listaSuscripcionPersonas = new ArrayList();
        }
        this.listaSuscripcionPersonas.add(obj);
    }

    public List<UnSuscripcionCartera> getListaSuscripcionCarteras() {
        return listaSuscripcionCarteras;
    }

    public void setListaSuscripcionCarteras(List<UnSuscripcionCartera> listaSuscripcionCarteras) {
        this.listaSuscripcionCarteras = listaSuscripcionCarteras;
    }

    public List<UnSuscripcionPago> getListaSuscripcionPagos() {
        return listaSuscripcionPagos;
    }

    public void setListaSuscripcionPagos(List<UnSuscripcionPago> listaSuscripcionPagos) {
        this.listaSuscripcionPagos = listaSuscripcionPagos;
    }

    public boolean isTitular() {
        return titular;
    }

    public String getTitularStr() {
        return (titular) ? "SI" : "NO";
    }

    public void setTitular(boolean titular) {
        this.titular = titular;
    }

    @Override
    public String toString() {
        return "UnSuscripcion{" + "id=" + id + ", sede=" + sede + ", maeTipoTipo=" + maeTipoTipo + ", maeTipoId=" + maeTipoId + ", maeTipoCodigo=" + maeTipoCodigo + ", maeTipoValor=" + maeTipoValor + ", maePlanTipo=" + maePlanTipo + ", maePlanId=" + maePlanId + ", maePlanCodigo=" + maePlanCodigo + ", maePlanValor=" + maePlanValor + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", fechaInicioMinima=" + fechaInicioMinima + ", valor=" + valor + ", valorPagado=" + valorPagado + ", valorPendiente=" + valorPendiente + ", unPersona=" + unPersona + ", unComercial=" + unComercial + ", personaAsignada=" + personaAsignada + ", listaSuscripcionPersonas=" + listaSuscripcionPersonas + '}';
    }

}
