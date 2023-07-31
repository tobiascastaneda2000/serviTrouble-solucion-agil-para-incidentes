package ar.edu.utn.frba.dds.notificador;

import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;

public class Notificacion {

  Usuario usuario;
  Incidente incidente;

  public Notificacion(Usuario usuario, Incidente incidente) {
    this.usuario = usuario;
    this.incidente = incidente;
  }
  public void ejecutarse() {
    usuario.notificarIncidente(incidente);
    usuario.sacarNotificacion(this);
  }
}

