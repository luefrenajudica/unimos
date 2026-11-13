package co.com.systemtech.unimos.dominio.facturaelectronica;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Raúl Ignacio Palacios Correa
 */
public class FacturaElectronicaSalud {

    private String prefijo;
    private String folio;
    private LocalDate fechaEmision;
    private String codigoCUFE;
    private String codigoUnicoValidacionRIPS;
    private PrestadorSalud emisor;
    private EntidadPagadora receptor;
    private BigDecimal totalBruto;
    private BigDecimal valorCopago;
    private BigDecimal valorCuotaModeradora;
    private BigDecimal valorCuotaRecuperacion;
    private BigDecimal valorPagosCompartidos;
    private BigDecimal valorNetoPagar;
    private List<ItemFacturaSalud> detalles;

    // Método de conveniencia para el XML
    public String getNumeroFactura() {
        return (prefijo != null ? prefijo : "") + (folio != null ? folio : "");
    }

    // --- Getters y Setters Completos ---
    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getCodigoCUFE() {
        return codigoCUFE;
    }

    public void setCodigoCUFE(String codigoCUFE) {
        this.codigoCUFE = codigoCUFE;
    }

    public String getCodigoUnicoValidacionRIPS() {
        return codigoUnicoValidacionRIPS;
    }

    public void setCodigoUnicoValidacionRIPS(String c) {
        this.codigoUnicoValidacionRIPS = c;
    }

    public PrestadorSalud getEmisor() {
        return emisor;
    }

    public void setEmisor(PrestadorSalud emisor) {
        this.emisor = emisor;
    }

    public EntidadPagadora getReceptor() {
        return receptor;
    }

    public void setReceptor(EntidadPagadora receptor) {
        this.receptor = receptor;
    }

    public BigDecimal getTotalBruto() {
        return totalBruto;
    }

    public void setTotalBruto(BigDecimal totalBruto) {
        this.totalBruto = totalBruto;
    }

    public BigDecimal getValorCopago() {
        return valorCopago;
    }

    public void setValorCopago(BigDecimal valorCopago) {
        this.valorCopago = valorCopago;
    }

    public BigDecimal getValorCuotaModeradora() {
        return valorCuotaModeradora;
    }

    public void setValorCuotaModeradora(BigDecimal v) {
        this.valorCuotaModeradora = v;
    }

    public BigDecimal getValorCuotaRecuperacion() {
        return valorCuotaRecuperacion;
    }

    public void setValorCuotaRecuperacion(BigDecimal v) {
        this.valorCuotaRecuperacion = v;
    }

    public BigDecimal getValorPagosCompartidos() {
        return valorPagosCompartidos;
    }

    public void setValorPagosCompartidos(BigDecimal v) {
        this.valorPagosCompartidos = v;
    }

    public BigDecimal getValorNetoPagar() {
        return valorNetoPagar;
    }

    public void setValorNetoPagar(BigDecimal v) {
        this.valorNetoPagar = v;
    }

    public List<ItemFacturaSalud> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<ItemFacturaSalud> detalles) {
        this.detalles = detalles;
    }
}
