package ar.edu.utn.frba.dds.controller;


import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ar.edu.utn.frba.dds.comunidad.*;
import ar.edu.utn.frba.dds.repositorios.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import spark.ResponseTransformer;

public class ControllerAPIComunidades implements WithSimplePersistenceUnit{

  public ModelAndView mostrarComunidades(Request request, Response response) {
    Long id = Usuario.redirigirSesionNoIniciada(request, response);
    Usuario usuario = RepoUsuarios.getInstance().getOne(id);
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    List<Comunidad> comunidades = usuario.comunidadesPertenecientes();
    modelo.put("comunidades", comunidades);
    return new ModelAndView(modelo, "comunidades.html.hbs");
  }

  public Object listadoComunidades(Request request, Response response) {

    List<Comunidad> comunidades = RepositorioComunidades.getInstance().getAll();
    response.type("application/json");
    return comunidades;
  }


}
