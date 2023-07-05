package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;

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

  public void actualizarEstadoServicio(Incidente incidente){
    historialIncidentes.add(incidente);
  }


  //RANKINGs

  public List<Incidente> incidentesDe24Horas() {

    List<Incidente> lista = this.historialIncidentes;
    if(lista.size()<=1)
     return lista;
    else {
      Comparator<Incidente> criterioPrimerIncidenteOcurridoNoCerrado = Comparator.comparing(Incidente::getFechaHoraAbre);
      lista.sort(criterioPrimerIncidenteOcurridoNoCerrado);

      return lista;

      //Falta filtrar incidentes cuyas aperturas no tengan mas de 24 horas (Sol ose dejara el primero)
      //Por conveniencia hacer aca tambien filtrado abiertocerrado
    }

  }
}
