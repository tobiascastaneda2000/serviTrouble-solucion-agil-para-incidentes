package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.comunidad.Usuario;
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
    Long idsession = Usuario.redirigirSesionNoIniciada(request, response);
    String id = request.params(":id");
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    //List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    //modelo.put("criterios", criterio);

    /*

        //List<Entidad> entidades = RepoEntidades.getInstance().getAll(); ya estaba comentado
        //entidades.sort(criterioRanking.getCriterio()); ya estaba comentado


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
    */

    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    CriterioRanking criterioRanking1 = RepoRanking.getInstance().getOne(Long.parseLong("1"));
    CriterioRanking criterioRanking2 = RepoRanking.getInstance().getOne(Long.parseLong("2"));
    LectorCSVLectura lectorCSVLectura1 = new LectorCSVLectura(criterioRanking1.getPath());
    LectorCSVLectura lectorCSVLectura2 = new LectorCSVLectura(criterioRanking2.getPath());
    List<Entidad> entidades1 = lectorCSVLectura1.obtenerEntidadesDeCSV();
    List<Entidad> entidades2 = lectorCSVLectura2.obtenerEntidadesDeCSV();
    List<Entidad> entidades1Diez = entidades1.stream().limit(10).collect(Collectors.toList());
    List<Entidad> entidades2Diez = entidades2.stream().limit(10).collect(Collectors.toList());
    modelo.put("criterio1", criterio.get(0));
    modelo.put("criterio2", criterio.get(1));
    modelo.put("ranking1", entidades1Diez);
    modelo.put("ranking2", entidades2Diez);

    return new ModelAndView(modelo, "rankingListadoEntidades.html.hbs");
  }

}
