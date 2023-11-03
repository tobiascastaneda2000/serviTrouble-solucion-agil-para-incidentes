package ar.edu.utn.frba.dds.repositorios;

import ar.edu.utn.frba.dds.rankings.CriterioRanking;

public class RepoRanking extends Repositorio<CriterioRanking> {

  private static RepoRanking instance = null;

  private RepoRanking() {
    super(CriterioRanking.class);
  }

  public static RepoRanking getInstance() {
    if (instance == null) {
      instance = new RepoRanking();
    }
    return instance;
  }
}
