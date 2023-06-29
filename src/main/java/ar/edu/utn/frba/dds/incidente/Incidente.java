package ar.edu.utn.frba.dds.incidente;

import ar.edu.utn.frba.dds.TipoServicio;

import java.time.LocalDateTime;

public class Incidente {
  TipoServicio servicio;
  boolean estaAbierto;
  String observacion;
  LocalDateTime fechaHora;

  public Incidente(TipoServicio servicio, String observacion) {
    this.servicio = servicio;
    this.observacion = observacion;
    this.estaAbierto = true;
    this.fechaHora = LocalDateTime.now();
  }

  public void cerrar() {
    this.estaAbierto = false;
  }
}
