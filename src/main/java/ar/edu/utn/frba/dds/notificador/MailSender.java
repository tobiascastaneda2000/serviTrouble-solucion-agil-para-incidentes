package ar.edu.utn.frba.dds.notificador;

import ar.edu.utn.frba.dds.incidentes.Incidente;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailSender implements MedioNotificador {
  final String correoRemitente = "mailsendergrupo7@gmail.com";


  //La contraseña comun es: dds2023v
  //La contraseña para la app esta guardada en src->main->resources-> myConfigs.password
  String passwordRemitente = null;

  Session session = null;

  MimeMessage message = null;

  Properties props = new Properties();


  public MailSender() {
  }

  @Override
  public void notificarUnIncidente(Incidente incidente, String contacto) {
    String texto = incidente.getObservacion();
    this.configurarPropiedades();
    this.armarMensaje(contacto, texto, "Nuevo incidente");
    this.enviar();


  }

  private void cargarContrasenia() {
    try {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      InputStream inputStream = loader.getResourceAsStream("myConfigs.password.txt");

      props.load(inputStream);

      passwordRemitente = props.getProperty("passwordRemitente");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  private void enviar()  {
    try{
      Transport.send(message);
    }catch (MessagingException e) {
      Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, e);
    }
    // Envia el mensaje

  }

  private void armarMensaje(String correoDestino, String unTexto, String unAsunto) {
    try {

      //Escribimos los atributos del mensaje
      message = new MimeMessage(session);
      message.setFrom(new InternetAddress(correoRemitente));
      message.setSubject(unAsunto);
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoDestino));
      message.setText(unTexto);

    } catch (MessagingException e) {
      Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, e);
    }
  }

  private void configurarPropiedades() {


      this.cargarContrasenia();
      props.put("mail.smtp.host", "smtp.gmail.com"); // Cambia esto al servidor SMTP que estés utilizando
      props.put("mail.smtp.port", "587");
      props.put("mail.smtp.auth", "true"); // Habilita la autenticación SMTP si es necesario
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.user", correoRemitente); // Dirección de correo electrónico del remitente
      props.put("mail.smtp.password", passwordRemitente); // Contraseña de correo electrónico del remitente
      props.put("mail.smtp.ssl.protocols", "TLSv1.2");

       session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication(correoRemitente, passwordRemitente);
            }
          });

  }

}
