package ar.edu.utn.frba.dds.notificador;

import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

public class MailSender implements MedioNotificador {

  //Hay dos notificar, borrar uno
  @Override
  public void notificarNuevoIncidente(Incidente incidente, String contacto) {
    String texto = incidente.toString();
    enviarCorreo(contacto, texto, "Nuevo incidente");

  }

  public void notificarSugerenciaRevisionIncidente(Servicio servicio) {

  }

  public static void enviarCorreo(String correoDestino, String unTexto, String unAsunto) {

    final String username = "mailsendergrupo7@gmail.com";
    final String password = "eqvhrzkvmlgasbnm";
    //La contraseña comun es dds2023v

    // Configura las propiedades del sistema
    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com"); // Cambia esto al servidor SMTP que estés utilizando
    props.put("mail.smtp.port", "465"); // Cambia esto al puerto SMTP correspondiente (por ejemplo, 25, 465 o 587)
    props.put("mail.smtp.auth", "true"); // Habilita la autenticación SMTP si es necesario
    props.put("mail.smtp.ssl.enable", "true"); // Habilita SSL si es necesario
    props.put("mail.smtp.user", username); // Tu dirección de correo electrónico
    props.put("mail.smtp.password", password); // Tu contraseña de correo electrónico

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
