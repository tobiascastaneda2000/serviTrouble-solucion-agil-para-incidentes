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
import java.util.HashSet;
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
    modelo.put("nombreUsuario", usuario.usuario);

    return renderPage(modelo, "homeAdminV2.html.hbs", usuario);
  }

  public ModelAndView verComunidadesAdministrables(Request request, Response response) {
    Long id = Usuario.redirigirSesionNoIniciada(request, response);
    Usuario usuario = RepoUsuarios.getInstance().getOne(id);
    Map<String, Object> modelo = new HashMap<>();
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();

    modelo.put("criterios", criterio);
    modelo.put("anio", LocalDate.now().getYear());
    modelo.put("nombreUsuario", usuario.usuario);
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
    modelo.put("nombreUsuario", usuario.usuario);
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    Comunidad comunidad = RepositorioComunidades.getInstance().getOne(Long.parseLong(id));
    if (!usuario.comunidadesPertenecientes().contains(comunidad)) {
      response.redirect("/home");
    }
    List<Usuario> usuarios = comunidad.miembros.stream().map(m -> m.usuario).toList();
    modelo.put("nombreComunidad", comunidad.nombre);
    modelo.put("usuarios", usuarios);

    String filtro = request.queryParams("filtrado");
    if (filtro != null) {
      usuarios = usuarios.stream().filter(c -> c.getUsuario().toLowerCase().contains(filtro.toLowerCase())).toList();
      modelo.put("filtro", filtro);
    }


    String idPagina = request.queryParams("pagina");
    if (idPagina == null) {
      idPagina = "1";
    }
    int limiteInferior = (Integer.parseInt(idPagina) - 1) * 9;
    int limiteSuperior = limiteInferior + 9;

    int i = 0;
    int k = 1;
    HashSet<Integer> paginas = new HashSet<Integer>();
    paginas.add(1);
    for (Usuario usuarioN : usuarios) {
      i++;
      if (i > 9) {
        k++;
        paginas.add(k);
        i = 0;
      }
    }

    try {
      List<Usuario> usuariosPaginados = usuarios.subList(limiteInferior, limiteSuperior);
      modelo.put("usuarios", usuariosPaginados);
      modelo.put("paginas", paginas);
      return renderPage(modelo, "verUsuariosComunidad.html.hbs", usuario);
    } catch (Exception e) {
      try {
        List<Usuario> usuariosPaginados = usuarios.subList(limiteInferior, usuarios.size());
        if (usuariosPaginados.size() == 0) {
          throw new Exception("No hay usuarios para esta pagina");
        }
        modelo.put("usuarios", usuariosPaginados);
        modelo.put("paginas", paginas);
        return renderPage(modelo, "verUsuariosComunidad.html.hbs", usuario);
      } catch (Exception e2) {
        return renderPage(modelo, "verUsuariosComunidad.html.hbs", usuario);
      }
    }
  }

  public ModelAndView eliminarMiembro(Request request, Response response) {
    Long idsession = Usuario.redirigirSesionNoIniciada(request, response);
    Usuario usuariosession = RepoUsuarios.getInstance().getOne(idsession);
    String idusuario = request.queryParams("idusuario");
    String id = request.params(":id");
    Comunidad comunidad = RepositorioComunidades.getInstance().getOne(Long.parseLong(id));
    if (usuariosession.esAdmin()) {
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
    if (!usuario.esAdmin()) {
      return getErrorPage(usuario);
    }
    return new ModelAndView(modelo, template);
  }

}
