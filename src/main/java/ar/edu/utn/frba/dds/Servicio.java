package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Servicio {

  public List<Incidente> getHistorialIncidentes() {
    return historialIncidentes;
  }

  List<Incidente> historialIncidentes = new ArrayList<>();

  public Servicio(TipoServicio tipoServicio) {
    this.tipoServicio = tipoServicio;
  }

  TipoServicio tipoServicio;

  public void actualizarEstadoServicio(Incidente incidente) {
    historialIncidentes.add(incidente);
  }


  //RANKINGs

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
