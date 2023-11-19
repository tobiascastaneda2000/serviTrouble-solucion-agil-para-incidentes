package ar.edu.utn.frba.dds.comunidad;

import ar.edu.utn.frba.dds.incidentes.EstadoIncidente;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.entidades.Servicio;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
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
public class Comunidad implements WithSimplePersistenceUnit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  public String nombre;
  @OneToMany
  @JoinColumn(name = "comunidad_id")
  public Set<Miembro> miembros;
  @OneToMany
  @JoinColumn(name = "comunidad_id")
  public List<Incidente> incidentes = new ArrayList<>();
  @OneToMany
  @JoinColumn(name = "comunidad2_id")
  public List<Incidente> incidentesCerrados = new ArrayList<>();
  @ManyToMany
  public List<Servicio> serviciosDeInteres = new ArrayList<Servicio>();

  public void agregarServicioInteres(Servicio servicio){
    this.serviciosDeInteres.add(servicio);
  }
  //-------------------------CONSTRUCTOR----------------------------------------//

  protected Comunidad(){
  }

  public Comunidad(String nombre) {
    this.nombre = nombre;
    this.miembros = new HashSet<>();
  }

  public void agregarUsuario(Usuario usuario,PermisoComunidad permiso){
    Miembro miembro = new Miembro(usuario,permiso);
    miembros.add(miembro);
    persist(miembro);
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

  public Long getId(){
    return id;
  }

  public String getNombre(){
    return nombre;
  }

  //------------------------------APERTURA DE INCIDENTE---------------------------------------------------//

  public void abrirIncidenteEnComunidad(String observacion, Servicio servicio) {

    Incidente incidente = new Incidente(observacion, servicio);
    incidentes.add(incidente);
    miembros.forEach(m -> m.getUsuario().guardarNotificacion(incidente));
    persist(incidente);
    entityManager().getTransaction().begin();
    entityManager().flush();
    entityManager().getTransaction().commit();



  }

  //-------------------------------CIERRE DE INCIDENTE-------------------------------------------------//

  public void cerrarIncidente(Incidente incidente) {
    incidente.cerrar();
    incidentes.remove(incidente);
    miembros.forEach(m->m.usuario.notificacionesPendientes.remove(m.usuario.obtenerNotificacion(incidente)));
    incidentesCerrados.add(incidente);
    persist(incidente);
    entityManager().getTransaction().begin();
    entityManager().flush();
    entityManager().getTransaction().commit();

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


  public boolean contieneIncidente(Incidente incidente) {
    return incidentes.contains(incidente);
  }

  public boolean miembroEsAdmin(Usuario usuario) {
    return miembros.stream().filter(m->m.usuario.equals(usuario))
        .toList().get(0).permisoComunidad.equals(PermisoComunidad.ADMIN_COMUNIDAD);
  }
}