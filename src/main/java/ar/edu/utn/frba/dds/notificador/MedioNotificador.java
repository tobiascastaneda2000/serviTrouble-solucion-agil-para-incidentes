package ar.edu.utn.frba.dds.notificador;


import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;

import java.util.List;

public interface MedioNotificador {

  void notificarUnIncidente(Incidente incidente, String contacto);
//Incluye la apertura de incidentes y la sugerencias
}
