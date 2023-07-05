package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.Entidad;

import java.util.Comparator;
import java.util.List;

public interface CriterioRanking {
  Comparator<Entidad> criterioDeComparacion();
}
