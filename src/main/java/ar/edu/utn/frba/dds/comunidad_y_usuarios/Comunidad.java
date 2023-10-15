package ar.edu.utn.frba.dds.comunidad_y_usuarios;

import ar.edu.utn.frba.dds.incidentes.EstadoIncidente;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.entidades_y_servicios.Servicio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Comunidad {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nombre;
  @OneToMany
  @JoinColumn(name = "comunidad_id")
  public Set<Miembro> miembros;
  @ManyToMany //ya que creamos un incidente para varias comunidades
  public List<Incidente> incidentes = new ArrayList<>();
  @OneToMany
  @JoinColumn(name = "comunidad_id")
  public List<Incidente> incidentesCerrados = new ArrayList<>();
  @ManyToMany
  public List<Servicio> serviciosDeInteres = new ArrayList<Servicio>();

  public void agregarServicioInteres(Servicio servicio){
    this.serviciosDeInteres.add(servicio);
  }
  //-------------------------CONSTRUCTOR----------------------------------------//

  public Comunidad() {
    this.miembros = new HashSet<>();
  }

  //-------------------------GETTERS-------------------------------------------//

  public List<Incidente> getIncidentes() {
    return this.incidentes;
  }

  public Set<Miembro> getMiembros() {
    return this.miembros;
  }

  public List<Servicio> getServiciosDeInteres() {
    return this.serviciosDeInteres;
  }

  //METODOS

  public void aniadirServicioInteres(Servicio servicio) {
    this.serviciosDeInteres.add(servicio);
  }

  public Boolean contieneServicioDeInteres(Servicio servicio) {
    return this.getServiciosDeInteres().contains(servicio);
  }

  public void registrarMiembro(Usuario usuario) {
    miembros.add(new Miembro(usuario, PermisoComunidad.USUARIO_COMUNIDAD));
  }

  public boolean contieneUsuario(Usuario usuario) {
    return miembros.stream().map(Miembro::getUsuario).toList().contains(usuario);
  }

  //------------------------------APERTURA DE INCIDENTE---------------------------------------------------//

  public void abrirIncidenteEnComunidad(String observacion, Servicio servicio) {

    Incidente incidente = new Incidente(observacion, servicio);
    incidentes.add(incidente);
    miembros.forEach(m -> m.getUsuario().guardarNotificacion(incidente));
  }

  //-------------------------------CIERRE DE INCIDENTE-------------------------------------------------//

  public void cerrarIncidente(Incidente incidente) {
    incidente.cerrar();
    incidentes.remove(incidente);
    miembros.forEach(m->m.usuario.getNotificaciones().remove(m.usuario.obtenerNotificacion(incidente)));
    incidentesCerrados.add(incidente);
  }

  //---------------------------------------------------------------------------------------------------//

  public List<Incidente> incidentesPorEstado(EstadoIncidente estadoIncidente) {
    return this.incidentes.stream().filter(i -> i.getEstado() == estadoIncidente).toList();
  }

  public void notificarMiembros(Incidente incidente) {
    if (this.contieneServicioDeInteres(incidente.getServicioAsociado())) {
      this.getMiembros().forEach(miembro -> miembro.getUsuario().notificarIncidente(incidente));
    }
  }


  //--------------------------SUGERENCIA DE INCIDENTES--------------------------------//
  public void sugerirIncidentes() {
    miembros.forEach(
        m -> incidentes.forEach(
            i -> m.usuario.notificarSiEstaCerca(i)
        )
    );
  }


}