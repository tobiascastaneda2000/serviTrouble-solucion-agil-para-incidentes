package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.entidades.Entidad;
import ar.edu.utn.frba.dds.lectorCSV.LectorCSVLectura;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.repositorios.RepoRanking;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.stream.Collectors;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerListadoRanking implements WithSimplePersistenceUnit {


  public ModelAndView mostrarListaDeRanking(Request request, Response response) {

    Long idsession = request.session().attribute("user_id");
    if (idsession != null) {
      String id = request.params(":id");
      CriterioRanking criterioRanking = RepoRanking.getInstance().getOne(Long.parseLong(id));
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
      modelo.put("criterios", criterio);
    /*
        List<Entidad> entidades = RepoEntidades.getInstance().getAll();
        entidades.sort(criterioRanking.getCriterio());
    */

      LectorCSVLectura lectorCSVLectura = new LectorCSVLectura(criterioRanking.getPath());
      List<Entidad> entidades = lectorCSVLectura.obtenerEntidadesDeCSV();

      List<Entidad> entidadesDiez = entidades.stream().limit(10).collect(Collectors.toList());

      if (entidadesDiez.get(0) != null) {
        Entidad entidadBorrar = entidadesDiez.get(0);
        modelo.put("primeraEntidad", entidadBorrar);
        entidadesDiez.remove(entidadBorrar);
      } else {
        modelo.put("primeraEntidad", "No existe entidad");
      }
      if (entidadesDiez.get(0) != null) {
        Entidad entidadBorrar = entidadesDiez.get(0);
        modelo.put("segundaEntidad", entidadBorrar);
        entidadesDiez.remove(entidadBorrar);
      } else {
        modelo.put("segundaEntidad", "No existe entidad");
      }
      if (entidadesDiez.get(0) != null) {
        Entidad entidadBorrar = entidadesDiez.get(0);
        modelo.put("terceraEntidad", entidadBorrar);
        entidadesDiez.remove(entidadBorrar);
      } else {
        modelo.put("terceraEntidad", "No existe entidad");
      }

      modelo.put("entidadesOrdenadas", entidadesDiez);
      modelo.put("nombre", criterioRanking.getNombre_criterio());
      return new ModelAndView(modelo, "rankingListadoEntidades.html.hbs");
    } else {
      response.redirect("/");
      return null;
    }
  }
}
