package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.Localizacion;
import java.util.ArrayList;
import java.util.List;

public class Establecimiento {
  Localizacion localizacion;
  public List<Servicio> getServicios() {
    return servicios;
  }
  List<Servicio> servicios = new ArrayList<>();

  public Localizacion getLocalizacion(){
    return localizacion;
  }

  public void setLocalizacion(Localizacion localizacion) {
    this.localizacion = localizacion;
  }

  public void agregarServicio(Servicio servicio){
    servicios.add(servicio);
  }


  //Servicio de ubicacion para calcular la ubicacion exacta en longitud y latitud del establecimiento
  ServicioUbicacion servicioUbicacion;
  public void setServicioUbicacion(ServicioUbicacion servicioUbicacion) {
    this.servicioUbicacion = servicioUbicacion;
  }

  public Ubicacion ubicacion(){
    return this.servicioUbicacion.ubicacionEstablecimiento(this);
  }

}

