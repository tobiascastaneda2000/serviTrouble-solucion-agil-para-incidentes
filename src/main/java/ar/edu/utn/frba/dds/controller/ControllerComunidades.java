package ar.edu.utn.frba.dds.controller;


import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.entidades_y_servicios.Establecimiento;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.text.CompactNumberFormat;
import java.util.ArrayList;
import net.bytebuddy.matcher.StringMatcher;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.*;
import ar.edu.utn.frba.dds.repositorios.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ControllerComunidades implements WithSimplePersistenceUnit {

  public ModelAndView mostrarComunidades(Request request, Response response) {

    Long id = request.session().attribute("user_id");
    if (id != null) {
      Usuario usuario = RepoUsuarios.getInstance().getOne(id);
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      List<Comunidad> comunidades = usuario.comunidadesPertenecientes();
      modelo.put("comunidades", comunidades);
      return new ModelAndView(modelo, "comunidades.html.hbs");
    }
    else{
      response.redirect("/");
      return null;
    }

  }


  public ModelAndView mostrarIncidentes(Request request, Response response) {


    Long idsession = request.session().attribute("user_id");
    if (idsession != null) {
      String id = request.params(":id");
      Comunidad comunidad = RepositorioComunidades.getInstance().getOne(Long.parseLong(id));
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      List<Incidente> incidentesAbiertos = comunidad.incidentes;
      List<Incidente> incidentesCerrados = comunidad.incidentesCerrados;
      modelo.put("incidentesAbiertos", incidentesAbiertos);
      modelo.put("incidentesCerrados", incidentesCerrados);
      return new ModelAndView(modelo, "incidentes.html.hbs");
    }
    else{
      response.redirect("/");
      return null;
    }
  }


  public ModelAndView verComunidadesAdministrables(Request request, Response response){

    Long id = request.session().attribute("user_id");
    if (id != null) {
      Usuario usuario = RepoUsuarios.getInstance().getOne(id);
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      List<Comunidad> comunidades = usuario.comunidadesPertenecientes();
      List<Comunidad> comunidadesAdmin = comunidades.stream().filter(c->c.miembroEsAdmin(usuario)).toList();
      if(comunidadesAdmin.isEmpty()){
        String vacio = "No eres administrador de ninguna comunidad";
        modelo.put("vacio",vacio);
      }
      modelo.put("comunidades", comunidadesAdmin);
      return new ModelAndView(modelo, "comunidadesAdministrables.html.hbs");
    }
    else{
      response.redirect("/");
      return null;
    }
  }


  public ModelAndView verComunidadAdministrable(Request request, Response response){

    Long idsession = request.session().attribute("user_id");
    if (idsession != null) {
      String id = request.params(":id");
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      Comunidad comunidad = RepositorioComunidades.getInstance().getOne(Long.parseLong(id));
      modelo.put("comunidad",comunidad);
      return new ModelAndView(modelo, "verDetalleComunidad.html.hbs");
    }
    else{
      response.redirect("/");
      return null;
    }
  }

  public ModelAndView verMiembros(Request request, Response response){

    Long idsession = request.session().attribute("user_id");
    if (idsession != null) {
      String id = request.params(":id");
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      Comunidad comunidad = RepositorioComunidades.getInstance().getOne(Long.parseLong(id));
      List<Usuario> usuarios = comunidad.miembros.stream().map(m->m.usuario).toList();
      modelo.put("usuarios",usuarios);
      return new ModelAndView(modelo, "verUsuariosComunidad.html.hbs");
    }
    else{
      response.redirect("/");
      return null;
    }
  }

  public ModelAndView eliminarMiembro(Request request, Response response){

    Long idsession = request.session().attribute("user_id");
    if (idsession != null) {
      String id = request.params(":id");
      String idusuario = request.queryParams("idusuario");
      Comunidad comunidad = RepositorioComunidades.getInstance().getOne(Long.parseLong(id));
      Usuario usuario = RepoUsuarios.getInstance().buscarUsuarioPorID(Long.parseLong(idusuario));
      Miembro miembro = comunidad.miembros.stream().filter(m->m.usuario.equals(usuario)).toList().get(0);
      getTransaction().begin();
      comunidad.miembros.remove(miembro);
      remove(miembro);
      getTransaction().commit();
      response.redirect("/admin-comunidades");
      return null;
    }
    else{
      response.redirect("/");
      return null;
    }
  }


}
