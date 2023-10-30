package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.*;
import ar.edu.utn.frba.dds.repositorios.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class ControllerEntidades implements WithSimplePersistenceUnit {

  public ModelAndView mostrarEntidades(Request request, Response response) {


    Long id = request.session().attribute("user_id");
    if (id != null) {
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      List<Entidad> entidades = RepoEntidades.instance.listarEntidades();
      modelo.put("entidades", entidades);
      return new ModelAndView(modelo, "entidades.html.hbs");
    }
    else{
      response.redirect("/");
      return null;
    }

  }
}
