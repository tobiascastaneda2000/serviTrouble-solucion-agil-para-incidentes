package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.comunidad.Comunidad;
import ar.edu.utn.frba.dds.comunidad.PermisoUsuario;
import ar.edu.utn.frba.dds.comunidad.Usuario;
import ar.edu.utn.frba.dds.entidades.Entidad;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import ar.edu.utn.frba.dds.lectorCSV.LectorCSVLectura;
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
     if(cuatroIncidentes.isEmpty()){
       modelo.put("mensajeIncidentes","No tienes incidentes cercanos para revisar");
      }
      modelo.put("incidentes",cuatroIncidentes);
      if(usuario.permisoUsuario.equals(PermisoUsuario.USUARIO_COMUN)){
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
        modelo.put("criterio1",criterio.get(0));
        modelo.put("criterio2",criterio.get(1));
        modelo.put("ranking1",entidades1Diez);
        modelo.put("ranking2",entidades2Diez);
        return new ModelAndView(modelo, "home.html.hbs");
      }
      if(usuario.permisoUsuario.equals(PermisoUsuario.ADMIN)){
        response.redirect("/admin-home");
        return null;
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

  public ModelAndView mostrarHomeAdmin(Request request, Response response){

    Long id = request.session().attribute("user_id");
    if (id != null) {
      Usuario usuario = RepoUsuarios.getInstance().getOne(id);
      if (usuario.permisoUsuario.equals(PermisoUsuario.ADMIN)) {
        Map<String, Object> modelo = new HashMap<>();
        modelo.put("anio", LocalDate.now().getYear());
        List<Usuario> usuarios = RepoUsuarios.getInstance().listarUsuarios();
        modelo.put("usuarios", usuarios);
        List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
        modelo.put("criterios", criterio);
        return new ModelAndView(modelo, "homeAdmin.html.hbs");
      } else {
        response.redirect("/home");
        return null;
      }
    }
    else{
      response.redirect("/");
      return null;
    }
  }



}
