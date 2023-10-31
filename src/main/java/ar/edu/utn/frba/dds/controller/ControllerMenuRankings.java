package ar.edu.utn.frba.dds.controller;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ControllerMenuRankings implements WithSimplePersistenceUnit {

  public ModelAndView mostrarTodosRankings(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    return new ModelAndView(modelo, "rankings.html.hbs");
  }
}
