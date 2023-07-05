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
      Comparator<Incidente> criterioPrimerIncidenteOcurridoNoCerrado = Comparator.comparing(Incidente::getFechaHoraAbre);
      lista.sort(criterioPrimerIncidenteOcurridoNoCerrado);

      int i = 0;
      Incidente incidente;
      Incidente incidenteAnterior = null;
      List<Incidente> listadoIncidentesAQuitar = new ArrayList<>();
      for (i = 0; i < lista.size(); i++) {

        incidente = lista.get(i);

        if(i>0 ) {
          Duration duracion = Duration.between(incidenteAnterior.getFechaHoraAbre(), incidente.getFechaHoraAbre());
          if (incidenteAnterior.estaAbierto() && duracion.toHours() < 24) {
            listadoIncidentesAQuitar.add(incidente);
          }

        }

        incidenteAnterior = incidente;
      }

      lista.removeAll(listadoIncidentesAQuitar);

      return lista;
    }

  }
}
