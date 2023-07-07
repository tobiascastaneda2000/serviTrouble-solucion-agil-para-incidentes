package ar.edu.utn.frba.dds.comunidad_e_incidentes;

import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.TipoServicio;

import java.time.Duration;
import java.time.LocalDateTime;

public class Incidente {

  EstadoIncidente estadoIncidente;
  String observacion;
  LocalDateTime fechaHoraAbre;
  LocalDateTime fechaHoraCierre;
  Servicio servicioAsociado;

  public LocalDateTime getFechaHoraAbre() {
    return fechaHoraAbre;
  }

  public LocalDateTime getFechaHoraCierre() {
    return fechaHoraCierre;
  }

  public EstadoIncidente getEstado() {
    return estadoIncidente;
  }

  public String getObservacion() {
    return observacion;
  }

  public Servicio getServicioAsociado() {
    return this.servicioAsociado;
  }

  public Incidente(String observacion, Servicio servicioAsociado) {
    this.observacion = observacion;
    this.fechaHoraAbre = LocalDateTime.now();
    this.estadoIncidente = EstadoIncidente.ABIERTO;
    this.servicioAsociado = servicioAsociado;
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
