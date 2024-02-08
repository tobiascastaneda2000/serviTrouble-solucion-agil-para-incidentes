package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.notificador.Horario;
import ar.edu.utn.frba.dds.rankings.CriterioRanking;
import ar.edu.utn.frba.dds.repositorios.RepoRanking;
import ar.edu.utn.frba.dds.repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.repositorios.Repositorio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import ar.edu.utn.frba.dds.comunidad.*;

import java.util.List;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import java.util.stream.Collectors;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerUsuarios implements WithSimplePersistenceUnit {

  public ModelAndView mostrarUsuarios(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    List<Usuario> usuarios = RepoUsuarios.getInstance().listarUsuarios();
    modelo.put("usuarios", usuarios);
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    return new ModelAndView(modelo, "usuarios.html.hbs");
  }

  public ModelAndView modificarUsuario(Request request, Response response) {

    String hora;
    String minuto;
    String contrasenia = request.queryParams("contrasenia");
    String contacto = request.queryParams("contacto");
    String horario = request.queryParams("horario");

    try {
      String[] partes = horario.split(":");
      hora = partes[0];
      minuto = partes[1];
    }catch (Exception e){
      minuto = null;
      hora = null;
    }


    Long id = request.session().attribute("user_id");

    Usuario usuario = RepoUsuarios.getInstance().getOne(id);

    String finalHora = hora;
    String finalMinuto = minuto;
    withTransaction(() -> {
      usuario.setContrasenia(contrasenia);
      usuario.setContacto(contacto);
      if(usuario.horariosPlanificados.size()<5){
        if(finalHora != null && finalMinuto !=null){
          usuario.agregarHorario(new Horario(Integer.parseInt(finalHora),Integer.parseInt(finalMinuto)));
        }
      }
      RepoUsuarios.getInstance().update(usuario);
    });

    response.redirect("/profile");
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
        if (RepoUsuarios.getInstance().buscarPorUsuario(nombre).size() > 0) {
          Map<String, Object> modelo = new HashMap<>();
          modelo.put("anio", LocalDate.now().getYear());
          List<Usuario> usuarios = RepoUsuarios.getInstance().listarUsuarios();
          modelo.put("usuarios", usuarios);
          List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
          modelo.put("criterios", criterio);
          return new ModelAndView(modelo, "usuariosError.html.hbs");
        } else {
          withTransaction(() -> {
            Usuario usuario = new Usuario(nombre, contrasenia, contacto);
            usuario.permisoUsuario = PermisoUsuario.USUARIO_COMUN;
            RepoUsuarios.getInstance().add(usuario);
          });
          response.redirect("/admin/home");
        }
      } else {
        response.redirect("/home");
      }
    }
    return null;
  }

  public ModelAndView mostrarDetalleUsuario(Request request, Response response) {
    Long idsession = Usuario.redirigirSesionNoIniciada(request, response);
    String id = request.params(":id");
    Usuario usuario = RepoUsuarios.getInstance().getOne(Long.parseLong(id));
    Usuario usuariosession = RepoUsuarios.getInstance().getOne(Long.parseLong(idsession.toString()));
    if (usuariosession.permisoUsuario.equals(PermisoUsuario.ADMIN)) {
      Map<String, Object> modelo = new HashMap<>();
      if (usuario.medioNotificador != null) {
        modelo.put("medioNoti", usuario.medioNotificador.getNombre());
      } else {
        modelo.put("medioNoti", "");
      }
      modelo.put("entidadesInteres", usuario.getEntidadesInteres());
      modelo.put("horarios", usuario.getHorariosPlanificados());
      modelo.put("anio", LocalDate.now().getYear());
      modelo.put("usuarioDetalle", usuario);
      List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
      modelo.put("criterios", criterio);
      modelo.put("nombreUsuario",usuariosession.usuario);
      return new ModelAndView(modelo, "usuarioDetalle.html.hbs");
    }
    else{
      response.redirect("/home");
      return null;
    }
  }

  public ModelAndView mostrarPerfil(Request request, Response response) {
    Long id = Usuario.redirigirSesionNoIniciada(request, response);
    Usuario usuario = RepoUsuarios.getInstance().getOne(id);
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("id", usuario.id);
    modelo.put("entidadesInteres", usuario.getEntidadesInteres());
    modelo.put("horarios", usuario.getHorariosPlanificados());
    modelo.put("anio", LocalDate.now().getYear());
    modelo.put("usuarioDetalle", usuario);
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    modelo.put("nombreUsuario",usuario.usuario);
    return new ModelAndView(modelo, "perfilUsuario.html.hbs");
  }

  public ModelAndView modificarPerfil(Request request, Response response) {
    Long id = Usuario.redirigirSesionNoIniciada(request, response);
    Usuario usuario = RepoUsuarios.getInstance().getOne((id));
    Map<String, Object> modelo = new HashMap<>();
    if(usuario.horariosPlanificados.size()==0){
      modelo.put("horarioVacio", "No tenés horarios configurados");
    }
    modelo.put("id", usuario.id);
    modelo.put("entidadesInteres", usuario.getEntidadesInteres());
    modelo.put("horarios", usuario.getHorariosPlanificados());
    modelo.put("anio", LocalDate.now().getYear());
    modelo.put("usuarioDetalle", usuario);
    List<CriterioRanking> criterio = RepoRanking.getInstance().getAll();
    modelo.put("criterios", criterio);
    modelo.put("nombreUsuario",usuario.usuario);
    return new ModelAndView(modelo, "modificarPerfil.html.hbs");
  }

  public ModelAndView eliminarUsuario(Request request, Response response) {
    Long idsession = Usuario.redirigirSesionNoIniciada(request, response);
    Usuario usuariosession = RepoUsuarios.getInstance().getOne(Long.parseLong(idsession.toString()));
    if (usuariosession.permisoUsuario.equals(PermisoUsuario.ADMIN)) {
      String id = request.params(":id");
      Usuario usuario = RepoUsuarios.getInstance().getOne(Long.parseLong(id));
      List<Miembro> miembrosDeUsuario = usuario.obtenerMiembros();
      withTransaction(() -> {
        miembrosDeUsuario.forEach(m -> remove(m));
        remove(usuario);
      });
      response.redirect("/admin/home");
      return null;
    } else {
      return null;
    }
  }




  public ModelAndView borrarHorario(Request request, Response response) {

    Long id = request.session().attribute("user_id");
    String hora = request.queryParams("hora");
    String minuto = request.queryParams("minuto");
    Usuario usuario = RepoUsuarios.getInstance().getOne(id);

    withTransaction(() -> {

      Horario horario = usuario.horariosPlanificados.stream().filter(h->h.equals(new Horario(Integer.parseInt(hora),Integer.parseInt(minuto))))
          .collect(Collectors.toList()).get(0);
      usuario.sacarHorario(horario);

      RepoUsuarios.getInstance().update(usuario);
    });

    response.redirect("/profile/modificacion");
    return null;
  }
}
