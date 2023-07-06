package ar.edu.utn.frba.dds.comunidad_e_incidentes;

import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.TipoServicio;

import java.time.Duration;
import java.time.LocalDateTime;

public class Incidente {

  EstadoIncidente estadoIncidente;
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

  public String getObservacion() {
    return observacion;
  }

  public Incidente(String observacion) {
    this.observacion = observacion;
    this.fechaHoraAbre = LocalDateTime.now();
    this.estadoIncidente = EstadoIncidente.ABIERTO;
  }

  public void cerrar() {
    this.fechaHoraCierre = LocalDateTime.now();
    this.estadoIncidente = EstadoIncidente.CERRADO;
  }

  //Para rankings
  public boolean estaAbierto() {
    return estadoIncidente == EstadoIncidente.ABIERTO;
  }

  public boolean estaCerrado() {
    return !estaAbierto();
  }

  //PARA RANKINGS
  public Duration diferenciaEntreAperturayCierre() {
    return Duration.between(this.fechaHoraAbre, this.fechaHoraCierre);
  }


}
