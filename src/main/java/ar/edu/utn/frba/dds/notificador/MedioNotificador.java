package ar.edu.utn.frba.dds.notificador;


import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import ar.edu.utn.frba.dds.Usuario;

public interface MedioNotificador {

  void notificar(String mensaje, Usuario usuario, Servicio servicio);
}
