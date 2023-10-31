package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;

import java.util.Comparator;

public class CantidadReportesSemanal implements CriterioRanking {

  @Override
  public Comparator<Entidad> criterioDeComparacion() {
    return Comparator.comparing(Entidad::cantidadDeIncidentesReportados);
  }

  public String nombreDelRanking(){
    return "Cantidad de incidentes reportado";
  }
}
