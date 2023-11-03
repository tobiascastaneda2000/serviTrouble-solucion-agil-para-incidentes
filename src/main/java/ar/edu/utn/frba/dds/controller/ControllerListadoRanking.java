package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.entidades_y_servicios.Establecimiento;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.repositorios.RepoEntidades;
import ar.edu.utn.frba.dds.repositorios.RepoRanking;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerListadoRanking implements WithSimplePersistenceUnit {


  public ModelAndView mostrarListaDeRanking(Request request, Response response) {
    String id = request.params(":id");
    CriterioRanking criterioRanking = RepoRanking.getInstance().getOne(Long.parseLong(id));
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    List<Entidad> entidades = RepoEntidades.getInstance().getAll();
    entidades.sort(criterioRanking.getCriterio());
    modelo.put("entidadesOrdenadas", entidades);
    modelo.put("nombre", criterioRanking.getNombre_criterio());
    return new ModelAndView(modelo, "rankingListadoEntidades.html.hbs");
  }
}
