package ar.edu.utn.frba.dds.ServicesLocators;

import ar.edu.utn.frba.dds.notificador.MedioNotificador;
import ar.edu.utn.frba.dds.serviciolocalizacion.ServicioUbicacion;
import java.util.HashMap;
import java.util.Map;

public class ServiceLocatorMedioNotificador {

  private static Map<String, MedioNotificador> servicios = new HashMap<>();

  public static MedioNotificador getServicio(String nombre) {

    MedioNotificador servicio = servicios.get(nombre);

    if (servicio != null) {
      return servicio;
    }
    else{
      return null;
    }
  }

  public void setServicios(String nombre,MedioNotificador servicio){

    servicios.put(nombre,servicio);
  }
}
