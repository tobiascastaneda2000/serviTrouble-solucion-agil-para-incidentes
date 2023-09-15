package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.Localizacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Establecimiento {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Transient // FALTA Embeber? Tiene Herencia también
  private Localizacion localizacion;
  private String nombre;

  public List<Servicio> getServicios() {
    return servicios;
  }
  @OneToMany
  @JoinColumn(name = "Establecimiento_id")
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
  @Transient // FALTA
  ServicioUbicacion servicioUbicacion;
  public void setServicioUbicacion(ServicioUbicacion servicioUbicacion) {
    this.servicioUbicacion = servicioUbicacion;
  }

  public Ubicacion ubicacion(){
    return this.servicioUbicacion.ubicacionEstablecimiento(this);
  }

}

