package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.comunidad_e_incidentes.EstadoIncidente;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Entidad {
  private int id;
  private String razonSocial;
  private String email;

  public List<Establecimiento> getEstablecimientos() {
    return establecimientos;
  }

  public List<Establecimiento> establecimientos = new ArrayList<>();

  public Entidad(int id, String razonSocial, String email) {
    this.id = id;
    this.razonSocial = razonSocial;
    this.email = email;
  }

  public int getId() {
    return id;
  }

  public String getRazonSocial() {
    return razonSocial;
  }

  public String getEmail() {
    return email;
  }


  //PARA RANKINGS

  public List<Servicio> getServicios(){
    return this.establecimientos.stream().flatMap(e->e.getServicio().stream()).toList();
  }

  public List<Incidente> getIncidentes(){
    return getServicios().stream()
        .flatMap(i->i.getHistorialIncidentes().stream()).toList();
  }

  public List<Duration> listaTotalDuracionCierres(){
    return getIncidentes().stream()
        .filter(i->i.getEstado()== EstadoIncidente.CERRADO).toList().stream()
        .map(i->i.diferenciaEntreAperturayCierre()).toList();
  }

  public Duration duracionTotalIncidentesCerrados(){
    return listaTotalDuracionCierres().stream().reduce(Duration.ZERO,Duration::plus);
  }

  public long promedioDuracionIncidentes(){
    return duracionTotalIncidentesCerrados().toSeconds()/ listaTotalDuracionCierres().size();
  }
  public int cantidadDeIncidentes(){
    return filtrarPorCantidadUltimas24Horas().size();
  }

  public List<Incidente> filtrarPorCantidadUltimas24Horas(){
    return getServicios().stream().flatMap(s->s.incidentesDe24Horas().stream()).toList();
  }

}
