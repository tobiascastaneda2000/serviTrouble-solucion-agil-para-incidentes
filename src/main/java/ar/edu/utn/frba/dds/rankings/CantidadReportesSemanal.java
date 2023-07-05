package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.Entidad;

import java.util.Comparator;
import java.util.List;

public class CantidadReportesSemanal implements CriterioRanking {

  @Override
  public Comparator<Entidad> criterioDeComparacion() {
    return Comparator.comparing(Entidad::cantidadDeIncidentes);
  }
}
