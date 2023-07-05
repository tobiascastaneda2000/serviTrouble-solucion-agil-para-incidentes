package ar.edu.utn.frba.dds.comunidad_e_incidentes;

import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.TipoServicio;

import java.time.Duration;
import java.time.LocalDateTime;

public class Incidente {

  EstadoIncidente estadoIncidente;
  Servicio servicio;
  String observacion;

  public LocalDateTime getFechaHoraAbre() {
    return fechaHoraAbre;
  }

  LocalDateTime fechaHoraAbre;

  public LocalDateTime getFechaHoraCierre() {
    return fechaHoraCierre;
  }

  LocalDateTime fechaHoraCierre;

  public EstadoIncidente getEstado() {
    return estadoIncidente;
  }

  public Servicio getServicio() {
    return servicio;
  }

  public String getObservacion() {
    return observacion;
  }

  public Incidente(Servicio servicio, String observacion) {
    this.servicio = servicio;
    this.observacion = observacion;
    this.fechaHoraAbre = LocalDateTime.now();
    this.estadoIncidente = EstadoIncidente.ABIERTO;
  }

  public void cerrar() {
    this.fechaHoraCierre = LocalDateTime.now();
    this.estadoIncidente = EstadoIncidente.CERRADO;
  }

  //PARA RANKINGS
  public Duration diferenciaEntreAperturayCierre(){
    return Duration.between(this.fechaHoraAbre,this.fechaHoraCierre);
  }


}
