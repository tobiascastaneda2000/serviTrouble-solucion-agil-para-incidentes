package ar.edu.utn.frba.dds.incidente;

import ar.edu.utn.frba.dds.TipoServicio;

import java.time.LocalDateTime;

public class Incidente {
  public TipoServicio getServicio() {
    return servicio;
  }

  TipoServicio servicio;
  String observacion;
  LocalDateTime fechaHoraAbre;
  LocalDateTime fechaHoraCierre;

  public Incidente(TipoServicio servicio, String observacion) {
    this.servicio = servicio;
    this.observacion = observacion;
    //this.estaAbierto = true;
    this.fechaHoraAbre = LocalDateTime.now();
  }

  public void cerrar() {
    this.fechaHoraCierre = LocalDateTime.now();
  }
}
