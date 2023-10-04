package ar.edu.utn.frba.dds.notificador;

import ar.edu.utn.frba.dds.entidades_y_servicios.Servicio;
import ar.edu.utn.frba.dds.entidades_y_servicios.TipoServicio;
import ar.edu.utn.frba.dds.incidentes.Incidente;

class PruebaMailSender {

  public static void main(String[] args) {
    Servicio servicio = new Servicio(TipoServicio.ASCENSOR);
    Incidente incidente = new Incidente("Comentario que aparecera como texto del mensaje :)", servicio);

    String correoDestino = "tobias.2000david@gmail.com"; // Agregar la dirección de correo electrónico de destino

    MedioNotificador mailSender = new MailSender(new CorreoGmail());

    // Envía el correo electrónico
    mailSender.notificarUnIncidente(incidente, correoDestino);

  }

}