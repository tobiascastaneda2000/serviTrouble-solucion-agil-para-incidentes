package ar.edu.utn.frba.dds.controller;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ar.edu.utn.frba.dds.comunidad.*;
import ar.edu.utn.frba.dds.repositorios.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ControllerLogin implements WithSimplePersistenceUnit {
  public ModelAndView mostrarLogin(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    return new ModelAndView(modelo, "login.html.hbs");
  }

  public ModelAndView crearSesion(Request request, Response response) {
    try {
      Usuario usuario = RepoUsuarios.getInstance().buscarPorUsuarioYContrasenia(
          request.queryParams("nombre"),
          request.queryParams("contrasenia"));

      request.session().attribute("user_id", usuario.getId());
      response.redirect("/home");
      return null;
    } catch (Exception e) {
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      return new ModelAndView(modelo, "loginError.html.hbs");
    }
  }


  public ModelAndView cerrarSesion(Request request, Response response) {
    request.session().removeAttribute("user_id");
    response.redirect("/");
    return null;
  }

}
