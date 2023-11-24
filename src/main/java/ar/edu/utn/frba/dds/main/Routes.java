package ar.edu.utn.frba.dds.main;


import ar.edu.utn.frba.dds.NotificadorProgramado;
import ar.edu.utn.frba.dds.controller.*;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
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
    ControllerMenuRankings controllerMenuRanking = new ControllerMenuRankings();
    ControllerRankingCantidadReportes controllerRankingCantidadReportes = new ControllerRankingCantidadReportes();
    ControllerRankingPromedioCierres controllerRankingPromedioCierres = new ControllerRankingPromedioCierres();

    ControllerListadoRanking controllerListadoRanking = new ControllerListadoRanking();

    ControllerUsuarios controllerUsuarios = new ControllerUsuarios();

    //LOGIN Y HOME
    Spark.get("/", demoControllerhome::mostrarInicio, engine);
    Spark.get("/login", controllerLogin::mostrarLogin, engine);
    Spark.post("/login", controllerLogin::crearSesion, engine);
    Spark.get("/sesion", controllerLogin::cerrarSesion);
    Spark.get("/home", demoControllerhome::mostrarHome, engine);
    Spark.get("/admin-home", demoControllerhome::mostrarHomeAdmin, engine);
    Spark.post("/admin-home", controllerUsuarios::crearUsuario, engine);

    //APERTURA DE INCIDENTES
    Spark.get("/entidades", controllerEntidades::mostrarEntidades, engine);
    Spark.get("/entidades/:id", controllerEstablecimientos::mostrarEstablecimientos, engine);
    Spark.get("/establecimientos/:id", controllerEstablecimientos::cargarIncidente, engine);
    Spark.post("/establecimientos/:id", controllerEstablecimientos::crearIncidente);

    //VISUALIZACION Y CIERRE DE INCIDENTES
    Spark.get("/comunidades", controllerComunidades::mostrarComunidades, engine);
    Spark.get("/comunidades/:id", controllerComunidades::mostrarIncidentes, engine);
    Spark.post("/comunidades/:id", controllerIncidentes::cerrarIncidente);
    Spark.post("/incidente/:id", controllerIncidentes::cerrarIncidente);

    //RANKINGS
    Spark.get("/rankings/:id", controllerListadoRanking::mostrarListaDeRanking, engine);

    //USUARIOS

    Spark.get("/usuarios/:id", controllerUsuarios::mostrarDetalleUsuario, engine);
    Spark.post("/usuarios/:id", controllerUsuarios::eliminarUsuario);
    Spark.get("/profile", controllerUsuarios::mostrarPerfil, engine);
    Spark.get("/profile/modificacion", controllerUsuarios::modificarPerfil, engine);
    Spark.post("/profile/modificacion", controllerUsuarios::modificarUsuario, engine);


    //INCIDENTES SUGERIDOS
    Spark.get("/incidente-sugerido", controllerIncidentes::mostrarIncidentesSugeridos, engine);

    //ADMINISTRAR COMUNIDADES  
    Spark.get("/admin-comunidades", controllerComunidades::verComunidadesAdministrables, engine);
    Spark.get("/admin-comunidades/:id/miembros", controllerComunidades::verMiembros, engine);
    Spark.post("/admin-comunidades/:id/miembros", controllerComunidades::eliminarMiembro);

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
