package ar.edu.utn.frba.dds.notificador;

import ar.edu.utn.frba.dds.entidades_y_servicios.Servicio;
import ar.edu.utn.frba.dds.entidades_y_servicios.TipoServicio;
import ar.edu.utn.frba.dds.incidentes.Incidente;

class PruebaMailSender {

  public static void main(String[] args) {
    Servicio servicio = new Servicio("nombre",TipoServicio.ASCENSOR);
    Incidente incidente = new Incidente("El 4/11 es la presentacion de la 5° entrega :)", servicio);

    String correoDestino = "tobias.2000david@gmail.com"; // Agregar la dirección de correo electrónico de destino

    //MedioNotificador mailSender = new MailSender(new CorreoGmail());
    MedioNotificador mailSender = new MailSender();

    // Envía el correo electrónico
    mailSender.notificarUnIncidente(incidente, correoDestino);

  }

}