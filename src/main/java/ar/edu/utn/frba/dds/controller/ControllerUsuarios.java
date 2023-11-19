package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.repositorios.RepoRanking;
import ar.edu.utn.frba.dds.repositorios.RepoUsuarios;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import ar.edu.utn.frba.dds.comunidad.*;
import java.util.List;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerUsuarios implements WithSimplePersistenceUnit{

    public ModelAndView mostrarUsuarios(Request request, Response response) {
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      List<Usuario> usuarios = RepoUsuarios.getInstance().listarUsuarios();
      modelo.put("usuarios",usuarios);
      List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
      modelo.put("criterios", criterio);
      return new ModelAndView(modelo, "usuarios.html.hbs");
    }

    public ModelAndView modificarUsuario(Request request, Response response){
      String nombre = request.queryParams("usuario");
      String contrasenia = request.queryParams("contrasenia");
      String contacto = request.queryParams("contacto");
      Long id = request.session().attribute("user_id");

      Usuario usuario = RepoUsuarios.getInstance().getOne(id);

      usuario.setUsername(nombre);
      usuario.setContrasenia(contrasenia);
      usuario.setContacto(contacto);
      RepoUsuarios.getInstance().update(usuario);

      response.redirect("/home");
      return null;
    }

    public ModelAndView crearUsuario(Request request, Response response) {

      Long id = request.session().attribute("user_id");
      if (id != null) {
        Usuario usuariologueado = RepoUsuarios.getInstance().getOne(id);
        if (usuariologueado.permisoUsuario.equals(PermisoUsuario.ADMIN)) {

          String nombre = request.queryParams("nombre");
          String contrasenia = request.queryParams("contrasenia");
          String contacto = request.queryParams("contacto");
          if(RepoUsuarios.getInstance().buscarPorUsuario(nombre).size() >0){
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("anio", LocalDate.now().getYear());
            List<Usuario> usuarios = RepoUsuarios.getInstance().listarUsuarios();
            modelo.put("usuarios",usuarios);
            List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
            modelo.put("criterios", criterio);
            return new ModelAndView(modelo, "usuariosError.html.hbs");
          }
          else {
            Usuario usuario = new Usuario(nombre, contrasenia, contacto);
            usuario.permisoUsuario = PermisoUsuario.USUARIO_COMUN;
            persist(usuario);
            getTransaction().begin();
            entityManager().flush();
            getTransaction().commit();
            response.redirect("/admin-home");
          }
        }
        else{
          response.redirect("/home");
        }
      }
    return null;
    }

  public ModelAndView mostrarDetalleUsuario(Request request, Response response) {
    Long idsession = Usuario.redirigirSesionNoIniciada(request,response);
    String id = request.params(":id");
    Usuario usuario = RepoUsuarios.getInstance().getOne(Long.parseLong(id));
    Map<String, Object> modelo = new HashMap<>();
    if (usuario.medioNotificador != null) {
      modelo.put("medioNoti", usuario.medioNotificador.toString());
    } else {
      modelo.put("medioNoti", "");
    }
    modelo.put("entidadesInteres", usuario.getEntidadesInteres());
    modelo.put("horarios", usuario.getHorariosPlanificados());
    modelo.put("anio", LocalDate.now().getYear());
    modelo.put("usuarioDetalle", usuario);
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    return new ModelAndView(modelo, "usuarioDetalle.html.hbs");
  }

  public ModelAndView mostrarPerfil(Request request, Response response) {
    Long id = Usuario.redirigirSesionNoIniciada(request,response);
    Usuario usuario = RepoUsuarios.getInstance().getOne(id);
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("id", usuario.id);
    modelo.put("entidadesInteres", usuario.getEntidadesInteres());
    modelo.put("horarios", usuario.getHorariosPlanificados());
    modelo.put("anio", LocalDate.now().getYear());
    modelo.put("usuarioDetalle", usuario);
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    return new ModelAndView(modelo, "perfilUsuario.html.hbs");
  }

  public ModelAndView modificarPerfil(Request request, Response response) {
    Long id = Usuario.redirigirSesionNoIniciada(request,response);
    Usuario usuario = RepoUsuarios.getInstance().getOne((id));
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("id", usuario.id);
    modelo.put("entidadesInteres",usuario.getEntidadesInteres());
    modelo.put("horarios",usuario.getHorariosPlanificados());
    modelo.put("anio", LocalDate.now().getYear());
    modelo.put("usuarioDetalle",usuario);
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    return new ModelAndView(modelo, "modificarPerfil.html.hbs");
  }

  public ModelAndView eliminarUsuario(Request request, Response response) {
    Long idsession = Usuario.redirigirSesionNoIniciada(request,response);
    String id = request.params(":id");
    Usuario usuario = RepoUsuarios.getInstance().getOne(Long.parseLong(id));
    List<Miembro> miembrosDeUsuario = usuario.obtenerMiembros();
    try {
      getTransaction().begin();
      miembrosDeUsuario.forEach(m->remove(m));
      remove(usuario);
      getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    response.redirect("/home");
    return null;
  }
}
