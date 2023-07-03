package ar.edu.utn.frba.dds;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Comunidad {

  public Set<Miembro> miembros;
  List<Incidente> incidentesAResolver = new ArrayList<>();
  List<Incidente> incidentesResueltos = new ArrayList<>();

  //CONSTRUCTOR

  public Comunidad() {
    this.miembros = new HashSet<>();
  }

  //GETTERS

  public List<Incidente> getIncidentesAResolver() {
    return incidentesAResolver;
  }

  public List<Incidente> getIncidentesResueltos() {
    return incidentesResueltos;
  }

  public Set<Miembro> getMiembros() {
    return miembros;
  }

  //METODOS

  public void registrarMiembro(Usuario usuario,
                               //List<Map<Integer, Integer>> horarios,
                               MedioNotificador medio) {
    miembros.add(new Miembro(usuario, PermisoComunidad.USUARIO_COMUNIDAD,
        //horarios,
        medio));
  }
  //ACLARACION: siempre que se registra un miembro desde comunidado su permiso es USUARIO_COMUNIDAD

  boolean contieneMiembro(Miembro miembro) {
    return miembros.contains(miembro);
  }

  public void abrirIncidente(TipoServicio servicio, String observaciones) {
    Incidente incidente = new Incidente(servicio, observaciones);
    efectivizarAperturaIncidente(incidente);
    notificarAperturaIncidente(incidente);
  }

  private void efectivizarAperturaIncidente(Incidente incidente) {
    incidentesAResolver.add(incidente);

  }

  public void notificarAperturaIncidente(Incidente incidente) {
    miembrosInteresados(incidente.getServicio()).forEach(m -> m.tipoNotificador.notificar("Apertura Incidente", m.getCorreo(), incidente));
  }

  private List<Miembro> miembrosInteresados(TipoServicio servicio) {
    return this.miembros.stream().filter(m -> m.usuario.getServiciosDeInteres().contains(servicio)).toList();
  }

  public void cerrarIncidente(Incidente incidente) {
    efectivizarCierreIncidente(incidente);
  }

  private void efectivizarCierreIncidente(Incidente incidente) {
    incidente.cerrar();
    incidentesAResolver.remove(incidente);
    incidentesResueltos.add(incidente);
  }


  public boolean contieneUsuario(Usuario usuario) {
    return miembros.stream().map(Miembro::getUsuario).toList().contains(usuario);
  }


  public List<Incidente> incidentesPorEstado(EstadoIncidente estadoIncidente) {
    return this.incidentesAResolver.stream().filter(i->i.getEstado() == estadoIncidente).toList();
  }
}