package ar.edu.utn.frba.dds.comunidad_e_incidentes;

import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.TipoServicio;

import java.time.Duration;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Incidente implements Cloneable {
  @Id
  @GeneratedValue
  Long id;
  @Transient
  EstadoIncidente estadoIncidente;
  String observacion;
  LocalDateTime fechaHoraAbre;
  LocalDateTime fechaHoraCierre;

  public Servicio getServicioAsociado() {
    return servicioAsociado;
  }

  @Transient
  Servicio servicioAsociado;

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

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


  public Incidente(String observacion, Servicio servicioAsociado) {
    this.observacion = observacion;
    this.fechaHoraAbre = LocalDateTime.now();
    this.estadoIncidente = EstadoIncidente.ABIERTO;
    this.servicioAsociado = servicioAsociado;
  }

  //-------------------------CERRAR INCIDENTES---------------------------------//

  public void cerrar() {
    this.fechaHoraCierre = LocalDateTime.now();
    this.estadoIncidente = EstadoIncidente.CERRADO;
  }

  //-------------------------RANKINGS----------------------------------------//
  public boolean estaAbierto() {
    return estadoIncidente == EstadoIncidente.ABIERTO;
  }

  public boolean estaCerrado() {
    return !estaAbierto();
  }

  public Duration diferenciaEntreAperturayCierre() {
    return Duration.between(this.fechaHoraAbre, this.fechaHoraCierre);
  }


}
