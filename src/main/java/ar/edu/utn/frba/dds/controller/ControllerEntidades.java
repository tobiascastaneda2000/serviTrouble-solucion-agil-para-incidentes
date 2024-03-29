package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.comunidad.Usuario;
import ar.edu.utn.frba.dds.entidades.Entidad;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ar.edu.utn.frba.dds.repositorios.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class ControllerEntidades implements WithSimplePersistenceUnit {

  public ModelAndView mostrarEntidades(Request request, Response response) {
    Long id = Usuario.redirigirSesionNoIniciada(request, response);
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    List<Entidad> entidades = RepoEntidades.getInstance().getAll();
    System.out.println(entidades.size());
    modelo.put("entidades", entidades);
    Usuario usuario = RepoUsuarios.getInstance().getOne(id);
    modelo.put("nombreUsuario",usuario.usuario);
    return new ModelAndView(modelo, "entidades.html.hbs");
  }

}
