package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;

@Entity
public class Entidad {
  @Id
  @GeneratedValue
  private int id;

  private String razonSocial;
  private String email;

  public List<Establecimiento> getEstablecimientos() {
    return establecimientos;
  }

  @OneToMany
  @JoinColumn(name = "entidad_id")
  public List<Establecimiento> establecimientos = new ArrayList<>();
  @Transient
  public List<Incidente> incidentes = new ArrayList<>();

  public void agregarEstablecimiento(Establecimiento establecimiento) {
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
    return getIncidentes().stream().filter(Incidente::estaCerrado).toList();
  }

  public List<Incidente> getIncidentes() {
    return getServicios().stream()
        .flatMap(i -> i.getHistorialIncidentes().stream()).toList();
  }

  //PARA PROMEDIOS DE CIERRES DE INCIDENTES

  public Duration duracionTotalDeTodosLosIncidentesCerrados() {
    return getIncidentesCerrados().stream()
        .map(Incidente::diferenciaEntreAperturayCierre).toList().stream().reduce(Duration.ZERO, Duration::plus);
  }

  public Duration promedioDuracionIncidentes() {
    if (getIncidentes().size() != 0 && getIncidentesCerrados().size() != 0) {
      long duracion = duracionTotalDeTodosLosIncidentesCerrados().toSeconds() / getIncidentesCerrados().size();
      return Duration.ofMinutes(duracion); ///Duracion en minutos
    } else {
      return Duration.ZERO;
    }


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

    Incidente incidente = new Incidente(observaciones, servicio);
    this.incidentes.add(incidente);
    servicio.aniadirIncidente(incidente);
    this.notificarEnEsteMomento(incidente);
  }

  private void notificarEnEsteMomento(Incidente incidente) {
    RepoUsuarios repoUsuarios = RepoUsuarios.instance;
    List<Usuario> usuariosInteresados = repoUsuarios.interesadoEnEntidad(this);
    usuariosInteresados.forEach(u -> u.notificarIncidente(incidente));
  }
}
