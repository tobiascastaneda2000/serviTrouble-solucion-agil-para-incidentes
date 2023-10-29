package ar.edu.utn.frba.dds.main;


import ar.edu.utn.frba.dds.controller.*;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
import spark.Session;
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
    //DemoController demoController = new DemoController();
    DemoControllerHome demoControllerhome = new DemoControllerHome();
    ControllerLogin controllerLogin = new ControllerLogin();
    ControllerEntidades controllerEntidades = new ControllerEntidades();
    ControllerEstablecimientos controllerEstablecimientos = new ControllerEstablecimientos();


    Spark.get("/", demoControllerhome::mostrarInicio, engine);
    Spark.get("/login", controllerLogin::mostrarLogin, engine);
    Spark.post("/login", controllerLogin::crearSesion);
    Spark.get("/login-error", controllerLogin::mostrarLoginError, engine);
    Spark.post("/login-error", controllerLogin::crearSesion);
    Spark.get("/home", demoControllerhome::mostrarHome, engine);
    Spark.get("/entidades", controllerEntidades::mostrarEntidades, engine);
    Spark.get("/entidades/:id", controllerEstablecimientos::mostrarEstablecimientos, engine);
    Spark.get("/establecimientos/:id", controllerEstablecimientos::cargarIncidente, engine);
    Spark.post("/establecimientos/:id", controllerEstablecimientos::crearIncidente, engine);

    Spark.exception(PersistenceException.class, (e, request, response) -> {
      response.redirect("/500");
    });

    Spark.before((request, response) -> {
      entityManager().clear();
    });
  }


}
