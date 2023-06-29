package ar.edu.utn.frba.dds.incidente;

import ar.edu.utn.frba.dds.TipoServicio;

public class Notificacion {

  String mensaje;
  TipoServicio servicio;

  public Notificacion(String mensaje, TipoServicio servicio) {
    this.mensaje = mensaje;
    this.servicio = servicio;
  }
}
