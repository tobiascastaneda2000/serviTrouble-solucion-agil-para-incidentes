package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.rankings.CriterioRanking;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RepoEntidades {

  public static RepoEntidades instance = new RepoEntidades();
  List<Entidad> entidades = new ArrayList<>();

  public RepoEntidades getInstance() {
    return instance;
  }

  public List<Entidad> getEntidades() {
    return this.entidades;
  }

  public void guardarEntidad(Entidad entidad) {
    entidades.add(entidad);
  }

  public List<Entidad> realizarRankingSegunCriterio(CriterioRanking criterioRanking) {

    List<Entidad> entidades = this.entidades;
    Comparator<Entidad> criterio = criterioRanking.criterioDeComparacion();
    entidades.sort(criterio);
    return entidades;
  }


}
