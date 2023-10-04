package ar.edu.utn.frba.dds.notificador;


import ar.edu.utn.frba.dds.incidentes.Incidente;

public interface MedioNotificador {

  void notificarUnIncidente(Incidente incidente, String contacto);
//Incluye la apertura de incidentes y la sugerencias
}
