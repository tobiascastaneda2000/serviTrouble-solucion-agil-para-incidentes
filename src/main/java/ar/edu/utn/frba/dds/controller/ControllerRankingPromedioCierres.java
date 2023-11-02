package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.lectorCSV_y_entidadesPrestadoras.LectorCSVLectura;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.rankings.PromedioCierresSemanal;
import ar.edu.utn.frba.dds.repositorios.RepoEntidades;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerRankingPromedioCierres implements WithSimplePersistenceUnit {

  /*
  public ModelAndView mostrarRankingPromediosCierre(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    CriterioRanking criterioRanking = new PromedioCierresSemanal();
    List<Entidad> entidades = RepoEntidades.instance.listarEntidades();
    entidades.sort(criterioRanking.getCriterio());
    modelo.put("entidades", entidades);

    return new ModelAndView(modelo, "rankingsPromedioCierres.html.hbs");
  }*/

  public ModelAndView mostrarRankingPromedioCierre(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    //path del ranking csv
    LectorCSVLectura lector = new LectorCSVLectura("C:\\Users\\fmartinez\\Downloads\\2023-tpa-vi-no-grupo-07-prueba\\2023-tpa-vi-no-grupo-07\\src\\main\\java\\ar\\edu\\utn\\frba\\dds\\rankings\\rankings-entidades-pc.csv");
    List<Entidad> entidadesOrdenadas = lector.obtenerEntidadesDeCSV();
    modelo.put("entidades",entidadesOrdenadas);
    return new ModelAndView(modelo, "rankingsCantidadReportes.html.hbs");
  }
}
