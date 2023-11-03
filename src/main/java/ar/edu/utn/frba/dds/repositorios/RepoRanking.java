package ar.edu.utn.frba.dds.repositorios;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RepoRanking implements WithSimplePersistenceUnit {

  public static RepoRanking instance = new RepoRanking();

  public RepoRanking getInstance() {
    return instance;
  }

  List<CriterioRanking> criterios = new ArrayList<>();


  public void agregarRanking(CriterioRanking criterioRanking) {
    criterios.add(criterioRanking);
  }

  public List<CriterioRanking> listarCriterio() {
    return entityManager().createQuery("from CriterioRanking", CriterioRanking.class)
        .getResultList();
  }

  public CriterioRanking buscarPorId(int id) {
    return entityManager()
        .createQuery("from CriterioRanking where id = :id", CriterioRanking.class)
        .setParameter("id", id)
        .getResultList()
        .get(0);
  }
}
