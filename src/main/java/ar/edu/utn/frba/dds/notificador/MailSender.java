package ar.edu.utn.frba.dds.notificador;

import ar.edu.utn.frba.dds.incidentes.Incidente;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("mailSender")
public class MailSender extends MedioNotificador {

  @Transient
  final String correoRemitente = "mailsendergrupo7@gmail.com";

  @Transient
  String passwordRemitente = "qxaetsrxgepggvxu";
  //String passwordRemitente = null;

  @Transient
  Session session = null;
  @Transient
  MimeMessage message = null;
  @Transient
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

      InputStream inputStream = new FileInputStream("myConfigs.properties");

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

      //La contraseña comun es dds2023v
      //this.cargarContrasenia();
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
