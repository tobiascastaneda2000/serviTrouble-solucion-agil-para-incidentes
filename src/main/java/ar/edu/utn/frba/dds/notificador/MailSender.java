package ar.edu.utn.frba.dds.notificador;

import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class MailSender implements MedioNotificador {

  //Hay dos notificar, borrar uno
  @Override
  public void notificarNuevoIncidente(Incidente incidente, String contacto) {
    String texto = incidente.toString();
    enviarCorreo(contacto, texto, "Nuevo incidente");

  }

  @Override
  public void notificarNuevoIncidente(List<Incidente> incidentes, String contacto) {

  }

  public void notificarSugerenciaRevisionIncidente(Servicio servicio) {

  }

  public static void enviarCorreo(String correoDestino, String unTexto, String unAsunto) {

    final String username = "ejemplo@gmail.com";
    final String password = "password";

    // Configura las propiedades del sistema
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");// Puerto del servidor SMTP

    Session session = Session.getInstance(props,
        new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
          }
        });

    try {

      // Define message
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(username));
      message.setSubject(unAsunto);
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoDestino));
      message.setText(unTexto);
      // Envia el mensaje
      Transport.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }

  }
}
