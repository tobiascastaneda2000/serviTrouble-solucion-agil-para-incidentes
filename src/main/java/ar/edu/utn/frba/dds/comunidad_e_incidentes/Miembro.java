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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Miembro {

  @Id
  @GeneratedValue
  Long id;
  public Usuario getUsuario() {
    return usuario;
  }
  @Transient
  public Usuario usuario;
  @Transient
  public PermisoComunidad permisoComunidad;

  public Miembro(Usuario usuario, PermisoComunidad permisoComunidad) {
    this.usuario = usuario;
    this.permisoComunidad = permisoComunidad;
  }

}
