package ar.edu.utn.frba.dds.notificador;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class CorreoGmail {

  Session session;
  String correoRemitente;

  public void configurarPropiedades(String correoRemitente, String passwordRemitente){
    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com"); // Cambia esto al servidor SMTP que estés utilizando
    props.put("mail.smtp.port", "465"); // Cambia esto al puerto SMTP correspondiente (por ejemplo, 25, 465 o 587)
    props.put("mail.smtp.auth", "true"); // Habilita la autenticación SMTP si es necesario
    props.put("mail.smtp.ssl.enable", "true"); // Habilita SSL si es necesario
    props.put("mail.smtp.user", correoRemitente); // Dirección de correo electrónico del remitente
    props.put("mail.smtp.password", passwordRemitente); // Contraseña de correo electrónico del remitente

    this.session = javax.mail.Session.getInstance(props,
        new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(correoRemitente, passwordRemitente);
          }
        });

    this.correoRemitente =correoRemitente;
  }

  public void enviarCorreo(String correoDestino, String unTexto, String unAsunto){
    try {

      //Escribimos los atributos del mensaje
      MimeMessage message = new MimeMessage(this.session);
      message.setFrom(new InternetAddress(this.correoRemitente));
      message.setSubject(unAsunto);
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoDestino));
      message.setText(unTexto);
      // Envia el mensaje
      Transport.send(message);
    } catch (MessagingException e) {
      //e.printStackTrace();
      throw new RuntimeException("Hubo un error en el Servidor, espere unos minutos..");
    }
  }
}
