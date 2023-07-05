package ar.edu.utn.frba.dds.comunidad_e_incidentes;


import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.Establecimiento;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.notificador.MedioNotificador;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Miembro {

  public Usuario getUsuario() {
    return usuario;
  }

  public List<Entidad> entidadesAsociadas = new ArrayList<>();

  public Usuario usuario;
  public PermisoComunidad permisoComunidad;

  public MedioNotificador getTipoNotificador() {
    return tipoNotificador;
  }

  public MedioNotificador tipoNotificador;

  //List<Map<Integer, Integer>> horarios;

  public Miembro(Usuario usuario,
                 PermisoComunidad permisoComunidad,
                 MedioNotificador tipoNotificador) {
    this.usuario = usuario;
    this.permisoComunidad = permisoComunidad;
    //this.horarios = horarios;
    this.tipoNotificador = tipoNotificador;
  }

  public void informarIncidente(TipoServicio servicio, String observaciones) {

    List<Comunidad> comunidades = usuario.comunidadesPertenecientes();
    comunidades.forEach(c -> c.abrirIncidente(servicio, observaciones));
    //comunidades.forEach(c->c.notificarAperturaIncidente(servicio));

  }

  //1. Se agrega el inicidente solo a la comunidad del miembro
  // 2. Se notifica a a los miembros de todas la comunidades que
  // tengan ese servicio como interes y y se encuentre suscrito

  public void cerrarIncidente(Incidente incidente) {
    List<Comunidad> comunidades = usuario.comunidadesPertenecientes();
    comunidades.forEach(c -> c.cerrarIncidente(incidente));
  }

  public String getCorreo() {
    return this.usuario.getCorreo();

  }
/*
    public List<Incidente> getIncidentesPorEstado(EstadoIncidente estadoIncidente){
     return comunidadDelMiembro().incidentesPorEstado(estadoIncidente);
    }*/

  public List<Incidente> getIncidentes(EstadoIncidente estadoIncidente) {
    return comunidadDelMiembro().incidentesPorEstado(estadoIncidente);
  }

  public Comunidad comunidadDelMiembro() {
    return RepositorioComunidades.getInstance().devolverComunidad(this);
  }

  public List<TipoServicio> serviciosDeInteres() {
    return this.entidadesAsociadas.stream()
        .flatMap(e -> e.getEstablecimientos().stream()).toList().stream()
        .flatMap(e -> e.getServicio().stream()).toList();
  }

}
