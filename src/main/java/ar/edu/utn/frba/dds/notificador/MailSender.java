package ar.edu.utn.frba.dds.notificador;

import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import java.util.List;

public class MailSender implements MedioNotificador {

  @Override
  public void notificar(Incidente incidente, String contacto) {

  }

  @Override
  public void notificar(List<Incidente> incidentes, String contacto) {

  }

  public void notificarSugerenciaRevisionIncidente(Servicio servicio){

  }

}
