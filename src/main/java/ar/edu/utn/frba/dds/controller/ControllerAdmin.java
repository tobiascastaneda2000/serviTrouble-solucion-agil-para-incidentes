package ar.edu.utn.frba.dds.controller;
import ar.edu.utn.frba.dds.comunidad.Comunidad;
import ar.edu.utn.frba.dds.comunidad.Miembro;
import ar.edu.utn.frba.dds.comunidad.PermisoUsuario;
import ar.edu.utn.frba.dds.comunidad.Usuario;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.repositorios.RepoRanking;
import ar.edu.utn.frba.dds.repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.repositorios.RepositorioComunidades;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerAdmin implements WithSimplePersistenceUnit {

  public ModelAndView mostrarHome(Request request, Response response) {
    Long id = request.session().attribute("user_id");
    if (id == null) {
      response.redirect("/");
      return null;
    }

    Usuario usuario = RepoUsuarios.getInstance().getOne(id);
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    modelo.put("nombreUsuario",usuario.usuario);

    return renderPage(modelo, "homeAdminV2.html.hbs", usuario);
  }
  public ModelAndView verComunidadesAdministrables(Request request, Response response) {
    Long id = Usuario.redirigirSesionNoIniciada(request, response);
    Usuario usuario = RepoUsuarios.getInstance().getOne(id);
    Map<String, Object> modelo = new HashMap<>();
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();

    modelo.put("criterios", criterio);
    modelo.put("anio", LocalDate.now().getYear());
    modelo.put("nombreUsuario",usuario.usuario);
    List<Comunidad> comunidades = usuario.comunidadesPertenecientes();
    List<Comunidad> comunidadesAdmin = comunidades.stream().filter(c -> c.miembroEsAdmin(usuario)).toList();
    if (comunidadesAdmin.isEmpty()) {
      String vacio = "No eres administrador de ninguna comunidad";
      modelo.put("vacio", vacio);
    }
    modelo.put("comunidades", comunidadesAdmin);
    return renderPage(modelo, "comunidadesAdministrables.html.hbs", usuario);
  }

  public ModelAndView verMiembros(Request request, Response response) {
    Long idsession = Usuario.redirigirSesionNoIniciada(request, response);
    Usuario usuario = RepoUsuarios.getInstance().getOne(idsession);
    String id = request.params(":id");
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    modelo.put("nombreUsuario",usuario.usuario);
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    Comunidad comunidad = RepositorioComunidades.getInstance().getOne(Long.parseLong(id));
    if (!usuario.comunidadesPertenecientes().contains(comunidad)) {
      response.redirect("/home");
    }
    List<Usuario> usuarios = comunidad.miembros.stream().map(m -> m.usuario).toList();
    modelo.put("nombreComunidad", comunidad.nombre);
    modelo.put("usuarios", usuarios);
    return renderPage(modelo, "verUsuariosComunidad.html.hbs", usuario);
  }

  public ModelAndView eliminarMiembro(Request request, Response response) {
    Long idsession = Usuario.redirigirSesionNoIniciada(request, response);
    Usuario usuariosession = RepoUsuarios.getInstance().getOne(idsession);
    String idusuario = request.queryParams("idusuario");
    String id = request.params(":id");
    Comunidad comunidad = RepositorioComunidades.getInstance().getOne(Long.parseLong(id));
    if(usuariosession.esAdmin()) {
      return getErrorPage(usuariosession);
    }
    if (!usuariosession.comunidadesPertenecientes().contains(comunidad)) {
      response.redirect("/home");
    }
    Usuario usuario = RepoUsuarios.getInstance().buscarUsuarioPorID(Long.parseLong(idusuario));
    Miembro miembro = comunidad.miembros.stream().filter(m -> m.usuario.equals(usuario)).toList().get(0);
    withTransaction(() -> {
      comunidad.miembros.remove(miembro);
      remove(miembro);
    });
    response.redirect("/admin/comunidades");
    return null;
  }

  private ModelAndView getErrorPage(Usuario usuario) {
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("status", 401);
    modelo.put("error", "No tienes permisos para acceder a esta sección");
    modelo.put("nombreUsuario", usuario.usuario);
    return new ModelAndView(modelo, "error.html.hbs");
  }

  public ModelAndView renderPage(Map<String, Object> modelo, String template, Usuario usuario) {
    if(!usuario.esAdmin()) {
      return getErrorPage(usuario);
    }
    return new ModelAndView(modelo, template);
  }

}
