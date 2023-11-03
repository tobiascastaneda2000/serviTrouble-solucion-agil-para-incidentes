package ar.edu.utn.frba.dds.repositorios;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.rankings.LectorCSVEscritura;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RepoEntidades implements WithSimplePersistenceUnit { // Hacer que extienda de Repositorios

  public static RepoEntidades instance = new RepoEntidades();


  /*
  public RepoEntidades(DAOHibernate<Entidad> dao) { // Pasar a private y que usen el singleton
    super(dao);
  }
   */
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

  public void generarRankingEnCsvCantidadReportes(CriterioRanking criterio) {
    LectorCSVEscritura lectorCSVEscritura = new LectorCSVEscritura("src/main/java/ar/edu/utn/frba/dds/rankings/rankings-entidades-cr.csv");
    lectorCSVEscritura.escribirRankings(ordenarEntidadesSegunCriterio(criterio));
  }

  public void generarRankingEnCsvPromedioCierres(CriterioRanking criterio) {
    LectorCSVEscritura lectorCSVEscritura = new LectorCSVEscritura("src/main/java/ar/edu/utn/frba/dds/rankings/rankings-entidades-pc.csv");
    lectorCSVEscritura.escribirRankings(ordenarEntidadesSegunCriterio(criterio));
  }

  public List<Entidad> ordenarEntidadesSegunCriterio(CriterioRanking criterioRanking) {

    List<Entidad> entidades = listarEntidades();
    Comparator<Entidad> criterio = criterioRanking.getCriterio();
    entidades.sort(criterio);
    return entidades;
  }

  public void clear() {
    this.entidades = new ArrayList<>();
  }


  public List<Entidad> listarEntidades(){
    return entityManager().createQuery("from Entidad", Entidad.class)
        .getResultList();
  }

  public Entidad buscarPorId(int id) {

    return entityManager()
        .createQuery("from Entidad where id = :id", Entidad.class)
        .setParameter("id", id)
        .getResultList()
        .get(0);
  }
}
