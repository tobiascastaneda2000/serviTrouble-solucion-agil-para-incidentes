package ar.edu.utn.frba.dds.notificador;


import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import java.util.List;

public interface MedioNotificador {

  void notificar(Incidente incidente,String contacto);

  void notificar(List<Incidente> incidentes, String contacto);
  void notificarSugerenciaRevisionIncidente(Servicio servicio);
}
