package ar.edu.utn.frba.dds.notificador;


import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;

public class WhatsAppSender implements MedioNotificador {
  @Override
  public void notificarNuevoIncidente(Incidente incidente, String contacto) {
    
  }

  public void notificarSugerenciaRevisionIncidente(Servicio servicio){

  }
}
