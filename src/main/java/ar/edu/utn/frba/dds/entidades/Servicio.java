package ar.edu.utn.frba.dds.entidades;

import ar.edu.utn.frba.dds.incidentes.Incidente;

import ar.edu.utn.frba.dds.repositorios.RepoIncidentes;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Servicio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  public String nombre;
  public List<Incidente> getHistorialIncidentes() {
    return historialIncidentes;
  }

  @OneToMany(mappedBy = "servicioAsociado")
  List<Incidente> historialIncidentes = new ArrayList<>();
  @Enumerated(EnumType.STRING)
  TipoServicio tipoServicio;

  @ManyToOne
  Establecimiento establecimiento;

  public Servicio(TipoServicio tipoServicio) {
    this.tipoServicio = tipoServicio;
    this.nombre = "";
  }

  public Servicio(String nombre, TipoServicio tipoServicio) {
    this.nombre = nombre;
    this.tipoServicio = tipoServicio;
  }

  protected Servicio() {
  }

  public Long getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public void aniadirIncidente(Incidente incidente) {
    this.historialIncidentes.add(incidente);
    RepoIncidentes.getInstance().add(incidente);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Servicio: ");
    sb.append("[id]: " + this.id + " ");
    sb.append("[nombre]: " + this.nombre + " ");
    sb.append("[incidentes]: { " + this.historialIncidentes.toString() + " }");
    return sb.toString();
  }


  //DEVOLVER LOS SERVICIOS DE UN MISMO INCIDENTES QUE PASE EL FILTRO DEL CRITERIO CANTIDAD REPORTES

  public List<Incidente> incidentesDe24Horas() {

    List<Incidente> lista = this.historialIncidentes;
    if (lista.size() <= 1)
      return lista;
    else {

      List<Incidente> listadoIncidentesFiltrados = new ArrayList<>();
      Iterator<Incidente> iterator = lista.iterator();
      Incidente incidenteAnterior = iterator.next();
      listadoIncidentesFiltrados.add(incidenteAnterior);

      while (iterator.hasNext()) {
        Incidente incidente = iterator.next();
        Duration duracion = Duration.between(incidenteAnterior.getFechaHoraAbre(), incidente.getFechaHoraAbre());

        if (incidenteAnterior.estaCerrado() || duracion.toHours() >= 24) {
          listadoIncidentesFiltrados.add(incidente);
        }

        incidenteAnterior = incidente;
      }

      return listadoIncidentesFiltrados;
    }
  }

  public Establecimiento getEstablecimiento() {
    return establecimiento;
  }
}
