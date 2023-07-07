package ar.edu.utn.frba.dds.comunidad_e_incidentes;

import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Comunidad {
  public Set<Miembro> miembros;
  public List<Incidente> incidentes = new ArrayList<>();
  public List<Servicio> serviciosDeInteres = new ArrayList<Servicio>();

  //CONSTRUCTOR

  public Comunidad() {
    this.miembros = new HashSet<>();
  }

  //GETTERS

  public List<Incidente> getIncidentes() {
    return this.incidentes;
  }

  public Set<Miembro> getMiembros() {
    return this.miembros;
  }

  //METODOS

  public void aniadirServicioInteres(Servicio servicio) {
    this.serviciosDeInteres.add(servicio);
  }

  public List<Servicio> getServiciosDeInteres() {
    return this.serviciosDeInteres;
  }

  public Boolean contieneServicioDeInteres(Servicio servicio) {
    return this.getServiciosDeInteres().contains(servicio);
  }


  public void registrarMiembro(Usuario usuario) {
    miembros.add(new Miembro(usuario, PermisoComunidad.USUARIO_COMUNIDAD));
  }
  //ACLARACION: siempre que se registra un miembro desde comunidado su permiso es USUARIO_COMUNIDAD

  public Miembro getMiembro(Usuario usuario) {
    return this.miembros.stream().filter(m -> m.getUsuario() == usuario).findFirst().orElse(null);
  }

  public void abrirIncidente(Servicio servicio, String observaciones) {
    Incidente incidente = new Incidente(observaciones, servicio);
    servicio.aniadirIncidente(incidente); //Para ranking
    efectivizarAperturaIncidente(incidente);
    notificarAperturaIncidente(servicio);
  }

  private void efectivizarAperturaIncidente(Incidente incidente) {
    incidentes.add(incidente);

  }

  public void notificarAperturaIncidente(Servicio servicio) {
    // miembrosInteresados(servicio).forEach(m -> m.getUsuario().tipoNotificador.notificar("Apertura Incidente", m.getUsuario(), servicio));

  }

  private List<Miembro> miembrosInteresados(Servicio servicio) {
    return this.miembros.stream().filter(m -> m.usuario.serviciosDeInteres().contains(servicio)).toList();
  }

  public void cerrarIncidente(Incidente incidente) {
    incidente.cerrar();
  }


  public boolean contieneUsuario(Usuario usuario) {
    return miembros.stream().map(Miembro::getUsuario).toList().contains(usuario);
  }

  public List<Incidente> incidentesPorEstado(EstadoIncidente estadoIncidente) {
    return this.incidentes.stream().filter(i -> i.getEstado() == estadoIncidente).toList();
  }

  public void notificarMiembros(Incidente incidente) {
    if (this.contieneServicioDeInteres(incidente.getServicioAsociado())) {
      this.getMiembros().forEach(miembro -> miembro.getUsuario().notificar(incidente));
    }
  }

}