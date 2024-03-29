package ar.edu.utn.frba.dds.repositorios;

import ar.edu.utn.frba.dds.entidades.Entidad;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.rankings.LectorCSVEscritura;

import java.util.Comparator;
import java.util.List;

public class RepoEntidades extends Repositorio<Entidad> { // Hacer que extienda de Repositorios

  public static RepoEntidades instance = null;


  private RepoEntidades() {
    super(Entidad.class);
  }

  public static RepoEntidades getInstance() {
    if (instance == null) {
      instance = new RepoEntidades();
    }
    return instance;
  }

  // ---------------------------------RANKINGS----------------------------------------------------//


  public void generarRankingCSVSegunCriterioEnUnPathEspecifico(CriterioRanking criterio, String path) {
    LectorCSVEscritura lectorCSVEscritura = new LectorCSVEscritura(path);
    lectorCSVEscritura.escribirRankings(ordenarEntidadesSegunCriterio(criterio));
  }

  public void generarRankingCSVSegunCriterio(CriterioRanking criterio) {
    LectorCSVEscritura lectorCSVEscritura = new LectorCSVEscritura(criterio.getPath());
    lectorCSVEscritura.escribirRankings(ordenarEntidadesSegunCriterio(criterio));
  }

  public List<Entidad> ordenarEntidadesSegunCriterio(CriterioRanking criterioRanking) {

    List<Entidad> entidades = getAll();
    Comparator<Entidad> criterio = criterioRanking.getCriterio();
    entidades.sort(criterio);
    return entidades;
  }
}
