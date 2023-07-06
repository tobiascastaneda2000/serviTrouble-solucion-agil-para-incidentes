package ar.edu.utn.frba.dds.comunidad_e_incidentes;

import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.notificador.MedioNotificador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Comunidad {

  public Set<Miembro> miembros;

  List<Incidente> incidentes = new ArrayList<>();

  //CONSTRUCTOR

  public Comunidad() {
    this.miembros = new HashSet<>();
  }

  //GETTERS

  public List<Incidente> getIncidentes() {
    return this.incidentes;
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

  public Miembro getUnMiembro(Usuario usuario) {
    return this.miembros.stream().filter(m -> m.getUsuario() == usuario).findFirst().orElse(null);
  }

  public void abrirIncidente(Servicio servicio, String observaciones) {
    Incidente incidente = new Incidente(observaciones);
    servicio.actualizarEstadoServicio(incidente); //Para ranking
    efectivizarAperturaIncidente(incidente);
    notificarAperturaIncidente(servicio);
  }

  private void efectivizarAperturaIncidente(Incidente incidente) {
    incidentes.add(incidente);

  }

  public void notificarAperturaIncidente(Servicio servicio) {
    miembrosInteresados(servicio).forEach(m -> m.getUsuario().tipoNotificador.notificar("Apertura Incidente", m.getUsuario(), servicio));
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

  public List<Incidente> incidentesPorEstado(EstadoIncidente estadoIncidente){
    return this.incidentes.stream().filter(i->i.getEstado() == estadoIncidente).toList();
  }

}