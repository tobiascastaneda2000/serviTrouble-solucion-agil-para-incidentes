package ar.edu.utn.frba.dds;

import java.time.LocalDateTime;

public class Incidente {

  EstadoIncidente estadoIncidente;
  TipoServicio servicio;
  String observacion;
  LocalDateTime fechaHoraAbre;

  public LocalDateTime getFechaHoraCierre() {
    return fechaHoraCierre;
  }

  LocalDateTime fechaHoraCierre;

  public EstadoIncidente getEstado(){
    return estadoIncidente;
  }

  public TipoServicio getServicio() {
    return servicio;
  }

  public String getObservacion() {
    return observacion;
  }

  public Incidente(TipoServicio servicio, String observacion) {
    this.servicio = servicio;
    this.observacion = observacion;
    this.fechaHoraAbre = LocalDateTime.now();
    this.estadoIncidente = EstadoIncidente.ABIERTO;
  }

  public void cerrar() {
    this.fechaHoraCierre = LocalDateTime.now();
    this.estadoIncidente = EstadoIncidente.CERRADO;
  }
}
