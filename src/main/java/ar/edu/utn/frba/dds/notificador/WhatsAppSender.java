package ar.edu.utn.frba.dds.notificador;


import ar.edu.utn.frba.dds.incidentes.Incidente;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@DiscriminatorValue("whatsAppSender")
public class WhatsAppSender extends MedioNotificador {
  @Override
  public void notificarUnIncidente(Incidente incidente, String contacto) {

  }

}
