package ar.edu.utn.frba.dds.comunidad_e_incidentes;

import ar.edu.utn.frba.dds.RepoUsuarios;
import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Comunidad{
  public Set<Miembro> miembros;
  public List<Incidente> incidentes = new ArrayList<>();
  public List<Incidente> incidentesCerrados = new ArrayList<>();
  public List<Servicio> serviciosDeInteres = new ArrayList<Servicio>();

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

  public void abrirIncidente(Incidente incidente) {

    incidentes.add(incidente);
    miembros.forEach(m->m.getUsuario().guardarNotificacion(incidente));
  }

  //-------------------------------CIERRE DE INCIDENTE-------------------------------------------------//

  public void cerrarIncidente(Incidente incidente) throws CloneNotSupportedException {
    Incidente nuevoIncidente = (Incidente) incidente.clone();
    nuevoIncidente.cerrar();
    incidentes.remove(incidente);
    RepoUsuarios.instance.sacarIncidentesCerrados(incidente);  // saca al incidente de la lista de notificaciones pendientes de cada usuario para que no notifique un incidente cerrado
    incidentesCerrados.add(nuevoIncidente);
  }

  //---------------------------------------------------------------------------------------------------//

  private List<Miembro> miembrosInteresados(Servicio servicio) {
    return this.miembros.stream().filter(m -> m.usuario.serviciosDeInteres().contains(servicio)).toList();
  }



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
        m-> incidentes.forEach(
            i-> m.usuario.notificarSiEstaCerca(i)
        )
    );
  }




}