package ar.edu.utn.frba.dds.notificador;

import ar.edu.utn.frba.dds.incidentes.Incidente;

public class MailSender implements MedioNotificador {

  CorreoGmail correoGmail;

  public MailSender(CorreoGmail correoGmail) {
    this.correoGmail = correoGmail;
  }

  //Hay dos notificar, borrar uno
  @Override
  public void notificarUnIncidente(Incidente incidente, String contacto) {
    String texto = incidente.getObservacion();
    //enviarCorreo(contacto, texto, "Nuevo incidente");
    correoGmail.configurarPropiedades("mailsendergrupo7@gmail.com","eqvhrzkvmlgasbnm");
    correoGmail.enviarCorreo(contacto, texto, "Nuevo incidente");

  }
/*
  public static void enviarCorreo(String correoDestino, String unTexto, String unAsunto) {

    final String correoRemitente = "mailsendergrupo7@gmail.com";
    final String passwordRemitente = "eqvhrzkvmlgasbnm";
    //La contraseña comun es dds2023v

    // Configura las propiedades del sistema
    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com"); // Cambia esto al servidor SMTP que estés utilizando
    props.put("mail.smtp.port", "465"); // Cambia esto al puerto SMTP correspondiente (por ejemplo, 25, 465 o 587)
    props.put("mail.smtp.auth", "true"); // Habilita la autenticación SMTP si es necesario
    props.put("mail.smtp.ssl.enable", "true"); // Habilita SSL si es necesario
    props.put("mail.smtp.user", correoRemitente); // Dirección de correo electrónico del remitente
    props.put("mail.smtp.password", passwordRemitente); // Contraseña de correo electrónico del remitente

    Session session = Session.getInstance(props,
        new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(correoRemitente, passwordRemitente);
          }
        });

    try {

      //Escribimos los atributos del mensaje
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(correoRemitente));
      message.setSubject(unAsunto);
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoDestino));
      message.setText(unTexto);
      // Envia el mensaje
      Transport.send(message);
    } catch (MessagingException e) {
       throw new RuntimeException("Hubo un error en el Servidor, espere unos minutos..");
    }

  }*/
}
