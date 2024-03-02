package ar.edu.utn.frba.dds.main;


import ar.edu.utn.frba.dds.NotificadorProgramado;
import ar.edu.utn.frba.dds.ServicesLocators.ServiceLocatorMedioNotificador;
import ar.edu.utn.frba.dds.ServicesLocators.ServiceLocatorUbicacion;
import ar.edu.utn.frba.dds.controller.*;
import ar.edu.utn.frba.dds.comunidad.*;
import ar.edu.utn.frba.dds.notificador.MailSender;
import ar.edu.utn.frba.dds.repositorios.RepositorioComunidades;
import ar.edu.utn.frba.dds.serviciolocalizacion.ServicioUbicacion1;
import com.google.gson.Gson;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import java.util.Set;
import java.time.LocalDateTime;
import ar.edu.utn.frba.dds.comunidad.Usuario;
import ar.edu.utn.frba.dds.repositorios.RepoUsuarios;

import javax.persistence.PersistenceException;

public class Routes implements WithSimplePersistenceUnit {

  public static void main(String[] args) {
    if(args.length==0) new Routes().start();
    else{
      if(args[0].equalsIgnoreCase("notificador")) new NotificadorProgramado().run();
    }
  }

  public void start() {
    System.out.println("Iniciando servidor");

    new ServiceLocatorUbicacion().setServicios("servicioUbicacion",new ServicioUbicacion1());
    new ServiceLocatorMedioNotificador().setServicios("mailSender", new MailSender());

    new Bootstrap().run();
    Spark.port(getHerokuAssignedPort());
    Spark.staticFileLocation("/public");

    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
    DemoControllerHome demoControllerhome = new DemoControllerHome();
    ControllerLogin controllerLogin = new ControllerLogin();
    ControllerEntidades controllerEntidades = new ControllerEntidades();
    ControllerEstablecimientos controllerEstablecimientos = new ControllerEstablecimientos();
    ControllerIncidentes controllerIncidentes = new ControllerIncidentes();
    ControllerComunidades controllerComunidades = new ControllerComunidades();
    ControllerListadoRanking controllerListadoRanking = new ControllerListadoRanking();
    ControllerUsuarios controllerUsuarios = new ControllerUsuarios();
    ControllerAdmin controllerAdmin = new ControllerAdmin();

    //LOGIN Y HOME
    Spark.get("/", demoControllerhome::mostrarInicio, engine);
    Spark.get("/login", controllerLogin::mostrarLogin, engine);
    Spark.post("/login", controllerLogin::crearSesion, engine);
    Spark.get("/sesion", controllerLogin::cerrarSesion);
    Spark.get("/home", demoControllerhome::mostrarHome, engine);
    Spark.get("/admin/home", demoControllerhome::mostrarHomeAdmin, engine);
    Spark.get("/admin/usuarios", controllerUsuarios::mostrarUsuarios, engine);
    Spark.get("/admin/rankings", controllerListadoRanking::mostrarListaDeRanking, engine);
    Spark.get("/admin/usuarios/nuevo", controllerUsuarios::cargarUsuario, engine);
    //Navegacion entre rakings
    Spark.post("/admin/usuarios", controllerUsuarios::crearUsuario, engine);
    //Spark.post("/admin/usuarios", controllerUsuarios::crearUsuario, engine);

    //APERTURA DE INCIDENTES
    Spark.get("/entidades", controllerEntidades::mostrarEntidades, engine);
    Spark.get("/entidades/:id", controllerEstablecimientos::mostrarEstablecimientos, engine);
    Spark.get("/establecimientos/:id", controllerEstablecimientos::cargarIncidente, engine);
    Spark.get("/entidades/:entidadId/establecimientos/:establecimientoId/incidentes", controllerEstablecimientos::mostrarIncidentes, engine);
    Spark.post("/establecimientos", controllerEstablecimientos::crearIncidente);

    //VISUALIZACION Y CIERRE DE INCIDENTES
    Spark.get("/comunidades", controllerComunidades::mostrarComunidades, engine);
    Spark.get("/comunidades/:id", controllerComunidades::mostrarIncidentes, engine);
    Spark.get("/incidentes/:id", controllerIncidentes::verDetalleIncidente, engine);
    Spark.post("/incidentes", controllerIncidentes::cerrarIncidente);

    //USUARIOS

    Spark.get("/admin/usuarios/:id", controllerUsuarios::mostrarDetalleUsuario, engine);
    Spark.post("/admin/usuarios/:id", controllerUsuarios::eliminarUsuario);
    Spark.get("/profile", controllerUsuarios::mostrarPerfil, engine);
    Spark.get("/profile/modificacion", controllerUsuarios::modificarPerfil, engine);
    Spark.post("/profile/modificacion", controllerUsuarios::modificarUsuario, engine);
    Spark.get("/profile/modificacion/horarios",controllerUsuarios::modificarHorarios,engine);
    Spark.post("/profile/modificacion/horarios",controllerUsuarios::modificarHorariosNot,engine);
    Spark.post("/profile/modificacion/horario", controllerUsuarios::borrarHorario, engine);


    //INCIDENTES SUGERIDOS
    Spark.get("/incidentesSugeridos", controllerIncidentes::mostrarIncidentesSugeridosPaginados, engine);

    //ADMINISTRAR COMUNIDADES  
    Spark.get("/admin/comunidades", controllerAdmin::verComunidadesAdministrables, engine);
    Spark.get("/admin/comunidades/:id/miembros", controllerAdmin::verMiembros, engine);
    Spark.post("/admin/comunidades/:id/miembros", controllerAdmin::eliminarMiembro);





    //---------------------------------------API COMUNIDADES-------------------------------------//



    Gson gson = new Gson();

    //NO PUEDO PASAR LA RESPONSE A JSON

    //VER COMUNIDADES
    Spark.get("/api/admin/comunidades",((request, response) -> {

      try{
        String nombreusuario = request.headers("usuario");
        String contrasenia = request.headers("contrasenia");
        Usuario usuario = RepoUsuarios.getInstance().buscarPorUsuarioYContrasenia(
            nombreusuario,
            contrasenia);
        if (usuario.permisoUsuario.equals(PermisoUsuario.ADMIN)){
          List<Comunidad> comunidades = RepositorioComunidades.getInstance().getAll();
          System.out.println("entra");
          return comunidades;
        }
        else{
          response.status(401);
          return null;
        }
      } catch (Exception e) {
        response.status(401);
        return null;
      }
    }));

    //VER DETALLE DE UNA COMUNIDAD
    Spark.get("/api/admin/comunidades/:id",((request, response) -> {

      try{
        String nombreusuario = request.headers("usuario");
        String contrasenia = request.headers("contrasenia");
        Usuario usuario = RepoUsuarios.getInstance().buscarPorUsuarioYContrasenia(
            nombreusuario,
            contrasenia);
        if (usuario.permisoUsuario.equals(PermisoUsuario.ADMIN)){
          String id = request.params(":id");
          Comunidad comunidad = RepositorioComunidades.getInstance().getOne(Long.parseLong(id));
          return comunidad;
        }
        else{
          response.status(401);
          return null;
        }
      } catch (Exception e) {
        response.status(401);
        return null;
      }
    }));

    //BORRAR UNA COMUNIDAD
    Spark.delete("/api/admin/comunidades/:id",((request, response) -> {

      try{
        String nombreusuario = request.headers("usuario");
        String contrasenia = request.headers("contrasenia");
        Usuario usuario = RepoUsuarios.getInstance().buscarPorUsuarioYContrasenia(
            nombreusuario,
            contrasenia);
        if (usuario.permisoUsuario.equals(PermisoUsuario.ADMIN)){
          String id = request.params(":id");
          RepositorioComunidades repo = RepositorioComunidades.getInstance();
          Comunidad comunidad = repo.getOne(Long.parseLong(id));
          withTransaction(() -> {
            repo.remove(comunidad);
          });
          return null; // funca pero tira 404 idk
        }
        else{
          response.status(401);
          return null;
        }
      } catch (Exception e) {
        response.status(401);
        return null;
      }
    }));

    //CREAR UNA COMUNIDAD
    Spark.post("/api/admin/comunidades",((request, response) -> {

      try{
        String nombreusuario = request.headers("usuario");
        String contrasenia = request.headers("contrasenia");
        Usuario usuario = RepoUsuarios.getInstance().buscarPorUsuarioYContrasenia(
            nombreusuario,
            contrasenia);
        if (usuario.permisoUsuario.equals(PermisoUsuario.ADMIN)){
          String nombre = request.headers("nombreComunidad");
          Comunidad comunidad1 = new Comunidad(nombre);
          withTransaction(() -> {
            RepositorioComunidades.getInstance().add(comunidad1);
          });
          return null; // funca pero tira 404 idk
        }
        else{
          response.status(401);
          return null;
        }
      } catch (Exception e) {
        response.status(401);
        return null;
      }
    }));

    //EDITAR UNA COMUNIDAD
    Spark.put("/api/admin/comunidades/:id",((request, response) -> {

      try{
        String nombreusuario = request.headers("usuario");
        String contrasenia = request.headers("contrasenia");
        Usuario usuario = RepoUsuarios.getInstance().buscarPorUsuarioYContrasenia(
            nombreusuario,
            contrasenia);
        if (usuario.permisoUsuario.equals(PermisoUsuario.ADMIN)){
          String id = request.params(":id");
          RepositorioComunidades repo = RepositorioComunidades.getInstance();
          Comunidad comunidad = repo.getOne(Long.parseLong(id));
          String nombre = request.headers("nombreComunidad");
          comunidad.setNombre(nombre);
          withTransaction(() -> {
            repo.update(comunidad);
          });
          return null; // funca pero tira 404 idk
        }
        else{
          response.status(401);
          return null;
        }
      } catch (Exception e) {
        response.status(401);
        return null;
      }
    }));


    //----------------------------------------------------------------------------------------------//


    Spark.exception(PersistenceException.class, (e, request, response) -> {
      response.redirect("/500");
    });

    Spark.before((request, response) -> {
      entityManager().clear();
    });
  }


  static int getHerokuAssignedPort() {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get("PORT") != null) {
      return Integer.parseInt(processBuilder.environment().get("PORT"));
    }
    return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
  }

}
