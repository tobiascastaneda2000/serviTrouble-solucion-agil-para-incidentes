package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.entidades_y_servicios.Establecimiento;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import ar.edu.utn.frba.dds.entidades_y_servicios.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.*;
import ar.edu.utn.frba.dds.repositorios.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
public class ControllerIncidentes implements WithSimplePersistenceUnit {

  public ModelAndView mostrarIncidenteCreado(Request request, Response response) {

    Long id = request.session().attribute("user_id");
    if (id != null) {
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      return new ModelAndView(modelo, "incidenteCreado.html.hbs");
    }
    else{
      response.redirect("/");
      return null;
    }

  }

  public ModelAndView errorIncidente(Request request, Response response) {

    Long id = request.session().attribute("user_id");
    if (id != null) {
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      return new ModelAndView(modelo, "incidenteNoCreado.html.hbs");
    }
    else{
      response.redirect("/");
      return null;
    }

  }

}
