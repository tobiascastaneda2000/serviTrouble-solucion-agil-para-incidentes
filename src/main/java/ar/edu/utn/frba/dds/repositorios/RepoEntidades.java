package ar.edu.utn.frba.dds.repositorios;

import ar.edu.utn.frba.dds.comunidad_y_usuarios.Usuario;
import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.rankings.LectorCSVEscritura;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.ArrayList;
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

  public void generarRankingEnCsvCantidadReportes(CriterioRanking criterio) {
    LectorCSVEscritura lectorCSVEscritura = new LectorCSVEscritura("src/main/java/ar/edu/utn/frba/dds/rankings/rankings-entidades-cr.csv");
    lectorCSVEscritura.escribirRankings(ordenarEntidadesSegunCriterio(criterio));
  }

  public void generarRankingEnCsvPromedioCierres(CriterioRanking criterio) {
    LectorCSVEscritura lectorCSVEscritura = new LectorCSVEscritura("src/main/java/ar/edu/utn/frba/dds/rankings/rankings-entidades-pc.csv");
    lectorCSVEscritura.escribirRankings(ordenarEntidadesSegunCriterio(criterio));
  }

  public void generarRankingCSVSegunCriterio(CriterioRanking criterio){
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
