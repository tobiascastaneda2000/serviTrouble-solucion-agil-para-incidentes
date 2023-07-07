package ar.edu.utn.frba.dds.comunidad_e_incidentes;


import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.Establecimiento;
import ar.edu.utn.frba.dds.RepoEntidades;
import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.notificador.MedioNotificador;

import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.Localizacion;
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

  //List<Map<Integer, Integer>> horarios;

  public Miembro(Usuario usuario, PermisoComunidad permisoComunidad) {
    this.usuario = usuario;
    this.permisoComunidad = permisoComunidad;
    //this.horarios = horarios;
  }

  //1. Se agrega el inicidente solo a la comunidad del miembro
  // 2. Se notifica a a los miembros de todas la comunidades que
  // tengan ese servicio como interes y y se encuentre suscrito

  public String getCorreo() {
    return this.usuario.getCorreo();

  }
/*
    public List<Incidente> getIncidentesPorEstado(EstadoIncidente estadoIncidente){
     return comunidadDelMiembro().incidentesPorEstado(estadoIncidente);
    }*/
 RepoEntidades repoEntidad;
  public boolean estaCerca(Servicio servicio){
   return this.ubicacionMiembro() == repoEntidad.devolverLocalizacion(servicio);
  }

  //usar la interfaz
  public Localizacion ubicacionMiembro(){
    return this.usuario.getLocalizacionInteres();
  }
}
