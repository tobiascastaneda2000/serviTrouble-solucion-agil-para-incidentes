package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.rankings.CantidadReportesSemanal;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.repositorios.RepoEntidades;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerRankingCantidadReportes implements WithSimplePersistenceUnit {

  public ModelAndView mostrarRankingCantidadReportes(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    CriterioRanking criterioRanking = new CantidadReportesSemanal();
    List<Entidad> entidades = RepoEntidades.instance.listarEntidades();
    entidades.sort(criterioRanking.getCriterio());
    modelo.put("entidades", entidades);
    return new ModelAndView(modelo, "rankingsCantidadReportes.html.hbs");
  }
}
