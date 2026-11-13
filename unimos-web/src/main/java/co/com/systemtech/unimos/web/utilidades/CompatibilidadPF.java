/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.utilidades;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

/**
 *
 * @author sgiraldov
 */
public class CompatibilidadPF {
    
    //Date a LocalDateTime
    public static LocalDateTime convertirDateToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    //Hash String FilterMeta to Filter String Obj
    public static HashMap<String, Object> ConvertirFiltroMetaToHash(Map<String, FilterMeta> filtros) {
        HashMap<String, Object> filtroObj = new HashMap<>();
        filtros.entrySet().forEach(filtroMeta -> {
            if (filtroMeta.getValue().getFilterValue() != null) {
                filtroObj.put(filtroMeta.getValue().getField(), filtroMeta.getValue().getFilterValue());
            }
        });
        return filtroObj;
    }
    
    //Convierte el HashMap propio de primefaces en uno propio
    public static HashMap<String, String> convertirFiltrosOrdenToHash(Map<String, SortMeta> filtroOrden) {
        HashMap<String, String> filtroFinal = new HashMap<>();
        for (SortMeta orden : filtroOrden.values()) {
            String ordenStr = "DESC";
            if (orden.getOrder().isAscending()) {
                ordenStr = "ASC";
            }
            filtroFinal.put(orden.getField(), ordenStr);
        }
        return filtroFinal;
    }
    
}
