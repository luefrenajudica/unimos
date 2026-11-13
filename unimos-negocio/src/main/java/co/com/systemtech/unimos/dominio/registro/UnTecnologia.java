/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.dominio.registro;

import co.com.systemtech.unimos.dominio.contrato.UnContrato;
import co.com.systemtech.unimos.dominio.generico.Auditoria;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
public class UnTecnologia extends Auditoria {
    
    public static final int TECNOLOGIA_TIPO_PROCEDIMIENTO = 1;
    public static final int TECNOLOGIA_TIPO_MEDICAMENTO = 2;
    public static final int TECNOLOGIA_TIPO_INSUMO = 3;
    public static final int TECNOLOGIA_TIPO_PAQUETE = 4;

    private Integer id;
    private short tipo;
    private String codigo;
    private String codigoPropio;
    private String nombre;
    private String descripcion;
    private String maeServicioHabilitacionTipo;
    private int maeServicioHabilitacionId;
    private String maeServicioHabilitacionCodigo;
    private String maeServicioHabilitacionValor;
    private boolean activa;
    
    private UnContrato unContrato;

    public UnTecnologia() {
    }

    public UnTecnologia(Integer id) {
        this.id = id;
    }

    public UnTecnologia(Integer id, short tipo, String codigo, String codigoPropio, String nombre, String maeServicioHabilitacionTipo, int maeServicioHabilitacionId, String maeServicioHabilitacionCodigo, String maeServicioHabilitacionValor, boolean activa) {
        this.id = id;
        this.tipo = tipo;
        this.codigo = codigo;
        this.codigoPropio = codigoPropio;
        this.nombre = nombre;
        this.maeServicioHabilitacionTipo = maeServicioHabilitacionTipo;
        this.maeServicioHabilitacionId = maeServicioHabilitacionId;
        this.maeServicioHabilitacionCodigo = maeServicioHabilitacionCodigo;
        this.maeServicioHabilitacionValor = maeServicioHabilitacionValor;
        this.activa = activa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public short getTipo() {
        return tipo;
    }

    public String getTipoStr() {
        String val;
        switch (tipo) {
            case TECNOLOGIA_TIPO_PROCEDIMIENTO:
                val = "Procedimiento";
                break;
            case TECNOLOGIA_TIPO_MEDICAMENTO:
                val = "Medicamento";
                break;
            case TECNOLOGIA_TIPO_INSUMO:
                val = "Insumo";
                break;
            case TECNOLOGIA_TIPO_PAQUETE:
                val = "Paquete";
                break;
            default:
                val = "";
        }
        return val;
    }

    public void setTipo(short tipo) {
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoPropio() {
        return codigoPropio;
    }

    public void setCodigoPropio(String codigoPropio) {
        this.codigoPropio = codigoPropio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMaeServicioHabilitacionTipo() {
        return maeServicioHabilitacionTipo;
    }

    public void setMaeServicioHabilitacionTipo(String maeServicioHabilitacionTipo) {
        this.maeServicioHabilitacionTipo = maeServicioHabilitacionTipo;
    }

    public int getMaeServicioHabilitacionId() {
        return maeServicioHabilitacionId;
    }

    public void setMaeServicioHabilitacionId(int maeServicioHabilitacionId) {
        this.maeServicioHabilitacionId = maeServicioHabilitacionId;
    }

    public String getMaeServicioHabilitacionCodigo() {
        return maeServicioHabilitacionCodigo;
    }

    public void setMaeServicioHabilitacionCodigo(String maeServicioHabilitacionCodigo) {
        this.maeServicioHabilitacionCodigo = maeServicioHabilitacionCodigo;
    }

    public String getMaeServicioHabilitacionValor() {
        return maeServicioHabilitacionValor;
    }

    public void setMaeServicioHabilitacionValor(String maeServicioHabilitacionValor) {
        this.maeServicioHabilitacionValor = maeServicioHabilitacionValor;
    }

    public boolean isActiva() {
        return activa;
    }

    public String getActivaStr() {
        return (activa) ? "SI" : "NO";
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public UnContrato getUnContrato() {
        return unContrato;
    }

    public void setUnContrato(UnContrato unContrato) {
        this.unContrato = unContrato;
    }

    @Override
    public String toString() {
        return "UnTecnologia{" + "id=" + id + ", tipo=" + tipo + ", codigo=" + codigo + ", codigoPropio=" + codigoPropio + ", nombre=" + nombre + ", descripcion=" + descripcion + ", maeServicioHabilitacionTipo=" + maeServicioHabilitacionTipo + ", maeServicioHabilitacionId=" + maeServicioHabilitacionId + ", maeServicioHabilitacionCodigo=" + maeServicioHabilitacionCodigo + ", maeServicioHabilitacionValor=" + maeServicioHabilitacionValor + ", activa=" + getActivaStr() + '}';
    }

}
