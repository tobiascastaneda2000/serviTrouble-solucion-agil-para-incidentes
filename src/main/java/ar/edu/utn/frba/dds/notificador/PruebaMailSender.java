package ar.edu.utn.frba.dds.notificador;

import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;

class PruebaMailSender {

  public static void main(String[] args) {
    Servicio servicio = new Servicio(TipoServicio.ASCENSOR);
    Incidente incidente = new Incidente("Comentario que aparecera como texto del mensaje :)", servicio);

    String correoDestino = ""; // Agregar la dirección de correo electrónico de destino

    MedioNotificador mailSender = new MailSender();

    // Envía el correo electrónico
    mailSender.notificarNuevoIncidente(incidente, correoDestino);

  }

}