package ar.edu.utn.frba.dds.ServicesLocators;

import ar.edu.utn.frba.dds.serviciolocalizacion.ServicioUbicacion;
import java.util.HashMap;
import java.util.Map;

public class ServiceLocatorUbicacion {

  private static Map<String, ServicioUbicacion> servicios = new HashMap<>();

  public static ServicioUbicacion getServicio(String nombre) {

    ServicioUbicacion servicio = servicios.get(nombre);

    if (servicio != null) {
      return servicio;
    }
    else{
      return null;
    }
  }

  public void setServicios(String nombre,ServicioUbicacion servicio){

    servicios.put(nombre,servicio);
  }
}
