package ar.edu.utn.frba.dds.entidades;

import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.serviciolocalizacion.ServicioUbicacion;
import ar.edu.utn.frba.dds.serviciolocalizacion.Ubicacion;
import ar.edu.utn.frba.dds.serviciolocalizacion.Localizacion;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Establecimiento {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;
  @ManyToOne
  private Localizacion localizacion;
  public String nombre;

  public List<Servicio> getServicios() {
    return servicios;
  }

  @OneToMany
  @JoinColumn(name = "Establecimiento_id")
  public List<Servicio> servicios = new ArrayList<>();

  protected Establecimiento() {
  }

  public Establecimiento(String nombre) {
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }

  public Long getId() {
    return id;
  }

  public Localizacion getLocalizacion() {
    return localizacion;
  }

  public void setLocalizacion(Localizacion localizacion) {
    this.localizacion = localizacion;
  }

  public void agregarServicio(Servicio servicio) {
    servicios.add(servicio);
    servicio.establecimiento = this;
  }

  public List<Incidente> getIncidentes() {
    return servicios.stream().flatMap(servicio -> servicio.getHistorialIncidentes().stream()).toList();
  }

  //Servicio de ubicacion para calcular la ubicacion exacta en longitud y latitud del establecimiento
  @Transient
  ServicioUbicacion servicioUbicacion;

  public void setServicioUbicacion(ServicioUbicacion servicioUbicacion) {
    this.servicioUbicacion = servicioUbicacion;
  }

  public Ubicacion ubicacion() {
    return this.servicioUbicacion.ubicacionEstablecimiento(this);
  }


}

