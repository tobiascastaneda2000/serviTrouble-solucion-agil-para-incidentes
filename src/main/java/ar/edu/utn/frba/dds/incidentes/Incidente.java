package ar.edu.utn.frba.dds.incidentes;

import ar.edu.utn.frba.dds.entidades_y_servicios.Servicio;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Incidente implements WithSimplePersistenceUnit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;
  @Enumerated(EnumType.STRING)
  public EstadoIncidente estadoIncidente;
  public String observacion;
  public LocalDateTime fechaHoraAbre;

  public void setFechaHoraAbre(LocalDateTime fechaHoraAbre) {
    this.fechaHoraAbre = fechaHoraAbre;
  }

  public void setFechaHoraCierre(LocalDateTime fechaHoraCierre) {
    this.fechaHoraCierre = fechaHoraCierre;
  }

  public LocalDateTime fechaHoraCierre;

  @Transient
  public String fechaApertura;

  public String getFechaApertura() {
    return fechaApertura;
  }

  @Transient
  public String fechaCierre;

  public String getFechaCierre() {
    return fechaCierre;
  }

  //-------------------------CONSTRUCTOR----------------------------------------//
  protected Incidente() {
  }

  public Incidente(String observacion, Servicio servicioAsociado) {
    this.observacion = observacion;
    this.fechaHoraAbre = LocalDateTime.now();
    this.estadoIncidente = EstadoIncidente.ABIERTO;
    this.servicioAsociado = servicioAsociado;
  }

  @ManyToOne
  @JoinColumn(name = "servicio_id")
  Servicio servicioAsociado;

  public Servicio getServicioAsociado() {
    return servicioAsociado;
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
  public Long getId(){
    return id;
  }


  //-------------------------CERRAR INCIDENTES---------------------------------//

  public void cerrar() {

    this.fechaHoraCierre = LocalDateTime.now();
    this.estadoIncidente = EstadoIncidente.CERRADO;
    persist(this);
    entityManager().getTransaction().begin();
    entityManager().flush();
    entityManager().getTransaction().commit();
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Incidente: ");
    sb.append("[id]: "+this.id+" ");
    sb.append("[estado]: "+this.estadoIncidente+" ");
    sb.append("[obs]: "+this.observacion+" ");
    return sb.toString();
  }

}
