package ar.edu.utn.frba.dds.main;


import ar.edu.utn.frba.dds.controller.*;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
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



    //Spark.get("/", (request, response) -> "¡Hola mundo!");

    Spark.get("/", demoControllerhome::mostrarInicio, engine);
    Spark.get("/login", controllerLogin::mostrarLogin, engine);
    Spark.post("/login", controllerLogin::crearSesion);
    Spark.get("/login-error", controllerLogin::mostrarLoginError, engine);
    Spark.post("/login-error", controllerLogin::crearSesion);
    //Spark.get("/", demoControllerhome::mostrarInicio, engine);
    //Spark.get("/", demoControllerhome::mostrarInicio, engine);
    //Spark.get("/", demoControllerhome::mostrarInicio, engine);


    Spark.exception(PersistenceException.class, (e, request, response) -> {
      response.redirect("/500"); //TODO
    });

    Spark.before((request, response) -> {
      entityManager().clear();
    });
  }


}
