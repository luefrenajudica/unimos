package co.com.systemtech.unimos.web.facturacionelectronica.servicio;

import co.com.systemtech.unimos.dominio.facturaelectronica.FacturaElectronicaSalud;
import co.com.systemtech.unimos.dominio.facturaelectronica.ItemFacturaSalud;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class FacturacionSaludService {

    public String generarXMLUBL(FacturaElectronicaSalud factura) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        // Nodo Raíz Invoice
        Element invoice = doc.createElement("Invoice");
        invoice.setAttribute("xmlns", "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2");
        invoice.setAttribute("xmlns:cac", "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2");
        invoice.setAttribute("xmlns:cbc", "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2");
        invoice.setAttribute("xmlns:ext", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
        doc.appendChild(invoice);

        // --- EXTENSIONES (RESOLUCIÓN 2275) ---
        Element extensions = doc.createElement("ext:UBLExtensions");
        invoice.appendChild(extensions);

        Element ext = doc.createElement("ext:UBLExtension");
        Element content = doc.createElement("ext:ExtensionContent");

        // Contenedor de Interoperabilidad Salud
        Element customTag = doc.createElement("CustomTagGeneral");
        agregarNodo(doc, customTag, "Name", "Tipo, identificador:año del acto administrativo");
        agregarNodo(doc, customTag, "Value", "Resolución 2275:2023");

        Element interoperabilidad = doc.createElement("Interoperabilidad");
        Element group = doc.createElement("Group");
        group.setAttribute("schemeName", "Sector Salud");

        // Datos desde los métodos de tu clase
        if (factura.getEmisor() != null) {
            agregarInfoSalud(doc, group, "CODIGO_PRESTADOR", factura.getEmisor().getCodigoREPS(), null);
        }
        agregarInfoSalud(doc, group, "CUV", factura.getCodigoUnicoValidacionRIPS(), null);

        interoperabilidad.appendChild(group);
        customTag.appendChild(interoperabilidad);
        content.appendChild(customTag);
        ext.appendChild(content);
        extensions.appendChild(ext);

        // --- ENCABEZADO ---
        agregarCbc(doc, invoice, "cbc:ID", factura.getNumeroFactura());
        agregarCbc(doc, invoice, "cbc:IssueDate", factura.getFechaEmision() != null ? factura.getFechaEmision().toString() : "");

        // --- DETALLE DE LÍNEAS ---
        if (factura.getDetalles() != null) {
            for (ItemFacturaSalud item : factura.getDetalles()) {
                Element line = doc.createElement("cac:InvoiceLine");

                // Mapeo de campos del item
                agregarCbc(doc, line, "cbc:ID", item.getIdDetalleRIPS() != null ? item.getIdDetalleRIPS() : "1");
                agregarCbc(doc, line, "cbc:InvoicedQuantity", String.valueOf(item.getCantidad()));

                Element cacItem = doc.createElement("cac:Item");
                agregarCbc(doc, cacItem, "cbc:Description", item.getDescripcionServicio());

                // Si existe autorización
                if (item.getNumeroAutorizacion() != null) {
                    Element prop = doc.createElement("cac:AdditionalItemProperty");
                    agregarCbc(doc, prop, "cbc:Name", "NumeroAutorizacion");
                    agregarCbc(doc, prop, "cbc:Value", item.getNumeroAutorizacion());
                    cacItem.appendChild(prop);
                }

                line.appendChild(cacItem);
                invoice.appendChild(line);
            }
        }

        return serializar(doc);
    }

    private void agregarInfoSalud(Document doc, Element parent, String name, String val, String scheme) {
        Element info = doc.createElement("AdditionalInformation");
        agregarNodo(doc, info, "Name", name);
        Element value = doc.createElement("Value");
        if (scheme != null) {
            value.setAttribute("schemeName", scheme);
        }
        value.appendChild(doc.createTextNode(val != null ? val : ""));
        info.appendChild(value);
        parent.appendChild(info);
    }

    private void agregarCbc(Document doc, Element p, String t, String v) {
        Element e = doc.createElement(t);
        e.appendChild(doc.createTextNode(v != null ? v : ""));
        p.appendChild(e);
    }

    private void agregarNodo(Document doc, Element p, String t, String v) {
        Element e = doc.createElement(t);
        e.appendChild(doc.createTextNode(v != null ? v : ""));
        p.appendChild(e);
    }

    private String serializar(Document doc) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter sw = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(sw));
        return sw.toString();
    }
}
