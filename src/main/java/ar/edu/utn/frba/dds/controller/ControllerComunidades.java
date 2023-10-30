package ar.edu.utn.frba.dds.controller;


import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.entidades_y_servicios.Establecimiento;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.text.CompactNumberFormat;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.*;
import ar.edu.utn.frba.dds.repositorios.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ControllerComunidades implements WithSimplePersistenceUnit {

  public ModelAndView mostrarComunidades(Request request, Response response) {

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    List<Comunidad> comunidades = RepositorioComunidades.getInstance().listarComunidades();
    modelo.put("comunidades", comunidades);
    return new ModelAndView(modelo, "comunidades.html.hbs");
  }

  public ModelAndView mostrarIncidentes(Request request, Response response) {

    String id = request.params(":id");
    Comunidad com = entityManager().find(Comunidad.class,Long.parseLong(id));
    Comunidad comunidad = RepositorioComunidades.instance.buscarPorId(Long.parseLong(id));
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    List<Incidente> incidentesAbiertos = comunidad.incidentes;
    System.out.println(comunidad.incidentes.size());
    System.out.println(com.incidentes.size());
    List<Incidente> incidentesCerrados = comunidad.incidentesCerrados;
    modelo.put("incidentesAbiertos", incidentesAbiertos);
    modelo.put("incidentesCerrados", incidentesCerrados);
    return new ModelAndView(modelo, "incidentes.html.hbs");
  }

}
