package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.repositorios.RepoUsuarios;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.*;
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
      return new ModelAndView(modelo, "usuarios.html.hbs");
    }

    public ModelAndView crearUsuario(Request request, Response response) {
    String nombre = request.queryParams("nombre");
    String contrasenia = request.queryParams("contrasenia");
    String contacto = request.queryParams("contacto");
    if(RepoUsuarios.getInstance().buscarPorUsuario(nombre).size() >0){
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("anio", LocalDate.now().getYear());
      List<Usuario> usuarios = RepoUsuarios.getInstance().listarUsuarios();
      modelo.put("usuarios",usuarios);
      return new ModelAndView(modelo, "usuariosError.html.hbs");
    }
    else {
      Usuario usuario = new Usuario(nombre, contrasenia, contacto);
      usuario.permisoUsuario = PermisoUsuario.USUARIO_COMUN;
      persist(usuario);
      getTransaction().begin();
      entityManager().flush();
      getTransaction().commit();
      response.redirect("/usuarios");
    }

    return null;
    }

  public ModelAndView mostrarDetalleUsuario(Request request, Response response) {
    String id = request.params(":id");
    Usuario usuario = RepoUsuarios.getInstance().buscarUsuarioPorID(Long.parseLong(id));
    Map<String, Object> modelo = new HashMap<>();
    if(usuario.medioNotificador != null){
      modelo.put("medioNoti",usuario.medioNotificador.toString());
    }
    else{
      modelo.put("medioNoti","");
    }
    modelo.put("entidadesInteres",usuario.getEntidadesInteres());
    modelo.put("horarios",usuario.getHorariosPlanificados());
    modelo.put("anio", LocalDate.now().getYear());
    modelo.put("usuarioDetalle",usuario);
    return new ModelAndView(modelo, "usuarioDetalle.html.hbs");
  }


  public ModelAndView eliminarUsuario(Request request, Response response) {
    String id = request.params(":id");
    Usuario usuario = RepoUsuarios.getInstance().buscarUsuarioPorID(Long.parseLong(id));
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
