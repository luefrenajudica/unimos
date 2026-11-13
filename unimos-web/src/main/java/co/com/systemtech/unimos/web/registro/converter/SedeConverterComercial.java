/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.registro.converter;
import co.com.systemtech.unimos.dominio.registro.Sede;
import co.com.systemtech.unimos.web.registro.bean.ComercialBean;
import java.util.Map;
import java.util.Objects;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author rpalacic (Raúl Ignacio Palacios Correa) - SystemTech Integral
 */
@FacesConverter("sedeConverterComercial")
public class SedeConverterComercial implements Converter {

    @Override
    public Sede getAsObject(FacesContext context, UIComponent component, String value) {
        Sede obj = null;
        if(value != null && value.trim().length() > 0) {
            try {
                Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
                ComercialBean service = (ComercialBean) viewMap.get("comercialBean");
                for(Sede _obj : service.getListaSedes()){
                    if(Objects.equals(Integer.valueOf(value), _obj.getId())){
                        obj = _obj;
                        break;
                    }
                }
            } catch(NumberFormatException e) {
//                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Conversión", "Ubicación Inválida."));
            }
        }else {
            return null;
        }
        return obj;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null) {
            return String.valueOf(((Sede) value).getId());
        }else {
            return null;
        }
    }
    
}
