package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.validaciones_password.MaxCantIntentosInicioSesionException;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import net.bytebuddy.matcher.StringMatcher;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ar.edu.utn.frba.dds.comunidad.*;
import ar.edu.utn.frba.dds.repositorios.*;

import javax.servlet.http.Cookie;
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
    Usuario usuario;
    String nombreUsuario;
    String contraseniaUsuario;

    try {
      nombreUsuario = request.queryParams("nombre");
      contraseniaUsuario = request.queryParams("contrasenia");

      usuario = RepoUsuarios.getInstance().buscarPorUsuarioYContrasenia(nombreUsuario, contraseniaUsuario);

      Usuario finalUsuario = usuario;
      /*
      withTransaction(() -> {
        finalUsuario.iniciarSesion(nombreUsuario, contraseniaUsuario);
      });*/

      request.session().attribute("user_id", usuario.getId());


      Integer intentos = request.session().attribute("intentos");
      if (intentos == null) {
        intentos = 0;
      }

      if (intentos < 3) {
        request.session().attribute("intentos", intentos);
        response.redirect("/home");
        return null;

      }
      else {
        return bloquearLogin(request,response);
      }


    } catch (Exception e) {

      Integer intentos = request.session().attribute("intentos");
      if (intentos == null) {
        intentos = 0;
      }

      request.session().attribute("intentos", intentos + 1);


      if (intentos >= 3) {
        return bloquearLogin( request,  response);
      } else {
        return mostrarErrorDeLogueo(request, response);
      }


    }

  }

  public ModelAndView mostrarErrorDeLogueo(Request request, Response response){
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    modelo.put("intentos", request.session().attribute("intentos"));
    return new ModelAndView(modelo, "loginError.html.hbs");
  }

  public ModelAndView bloquearLogin(Request request, Response response){
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    return new ModelAndView(modelo, "loginErrorBloqueo.html.hbs");

  }


  public ModelAndView cerrarSesion(Request request, Response response) {
    request.session().removeAttribute("user_id");
    request.session().removeAttribute("intentos");
    response.redirect("/");
    return null;
  }

}
