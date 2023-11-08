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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("mailSender")
public class MailSender extends MedioNotificador {

  @Transient
  final String correoRemitente = "mailsendergrupo7@gmail.com";

  @Transient
  String passwordRemitente = null;

  @Transient
  Session session = null;
  @Transient
  MimeMessage message = null;
  @Transient
  Properties props = new Properties();

  @Transient
  Logger logger = null;

  public MailSender() {
  }

  @Override
  public void notificarUnIncidente(Incidente incidente, String contacto) {
    String texto = incidente.getObservacion();
    this.configurarPropiedades();
    this.armarMensaje(contacto, texto, "Nuevo incidente");
    this.enviar();
  }

  //Guarda la contraseña cargada en un archivo de texto
  private void cargarContrasenia() {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    try (InputStream inputStream = loader.getResourceAsStream("myConfigs.password.txt")) {

      props.load(inputStream);

      passwordRemitente = props.getProperty("passwordRemitente");

    } catch (IOException e) {
      logger.log(Level.SEVERE, "Error al cargar la contrasenia", e);
    }
  }

  //Envia el mensaje
  private void enviar() {
    try {
      Transport.send(message);
    } catch (MessagingException e) {
      logger.log(Level.SEVERE, "Error al enviar el mensaje", e);
    }

  }

  //Arma la estructura del mensaje
  private void armarMensaje(String correoDestino, String unTexto, String unAsunto) {
    try {

      //Escribimos los atributos del mensaje
      message = new MimeMessage(session);
      message.setFrom(new InternetAddress(correoRemitente));
      message.setSubject(unAsunto);
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoDestino));
      message.setText(unTexto);

    } catch (MessagingException e) {
      logger.log(Level.SEVERE, "Error en el Armado del mensaje", e);
    }
  }

  //Configura el entorno para el envio de mails e inicia la sesion
  private void configurarPropiedades() {

    this.cargarContrasenia();
    props.put("mail.smtp.host", "smtp.gmail.com"); // Cambia esto al servidor SMTP que estés utilizando
    props.put("mail.smtp.port", "587"); //Puerto
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
