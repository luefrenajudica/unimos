/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.systemtech.unimos.dominio.contrato;

import co.com.systemtech.unimos.dominio.generico.Auditoria;
import java.util.List;

/**
 *
 * @author jmpiedrahita
 */
public class UnSuscripcionCarnetDto extends Auditoria {

    private String nombreCompletoTitular;
    private String cedulaTitular;
    private List<UnSuscripcionCarnetListaDto> listaSuscripcionCarnetListaDto;
    private String vencimientoContrato;

    public String getNombreCompletoTitular() {
        return nombreCompletoTitular;
    }

    public void setNombreCompletoTitular(String nombreCompletoTitular) {
        this.nombreCompletoTitular = nombreCompletoTitular;
    }

    public String getCedulaTitular() {
        return cedulaTitular;
    }

    public void setCedulaTitular(String cedulaTitular) {
        this.cedulaTitular = cedulaTitular;
    }

    public List<UnSuscripcionCarnetListaDto> getListaSuscripcionCarnetListaDto() {
        return listaSuscripcionCarnetListaDto;
    }

    public void setListaSuscripcionCarnetListaDto(List<UnSuscripcionCarnetListaDto> listaSuscripcionCarnetListaDto) {
        this.listaSuscripcionCarnetListaDto = listaSuscripcionCarnetListaDto;
    }

    public String getVencimientoContrato() {
        return vencimientoContrato;
    }

    public void setVencimientoContrato(String vencimientoContrato) {
        this.vencimientoContrato = vencimientoContrato;
    }

}
