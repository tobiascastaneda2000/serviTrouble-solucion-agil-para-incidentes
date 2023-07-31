package ar.edu.utn.frba.dds.comunidad_e_incidentes;


import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.Establecimiento;
import ar.edu.utn.frba.dds.RepoEntidades;
import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.notificador.MedioNotificador;

import ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref.Localizacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Miembro {

  public Usuario getUsuario() {
    return usuario;
  }

  public Usuario usuario;
  public PermisoComunidad permisoComunidad;

  public Miembro(Usuario usuario, PermisoComunidad permisoComunidad) {
    this.usuario = usuario;
    this.permisoComunidad = permisoComunidad;
  }

  public boolean estaCerca(Servicio servicio) throws IOException {
   return this.ubicacionMiembro() == RepoEntidades.instance.devolverLocalizacion(servicio);
  }

  //usar la interfaz
  public Localizacion ubicacionMiembro() throws IOException {
    return this.usuario.getLocalizacionInteres();
  }
}
