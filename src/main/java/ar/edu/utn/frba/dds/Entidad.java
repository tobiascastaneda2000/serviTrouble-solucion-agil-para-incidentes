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

  public List<Incidente> incidentes = new ArrayList<>();

  public void agregarEstablecimiento(Establecimiento establecimiento){
    establecimientos.add(establecimiento);
  }

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


  //PARA RANKINGS-

  public List<Servicio> getServicios() {
    return this.establecimientos.stream().flatMap(e -> e.getServicios().stream()).toList();
  }


  public List<Incidente> getIncidentesCerrados() {
    return getServicios().stream()
        .flatMap(i -> i.getHistorialIncidentes().stream()).toList().stream().filter(Incidente::estaCerrado).toList();
  }

  //PARA PROMEDIOS DE CIERRES DE INCIDENTES

  public Duration duracionTotalDeTodosLosIncidentesCerrados() {
    return getIncidentesCerrados().stream()
        .map(Incidente::diferenciaEntreAperturayCierre).toList().stream().reduce(Duration.ZERO, Duration::plus);
  }

  public Duration promedioDuracionIncidentes() {
    long duracion = duracionTotalDeTodosLosIncidentesCerrados().toSeconds() / getIncidentesCerrados().size();
    return Duration.ofMinutes(duracion); ///Duracio en minutos
  }

  //PARA CANTIDAD DE INCIDENTES REPORTADOS
  public int cantidadDeIncidentesReportados() {
    return filtrarPorCantidadUltimas24Horas().size();
  }

  public List<Incidente> filtrarPorCantidadUltimas24Horas() {
    return getServicios().stream().flatMap(s -> s.incidentesDe24Horas().stream()).toList();
  }

  //-------------------------------------------------------------------------------------//


  //-------------------CREAR INCIDENTES EN ENTIDAD---------------------------------------//
  public void crearIncidente(Servicio servicio, String observaciones) {

    RepoUsuarios repoUsuarios = RepoUsuarios.instance;
    List<Usuario> usuariosInteresados = repoUsuarios.interesadoEnEntidad(this);
    Incidente incidente = new Incidente(observaciones, servicio);
    this.incidentes.add(incidente);
    servicio.aniadirIncidente(incidente);
    this.notificarEnEsteMomento(usuariosInteresados, incidente);
  }

  private void notificarEnEsteMomento(List<Usuario> usuariosInteresados, Incidente incidente) {
    usuariosInteresados.forEach(u -> u.notificarIncidente(incidente));
  }
}
