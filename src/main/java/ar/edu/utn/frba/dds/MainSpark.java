package ar.edu.utn.frba.dds;

import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;


public class MainSpark {
  public static void main(String[] args) {
    Spark.port(9000);

    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

    Spark.get("/", (request, response) -> "¡Hola mundo!");
  }
}
