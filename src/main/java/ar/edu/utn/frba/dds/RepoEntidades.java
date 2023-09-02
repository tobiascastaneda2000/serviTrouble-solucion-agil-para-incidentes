package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.rankings.CriterioRanking;

import ar.edu.utn.frba.dds.rankings.LectorCSVEscritura;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

  // ---------------------------------RANKINGS----------------------------------------------------//

  public void generarRankingEnCsv(CriterioRanking criterio) {
    LectorCSVEscritura lectorCSVEscritura = new LectorCSVEscritura("src/main/java/ar/edu/utn/frba/dds/rankings/rankings-entidades.txt");
    lectorCSVEscritura.escribirRankings(ordenarEntidadesSegunCriterio(criterio));
  }

  public List<Entidad> ordenarEntidadesSegunCriterio(CriterioRanking criterioRanking) {

    List<Entidad> entidades = getEntidades();
    Comparator<Entidad> criterio = criterioRanking.criterioDeComparacion();
    entidades.sort(criterio);
    return entidades;
  }


}
