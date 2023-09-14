package ar.edu.utn.frba.dds.comunidad_e_incidentes;

import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.TipoServicio;

import java.time.Duration;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Incidente implements Cloneable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Enumerated(EnumType.STRING)
  public EstadoIncidente estadoIncidente;
  public String observacion;
  public LocalDateTime fechaHoraAbre;
  public LocalDateTime fechaHoraCierre;

  //-------------------------CONSTRUCTOR----------------------------------------//
  protected Incidente() {
  }

  public Incidente(String observacion, Servicio servicioAsociado) {
    this.observacion = observacion;
    this.fechaHoraAbre = LocalDateTime.now();
    this.estadoIncidente = EstadoIncidente.ABIERTO;
    this.servicioAsociado = servicioAsociado;
  }

  @Transient
  Servicio servicioAsociado;

  public Servicio getServicioAsociado() {
    return servicioAsociado;
  }

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
