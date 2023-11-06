package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.comunidad_y_usuarios.Comunidad;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.PermisoUsuario;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.Usuario;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.repositorios.RepoRanking;
import ar.edu.utn.frba.dds.repositorios.RepoUsuarios;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.stream.Collectors;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
public class DemoControllerHome implements WithSimplePersistenceUnit {

  public ModelAndView mostrarInicio(Request request, Response response) {
    Long id = request.session().attribute("user_id");
    if (id == null) {
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      return new ModelAndView(modelo, "index.html.hbs");
    }
    else{
      response.redirect("/home");
      return null;
    }
  }

  public ModelAndView mostrarHome(Request request, Response response) {

    Long id = request.session().attribute("user_id");
    if (id != null) {
      Usuario usuario = RepoUsuarios.getInstance().getOne(id);
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      List<Comunidad> comunidades = usuario.comunidadesPertenecientes();
      List<Incidente> incidentesCercanos = comunidades.stream().flatMap(comunidad -> comunidad.getIncidentes().stream() )
          .filter(incidente -> usuario.esIncidenteCercano(incidente) ).collect(Collectors.toList());
      List<Incidente> cuatroIncidentes = incidentesCercanos.stream().limit(4).collect(Collectors.toList());
      modelo.put("incidentes",cuatroIncidentes);
      if(usuario.permisoUsuario.equals(PermisoUsuario.USUARIO_COMUN)){
        List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
        modelo.put("criterios", criterio);
        return new ModelAndView(modelo, "home.html.hbs");
      }
      if(usuario.permisoUsuario.equals(PermisoUsuario.ADMIN)){
        return new ModelAndView(modelo, "homeAdmin.html.hbs");
      }
      else{
        response.redirect("/");
        return null;
      }
    }
    else{
      response.redirect("/");
      return null;
    }
  }
}
