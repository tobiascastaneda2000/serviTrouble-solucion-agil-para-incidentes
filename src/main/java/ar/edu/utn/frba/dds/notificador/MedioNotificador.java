package ar.edu.utn.frba.dds.notificador;


import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;

public interface MedioNotificador {

  void notificar(String mensaje, String mail, Servicio servicio);
}
