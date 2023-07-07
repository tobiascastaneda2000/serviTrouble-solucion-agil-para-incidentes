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

}

