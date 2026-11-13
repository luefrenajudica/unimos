/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.systemtech.unimos.web.utilidades;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author rpalacic
 */
public class CorreoHtml implements Runnable {

    public CorreoHtml(String destinos, String asunto, String mensaje) {
        this.destinos = destinos;
        this.asunto = asunto;
        this.mensaje = mensaje;
    }

    private static final String SEPARADOR_CORREOS = ",";

    private static final String GN_SMTP_AUTH_USER = PropApl.getInstance().get(PropApl.GN_SMTP_BASE_AUTH_USER);
    private static final String GN_SMTP_AUTH_PWD = PropApl.getInstance().get(PropApl.GN_SMTP_BASE_AUTH_PWD);
    private static final String GN_SMTP_HOST_NAME = PropApl.getInstance().get(PropApl.GN_SMTP_BASE_HOST_NAME);
    private static final String GN_SMTP_HOST_PORT = PropApl.getInstance().get(PropApl.GN_SMTP_BASE_HOST_PORT);
    private static final String GN_SERVER_TYPE = PropApl.getInstance().get(PropApl.GN_SMTP_BASE_SECURITY);

    String destinos = "";
    String asunto = "";
    String mensaje = "";

    @Override
    public void run() {
        switch (GN_SERVER_TYPE) {
            case "SMTPS":
                Transport transport = null;
                try {
                    Properties props = new Properties();
                    props.put("mail.transport.protocol", "smtps");
                    props.put("mail.smtps.host", GN_SMTP_HOST_NAME);
                    props.put("mail.smtps.auth", "true");

                    Session mailSession = Session.getDefaultInstance(props);
                    // mailSession.setDebug(true);
                    transport = mailSession.getTransport();
                    MimeMessage message = new MimeMessage(mailSession);
                    message.setSubject(asunto, "utf-8");
                    message.setContent(mensaje, "text/html; charset=utf-8");
                    if (destinos != null || !destinos.equals("")) {
                        String[] destino = destinos.split(SEPARADOR_CORREOS);
                        for (String dest : destino) {
                            message.addRecipient(Message.RecipientType.TO, new InternetAddress(dest));
                        }
                        transport.connect(GN_SMTP_HOST_NAME, Integer.parseInt(GN_SMTP_HOST_PORT), GN_SMTP_AUTH_USER, GN_SMTP_AUTH_PWD);
                        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
                    }
                } catch (MessagingException e) {
                    System.out.println("ERROR ENVÍO: " + e.getMessage());
                } finally {
                    if (transport != null && transport.isConnected()) {
                        try {
                            transport.close();
                        } catch (MessagingException exe) {
                        }
                    }
                }
                break;
            case "SMTP":
                Properties properties = new Properties();
                properties.put("mail.smtp.host", GN_SMTP_HOST_NAME);
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.port", GN_SMTP_HOST_PORT);
                properties.put("mail.smtp.mail.sender", GN_SMTP_AUTH_USER);
                properties.put("mail.smtp.user", GN_SMTP_AUTH_USER);
                properties.put("mail.smtp.auth", "true");
                Session session = Session.getDefaultInstance(properties);
                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender")));
                    if (destinos != null || !destinos.equals("")) {
                        String[] destino = destinos.split(SEPARADOR_CORREOS);
                        for (String dest : destino) {
                            message.addRecipient(Message.RecipientType.TO, new InternetAddress(dest));
                        }
                    }
                    message.setSubject(asunto, "utf-8");
                    message.setContent(mensaje, "text/html; charset=utf-8");
                    Transport t = session.getTransport("smtp");
                    t.connect((String) properties.get("mail.smtp.user"), GN_SMTP_AUTH_PWD);
                    t.sendMessage(message, message.getAllRecipients());
                    t.close();
                } catch (MessagingException me) {
                }
                break;
        }
    }

}
