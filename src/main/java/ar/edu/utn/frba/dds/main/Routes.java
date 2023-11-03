package ar.edu.utn.frba.dds.main;


import ar.edu.utn.frba.dds.MainTareasPlanificadas;
import ar.edu.utn.frba.dds.controller.*;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.jboss.jandex.Main;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import javax.persistence.PersistenceException;

public class Routes implements WithSimplePersistenceUnit {

  public static void main(String[] args) {
    new Routes().start();
  }

  public void start() {
    System.out.println("Iniciando servidor");

    new Bootstrap().run();
    Spark.port(8080);
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
    Spark.post("/login", controllerLogin::crearSesion);
    Spark.get("/login-error", controllerLogin::mostrarLoginError, engine);
    Spark.post("/login-error", controllerLogin::crearSesion);
    Spark.get("/cerrar-sesion", controllerLogin::cerrarSesion);
    Spark.get("/home", demoControllerhome::mostrarHome, engine);

    //APERTURA DE INCIDENTES
    Spark.get("/entidades", controllerEntidades::mostrarEntidades, engine);
    Spark.get("/entidades/:id", controllerEstablecimientos::mostrarEstablecimientos, engine);
    Spark.get("/establecimientos/:id", controllerEstablecimientos::cargarIncidente, engine);
    Spark.post("/establecimientos/:id", controllerEstablecimientos::crearIncidente);
    Spark.get("/incidente-creado", controllerIncidentes::mostrarIncidenteCreado, engine);
    Spark.get("/Incidente-No-Creado", controllerIncidentes::errorIncidente, engine);

    //VISUALIZACION Y CIERRE DE INCIDENTES
    Spark.get("/comunidades", controllerComunidades::mostrarComunidades, engine);
    Spark.get("/comunidades/:id", controllerComunidades::mostrarIncidentes, engine);
    Spark.get("/incidente-cerrado/:id", controllerIncidentes::verDetalleIncidenteCerrado, engine);
    Spark.get("/incidente/:id", controllerIncidentes::verDetalle, engine);
    Spark.post("/incidente/:id", controllerIncidentes::cerrarIncidente);

    //RANKINGS
    Spark.get("/rankings", controllerMenuRanking::mostrarTodosRankings, engine);
    //Spark.get("/rankings/cantidad-reportes", controllerRankingCantidadReportes::mostrarRankingCantidadReportes, engine);
    //Spark.get("/rankings/promedio-cierres", controllerRankingPromedioCierres::mostrarRankingPromedioCierre, engine);

    Spark.get("/rankings/:id", controllerListadoRanking::mostrarListaDeRanking, engine);

    //USUARIOS
    Spark.get("/usuarios", controllerUsuarios::mostrarUsuarios, engine);
    Spark.post("/usuarios", controllerUsuarios::crearUsuario, engine);
    Spark.get("/usuarios/:id", controllerUsuarios::mostrarDetalleUsuario, engine);
    Spark.post("/usuarios/:id", controllerUsuarios::eliminarUsuario);

    //INCIDENTES SUGERIDOS
    Spark.get("/incidente-sugerido", controllerIncidentes::mostrarIncidentesSugeridos,engine);

    //ADMINISTRAR COMUNIDADES  
    Spark.get("/admin-comunidades", controllerComunidades::verComunidadesAdministrables, engine);
    Spark.get("/administrar-comunidad/:id", controllerComunidades::verComunidadAdministrable, engine);
    Spark.get("/administrar-comunidad/:id/miembros", controllerComunidades::verMiembros, engine);
    Spark.post("/administrar-comunidad/:id/miembros",controllerComunidades::eliminarMiembro);

    Spark.exception(PersistenceException.class, (e, request, response) -> {
      response.redirect("/500");
    });

    Spark.before((request, response) -> {
      entityManager().clear();
    });
  }


}
