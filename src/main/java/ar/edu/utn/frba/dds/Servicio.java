package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.RepositorioComunidades;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Servicio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  protected Servicio() {

  }


  public List<Incidente> getHistorialIncidentes() {
    return historialIncidentes;
  }
  @OneToMany
  @JoinColumn(name = "servicio_id")
  List<Incidente> historialIncidentes = new ArrayList<>();
  @Enumerated(EnumType.STRING)
  TipoServicio tipoServicio;

  public Servicio(TipoServicio tipoServicio) {
    this.tipoServicio = tipoServicio;
  }


  public void aniadirIncidente(Incidente incidente) {
    this.historialIncidentes.add(incidente);
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
}
