package ar.edu.utn.frba.dds.controller;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
public class DemoControllerHome implements WithSimplePersistenceUnit {

  public ModelAndView mostrarInicio(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    return new ModelAndView(modelo, "index.html.hbs");
  }

  public ModelAndView mostrarHome(Request request, Response response) {

    Long id = request.session().attribute("user_id");
    if (id != null) {
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      return new ModelAndView(modelo, "home.html.hbs");
    }
    else{
      response.redirect("/");
      return null;
    }
  }
}