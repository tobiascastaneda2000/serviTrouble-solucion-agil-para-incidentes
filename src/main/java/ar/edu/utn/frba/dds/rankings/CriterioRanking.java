package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;

import java.util.Comparator;

public interface CriterioRanking {
  Comparator<Entidad> criterioDeComparacion();
}
