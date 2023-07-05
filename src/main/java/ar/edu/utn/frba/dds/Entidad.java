package ar.edu.utn.frba.dds;

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


  //PARA RANKINGS-Mejorar polimorfismo

  public List<Servicio> getServicios(){
    return this.establecimientos.stream().flatMap(e->e.getServicio().stream()).toList();
  }


  public List<Incidente> getIncidentesCerrados(){
    return getServicios().stream()
        .flatMap(i->i.getHistorialIncidentes().stream()).toList().stream().filter(Incidente::estaCerrado).toList();
  }

  public Duration duracionTotalDeTodosLosIncidentesCerrados(){
    return getIncidentesCerrados().stream()
        .map(Incidente::diferenciaEntreAperturayCierre).toList().stream().reduce(Duration.ZERO,Duration::plus);
  }
  public long promedioDuracionIncidentes(){
    return duracionTotalDeTodosLosIncidentesCerrados().toSeconds()/ getIncidentesCerrados().size();
  }
  public int cantidadDeIncidentesReportados(){
    return filtrarPorCantidadUltimas24Horas().size();
  }

  public List<Incidente> filtrarPorCantidadUltimas24Horas(){
    return getServicios().stream().flatMap(s->s.incidentesDe24Horas().stream()).toList();
  }

}
