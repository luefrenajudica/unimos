/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.administracion.bean;

import co.com.systemtech.unimos.dominio.administracion.GnDiaHabil;
import co.com.systemtech.unimos.dominio.administracion.Modulo;
import co.com.systemtech.unimos.utilidades.DateUtils;
import co.com.systemtech.unimos.web.administracion.servicio.CalendarioServicioIface;
import co.com.systemtech.unimos.web.administracion.servicio.CalendarioServicioImpl;
import co.com.systemtech.unimos.web.utilidades.Url;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author rpalacic (SystemTech Integral)
 */
@ManagedBean
@ViewScoped
public class CalendarioBean extends Url {

    private CalendarioServicioIface calendarioServicio;
    private GnDiaHabil objeto;
    private List<GnDiaHabil> registros;
    private ScheduleModel lazyEventModel;
    private Date selectedDate;

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public CalendarioBean() {
        this.objeto = new GnDiaHabil();
        Modulo _mod = super.validarModulo(Modulo.ID_CALENDARIO);
        if (_mod == null) {
            super.redireccionar("/unimos/home.faces");
        } else {
            super.setModulo(_mod);
        }
    }

    @PostConstruct
    public void init() {
        try {
            lazyEventModel = new LazyScheduleModel() {
                @Override
                public void loadEvents(LocalDateTime start, LocalDateTime end) {
                    try {
                        getParamConsulta().setParametroConsulta1(DateUtils.asDate(start));
                        getParamConsulta().setParametroConsulta2(DateUtils.asDate(end));
                        refrescar();
                        if (getRegistros() != null) {
                            for (GnDiaHabil obj : getRegistros()) {
                                DefaultScheduleEvent event = DefaultScheduleEvent.builder().title("").startDate(DateUtils.asLocalDateTime(obj.getFecha())).endDate(DateUtils.asLocalDateTime(obj.getFecha())).editable(true).build();
                                event.setStyleClass("myclass");
                                addEvent(event);
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("CalendarioBean - Error in loadEvents: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            };
        } catch (Exception e) {
            System.err.println("CalendarioBean - Error in init(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    public CalendarioServicioIface getCalendarioServicio() {
        if (calendarioServicio == null) {
            calendarioServicio = new CalendarioServicioImpl();
        }
        return calendarioServicio;
    }

    public void setCalendarioServicio(CalendarioServicioIface calendarioServicio) {
        this.calendarioServicio = calendarioServicio;
    }

    public GnDiaHabil getObjeto() {
        return objeto;
    }

    public void setObjeto(GnDiaHabil objeto) {
        this.objeto = objeto;
    }

    public List<GnDiaHabil> getRegistros() {
        return registros;
    }

    public void setRegistros(List<GnDiaHabil> registros) {
        this.registros = registros;
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public void setLazyEventModel(ScheduleModel lazyEventModel) {
        this.lazyEventModel = lazyEventModel;
    }

    public void refrescar() {
        try {
            super.setAccion(Url.ACCION_LISTAR);
            getCalendarioServicio().Accion(this);
        } catch (Exception e) {
            System.err.println("CalendarioBean - Error in refrescar(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void onEventSelect(SelectEvent selectEvent) {
        ScheduleEvent event = (ScheduleEvent) selectEvent.getObject();
        LocalDateTime date = event.getStartDate();
        Date fechaActual = new Date();
        Date fechaSeleccionada = DateUtils.asDate(date);
        if (fechaSeleccionada.before(fechaActual)) {
            addError("No es posible cambiar días anteriores al actual");
            setObjeto(new GnDiaHabil());
        } else {
            GnDiaHabil obj = new GnDiaHabil();
            obj.setFecha(fechaSeleccionada);
            Calendar cal = Calendar.getInstance();
            cal.setTime((Date) obj.getFecha());
            obj.setAgno(cal.get(Calendar.YEAR));
            obj.setHabil(false);
            setObjeto(obj);
            super.setAccion(ACCION_MODIFICAR);
            getCalendarioServicio().Accion(this);
        }
        PrimeFaces.current().ajax().update("frmCalendario");
        generarMensajes();
    }
    
    public void onDateSelect(SelectEvent selectEvent) {
        LocalDateTime date = (LocalDateTime) selectEvent.getObject();
        Date fechaActual = new Date();
        Date fechaSeleccionada = DateUtils.asDate(date);
        if (fechaSeleccionada.before(fechaActual)) {
            addError("No es posible cambiar días anteriores al actual");
            setObjeto(new GnDiaHabil());
        } else {
            GnDiaHabil obj = new GnDiaHabil();
            obj.setFecha(fechaSeleccionada);
            Calendar cal = Calendar.getInstance();
            cal.setTime((Date) obj.getFecha());
            obj.setAgno(cal.get(Calendar.YEAR));
            obj.setHabil(false);
            setObjeto(obj);
            super.setAccion(ACCION_MODIFICAR);
            getCalendarioServicio().Accion(this);
        }
        PrimeFaces.current().ajax().update("frmCalendario");
        generarMensajes();
    }

}
